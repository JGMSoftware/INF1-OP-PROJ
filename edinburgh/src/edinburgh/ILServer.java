package edinburgh;

import java.io.*;
import java.net.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.Calendar;

public class ILServer implements Runnable {

    private static List<ILServer> users = new ArrayList();
    private static HashMap<InetAddress, String> usernames = new HashMap();
    ObjectOutputStream out;
    ObjectInputStream in;
    private Socket connection;
    private InetAddress address;
    private SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
    private static String timestamp;
    private static Calendar time;

    public ILServer(Socket socket, InetAddress address) throws IOException {
        this.connection = socket;
        this.address = socket.getInetAddress();
        this.out = new ObjectOutputStream(connection.getOutputStream());
    }

    public void run() {
        try {
            in = new ObjectInputStream(connection.getInputStream());
        } catch (IOException ex) {
            Logger.getLogger(ILServer.class.getName()).log(Level.SEVERE, null, ex);
            return;
        }

        boolean going = true;
        MessageObj incoming;

        while (going) {
            try {
                incoming = (MessageObj) in.readObject();
                takeAction(incoming, address, out);
                if (incoming.getMsgType() == 2) {
                    in.close();
                    out.close();
                    connection.close();
                    going = false;
                    return;
                }
            } catch (IOException ex) {
                Logger.getLogger(ILServer.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(ILServer.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private synchronized void takeAction(MessageObj mess, InetAddress address, ObjectOutputStream out) {

        switch (mess.getMsgType()) {
            //User asked for list of users
            case 0:
                returnUsers(out);
                break;
            //User sent a message
            case 1:
                sendMessage(mess);
                break;
            //User logged out
            case 2:
                removeUser(address);
                break;
            //User changed username
            case 3:
                setUsername(mess, address);
                break;
            //User logged in
            case 4:
                setUsername(mess, address);
                sendLoginMessage(mess);
                break;
        }
    }

    private void sendLoginMessage(MessageObj msg) {
        time = Calendar.getInstance();
        timestamp = timeFormat.format(time.getTime());
        MessageObj loginMsg = new MessageObj(1, msg.getSender() + " has just logged in.", timestamp, "System");
        sendMessage(loginMsg);
    }

    private void returnUsers(ObjectOutputStream out) {
        MessageObj returnMsg;
        String userlist = "The following users are currently logged in: \n";
        for (ILServer s : users) {
            userlist += usernames.get(s.address) + "\n";
        }
        time = Calendar.getInstance();
        timestamp = timeFormat.format(time.getTime());
        returnMsg = new MessageObj(1, userlist, timestamp, "System");
        try {
            out.writeObject(returnMsg);
        } catch (IOException ex) {
            Logger.getLogger(ILServer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void setUsername(MessageObj msg, InetAddress address) {
        usernames.put(address, msg.getSender());
    }

    private synchronized void sendMessage(MessageObj msg) {
        for (int i = 0; i < users.size(); i++) {
            try {
                ((ILServer) users.get(i)).out.writeObject(msg);
                ((ILServer) users.get(i)).out.flush();
                System.out.println(msg.makeString());
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }

    }

    private void removeUser(InetAddress address) {
        for (int i = 0; i < users.size(); i++) {
            if (address == ((ILServer) users.get(i)).address) {
                usernames.remove(users.get(i).address);
                users.remove(i);
            }
        }
    }

    public static void main(String[] args) {
        int created = 0;

        //Try to create a network socket that the clients can connect to
        try {
            ServerSocket socket = new ServerSocket(2004);

            //Run an infinite loop that will accept a connection and create a thread to handle that connection
            while (true) {

                System.out.println("Waiting for connection");

                Socket acceptSocket = socket.accept();

                ILServer server = new ILServer((acceptSocket), acceptSocket.getInetAddress());
                Thread t = new Thread(server);
                t.start();

                users.add(server);

                //Writes a message to the terminal telling the person running the server about a new connection
                created++;
                System.out.println("New thread created for client from adress: " + acceptSocket.getInetAddress());
                System.out.println("There have been " + created + " threads created during this session.");
            }
        } //If we are not allowed access to the socket
        catch (IOException crash) {
            System.out.println("The server crashed due to the following exception: " + crash);
        }
    }
}

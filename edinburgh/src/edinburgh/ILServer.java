package edinburgh;

import java.io.*;
import java.net.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ILServer implements Runnable {

    private static List users = new ArrayList();
    ObjectOutputStream out;
    ObjectInputStream in;
    private Socket connection;
    private InetAddress address;

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
                takeAction(incoming);
            } catch (IOException ex) {
                Logger.getLogger(ILServer.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(ILServer.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private synchronized void takeAction(MessageObj mess) {


        switch (mess.getMsgType()) {
            case 1:
                sendMessage(mess);
//            case 2:
//                removeUser(uSocket);
        }
    }

    private void sendMessage(MessageObj msg) {
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

    private void removeUser(Socket socket) {
        for (int i = 0; i < users.size(); i++) {
            if (socket == users.get(i)) {
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

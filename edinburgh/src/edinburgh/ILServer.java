package edinburgh;

import java.io.*;
import java.net.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ILServer implements Runnable {

    private static List users = new ArrayList();
    static ObjectOutputStream out;
    ObjectInputStream in;
    private Socket connection;
    private InetAddress address;

    public ILServer(Socket socket, InetAddress address) {
        this.connection = socket;
        this.address = address;
    }

    public void run() {
        try {
            out = new ObjectOutputStream(connection.getOutputStream());
            out.flush();
            in = new ObjectInputStream(connection.getInputStream());
            //4. The two parts communicate via the input and output streams
            try {
                MessageObj message;
                message = (MessageObj) (in.readObject());
                //String statistics = WordCounter.countWords(message);
                takeAction(message, address);
                // sendMessage(statistics);
            } catch (ClassNotFoundException classnot) {
                System.err.println("Data received in unknown format");
            } catch (IOException ioException) {
                ioException.printStackTrace();
            } 
    
            finally {
                //4: Closing connection
                try {
                    in.close();
                    out.close();
                    connection.close();
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }

        } catch (IOException ex) {
            Logger.getLogger(ILServer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private synchronized void takeAction(MessageObj mess, InetAddress address) {
        switch (mess.getMsgType()) {
            case 1:
                sendMessage(mess.makeString());
                System.out.println(mess.makeString());
            case 2:
                removeUser(address);
        }
    }

    private void sendMessage(String msg) {
        for (int i = 0; i < users.size(); i++) {
            try {
                ObjectOutputStream output = new ObjectOutputStream(((Socket) users.get(i)).getOutputStream());
                output.writeObject(msg);
                out.flush();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }

    }

    private static void removeUser(InetAddress address) {
        for (int i = 0; i < users.size(); i++) {
            if (address == users.get(i)) {
                users.remove(i);
            }
        }
    }

    public static void main(String[] args) {
        //This list will be used to store the addresses of all users

        int created = 0;

        //Try to create a network socket that the clients can connect to
        try {
            ServerSocket socket = new ServerSocket(2004);

            //Run an infinite loop that will accept a connection and create a thread to handle that connection
            while (true) {

                System.out.println("Waiting for connection");

                Socket acceptSocket = socket.accept();

                // latestConnector = acceptSocket.getInetAddress();
                Thread t = new Thread(new ILServer((acceptSocket), acceptSocket.getInetAddress()));
                t.start();

                users.add(acceptSocket);

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

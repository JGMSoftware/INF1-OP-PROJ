package edinburgh;

import java.io.*;
import java.net.*;
import java.text.SimpleDateFormat;
import java.util.*;


public class ILServer implements Runnable {
    
    private static List<InetAddress> users = new ArrayList<>();
    private Socket socket;
    private InetAddress address;
   
    public ILServer(Socket socket, InetAddress address){
        this.socket = socket;
        this.address = address;
    }
    
    public void run(){
        
        
        
        /* While the user is logged on
        
            Listen for incoming messages
        
            Take the string and use makeString to transform it into a string
        
            Iterate over the list of users to send the string with the message to everyone
         */
        
        broadcast(makeString(message)); 
        
        //Execute when the user terminates the connection
        remover: for (int k=0; k<users.size(); k++)
            if (users.get(k) == clientId){
                users.remove(k);
                break remover;
            }
    }
    
    private static synchronized void broadcast(String s){
        //Iterate over users and send s
    }
    
    private static String makeString(MessageObj msg){
        String message = msg.getTimestamp() + "[" + msg.getSender() + "] " + msg.getMsgText() ;
        return message;
    }
    
    public static void main(String[]args){
        //This list will be used to store the addresses of all users
        
        int created = 0;
        
        //Try to create a network socket that the clients can connect to
        try{
           ServerSocket socket = new ServerSocket(2004);
           
           //Run an infinite loop that will accept a connection and create a thread to handle that connection
           while (true){
               
               Socket acceptSocket = socket.accept();
               
               Thread t = new Thread (new ILServer( acceptSocket, acceptSocket.getInetAddress()));
               t.start();
               
               users.add(acceptSocket.getInetAddress());
               
               //Writes a message to the terminal telling the person running the server about a new connection
               created++;
               System.out.println("New thread created for client from adress: " + acceptSocket.getInetAddress());
               System.out.println("There have been " + created + " threads created during this session.");
           }
        }
        //If we are not allowed access to the socket
        catch(IOException crash){
            System.out.println("The server crashed due to the following exception: " + crash);
        }
    }
 
}

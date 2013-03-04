package edinburgh;

import java.io.*;
import java.net.*;
import java.text.SimpleDateFormat;
import java.util.*;


public class ILServer implements Runnable {

  private static List users = new ArrayList();
	ObjectOutputStream out;
	ObjectInputStream in;
	private Socket connection;
	private InetAddress address;

	public ILServer(Socket socket, InetAddress address){
		this.connection = socket;
		this.address = address;
	}

	
	
	public void run(){
		out = new ObjectOutputStream(connection.getOutputStream());
		out.flush();
		in = new ObjectInputStream(connection.getInputStream());
		sendMessage("Connection successful");
		//4. The two parts communicate via the input and output streams
		do{
			try{
				MessageObj message;
				message = (MessageObj)(in.readObject());
				//String statistics = WordCounter.countWords(message);
				System.out.println("client>" + message);
				if (message.equals("bye"))
			//		sendMessage(statistics);
			}
			catch(ClassNotFoundException classnot){
				System.err.println("Data received in unknown format");
			}
		}while(!message.equals("bye"));
	}
	catch(IOException ioException){
		ioException.printStackTrace();
	}
	finally{
		//4: Closing connection
		try{
			in.close();
			out.close();
			providerSocket.close();
		}
		catch(IOException ioException){
			ioException.printStackTrace();
		}
	}
}
void sendMessage(String msg)
{
	for(int i = 0; i<users.size(); i++){
		try{
			ObjectOutputStream output=new ObjectOutputStream(((Socket)users.get(i)).getOutputStream());
			output.writeObject(msg);
			out.flush();
			//System.out.println("server>" + msg);
		}
		catch(IOException ioException){
			ioException.printStackTrace();
		}
	}

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

			//   latestConnector = acceptSocket.getInetAddress();
			Thread t = new Thread (new ILServer((acceptSocket), acceptSocket.getInetAddress()));
			t.start();

			users.add(acceptSocket);

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

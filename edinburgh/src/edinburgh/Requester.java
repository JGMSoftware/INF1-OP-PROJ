/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edinburgh;

/**
 *
 * @author s1248624
 */

import java.io.*;
import java.net.*;

public class Requester{
  Socket requestSocket;
	ObjectOutputStream out;
 	ObjectInputStream in;
 	MessageObj message;
	Requester(){}
	void run()
	{
		try{
			//1. creating a socket to connect to the server
			requestSocket = new Socket("tarsan", 2004);
			System.out.println("Connected to localhost in port 2004");
			//2. get Input and Output streams
			out = new ObjectOutputStream(requestSocket.getOutputStream());
			out.flush();
			in = new ObjectInputStream(requestSocket.getInputStream());
			//3: Communicating with the server
			do{
				
                                        message = new MessageObj(1, "Hello Server","12.00.00","freddy" );
					sendMessage(message);
				
				
			}while(true);
                        
		}
		catch(UnknownHostException unknownHost){
			System.err.println("You are trying to connect to an unknown host!");
		}
		catch(IOException ioException){
			ioException.printStackTrace();
		}
		finally{
			//4: Closing connection
			try{
				in.close();
				out.close();
				requestSocket.close();
			}
			catch(IOException ioException){
				ioException.printStackTrace();
			}
		}
	}
	void sendMessage(MessageObj msg)
	{
		try{
			out.writeObject(msg);
			out.flush();
			System.out.println("client>" + msg.makeString());
		}
		catch(IOException ioException){
			ioException.printStackTrace();
		}
	}
	public static void main(String args[])
	{
		Requester client = new Requester();
		client.run();
	}
}

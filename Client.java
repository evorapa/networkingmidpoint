import java.net.*;
import java.io.*;
import java.nio.*;
import java.nio.channels.*;
import java.util.*;

public class Client implements Runnable{
	Socket requestSocket;           //socket connect to the server
	ObjectOutputStream out;         //stream write to the socket
 	ObjectInputStream in;          //stream read from the socket
	String message;                //message send to the server
	String MESSAGE;                //capitalized message read from the server

	public void Client() {}

	public void run()
	{
		try{
			//create a socket to connect to the server
			requestSocket = new Socket("localhost", 9000);
			System.out.println("Connected to localhost in port 9000");
			//initialize inputStream and outputStream
			out = new ObjectOutputStream(requestSocket.getOutputStream());
			out.flush();
			in = new ObjectInputStream(requestSocket.getInputStream());
			
			// //get Input from standard input
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
			while(true)
			{
				System.out.print("Hello, please input a sentence: ");
			// 	//read a sentence from the standard input
				message = bufferedReader.readLine();
			// 	//Send the sentence to the server
				sendMessage(message);
				//Receive the upperCase sentence from the server
		BufferedReader in =
        	new BufferedReader(
            	new InputStreamReader(requestSocket.getInputStream()));

				MESSAGE = (String)in.readLine();
				//show the message to the user
				System.out.println("Receive message: " + MESSAGE);
		 	}
		 }
		 catch (ConnectException e) {
    			System.err.println("Connection refused. You need to initiate a server first.");
		} 

		catch(UnknownHostException unknownHost){
			System.err.println("You are trying to connect to an unknown host!");
		}
		catch(IOException ioException){
			ioException.printStackTrace();
		}
		finally{
			//Close connections
			try{
				requestSocket.close();
			}
			catch(IOException ioException){
				ioException.printStackTrace();
			}
		}
	}
	//send a message to the output stream
	void sendMessage(String msg)
	{
		try{
			//stream write the message
			out.writeObject(msg);
			out.flush();
		}
		catch(IOException ioException){
			ioException.printStackTrace();
		}
	}
	//main method
	public static void main(String args[])
	{
		Client client = new Client();
		client.run();
	}

}

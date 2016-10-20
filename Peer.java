import java.net.*;
import java.io.*;
import java.nio.*;
import java.nio.channels.*;
import java.util.*;

public class Peer {

	public Peer(int port)
	{
		ThreadPooledServer server = new ThreadPooledServer(port); //receiving
        
        Random r = new Random();
        int clientID = r.nextInt(5);
        Client client = new Client(clientID);

        System.out.println("Server started.");
        System.out.println("client id: "+clientID);

        new Thread(server).start();
        new Thread(client).start();
	}




}
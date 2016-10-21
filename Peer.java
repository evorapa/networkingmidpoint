import java.net.*;
import java.io.*;
import java.nio.*;
import java.nio.channels.*;
import java.util.*;
import java.util.concurrent.*;

public class Peer implements Runnable{

        // Client client;
        // ThreadPooledServer server;

        private Config config;
        private int thisID;

	public Peer(int thisID){
		this.thisID = thisID;
                try{
                        this.config = new Config("PeerInfo.cfg");
                }catch(Exception e){
                        e.printStackTrace();
                }
                
        }

        public void run(){
                try{
                        int myIndex = -1;
                        //loop through config file
                        for(int i = 0; i<config.getNumPeers(); i++){
                                //idk what myindex does
                                myIndex++;
                                //if the peer is me
                                if(config.getIDs().get(i)==thisID){
                                        System.out.println(thisID + "we broke");
                                        break;
                                }
                                //connect to the peer
                                System.out.println(thisID +" About to connect to peer ");
                                Socket socket1 = new Socket(config.getAddresses().get(i), config.getDownloadPort(i));           

                                //send the message to the peer
                                //write to socket 1
                                OutputStream outstream = socket1.getOutputStream(); 
                                PrintWriter out = new PrintWriter(outstream);

                                String toSend = " I, " + thisID + " just wanted to say hello\n";

                                out.print(toSend );
                                out.flush();
                                System.out.println(thisID + " Sent string");

                                //now read from the socket
                                // InputStream is1 = socket1.getInputStream();
                                // System.out.println(thisID + " got input stream");
                                // int read1;
                                // byte[] buffer1 = new byte[1024];
                                // System.out.println("About to read");
                                // read1= is1.read(buffer1);
                                // System.out.println("just read");
                                // System.out.println(read1);
                                // //while(true) {
                                // while(read1 != -1) {
                                //         System.out.println(thisID +" messin with buffer");
                                //         String output1 = new String(buffer1, 0, read1);
                                //         System.out.print(thisID + output1);
                                //         System.out.flush();
                                // };
                                //System.out.println(thisID + " about to close the socket");
                                //socket1.close();

                        }
                        //make sockets for listening
                        ServerSocket downloadServSoc = null;
                        ServerSocket uploadServSoc = null;
                        ServerSocket haveServSoc = null;
                        if (myIndex != config.getNumPeers()-1) {
                                
        //                              System.out.println("peerProcess: 243: peer " + this.myID +  " starts to listern to the port");
                                //open these ports and listen
                                System.out.println(thisID + " About to open port" + config.getDownloadPort(myIndex));
                                downloadServSoc = new ServerSocket(config.getDownloadPort(myIndex));
                                
                                for (int i = myIndex; i < config.getNumPeers()-1; i++) {
                                        //getInitialization(i, downloadServSoc, uploadServSoc, haveServSoc);
                                        //instead read the socket for the hello message
                                        //try:
                                        System.out.println(thisID +" About accept connection");
                                        Socket downloadSoc = downloadServSoc.accept();
                                        System.out.println(thisID +" accepted connection");
                                        InputStream is = downloadSoc.getInputStream();
                                        int read;
                                        byte[] buffer = new byte[1024];
                                        while((read = is.read(buffer)) != -1) {
                                                System.out.println(thisID +" messin with buffer");
                                                String output = new String(buffer, 0, read);
                                                System.out.print(thisID + output);
                                                System.out.flush();
                                                break;
                                        };
                                        System.out.println("OUTSIDE OF WHILE LOOP");
                                        //write to socket 1
                                        // OutputStream outstream = downloadSoc.getOutputStream(); 
                                        // PrintWriter out = new PrintWriter(outstream);

                                        // String toSend = "I just wanted to say hello";

                                        // out.print(toSend );
                                        // out.flush();
                                        // System.out.println(thisID + "Sent string");

                                        //downloadServSoc.close();
                                }
        //                              System.out.println("peerProcess: 251: peer " + this.myID + " finishes getInitialization");
                        }

                
                        if (myIndex != config.getNumPeers() - 1) {
                                downloadServSoc.close();
                        } 

                }catch (Exception e) {
                        e.printStackTrace();
                }
        }

        //         ThreadPooledServer server = new ThreadPooledServer(port); //receiving
        
        // Random r = new Random();
        // int clientID = r.nextInt(5);
        // Client client = new Client(clientID);

        // System.out.println("Server started.");
        // System.out.println("client id: "+clientID);
        // }
}
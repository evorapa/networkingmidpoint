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
                        int numParsedPeers = -1;
                        //loop through config file
                        for(int i = 0; i<config.getNumPeers(); i++){
                                
                                numParsedPeers++;
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


                        }
                        //make sockets for listening
                        ServerSocket downloadServSoc = null;
                        if (numParsedPeers != config.getNumPeers()-1) {
                                
                                //open these ports and listen
                                System.out.println(thisID + " About to open port" + config.getDownloadPort(numParsedPeers));
                                downloadServSoc = new ServerSocket(config.getDownloadPort(numParsedPeers));
                                
                                for (int i = numParsedPeers; i < config.getNumPeers()-1; i++) {

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

                                }

                        }

                
                        if (numParsedPeers != config.getNumPeers() - 1) {
                                downloadServSoc.close();
                        } 

                }catch (Exception e) {
                        e.printStackTrace();
                }
        }


}
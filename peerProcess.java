import java.net.*;
import java.io.*;
import java.nio.*;
import java.nio.channels.*;
import java.util.*;
import java.util.concurrent.*;

public class peerProcess implements Runnable{

        private Config config;
        private int thisID;

	public peerProcess(int thisID){
		//peer ID was passed in
                this.thisID = thisID;
                //set config file location
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
                        //we connect to peers that are already listening to connections
                        //makes TCP connections to all the peers started before it
                        //so the first peer will not connect to anything, it will just start listening for incoming conns
                        for(int i = 0; i<config.getNumPeers(); i++){
                                
                                numParsedPeers++;
                                //if the peer is me
                                if(config.getIDs().get(i)==thisID){
                                        //break so that we don't try to connect to ourselves
                                        System.out.println(thisID + " we broke");
                                        break;
                                        //this also means we aren't going to connect to any of the peers listed below our own entry
                                        //but they might connect to us
                                }
                                //connect to the peer
                                System.out.println(thisID +" About to connect to peer ");
                                Socket socket1 = new Socket(config.getAddresses().get(i), config.getDownloadPorts().get(i));           

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
                        ServerSocket serverSocket2 = null;

                        //if we aren't the last peer
                        if (numParsedPeers != config.getNumPeers()-1) {
                                
                                //open these ports and listen
                                System.out.println(thisID + " About to open port" + config.getDownloadPorts().get(numParsedPeers));
                                serverSocket2 = new ServerSocket(config.getDownloadPorts().get(numParsedPeers));
                                
                                //once a peer connects to a socket, they will send a hello message 
                                //read the data in each socket after there has been a connection
                                for (int i = numParsedPeers; i < config.getNumPeers()-1; i++) {
                                        //accept an incoming connection
                                        System.out.println(thisID +" About accept connection");
                                        Socket socket2 = serverSocket2.accept();
                                        System.out.println(thisID +" accepted connection");
                                        InputStream is = socket2.getInputStream();
                                        int read;
                                        //create a buffer to read the string that has been recieved
                                        byte[] buffer = new byte[1024];
                                        read = is.read(buffer);
                                        String output = new String(buffer, 0, read);
                                        System.out.print(thisID + output);
                                        System.out.flush();
                                }
                        }

                }catch (Exception e) {
                        e.printStackTrace();
                }
        }
}
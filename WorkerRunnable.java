import java.io.InputStream;
import java.io.OutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.*;
import java.io.*;
import java.nio.*;
import java.nio.channels.*;
import java.util.*;

//contains server code meant to be executed in a thread.
public class WorkerRunnable implements Runnable{

    private String message;    //message received from the client
    private String MESSAGE;    //uppercase message send to the client
    private ObjectInputStream in;   //stream read from the socket
    private ObjectOutputStream out;    //stream write to the socket
    private int no;     //The index number of the client

    protected Socket clientSocket = null;


    public WorkerRunnable(Socket clientSocket, int no) {
        this.clientSocket = clientSocket;
        this.no   = no;
    }

    public void run() {
        try 
        {
            in  = new ObjectInputStream(clientSocket.getInputStream());
            out = new ObjectOutputStream(clientSocket.getOutputStream());

            while(true)
            {
                //receive the message sent from the client
                message = (String)in.readObject();
                //show the message to the user
                System.out.println("Receive message: " + message + " from client " + no);
                //Capitalize all letters in the message
                MESSAGE = message.toUpperCase();
                //send MESSAGE back to the client
                sendMessage(MESSAGE);
            }
        } 
        catch(ClassNotFoundException classnot)
        {
            System.err.println("Data received in unknown format");
        }
        catch (IOException ioException) 
        {
            //report exception somewhere.
            System.out.println("Disconnect with Client " + no);
        }
        finally{
            //Close connections
            try{
                in.close();
                out.close();
                clientSocket.close();
            }
            catch(IOException ioException){
                System.out.println("Disconnect with Client " + no);
            }
        }
    }
    //send a message to the output stream
    public void sendMessage(String msg)
    {
        try
        {
            out.writeObject(msg);
            out.flush();
            System.out.println("Send message: " + msg + " to Client " + no);
        }
        catch(IOException ioException)
        {
            ioException.printStackTrace();
        }
    }
}

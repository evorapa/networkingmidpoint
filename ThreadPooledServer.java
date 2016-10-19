import java.net.ServerSocket;
import java.net.Socket;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadPooledServer implements Runnable{

    protected int          serverPort   = 8080;
    protected ServerSocket listener = null;
    protected boolean      isStopped    = false;
    protected Thread       runningThread= null;
    protected ExecutorService threadPool =
        Executors.newFixedThreadPool(10);

    public ThreadPooledServer(int port){
        this.serverPort = port;
    }

    public void run()
    {
        synchronized(this){
            this.runningThread = Thread.currentThread();
        }
        openServerSocket();
        int clientNum = 1;
        while(! isStopped())
        {
            Socket clientSocket = null;
            try 
            {
                clientSocket = this.listener.accept();
            } 
            catch (IOException e) 
            {
                if(isStopped()) 
                {
                    System.out.println("Server Stopped.") ;
                    break;
                }
                throw new RuntimeException(
                    "Error accepting client connection", e);
            }
            this.threadPool.execute(
                new WorkerRunnable(clientSocket, clientNum));
            System.out.println("Client "  + clientNum + " is connected!");
            clientNum++;
        }
        this.threadPool.shutdown();
        System.out.println("Server Stopped.") ;
    }


    private synchronized boolean isStopped() {
        return this.isStopped;
    }

    public synchronized void stop(){
        this.isStopped = true;
        try {
            this.listener.close();
        } catch (IOException e) {
            throw new RuntimeException("Error closing server", e);
        }
    }

    private void openServerSocket() {
        try {
            this.listener = new ServerSocket(this.serverPort);
        } catch (IOException e) {
            throw new RuntimeException("Cannot open port 8080", e);
        }
    }

    public static void main(String args[]){
        ThreadPooledServer server = new ThreadPooledServer(9000); //receiving
        ThreadPooledServer server2 = new ThreadPooledServer(9001);
        System.out.println("Server started.");
        new Thread(server).start();

        try {
            Thread.sleep(20 * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Stopping Server");
        server.stop();
    }

}
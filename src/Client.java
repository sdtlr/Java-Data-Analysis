import java.io.*;
import java.net.*;

public class Client
{
    private Socket clientSocket = null;
    private PrintWriter out = null;
    private BufferedReader stdIn = null;
  

    private ClientRead myRead;
    private ClientWrite myWrite;


    public Client()
    {
        try
        {
			// New socket object is created, with the ip 192.168.0.48 and port 5000.
            clientSocket = new Socket("192.168.0.48", 5000);
			// Print writer is created in order to write to the server.
            out = new PrintWriter(clientSocket.getOutputStream(), true);
			// Buffered reader is created in order to read from the server.
            stdIn = new BufferedReader(new InputStreamReader(System.in));

        }
		// Catches any errors thrown by the program. 
        catch(IOException e)
        {
            System.out.println("Some Error: " + e);
        }

        myWrite = ClientWrite.getClientWriteInstance();
        myWrite.setClientSocket(clientSocket);
        myRead = ClientRead.getClientReadInstance();
        myRead.setSocket(clientSocket);
    }

	// Starts the threads for reading and writing.
    public void StartThreads()
    {
    	Thread t1 = new Thread(myWrite);
        Thread t2 = new Thread(myRead);
        t1.start();
        t2.start();
    }

    public static void main(String [] args)
    {
        Client clientRun = new Client();
        // Starts all threads. 
		clientRun.StartThreads();
    }
}
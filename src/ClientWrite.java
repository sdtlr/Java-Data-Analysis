import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Observable;
import java.util.Observer;


public class ClientWrite implements Runnable, Observer
{

    private Socket clientSocket = null;
    private BufferedReader consoleInput = null;
    private String userInput;
    private PrintWriter output;
    private String regiID;

    private static ClientWrite clientWriteInstance;
    
    private ClientRead clientReadInstance = ClientRead.getClientReadInstance();

	// 
    private ClientWrite()
    {
    	clientReadInstance.addObserver(this);
    }

    public static ClientWrite getClientWriteInstance()
    {
        if(clientWriteInstance == null){
            clientWriteInstance = new ClientWrite();
        }
        return clientWriteInstance;
    }
    
	// Sets the socket values passed in from client.java
    public void setClientSocket(Socket aSocket){
        clientSocket = aSocket;
        consoleInput = new BufferedReader(new InputStreamReader(System.in));
    }
	
	// Main write to server method
    public void writeToServer()
    {
        try
        {
            output = new PrintWriter(clientSocket.getOutputStream(), true);
            
			// While userInput does not equal quite the loop runs.
            while ((userInput = consoleInput.readLine()) != "quit")
			{
				// Creates a string to build the output.
            	String outputBuilder = "";
					
					// If user input equals REGI, REGI is output to server.
    				 if(userInput.equals("regi") || userInput.equals("REGI"))
    	                {
    					 output.println("REGI");
    	                }
					// If user input equals DISP, DISP is added to the output builder string + the REGI id.
    				 else if(userInput.equals("disp") || userInput.equals("DISP") && regiID != "")
 	                {
    					 outputBuilder = "DISP:" + regiID;
    					 output.println(outputBuilder);
 	                }
					// If user input equals BUY, BUY is added to the output builder string.
    				 else if(userInput.equals("buy") || userInput.equals("BUY") && regiID != "")
    	                {
    					 System.out.println("Please enter the company name:");
						 // The user is asked for the name of the company they wish to buy, this is then added to the output string.
    					 outputBuilder = "BUY:" + consoleInput.readLine() + ":";
    					 // The user is asked how many shares they wish to purchase, this is then added to the output string. 
    					 System.out.println("Please enter the amount of shares you wish to buy:");
    					 outputBuilder = (outputBuilder + consoleInput.readLine() + ":" + regiID);
    					 // The string is then output to the server. 
    					 output.println(outputBuilder); 
    	                }
					// If user input equals SELL, SELL is added to the output builder string.
    				 else if(userInput.equals("sell") || userInput.equals("SELL") && regiID != "")
 	                {
						// The user is asked for the name of the company they wish to sell, this is then added to the output string.
    					 System.out.println("Please enter the company name:");
    					 outputBuilder = "SELL:" + consoleInput.readLine() + ":";
    					 // The user is asked how many shares they wish to sell, this is then added to the output string. 
    					 System.out.println("Please enter the amount of shares you wish to sell:");
    					 outputBuilder = (outputBuilder + consoleInput.readLine() + ":" + regiID);
    					 // The string is then output to the server. 
    					 output.println(outputBuilder); 
 	                }
    				 else 
    				 {
						 // If neither buy or sell are entered the command is output to the server.
    					 output.println(userInput);
    				 }
    				 
    				    							 
			}
        }
        catch(IOException e)
        {
            System.out.println("Error: " + e);
        }
    }



    public void run()
    {
        writeToServer();
    }

	@Override
	public void update(Observable o, Object arg) 
	{
		regiID = clientReadInstance.getRegiID();
	}


}


import java.io.*;
import java.net.*;
import java.util.Observable;
import java.util.Observer;

public class ClientRead extends Observable implements Runnable
{
	
	// Private variables, singleton desgn pattern. 
    private Socket clientSocket = null;
    private BufferedReader in = null;
    private String inString;
    private boolean run = true;
    private String regiID;

    private static ClientRead clientReadInstance;

    private ClientRead()
    {

    }
	
    public static ClientRead getClientReadInstance()
    {
		// If there is no instance of clientRead then create one.
      if(clientReadInstance == null)
      {
          clientReadInstance = new ClientRead();
      }

        return clientReadInstance;
    }
    
	
    public void setSocket(Socket aSocket)
    {
    	clientSocket = aSocket;
        try
        {
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        }
		// Catches any errors thrown by the program.
        catch(IOException e)
        {
            System.out.println("Error: " + e);
        }
    }

	// Read from server method.
    public void readFromServer()
    {
        try
        {
			// While the in string is NOT empty.
            while(!(inString = in.readLine()).equals(""))
            {
				// Print the contents of the string to the command line.
            	System.out.println(inString + "\n");
          
                // If the in string contains the command regi.
                if(inString.contains("REGI")){
					// Split the returned string at :, the output is stored in an array, then the 3rd value is stored in regiID variable.
	                regiID = inString.split(":")[2];
					// Prints the regiID
                	System.out.println(regiID);
                    setChanged();
                    notifyObservers();
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
    	while(run){
        readFromServer();
    	}
    }
    
  //Overidden method to add observers to list
    @Override
    public void addObserver(Observer observer) {
        super.addObserver(observer);
    }

    //Allow observers to remove themselves from the notify list.
    @Override
    public synchronized void deleteObserver(Observer observer) {
        super.deleteObserver(observer);
    }

    //Notifies all observers when called
    @Override
    public void notifyObservers() {
        super.notifyObservers();
    }

	public String getRegiID() {
		return regiID;
	}
    

}

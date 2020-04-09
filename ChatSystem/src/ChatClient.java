import java.io.IOException;
import java.net.*;

/*
 * Alternative to using a terminal to connect to the server
 */

public class ChatClient
{
    // DEFAULT SERVER VALUES
    protected static InetAddress address;
    static
    {
        try
        {
            address = InetAddress.getByName("localhost");
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
    protected static int port = 14401;

    public ChatClient(InetAddress address, int port) throws UnknownHostException // Constructor
    {
        ChatClient.address = address;
        ChatClient.port = port;
    }

    public void handleClient()
    {
        try
        {
            System.out.println("Attempting to connect...");
            Socket clientSocket = new Socket(address, port);

            // Client thread
            ChatClientWrite clientWrite = new ChatClientWrite(clientSocket); // The state being 0 means that this is a human client

            // Server thread
            ChatClientRead clientRead = new ChatClientRead(clientSocket, 0);

            // Start threads
            clientWrite.start();
            clientRead.start();
        }
        catch (ConnectException e) // When client enters incorrect socket or server is not running
        {
            System.out.println("Unable to join!\nPerhaps server is not running or it does not exist?");
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException
    {
        for (int i = 0; i < args.length; i++) // Iterate through command line arguments
        {
            if ("-cca".equals(args[i]) && i + 1 <= args.length) // Optional parameter to bind another IP address
            {
                try
                {
                    address = InetAddress.getByName(args[i + 1]);
                }
                catch (UnknownHostException e)
                {
                    System.out.println("Server does not exist");
                }
            }
            else if ("-ccp".equals(args[i]) && i + 1 <= args.length) // Optional parameter to bind another port
            {
                port = Integer.parseInt(args[i + 1]);
            }
        }
        System.out.println("ADDRESS: " + address);
        new ChatClient(address, port).handleClient();
    }
}

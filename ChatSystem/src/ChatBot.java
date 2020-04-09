import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ConnectException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

/*
    Bot - Listens to the chat and replies to anyone that says any key words/phrases
 */

public class ChatBot
{
    // DEFAULT SERVER VALUES
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

    public ChatBot(InetAddress address, int port)  // Constructor
    {
        ChatBot.address = address;
        ChatBot.port = port;
    }

    public void handleBot() // Handle how the bot connects to server
    {
        try
        {
            System.out.println("initiating Bot...");
            System.out.println("Attempting to connect...");
            Socket clientSocket = new Socket(address, port); // Connect to server

            // Send username "Bot" to server
            OutputStream outputStream = clientSocket.getOutputStream(); // Write stream to server
            PrintWriter writer = new PrintWriter(outputStream, true);

            writer.println("Bot");

            // Server thread
            ChatClientRead clientRead = new ChatClientRead(clientSocket, 1); // The state being 1 means that this is a bot client

            // Start threads
            clientRead.start();
        }
        catch (ConnectException e)
        {
            System.out.println("Incorrect address!\n Perhaps you typed it wrong or it does not exist?");
        }
        catch (IOException e)
        {
            System.out.println("hello");
            e.printStackTrace();
        }
    }

    public static void main(String[] args)
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
                    System.out.println("Bot is unable to connect");
                }
            }
            else if ("-ccp".equals(args[i]) && i + 1 <= args.length) // Optional parameter to bind another port
            {
                port = Integer.parseInt(args[i + 1]);
            }
        }
        new ChatBot(address, port).handleBot();
    }
}
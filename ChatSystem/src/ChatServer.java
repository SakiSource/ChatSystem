import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.*;
import java.util.HashSet;
import java.util.Set;

public class ChatServer
{
    /*
        * Accept incoming connections
        * Create a thread for each client that joins
            * Make them choose a username
            * Handle each client
        * Disconnect clients when they input quit
     */

    // Server Info
    private int port;
    private InetAddress address;

    // Sets
    private Set<Socket> clientSockets = new HashSet<>(); // Sets are used to disallow duplication and indexing is unimportant
    private Set<String> userNames = new HashSet<>();

    public ChatServer(int port, InetAddress address) // Constructor: Attempt to create a server
    {
        this.port = port;
        this.address = address;
    }

    public void handleServer() // Handle the incoming connections from clients
    {
        ExitThread exitThread = new ExitThread(this);
        exitThread.start(); // Create an exit thread

        try
        {
            ServerSocket serverSocket = new ServerSocket(port, 0, address);
            System.out.println("Server Created!\nInfo: " + serverSocket + "\nType EXIT to close server.");

            while (true) // Continuously accept connections
            {
                Socket clientSocket = serverSocket.accept(); // Accept any incoming connections
                System.out.println("Socket Info: " + clientSocket + " has joined the server.");
                ClientThread clientThread = new ClientThread(clientSocket, this);
                addSocket(clientSocket); // Add the client's socket to clientSocket set.
                clientThread.start(); // Start a new clientThread

            }
        }
        catch (BindException e) // Unable to bind
        {
            System.out.println("Unable to bind to address!\nPerhaps address is incorrect or already binded?");
            System.exit(0);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    void sendAll(String message) throws IOException // Send a message to everyone in the server
    {
        for(Socket clientSocket: clientSockets) // Iterate through all client
        {
            OutputStream output = clientSocket.getOutputStream(); // Collect the data stream between server and client
            PrintWriter writer = new PrintWriter(output, true);
            writer.println(message);
        }
    }

    void sendAllExclusion(String message, Socket excludeClient) throws IOException // Send a message to everyone EXCEPT the user sending it
    {
        for(Socket clientSocket: clientSockets) // Iterate through all client
        {
            if(clientSocket == excludeClient)
            {
                // Do nothing
            }
            else
            {
                OutputStream output = clientSocket.getOutputStream(); // Collect the data stream between server and client
                PrintWriter writer = new PrintWriter(output, true);
                writer.println(message);
            }
        }
    }

    void addSocket(Socket clientSocket) // Add socket to set
    {
        clientSockets.add(clientSocket);
    }


    void addUser(String userName) // Add username to set
    {
        userNames.add(userName);
    }

    boolean isDodOnline() // Check if DoDClient is online
    {
        for(String userName : userNames)
        {
            if("DoDClient".equals(userName))
            {
                return true;
            }
        }
        return false;
    }


    public void closeUserSocket(Socket clientSocket, String userName) throws IOException // Close the user socket
    {
        userNames.remove(userName);
        clientSockets.remove(clientSocket);
        sendAll(userName + " left the server!"); // Message to every client
        System.out.println("Connection: " + clientSocket + " has disconnected."); // Server message
    }

    void closeServer() throws IOException // Close the server
    {
        sendAll("Server has been closed!");
        for(Socket clientSocket: clientSockets)
        {
            clientSockets.remove(clientSocket);
        }
        for(String username: userNames)
        {
            userNames.remove(username);
        }

        System.out.println("Server has been closed!");
    }

    public static void main(String[] args) throws UnknownHostException
    {
        int port = 14401;
        InetAddress address = InetAddress.getByName(""); //localhost

            for(int i = 0; i < args.length; i++) // Optional, if server administrator wants to enter optional port
            {
                if("-csp".equals(args[i]))
                {
                    port = Integer.parseInt(args[i + 1]);
                }
            }
        new ChatServer(port, address).handleServer();
    }
}
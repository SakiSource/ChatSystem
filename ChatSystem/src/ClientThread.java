import java.io.*;
import java.net.Socket;
import java.net.InetAddress;
import java.net.SocketException;

/*
 * Handle the transmission of data between client and server
 * NOTE TO SELF:
 *   LISTEN IDIOT THE CLIENTTHREAD HANDLES THE SOCKET, IT DOESNT ACT LIKE A CLIENT, IT ACTS LIKE A SOCKET HANDLER BECAUSE IT IS
 *   IN TERMINAL: NC/TELNET IS USED TO JOIN CHATSERVER, NOT THE CLIENTTHREAD
 *   IN CHATCLIENT: THE CODE IS USED TO JOIN CHATSERVER, NOT THE CLIENTTHREAD
 *   USE THIS THREAD TO HANDLE DATA COMING IN AND SEND TO SERVER TO SEND TO ALL
 * Use getOutputStream to send data to server?
 * PrintWriter?
 */

public class ClientThread extends Thread
{
    private Socket clientSocket;
    private ChatServer chatServer;
    private PrintWriter writer;
    private String username;


    public ClientThread(Socket clientSocket, ChatServer chatServer) // Constructor
    {
        this.clientSocket = clientSocket;
        this.chatServer = chatServer;
    }

    public void run() // Handle the socket
    {
        try
        {
            OutputStream outputStream = clientSocket.getOutputStream(); // Write stream to server
            writer = new PrintWriter(outputStream, true);
            InputStream input = clientSocket.getInputStream(); // Collect data stream that server sends in
            BufferedReader reader = new BufferedReader(new InputStreamReader(input)); // Read socket stream coming IN
            String clientMessage = "";

            writer.println("Welcome to Bib's Server!\nChoose a username: ");
            username = "";
            do // Client selects a username
            {
                username = reader.readLine();
            } while (username == null || username == ""); // User could add a space but that is a possible name in my opinion

            chatServer.addUser(username);
            writer.println("Your username is: " + username + "\nEnjoy the server!");
            chatServer.sendAllExclusion(username + " has joined the server!", clientSocket);

            do
            {
                clientMessage = reader.readLine();
                if("quit".equals(clientMessage))
                {
                    // Do nothing
                }
                else if("JOIN".equals(clientMessage) && chatServer.isDodOnline() == true) // Only connect when DoDClient is online
                {
                    GameLogic.main(clientSocket);
                }
                else if (clientMessage != null)
                {
                    chatServer.sendAll(username + ": " + clientMessage); // [Local port being read, change later]
                }
            }while(!"quit".equals(clientMessage));

            // Closing client
            writer.println("You have quit!");
            chatServer.closeUserSocket(clientSocket, username); // Close client cleanly
            clientSocket.close();
        }
        catch (SocketException e)
        {
            if(clientSocket.isConnected()) // Client-side problem
            {
                try
                {
                    chatServer.closeUserSocket(clientSocket, username); // Close client cleanly
                }
                catch (IOException ex)
                {
                    ex.printStackTrace();
                }

            }
            else // Not supposed to do this (Problem with code)
            {
                e.printStackTrace();
            }
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}

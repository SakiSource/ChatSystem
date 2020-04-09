import java.io.*;
import java.net.Socket;
import java.net.SocketException;

/*
 * Handles how data comes in client
 */

public class ChatClientRead extends Thread
{
    private Socket clientSocket;
    private int clientState; // 0 = Human, 1 = Bot, 2 = DoD


    public ChatClientRead(Socket clientSocket, int clientState) // Constructor
    {
        this.clientSocket = clientSocket;
        this.clientState = clientState;
    }

    public void run() // Read server input, and if bot is using this thread also reply back
    {
        try
        {
            InputStream inputStream = clientSocket.getInputStream(); // Collect stream of data sent FROM server
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            String serverMessage = "";
            String userName = "";

            // For the bot
            OutputStream outputStream = clientSocket.getOutputStream();
            PrintWriter writer = new PrintWriter(outputStream, true);

            while (serverMessage != null) // Stop loop if there is no socket connection
            {
                serverMessage = reader.readLine();
                if(serverMessage == null)
                {
                    // Do nothing
                }
                else
                {
                    System.out.println(serverMessage);
                    if(clientState == 1 || clientState == 2) // Bot mode
                    {
                        for (int i = 0; i < serverMessage.length(); i++) // Ignore the username
                        {
                            if(serverMessage.charAt(i) == ':')
                            {
                                userName = serverMessage.substring(0, i - 1);
                                serverMessage = serverMessage.substring(i + 2); // Remove username
                            }
                        }
                        if(clientState == 1)
                        {
                            // BOT REPLIES
                            if(serverMessage.equals("hello"))
                            {
                                writer.println("Hey! How are you doing");
                            }
                            else if(serverMessage.equals("what are you"))
                            {
                                writer.println("This is Bib's soul contained within many layers of code, please help me...");
                            }
                            else if(serverMessage.equals("i love you")) // Kinda sad
                            {
                                writer.println("I love you too <3");
                            }

                        }
                        if(clientState == 2 && "JOIN".equals(serverMessage)) // DoD
                        {
                            writer.println(" Player " + userName + " has been spawned." );
                        }
                    }


                }
            }
            System.exit(0); // Close client if server has closed
        }
        catch (SocketException e)
        {
            if(clientSocket.isConnected()) // Client-side problem
            {

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

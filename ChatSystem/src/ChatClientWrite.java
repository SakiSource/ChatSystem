import java.io.*;
import java.net.Socket;

/*
 * Handles how ChatClient sends data
 */

public class ChatClientWrite extends Thread
{
    private Socket clientSocket;

    public ChatClientWrite(Socket clientSocket) // Constructor
    {
        this.clientSocket = clientSocket;
    }

    public void run() // ChatClient sends data to server
    {
        try
        {
            OutputStream outputStream = clientSocket.getOutputStream(); // Write stream to server
            PrintWriter writer = new PrintWriter(outputStream, true);
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            String clientMessage = "";

            do
            {
                clientMessage = reader.readLine();
                writer.println(clientMessage);
            } while (!"quit".equals(clientMessage)); // Exit when client types quit

        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }
}

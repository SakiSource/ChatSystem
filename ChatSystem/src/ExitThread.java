import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/*
    * Close server when system types exit
 */

public class ExitThread extends Thread
{
    private ChatServer chatServer;

    public ExitThread(ChatServer chatServer)
    {
        this.chatServer = chatServer;
    }
    public void run()
    {
        try
        {
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            String sysInput = "";

            while(!"EXIT".equals(sysInput))
            {
                sysInput = reader.readLine();
            }
            chatServer.closeServer();
            System.exit(0);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

    }
}

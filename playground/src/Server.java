import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.HashMap;
import java.util.Map;

public class Server {
	public void start(InetAddress ipAddress, int port) throws IOException
    {
		ServerSocket serverSocket = null;
		try
		{
			serverSocket = new ServerSocket(port);
		}
		catch (IOException e)
		{
			e.printStackTrace();
			return;
		}
        
		Map<Integer, ClientStateOM> clientStates = new HashMap();
        while (true)
        {
        	Socket clientSocket = null;
        	try
        	{
				clientSocket = serverSocket.accept();
			}
        	catch (IOException e)
        	{
				e.printStackTrace();
				continue;
			}
        	if (clientSocket.isConnected())
        	{
        	   BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        	   if (in.ready())
        	   {
        		   String clientState = in.readLine();
        	       System.out.println("Client sent: " + clientState);
        	       ClientStateOM clientStateOM = ClientStateOM.CreateClientStateOM(clientState);
        	       clientStates.put(clientStateOM.clientIdentifier, clientStateOM);
        	   }
        	   clientSocket.close();
        	}
        }
    }
}
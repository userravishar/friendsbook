import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.HashMap;
import java.util.Map;
import pkg.ClientStateOM;

public class Server {
	public void start(InetAddress ipAddress, int port) throws IOException, ClassNotFoundException
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
        	   InputStream is = clientSocket.getInputStream();
        	   ObjectInputStream ois = new ObjectInputStream(is);
        	   ClientStateOM clientStateOM = (ClientStateOM)ois.readObject();
        	   System.out.println("ClientId " + clientStateOM.clientIdentifier
        	   + " sent:- Cpu: " + clientStateOM.cpu + "; FreeMemory: " + clientStateOM.freeMemory);
        	   clientStates.put(clientStateOM.clientIdentifier, clientStateOM);
        	   clientSocket.close();
        	}
        }
    }
}
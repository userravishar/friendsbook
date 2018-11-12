import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import pkg.ClientStateOM;

public class Server {
	static int MaxThreads = 100;
	
	// clientStates[i] is set whenever the server receives a message
	// from client with identifier i and the state of the client has changed.
	Map<Integer, ClientStateOM> clientStates = new HashMap();

	// isDirty[i] is set to true when clientStates[i] changes.
	Map<Integer, Boolean> isDirty = new HashMap();
	
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
		
		ExecutorService executor = Executors.newFixedThreadPool(MaxThreads);

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
        		Socket localClientSocket = clientSocket;
        		executor.execute(() -> 
        		{
        			try
        			{
						RecordClientState(localClientSocket);
					}
        			catch (IOException e)
        			{
						e.printStackTrace();
					}
				});
        	}
        }
    }
	
	private void RecordClientState(Socket clientSocket) throws IOException
	{
		try
		{
			InputStream is = clientSocket.getInputStream();
			ObjectInputStream ois = new ObjectInputStream(is);
			ClientStateOM clientStateOM = (ClientStateOM)ois.readObject();
			OutputStream os = clientSocket.getOutputStream();
			ObjectOutputStream oos = new ObjectOutputStream(os);
			oos.writeObject("Copied");
			long threadId = Thread.currentThread().getId();
			System.out.println("ThreadId: " + threadId + "; ClientId " + clientStateOM.clientIdentifier
					+ " sent:- Cpu: " + clientStateOM.cpu + "; FreeMemory: " + clientStateOM.freeMemory);
			int key = clientStateOM.clientIdentifier;
			
			// Add to the dictionary if key is not present in it or
			// if clientState differs.
			if (!clientStates.containsKey(key) ||
					!(clientStates.get(key).equals(clientStateOM)))
			{
				clientStates.put(clientStateOM.clientIdentifier, clientStateOM);
				isDirty.put(key, true);
			}
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		catch (ClassNotFoundException e)
		{
			e.printStackTrace();
		}
		finally
		{
			clientSocket.close();
		}
	}
}
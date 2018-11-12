import java.io.*;
import java.net.*;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import pkg.ClientStateOM;

/**
 * @author gbadoni
 *
 */
public class APIServerWorker extends Thread {
	private int data;
	private String serverName;
	private int port;
	private Map<Integer, Boolean> isDirty;
	private Map<Integer, ClientStateOM> clientStates;
	
	APIServerWorker(Map<Integer, ClientStateOM> clientStates, Map<Integer, Boolean> isDirty) {
		this.clientStates = clientStates;
		this.isDirty = isDirty;
		this.serverName = "localhost";
		this.port = 7654;
	}
	public void sendToEtcd(ObjectOutputStream oos, ObjectInputStream ois, 
			Map<Integer, ClientStateOM> clientStates, Map<Integer, Boolean> isDirty) {

		System.out.println("APIServerWOrker: Checking dirty states");
		try {
			for (Integer key : isDirty.keySet()) {
				Boolean dirty = (Boolean)isDirty.get(key);
				if (dirty) {
					ClientStateOM c = (ClientStateOM)clientStates.get(key);
					
					// Write to server
					oos.writeObject(c);
					isDirty.put(key, false);
					System.out.println("APIServerWorker: Sent dirty object to Etcd:" + c.toString());
				}
			}
			//String result = (String)ois.readObject();
		} catch (Exception e)
		{
			e.printStackTrace();
		}

	}
	public void run() {
		try {
			// Connect to server
			Socket sock = new Socket(serverName, port);
			
			// Build object streams
			OutputStream os = sock.getOutputStream();
			ObjectOutputStream oos = new ObjectOutputStream(os);
			InputStream is = sock.getInputStream();
			ObjectInputStream ois = new ObjectInputStream(is);
			
			System.out.println("APIServerWorker: created streams");
			ScheduledExecutorService scheduledService = Executors.newScheduledThreadPool(1);

			// Every 5 secs check for dirty state of data
			scheduledService.scheduleAtFixedRate(()->
			{
				try
				{
					this.sendToEtcd(oos, ois, clientStates, isDirty);
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}
			}, 0, 5, TimeUnit.SECONDS);

		}
		catch (Exception e) {
			System.out.println("APIServerWorker: " + e.getMessage());
		}
		
	}
}

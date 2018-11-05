import java.io.*;
import java.net.*;

/**
 * @author gbadoni
 *
 */
public class APIServerWorker extends Thread {
	private int data;
	private String serverName;
	private int port;
	
	APIServerWorker() {
		this.data = 0;
		this.serverName = "localhost";
		this.port = 7654;
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
			
			//Run the loop
			while (true) {
				//Prepare data to send
				this.data = System.in.read();
				
				// Write to server
				oos.writeObject(this.data);
				String result = (String)ois.readObject();
				System.out.println("APIServerWorker: Response from etcd:" + result);
			}
		}
		catch (Exception e) {
			System.out.println("APIServerWorker: " + e.getMessage());
		}
		
	}
}

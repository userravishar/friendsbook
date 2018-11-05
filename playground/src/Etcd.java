import java.io.*;
import java.net.*;


public class Etcd {

	public Etcd() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			ServerSocket ss = new ServerSocket(7654);
			Socket sock = ss.accept();
			
			System.out.println("Etcd: accepted incoming client" + sock.getPort());
			
			// Build object streams
			OutputStream os = sock.getOutputStream();
			ObjectOutputStream oos = new ObjectOutputStream(os);
			InputStream is = sock.getInputStream();
			ObjectInputStream ois = new ObjectInputStream(is);
			
			// Build File output Stream or open DB connection
			FileOutputStream fos = new FileOutputStream("etcd.db");
			ObjectOutputStream dbos = new ObjectOutputStream(fos);
						
			while (true) {
				
				// Read data coming from API Server Worker Thread
				int data = (int)ois.readObject();
				System.out.println("Etcd: got data from client " + data);

				// Send Response to the API Server Worker Thread
				oos.writeObject("Success");
				
				//Write the data in a local database
				dbos.writeByte(data);
			} 
		}
		catch (Exception e) {
			System.out.println("Etcd: exception " + e.getMessage());
		}

	}

}

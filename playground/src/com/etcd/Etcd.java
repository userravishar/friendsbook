package com.etcd;
import java.io.*;
import java.net.*;
import pkg.ClientStateOM;

public class Etcd {	
	public Etcd() {
		// TODO Auto-generated constructor stub
	}

	public void queryCPUStats() {
		try {
			FileInputStream fis = new FileInputStream("etcd.db");
			ObjectInputStream ois = new ObjectInputStream(fis);
			while (true) {
				if (fis.available() != 0) {
					ClientStateOM data = (ClientStateOM)ois.readObject();
					System.out.println("Read the data from the database:" + data.toString());
				} else {
					break;
				}
			}
			ois.close();
			fis.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public static void main(String[] args) {
		int dbCounter = 0;
		
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
			FileOutputStream fos = new FileOutputStream("etcd.db", false);
			ObjectOutputStream dbos = new ObjectOutputStream(fos);
						
			while (true) {
				
				// Read data coming from API Server Worker Thread
				ClientStateOM data = (ClientStateOM)ois.readObject();
				System.out.println("Etcd: got data from client " + data.toString());

				// Send Response to the API Server Worker Thread
				//oos.writeObject("Success");
				
				//Write the data in a local database
				dbos.writeObject(data);
				dbCounter++;
				if (dbCounter > 5) {
					dbCounter = 0;
					dbos.close();
					fos.close();
					System.out.println("Etcd: overwriting the file");
					fos = new FileOutputStream("etcd.db", false);
					dbos = new ObjectOutputStream(fos);
				}
			} 
		}
		catch (Exception e) {
			System.out.println("Etcd: exception " + e.getMessage());
		}

	}

}

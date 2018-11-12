import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.time.Instant;

import pkg.ClientStateOM;

public class SystemState {
    String ServerHostName = "localhost";
    int ServerPortNumber = 5001;
	
	public void SendToController(int portNumber, CpuMonitor cpuMonitor) throws IOException
	{
		long freeMemory = Runtime.getRuntime().freeMemory();
		double cpu = cpuMonitor.getCpuUsage();
		Socket socket = null;
		
		try
		{
			long start = System.nanoTime();
			socket = new Socket(ServerHostName, ServerPortNumber);
			OutputStream os = socket.getOutputStream();
			ObjectOutputStream oos = new ObjectOutputStream(os);
			ClientStateOM csOM = new ClientStateOM(cpu, freeMemory, portNumber);
			oos.writeObject(csOM);
			InputStream is = socket.getInputStream();
			ObjectInputStream ois = new ObjectInputStream(is);
			String output = (String)ois.readObject();
			long end = System.nanoTime();
			long elapsedTime = end - start;
			BufferedWriter bw = new BufferedWriter(new FileWriter(portNumber + ".txt", true));
			bw.write(Instant.now() + "," + Long.toString(elapsedTime));
			bw.newLine();
			bw.flush();
	    }
		catch (UnknownHostException e)
		{
			e.printStackTrace();
		}
		catch (ClassNotFoundException e)
		{
			e.printStackTrace();
		}
		finally
		{
			socket.close();
		}
	}
}
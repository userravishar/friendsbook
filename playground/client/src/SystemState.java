import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import pkg.ClientStateOM;

public class SystemState {
    String ServerHostName = "localhost";
    int ServerPortNumber = 5001;
	
	public void SendToController(int portNumber, CpuMonitor cpuMonitor) throws IOException
	{
		long freeMemory = Runtime.getRuntime().freeMemory();
		double cpu = cpuMonitor.getCpuUsage();
		
		try
		{
			Socket socket = new Socket(ServerHostName, ServerPortNumber);
			OutputStream os = socket.getOutputStream();
			ObjectOutputStream oos = new ObjectOutputStream(os);
			ClientStateOM csOM = new ClientStateOM(cpu, freeMemory, portNumber);
			oos.writeObject(csOM);
	    }
		catch (UnknownHostException e)
		{
			e.printStackTrace();
		}
	}
}
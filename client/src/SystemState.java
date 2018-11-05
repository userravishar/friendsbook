import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

public class SystemState {
	public void SendToController(int portNumber, CpuMonitor cpuMonitor) throws IOException
	{
		long freeMemory = Runtime.getRuntime().freeMemory();
		double cpu = cpuMonitor.getCpuUsage();
		String systemState = "Port:" + portNumber + ",Mem:" + freeMemory + ",Cpu:" + cpu;
		String hostName = "localhost";
		int serverPortNumber = 84;
		Socket socket = new Socket(hostName, serverPortNumber);
		PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
		out.println(systemState);
	}
}
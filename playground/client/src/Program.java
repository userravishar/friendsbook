import java.io.IOException;
import java.net.UnknownHostException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Program {
	
	// Represents port number of the client.
	private static int portNumber;
	
	public static void main(String[] args) throws IOException {
		if (args.length != 1)
		{
			throw new IllegalArgumentException("Port number for the client is missing.");
		}

		try
		{
			portNumber = Integer.parseInt(args[0]);
		}
		catch (NumberFormatException e)
		{
			throw new IllegalArgumentException(args[0] + " is not a valid port number.");
		}

		ScheduledExecutorService scheduledService = Executors.newScheduledThreadPool(1);
		SystemState state = new SystemState();
		CpuMonitor cpuMonitor = new CpuMonitor();
		
		// Every 5 secs send client state to the controller.
		scheduledService.scheduleAtFixedRate(()->
		{
			try
			{
				state.SendToController(portNumber, cpuMonitor);
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}, 0, 5, TimeUnit.SECONDS);

		while (true)
		{
			// Simulate client being busy.
			;
		}
	}
}
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ClientStateOM {
	private ClientStateOM(double cpu, int freeMemory, int clientId)
	{
		this.cpu = cpu;
		this.freeMemory = freeMemory;
		this.clientIdentifier = clientId;
	}
	
	public static ClientStateOM CreateClientStateOM(String state)
	{
		Pattern pattern = Pattern.compile("Port:(\\d+),Mem:(\\d+),Cpu:(.*)");
		Matcher matcher = pattern.matcher(state);
		if (matcher.matches())
		{
			int clientId = Integer.parseInt(matcher.group(1));
			int mem = Integer.parseInt(matcher.group(2));
			double cpu = Double.parseDouble(matcher.group(3));
			return new ClientStateOM(cpu, mem, clientId);
		}
		
		throw new IllegalArgumentException(state + " does not represent a valid client state");
	}
	
	public double cpu;
	public int freeMemory;
	public int clientIdentifier;
}
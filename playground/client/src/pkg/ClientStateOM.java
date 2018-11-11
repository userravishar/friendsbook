package pkg;
import java.io.Serializable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ClientStateOM implements Serializable {
	public ClientStateOM(double cpu, long freeMemory, int clientId)
	{
		this.cpu = cpu;
		this.freeMemory = freeMemory;
		this.clientIdentifier = clientId;
	}

	public double cpu;
	public long freeMemory;
	public int clientIdentifier;
}
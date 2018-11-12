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
	
	public boolean equals(Object other)
	{
		if (other == this)
		{
			return true;
		}
		
		if (!(other instanceof ClientStateOM))
		{
			return false;
		}
		
		ClientStateOM otherAsCsom = (ClientStateOM)other;		
		return this.cpu == otherAsCsom.cpu &&
				this.freeMemory == otherAsCsom.freeMemory &&
				this.clientIdentifier == otherAsCsom.clientIdentifier;
	}
	
	public int hashCode()
	{
		String serialized = "cpu" + cpu + "freeMemory" + freeMemory + "clientId" + clientIdentifier;
		return serialized.hashCode();
	}
	
	public String toString()
	{
		String serialized = "cpu" + cpu + "freeMemory" + freeMemory + "clientId" + clientIdentifier;
		return serialized;
	}

	public double cpu;
	public long freeMemory;
	public int clientIdentifier;
}
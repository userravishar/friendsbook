import java.io.IOException;
import java.net.Inet4Address;
import java.net.InetAddress;

public class Program {
	private static int port = 84;
	
	public static void main(String[] args) throws IOException {
		Server server = new Server();
		InetAddress localHostAddress = Inet4Address.getLoopbackAddress();		
		server.start(localHostAddress, port);
	}
}
import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;

public class Server {

	public void start(InetAddress ipAddress, int port) throws IOException
    {
		ServerSocket serverSocket = null;
		try {
			serverSocket = new ServerSocket(port);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			return;
		}
        
        while (true)
        {
        	Socket clientSocket = null;
        	try {
				clientSocket = serverSocket.accept();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				continue;
			}
        	PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
        	out.println("4534");
        	clientSocket.close();
        }
    }
}
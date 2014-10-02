import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class multiserver
{
	public static DatagramSocket dsock;
	public static DatagramPacket dpack;
	public static byte[] send;
	public static InetAddress group;
	
	public static void main(String[] args) throws IOException
	{
		String message = "Hello!";
		send = message.getBytes();
		
		dsock = new DatagramSocket();
		group = InetAddress.getByName("225.235.245.255");
		
		dpack = new DatagramPacket(send, send.length, group, 5005);
		
		dsock.send(dpack);
		dsock.close();
	}
}
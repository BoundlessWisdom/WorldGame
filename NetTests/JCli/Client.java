import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class Client
{
	public static DatagramSocket dsock;
	public static DatagramPacket dpack;
	public static byte[] send;
	public static byte[] buffer;
	
	public int port;
	public static InetAddress address;

	public static void main(String[] args) throws IOException
	{
		dsock = new DatagramSocket();
		dsock.setSoTimeout(5000);
		address = InetAddress.getLocalHost();
		send = new byte[3];
		send[0] = 3;
		send[1] = 2;
		send[2] = 1;
		
		dpack = new DatagramPacket(send, send.length, address, 5005);
		
		dsock.send(dpack);
		System.out.printf("Packet sent!\n");
		buffer = new byte[256];
		dpack = new DatagramPacket(buffer, buffer.length);
		dsock.receive(dpack);
		buffer = dpack.getData();
		
		System.out.println("First byte of response:" + buffer[0]);
		
		dsock.close();
	}
}
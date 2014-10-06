import java.io.*;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

public class multiclient
{
	public static MulticastSocket msock;
	public static DatagramPacket dpack;
	public static byte[] buffer;
	public static InetAddress group;
	
	public static void main(String[] args) throws IOException
	{
		msock = new MulticastSocket(5005);

		group = InetAddress.getByName("225.235.245.255");
		
		msock.joinGroup(group);
		
		buffer = new byte[256];
		dpack = new DatagramPacket(buffer, buffer.length);
		
		
		msock.receive(dpack);
		
		String received = new String(dpack.getData());
		System.out.println("Received data: " + received);
		
		msock.leaveGroup(group);
		
		msock.close();
	}
}
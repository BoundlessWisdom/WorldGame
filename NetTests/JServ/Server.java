import java.io.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;


public class Server
{
	public static DatagramSocket dsock;
	public static DatagramPacket dpack;
	public static byte[] buffer;
	
	public static void main(String[] args) throws IOException
	{
		buffer = new byte[256];
		dsock = new DatagramSocket(5005);
		dpack = new DatagramPacket(buffer, buffer.length);
		System.out.printf("Waiting...\n");
		dsock.receive(dpack);
		System.out.printf("Received packet.\n");
		System.out.println((int)buffer[0]);
		InetAddress address = dpack.getAddress();
		int port = dpack.getPort();
		byte[] send = new byte[3];
		send[0] = 1;
		send[1] = 2;
		send[2] = 3;
		dpack = new DatagramPacket(send, send.length, address, port);
		dsock.send(dpack);
		
		dsock.close();
	}
}
package net.networking;

import java.io.IOException;
import java.net.*;

// Magic number 5005 used throughout is arbitrary open port

@SuppressWarnings("unused")
public class Communications
{
	private static DatagramSocket socket;
	private static DatagramPacket packet;
	private static InetAddress group;
	byte[] buffer;
	
	private Communications() throws UnknownHostException
	{
		group = InetAddress.getByName("225.235.245.255");
	}
	
	public Communications Server() throws UnknownHostException, SocketException
	{
		socket = new DatagramSocket();
		return new Communications();
	}
	
	public Communications Client() throws IOException
	{
		socket = new MulticastSocket(5005);
		return new Communications();
	}
	
	private void BroadcastServer() throws IOException
	{
		String servername = "Some identifier";
		buffer = servername.getBytes();
		packet = new DatagramPacket(buffer, buffer.length, group, 5005);
		socket.send(packet);
	}
	
	private void ClientContact() throws IOException
	{
		socket = new MulticastSocket(5005);
		((MulticastSocket) socket).joinGroup(group);
		
		String request = "You wanna go, bro? " + InetAddress.getLocalHost().getHostName();
		buffer = request.getBytes();
		
		packet = new DatagramPacket(buffer, buffer.length);
	}
	
	private void ServerAccept() throws IOException
	{
		buffer = new byte[256];
		packet = new DatagramPacket(buffer, buffer.length);
		
		socket.receive(packet);
		
	}
	
	private void Destroy()
	{
		socket.close();
	}
}
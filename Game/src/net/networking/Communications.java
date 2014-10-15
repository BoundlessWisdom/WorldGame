package net.networking;

import java.io.IOException;
import java.net.*;
import java.util.ArrayList;

import org.lwjgl.input.Keyboard;

import com.engine.core.Input;

@SuppressWarnings("unused")
// Magic number 5005 used throughout is arbitrary open port
public class Communications
{
	private static DatagramSocket socket;
	private static DatagramPacket packet;
	private static InetAddress group;
	public ArrayList<InetAddress> addresses;

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
	
	public void ClientListen() throws IOException
	{
		socket = new MulticastSocket(5005);
		((MulticastSocket) socket).joinGroup(group);
		
		buffer = new byte[256];
		packet = new DatagramPacket(buffer, buffer.length);
		socket.setSoTimeout(250);
		try
		{
			socket.receive(packet);
		}
		catch (SocketTimeoutException e)
		{
			return;
		}
		if(Input.getKey(Keyboard.KEY_C))
			ClientContact();
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
		addresses.add(packet.getAddress());
		
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
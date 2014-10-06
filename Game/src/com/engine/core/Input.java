package com.engine.core;

import java.util.ArrayList;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

public class Input 
{
	public static final int NUM_KEYCODES = 256;
	public static final int NUM_MOUSECODES = 5;
	
	private static ArrayList<Integer> currentKeys = new ArrayList<Integer>();
	private static ArrayList<Integer> downKeys = new ArrayList<Integer>(); //single frame
	private static ArrayList<Integer> upKeys = new ArrayList<Integer>(); //single frame
	
	private static ArrayList<Integer> currentMouse = new ArrayList<Integer>(); 
	private static ArrayList<Integer> downMouse = new ArrayList<Integer>(); //single frame
	private static ArrayList<Integer> upMouse = new ArrayList<Integer>(); //single frame
	
	
	public static void update()
	{
		
		upMouse.clear();
		for(int i = 0; i < NUM_MOUSECODES; i++)
		{
			if(!getMouse(i) && currentMouse.contains(i))
				upMouse.add(i);
		}
		
		downMouse.clear();
		for(int i = 0; i < NUM_MOUSECODES; i++)
		{
			if(getMouse(i) && !currentMouse.contains(i))
				downMouse.add(i);
		}
		
		upKeys.clear();
		for(int i = 0; i < NUM_KEYCODES; i++)
		{
			if(!getKey(i) && currentKeys.contains(i))
				upKeys.add(i);
		}
		
		downKeys.clear();
		for(int i = 0; i < NUM_KEYCODES; i++)
		{
			if(getKey(i) && !currentKeys.contains(i))
				downKeys.add(i);
		}
		
		currentKeys.clear();
		
		for(int i = 0; i < NUM_KEYCODES; i++)
		{
			if(getKey(i))
				currentKeys.add(i);
		}
		
		currentMouse.clear();
		for(int i = 0; i < NUM_MOUSECODES; i++)
		{
			if(getMouse(i))
				currentMouse.add(i);
		}
	}
	
	public static boolean getKey(int keyCode)
	{
		return Keyboard.isKeyDown(keyCode);
	}
	
	public static boolean getKeyDown(int keyCode) //one frame
	{
		return downKeys.contains(keyCode);		
	}
	
	public static boolean getKeyUp(int keyCode)
	{
		return upKeys.contains(keyCode);
	}
	
	public static boolean getMouse(int mButton)
	{
		return Mouse.isButtonDown(mButton);
	}
	
	public static boolean getMouseDown(int mButton)
	{
		return downMouse.contains(mButton);
	}
	
	public static boolean getMouseUp(int mButton)
	{
		return upMouse.contains(mButton);
	}
	
	public static Vector2f getMousePosition()
	{
		return new Vector2f(Mouse.getX(), Mouse.getY());
	}
	
	public static void setMousePosition(Vector2f pos)
	{
		Mouse.setCursorPosition((int)pos.getX(), (int)pos.getY());
	}
	
	public static void setCursor(boolean enabled)
	{
		Mouse.setGrabbed(!enabled);
	}
}

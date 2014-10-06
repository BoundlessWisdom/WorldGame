package com.engine.rendering;


import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

import com.engine.core.Vector2f;

public class Window 
{
	private static int w = 0, h = 0;
	private static String title = "";
	
	public static void createWindow(int width, int height, String windowTitle)
	{
		w = width; h = height;
		title = windowTitle;
		
		Display.setTitle(title);
		try 
		{
			Display.setDisplayMode(new DisplayMode(w, h));
			Display.create();
			Keyboard.create();
			Mouse.create();
		} catch (LWJGLException e) {
			e.printStackTrace();
		}
	}
	
	public static void render()
	{
		Display.update();
	}
	
	public static void dispose()
	{
		Display.destroy();
		Keyboard.destroy();
		Mouse.destroy();
	}
	
	public static boolean isCloseRequested()
	{
		return Display.isCloseRequested();
	}
	
	public static int getWidth()
	{
		return Display.getDisplayMode().getWidth();
	}
	
	public static int getHeight()
	{
		return Display.getDisplayMode().getHeight();
	}
	
	public static String getTitle()
	{
		return title;
	}
	
	public Vector2f getCenter()
	{
		return new Vector2f((float)getWidth() / 2f, (float)getHeight() / 2f);
	}
}

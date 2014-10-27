package com.engine.rendering;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;

import com.game.OurGame;

//import com.engine.core.Input;
//TODO: Maybe find a better place for this file

public class Menu // Actions triggered by mouse clicks
{
	
	public boolean isopen = true;
	public Menu() // Creates menu and displays
	{
		// Display menu here, probably a 2-D overlay still image
		while(isopen)
		{
			Update();
		}
	}
	
	public void MuliplayerStart() // Begins network contact procedures
	{
		
	}
	
	public void SinglePlayerStart() // For free roam, or at LEAST for testing without networking
	{
		OurGame.menuselection = 1;
		Close();
	}
	
	public void Update() // Handles mouse clicks
	{
		Display.update();
		//Input.update();
		System.out.println(isopen);
			//if(Input.getMousePosition() != null)
			//{
				System.out.println(Mouse.getX());
				System.out.println(Mouse.getY());
			//}
			
		if((Mouse.getX() < Display.getWidth()/2) && (Mouse.getY() < Display.getHeight()/2))
		{
			System.out.println("Hovering over button!");
			if(Mouse.isButtonDown(1)) // Menu setup currently runs on right-clicks in order to avoid meddling with mouse-centering system.
			{
				System.out.printf("Clicking!\n");
				SinglePlayerStart();
			}
		}
	}
	
	public void Quit() // For people too lazy to click the X in the top corner
	{
		
	}
	
	public void Close() // Closes menu once the game is loaded, not by immediate mouse click
	{
		isopen = false;
	}
}


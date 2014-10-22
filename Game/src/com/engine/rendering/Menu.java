package com.engine.rendering;



import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;

import com.engine.core.Input;
//TODO: Maybe find a better place for this file
import com.game.OurGame;

public class Menu // Actions triggered by mouse clicks
{
	public Menu() // Creates menu and displays
	{
		
		// Display menu here, probably a 2-D overlay still image
		Update();
	}
	
	public void MuliplayerStart() // Begins network contact procedures
	{
		
	}
	
	public void SinglePlayerStart() // For free roam, or at LEAST for testing without networking
	{
		
	}
	
	public void Update() // Handles mouse clicks
	{
			Input.update();
			if(Input.getMousePosition() != null)
			{
				System.out.println(Input.getMousePosition().getX());
				System.out.println(Input.getMousePosition().getY());
				System.out.println(OurGame.state);
			}
			if((Input.getMousePosition().getX() < Display.getWidth()/2) && (Input.getMousePosition().getY() < Display.getHeight()/2))
			{
				System.out.println("Hovering over button!");
				if(Input.getMouse(1)) // Menu setup currently runs on right-clicks in order to avoid meddling with mouse-centering system.
				{
					OurGame.state = true;
					System.out.printf("Clicking!\n");
				}
			}
	}
	
	public void Quit() // For people too lazy to click the X in the top corner
	{
		
	}
	
	public void Close() // Closes menu once the game is loaded, not by immediate mouse click
	{
		
	}
}
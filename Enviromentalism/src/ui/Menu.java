package ui;

import java.io.IOException;
import java.util.ArrayList;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.Color;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

public class Menu {
	//TODO: Interface: Refer to GoHarsha to acquire Texture support.
	
//	public static final Texture background;
	
	public static final Menu noMenu				= new Menu(null);
	public static final Menu rootMenu			= new Menu("");
	public static final Menu multiplayerMenu	= new Menu("");
	public static final Menu newWorldMenu		= new Menu("");
	public static final Menu menuOptions		= new Menu("");
	public static final Menu ingameOptions		= new Menu("");
	
	public final ArrayList<Button> buttons = new ArrayList<Button>();
	
	public static final Button backButton0	= new ChangeMenuButton(newWorldMenu, 0, 0, 0, 0).setVariable(rootMenu);
	public static final Button backButton1	= new ChangeMenuButton(multiplayerMenu, 0, 0, 0, 0).setVariable(rootMenu);
	public static final Button backButton2	= new ChangeMenuButton(menuOptions, 0, 0, 0, 0).setVariable(rootMenu);
	public static final Button backButton3	= new ChangeMenuButton(ingameOptions, 0, 0, 0, 0).setVariable(noMenu);
	
	public static final Button quitButton0	= new QuitButton(rootMenu, 0, 0, 0, 0, 0);
	public static final Button quitButton1	= new ChangeMenuButton(ingameOptions, 1, 0, 0, 0).setVariable(noMenu);
	
	
	
	public Menu(String backgroundURL)
	{
		Texture background = null;
		try {
			background = TextureLoader.getTexture("PNG",  ResourceLoader.getResourceAsStream("res/menubg.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Color.white.bind();
		background.bind();
		
		GL11.glBegin(GL11.GL_QUADS);
			GL11.glTexCoord2f(0,  0);
			GL11.glVertex2f(0,  0);
			GL11.glTexCoord2f(1,  0);
			GL11.glVertex2f(Display.getWidth(),  0);
			GL11.glTexCoord2f(1,  1);
			GL11.glVertex2f(Display.getWidth(), Display.getHeight());
			GL11.glTexCoord2f(0,  1);
			GL11.glVertex2f(0,  Display.getHeight());
		GL11.glEnd();
		
		
//		background = Texture.loadTexture(backgroundURL);
	}
	
	public void Update() // Handles mouse clicks
	{
		for(Button b : buttons)
		{
			if(b.hover())
			{
				// Change color in order to indicate that the mouse is indeed hovering over the button
				if(Mouse.isButtonDown(1)) // Right click
				{
					b.function();
				}
			}
		}
		
		Display.update();
	}
	
}

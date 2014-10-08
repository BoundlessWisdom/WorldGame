package com.engine.rendering;


import static org.lwjgl.opengl.GL11.*;

import java.io.File;
import java.io.FileInputStream;

import org.newdawn.slick.opengl.TextureLoader;

public class Texture 
{
	private int id;
	
	public Texture(String file)
	{
		this(loadTexture(file));
	}
	
	public Texture(int id)
	{
		this.id = id;
	}
	
	public void bind()
	{
		glBindTexture(GL_TEXTURE_2D, id);
	}
	
	public int getID()
	{
		return id;
	}
	
	private static int loadTexture(String file)
	{
		String[] splitArray = file.split("\\.");
		String ext = splitArray[splitArray.length - 1];
		
		try 
		{
			int id = TextureLoader.getTexture(ext, new FileInputStream(new File("./res/textures/" + file))).
					getTextureID();
			
			return id;
			
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
		
		return 0;
	}
}
package com.engine.core;

import com.engine.rendering.RenderingEngine;

public abstract class Game 
{
	private GameObject root;
	
	public void init() 
	{
		
	}	
	
	public void input(float delta) 
	{
		getRootObject().input(delta);
	}
	
	public void update(float delta) 
	{
		getRootObject().update(delta);
	}
	
	public GameObject getRootObject()
	{
		if(root == null)
			root = new GameObject();
			
		return root;
	}
	
	public static RenderingEngine getRenderingEngine()
	{
		return CoreEngine.getRenderingEngine();
	}
}

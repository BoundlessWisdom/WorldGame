package com.engine.components;

import com.engine.core.GameObject;
import com.engine.core.Transform;
import com.engine.rendering.RenderingEngine;
import com.engine.rendering.shaders.Shader;

public abstract class GameComponent 
{	
	private GameObject parent;
	
	public void input(float delta) {}
	public void update(float delta) {}
	public void render(Shader shader) {}
	public void addToRenderingEngine(RenderingEngine renderingEngine){}
	
	public Transform getTransform()
	{
		return parent.getTransform();
	}
	
	/*public GameObject getParent() 
	{
		return parent;
	}*/
	
	public void setParent(GameObject parent) 
	{
		this.parent = parent;
	}
	
	
}

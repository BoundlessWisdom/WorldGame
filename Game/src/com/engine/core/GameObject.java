package com.engine.core;

import java.util.ArrayList;

import com.engine.components.GameComponent;
import com.engine.rendering.RenderingEngine;
import com.engine.rendering.shaders.Shader;

public class GameObject 
{
	protected ArrayList<GameObject> children;
	protected ArrayList<GameComponent> components;
	protected Transform transform;
	
	public GameObject()
	{
		children = new ArrayList<GameObject>();
		components = new ArrayList<GameComponent>();
		transform = new Transform();
	}
	
	public void addChild(GameObject gObj)
	{
		children.add(gObj); 
	}
	
	public void addComponent(GameComponent gComp)
	{
		gComp.setParent(this);
		components.add(gComp);
	}
	
	public void input(float delta)
	{
		for(GameComponent component : components)
			component.input(delta);
		
		for(GameObject child : children)
			child.input(delta);
	}
	
	public void update(float delta)
	{
		for(GameComponent component : components)
			component.update(delta);
		
		for(GameObject child : children)
			child.update(delta);
	}
	
	public void render(Shader shader)
	{
		for(GameComponent component : components)
			component.render(shader);
		
		for(GameObject child : children)
			child.render(shader);
	}
	
	public void addToRenderingEngine(RenderingEngine renderingEngine) 
	{
		for(GameComponent component : components)
			component.addToRenderingEngine(renderingEngine);
		
		for(GameObject child : children)
			child.addToRenderingEngine(renderingEngine);
	}
	
	public Transform getTransform()
	{
		return transform;
	}
	
	public int childrenSize()
	{
		return children.size();
	}
	
	public void setChild(int index, GameObject obj)
	{
		children.set(index, obj);
	}
	
	public void setComponent(int index, GameComponent component)
	{
		components.set(index, component);
	}
}

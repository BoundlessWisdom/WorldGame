package com.engine.components.renderObjs.terrain;

import com.engine.core.GameObject;

public class CompleteTerrain extends GameObject 
{
	private static CompleteTerrain instance = new CompleteTerrain();
	
	private CompleteTerrain() 
	{
	}
	
	public static CompleteTerrain getInstance()
	{
		return instance;
	}
	
	@Override
	public void addChild(GameObject gObj) 
	{
		privatize();
	}
	
	public boolean addTile(Tile t)
	{
		super.addChild(t);
		return true;
	}
}

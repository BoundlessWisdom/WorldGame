package com.engine.components.renderObjs.terrain;

import com.engine.core.GameObject;

public class CompleteTerrain extends GameObject 
{
	private CompleteTerrain instance = new CompleteTerrain();
	
	private CompleteTerrain() 
	{
	}
	
	public CompleteTerrain getInstance()
	{
		return instance;
	}
	
	public boolean addTerrain(Tile t)
	{
		return true;
	}
}

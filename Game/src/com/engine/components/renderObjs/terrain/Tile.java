package com.engine.components.renderObjs.terrain;

import com.engine.components.renderObjs.terrain.Terrain.OriginGravity;
import com.engine.core.GameObject;

public class Tile extends GameObject 
{
	private float width = 0;
	private float height = 0;
	
	private int numTerrains = 0;
	
	OriginGravity gravity;
	
	public Tile() 
	{
	}
	
	@Override
	public void addChild(GameObject gObj) 
	{
		privatize();
	}
	
	public boolean addTerrain(Terrain t)
	{
		if(numTerrains == 0)
		{
			gravity = t.getOriginGravity();
			children.add(t);
		}
		
		else
		{
			if(t.getOriginGravity() != gravity)
			{
				return false;
			}
		}
		
		return true;
	}
}

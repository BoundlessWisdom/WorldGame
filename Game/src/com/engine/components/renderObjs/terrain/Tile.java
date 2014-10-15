package com.engine.components.renderObjs.terrain;

import com.engine.components.renderObjs.terrain.Terrain.OriginGravity;
import com.engine.core.GameObject;
import com.engine.core.Vector3f;

public class Tile extends GameObject 
{
	private float width = 0;
	private float depth = 0;
	
	private Vector3f scale;
	private Vector3f pos;
	
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
	
	public boolean addTerrain(Terrain t, boolean autoAlign, boolean autoGravity, boolean autoScale, boolean autoStretch)
	{
		if(t.getWidth() != width || t.getDepth() != depth)
		{
			return false;
		}
		
		if(t.getOriginGravity() != gravity)
		{
			t.setOriginGravity(gravity);
		}
		
		if(t.getPosOrigin().getX() != pos.getX() || t.getPosOrigin().getZ() != pos.getZ())
		{
			t.setOriginPos(new Vector3f(pos.getX(), t.getPosOrigin().getY(), pos.getZ()));
		}
		
		if(t.getScale() != scale)
		{
			t.setScale(scale);
		}
		
		return addTerrain(t);
	}
	
	public boolean addTerrain(Terrain t)
	{
		if(numTerrains == 0)
		{
			width = t.getWidth();
			depth = t.getDepth();
			
			pos = t.getPosOrigin();
			scale = t.getScale();
			
			gravity = t.getOriginGravity();
			children.add(t);
			numTerrains++;
		}
		
		else
		{
			if(t.getOriginGravity() != gravity)
			{
				return false;
			}
			
			if(t.getPosOrigin().getX() != pos.getX() || t.getPosOrigin().getZ() != pos.getZ())
			{
				return false;
			}
			
			if(t.getScale() != scale)
			{
				return false;
			}
			
			if(t.getWidth() != width || t.getDepth() != depth)
			{
				return false;
			}
			
			super.addChild(t);
			numTerrains++;
		}
		
		return true;
	}
}

package com.engine.components.terrain;

import java.util.ArrayList;

import com.engine.components.terrain.Terrain.OriginGravity;
import com.engine.core.GameObject;
import com.engine.core.Vector2f;
import com.engine.core.Vector3f;

public class TerrainTile extends GameObject 
{
	private float width = 0;
	private float depth = 0;
	
	private Vector3f scale;
	private Vector3f pos;
	
	private int numTerrains = 0;
	
	OriginGravity gravity;
	
	ArrayList<Terrain> terrains;
	
	public TerrainTile() 
	{
		terrains = new ArrayList<Terrain>();
	}
	
	@Override
	public void AddChild(GameObject gObj) 
	{
		privatize();
	}
	
	public boolean addTerrain(Terrain t, boolean autoAlign, boolean autoGravity, boolean autoScale, boolean autoStretch)
	{
		if(t.GetWidth() != width || t.GetDepth() != depth)
		{
			return false;
		}
		
		if(t.GetOriginGravity() != gravity)
		{
			t.SetOriginGravity(gravity);
		}
		
		if(t.GetPosOrigin().GetX() != pos.GetX() || t.GetPosOrigin().GetZ() != pos.GetZ())
		{
			t.SetOriginPos(new Vector3f(pos.GetX(), t.GetPosOrigin().GetY(), pos.GetZ()));
		}
		
		if(t.GetScale() != scale)
		{
			t.SetScale(scale);
		}
		
		return addTerrain(t);
	}
	
	public boolean addTerrain(Terrain t)
	{
		if(!t.built)
		{
			t.compile();
		}
		
		if(numTerrains == 0)
		{
			width = t.GetWidth();
			depth = t.GetDepth();
			
			pos = t.GetPosOrigin();
			scale = t.GetScale();
			
			gravity = t.GetOriginGravity();
			terrains.add(t);
			numTerrains++;
		}
		
		else
		{
			if(t.GetOriginGravity() != gravity)
			{
				return false;
			}
			
			if(t.GetPosOrigin().GetX() != pos.GetX() || t.GetPosOrigin().GetZ() != pos.GetZ())
			{
				return false;
			}
			
			if(t.GetScale() != scale)
			{
				return false;
			}
			
			if(t.GetWidth() != width || t.GetDepth() != depth)
			{
				return false;
			}
			
			terrains.add(t);
			numTerrains++;
		}
		
		return true;
	}
	
	public void compile()
	{
		for(Terrain t : terrains)
		{
			super.AddChild(t);
		}
		
		terrains.clear();
	}
	
	public boolean InRange(Vector2f xz)
	{
		if(xz.GetX() <= pos.GetX() + width && xz.GetX() >= pos.GetX() - width)
		{
			if(xz.GetY() <= pos.GetY() + depth && xz.GetX() >= pos.GetY() - depth)
			{
				return true;
			}
		}
		
		return false;
	}
	
	public float GetHeight(Vector2f xz)
	{
		if(!InRange(xz))
			return Float.NaN;
		
		return HeightMap.GetHeight(xz.GetX(), xz.GetY(), terrains.get(0));
	}
}

package com.engine.components.terrain;

import java.util.ArrayList;

import com.engine.core.GameObject;
import com.engine.core.Vector2f;

public class CompleteTerrain extends GameObject 
{
	private static CompleteTerrain instance = new CompleteTerrain();
	
	ArrayList<TerrainTile> tiles;
	
	int numTiles = 0;
	
	private CompleteTerrain() 
	{
		tiles = new ArrayList<TerrainTile>();
	}
	
	public static CompleteTerrain getInstance()
	{
		return instance;
	}
	
	@Override
	public void AddChild(GameObject gObj) 
	{
		privatize();
	}
	
	public boolean addTile(TerrainTile t)
	{
		tiles.add(t);
		return true;
	}
	
	public void compile()
	{
		for(TerrainTile t : tiles)
		{
			t.compile();
			super.AddChild(t);
		}
	}
	
	public float GetHeight(Vector2f xz)
	{
		float h = 0;
		for(int i = 0; i < tiles.size(); i++)
		{
			h = tiles.get(i).GetHeight(xz);
			if(h != Float.NaN)
			{
				return h;
			}
			
			else
				continue;
		}
		
		return 0;
	}
}

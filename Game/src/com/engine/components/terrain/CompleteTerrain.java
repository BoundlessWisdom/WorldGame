package com.engine.components.terrain;

import java.util.ArrayList;

import com.engine.core.GameObject;

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
		
		tiles.clear();
	}
}

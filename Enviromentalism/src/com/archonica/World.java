package com.archonica;

import java.util.ArrayList;

public class World {
	public final int width;
	public final int height;
	
	public Tile[][] worldMap;
	public TerrainTile[][] environmentMap;
	
	public World(int width, int height) {
		this.width = width;
		this.height = height;
		worldMap = new Tile[width][height];
		environmentMap = new TerrainTile[width][height];
		
		for(int i = 0; i < width; i++)
		{
			for(int j = 0; j < height; j++)
			{
				worldMap[i][j] = new Tile((1 + (2 * i)), (1 + (2 * j)));
			}
		}
	}	
	public World(int size) {
		this(size, size);
	}
	
	public float[] getGLCoords(int xTile, int zTile)
	{
		float[] glcoords = new float[2];
		
		glcoords[0] = (xTile * 2) + 1;
		glcoords[1] = (zTile * 2) + 1;
		
		return glcoords;
	}
	
	public int[] getTileIndex(float GLx, float GLz)
	{
		int[] index = new int[2];
		
		index[0] = (int)(GLx / 2);
		index[1] = (int)(GLz / 2);
		
		return index;
	}

	
	public void update(long dtime) {
		updateObjects(dtime);
	}
	
	public void updateObjects(long dtime) {
		for(Updateable ao : activeObjects)
			ao.update(dtime);
	}
	
	/***************************************************************/
	
	private ArrayList<Updateable> activeObjects = new ArrayList<Updateable>();
	
	public boolean add(Updateable object) {
		activeObjects.add(object);
		
		return true;
	}
	
	/*
	 * Note: Unsure whether to deal with effects as world-wide objects, to be checked through each turn,
	 * 		or to apply them tile by tile, and still controlled by a single effect object.
	 * 
	 * Note: Either way, a good way to check for targets would be with the Minecraft Material properties technique.
	 */
}

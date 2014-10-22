package com.archonica;

import java.util.ArrayList;

public class World {
	public final int width;
	public final int height;
	
	public Location[][] worldMap;
	
	public World(int width, int height) {
		this.width = width;
		this.height = height;
		worldMap = new Tile[width][height];
	}
	
	public World(int size) {
		this(size, size);
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
	
	/***************************************************************/
	
	public Tile getTile(int x, int z) {  //Using tile coordinate system.
		return (Tile) worldMap[2 * x - 1][2 * z - 1];
	}
}

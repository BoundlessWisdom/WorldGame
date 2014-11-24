package com.archonica;

import java.util.ArrayList;

import static com.archonica.EDirection.*;

public class World {
	public final int width;
	public final int height;
	
	private Location[][] worldMap;
	private TerrainTile[][] environmentMap;
	
	public World(int width, int height) {
		this.width = width;
		this.height = height;
		worldMap = new Tile[width][height];
		environmentMap = new TerrainTile[width][height];
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
		return (Tile) worldMap[2 * x + 1][2 * z + 1];
	}
	
	public Interaction getInteraction(EDirection direction, int x1, int z1, int x2, int z2) {
		return direction == EAST || direction == WEST ? getXWiseInteraction(x1, x2, z1)
				: getZWiseInteraction(x1, z1, z2); 
	}
	
	public Interaction getXWiseInteraction(int parx1, int parx2, int z) {
		int x2 = parx1 < parx2 ? parx2 : parx1;
		return (Interaction) worldMap[2 * x2][2 * z + 1];
	}
	
	public Interaction getZWiseInteraction(int x, int parz1, int parz2) {
		int z2 = parz1 < parz2 ? parz2 : parz1;
		return (Interaction) worldMap[2 * x + 1][2 * z2];
	}
}

package com.archonica;

public class TerrainTile extends Location {

	public TerrainTile(int x, int z) {
		super(x, z);
	}
	
	protected TerrainBody terrainBody;
	
	public void attach(TerrainBody terrain) {
		if (terrainBody == null)
			terrainBody = terrain;
	}
	
	public ETerrain getTerrain() {
		return terrainBody.terrain;
	}
	
	

}

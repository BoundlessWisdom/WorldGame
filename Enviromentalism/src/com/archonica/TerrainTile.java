package com.archonica;

public class TerrainTile {
	int x, z;

	public TerrainTile(int x, int z) {
		this.x = x;
		this.z = z;
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

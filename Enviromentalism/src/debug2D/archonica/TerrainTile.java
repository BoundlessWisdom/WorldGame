package debug2D.archonica;

import com.archonica.ETerrain;
import com.archonica.Entity;

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

	public void print(boolean b, boolean c) {
		return;
	}

	public void addEntity(Character e) {}

	@Override
	public void addEntity(debug2D.archonica.Entity e) {
		// TODO Auto-generated method stub
		
	}
	
	

}

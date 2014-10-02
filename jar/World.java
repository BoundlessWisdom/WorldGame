package jar;

import java.util.ArrayList;

public class World {
	public final int size;
	
	public Location[][] worldMap; 
	
	public World(int size) {
		this.size = size;
		worldMap = new Tile[size][size];
	}
	
	public void updateEntities() {
		for(Base b : activeObjects)
			b.update();
	}
	
	/***************************************************************/
	
	private ArrayList<Base> activeObjects = new ArrayList<Base>();
	
	public boolean addBase(Base base) {
		activeObjects.add(base);
		
		return true;
	}
	
	//Permanent and semi-permanent effects go here.
	private ArrayList<StandingEffect> standingEffects = new ArrayList<StandingEffect>();
	
	public void addEffect(StandingEffect effect) {
		standingEffects.add(effect);
	}
	
	/*
	 * Note: Unsure whether to deal with effects as world-wide objects, to be checked through each turn,
	 * 		or to apply them tile by tile, and still controlled by a single effect object.
	 * 
	 * Note: Either way, a good way to check for targets would be with the Minecraft Material properties technique.
	 */
}
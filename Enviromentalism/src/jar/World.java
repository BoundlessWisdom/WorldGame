package jar;

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
		updateEntities(dtime);
	}
	
	public void updateEntities(long dtime) {
		for(Entity e : activeObjects)
			e.update(dtime);
	}
	
	public void updateEffects(long dtime) {
		for(StandingEffect e : standingEffects)
			e.update(dtime);
	}
	
	/***************************************************************/
	
	private ArrayList<Entity> activeObjects = new ArrayList<Entity>();
	
	public boolean addEntity(Entity entity) {
		activeObjects.add(entity);
		
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

package jar;

import java.util.ArrayList;

public class Tile extends Location {
	private ArrayList<Entity> stack = new ArrayList<Entity>();
	private float stackSize = 0;
	
	public static final int tileSpace = 1;
	
	public boolean attemptPlacement(Entity entity) {
		if (stack.get(0).getTeamID() != entity.getTeamID())
			return false;
		
		if (entity.size + stackSize > tileSpace)
			return false;
		
		place(entity);
		return true;
	}
	
	public void place(Entity entity) {
		stack.add(entity);
		stackSize += entity.size;
	}
	
	public void remove(Entity entity) {
		//Figure out how to find it.
	}
	
	public Tile(int i, int j) {
		super(i, j);
	}
	
	public int[] translate(int sx, int sz) {
		return null;
	}
}

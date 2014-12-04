package debug2D.archonica;

import java.util.ArrayList;

import debug2D.archonica.Entity;

public class Tile extends Location {
	int heightMode = 4;
	public ArrayList<Entity> entities = new ArrayList<Entity>();
	int heightM = 4;
	public Tile(int i, int j) {
		super(i, j);
	}
	
	public void addEntity(Entity e) {
		entities.add(e);
		e.x = this.x;
	    e.z = this.z;
	}

	public void print(boolean top, boolean c) {
		
		if (top && (this.entities.size() > 0))
		{
			System.out.print(entities.get(0).identifier);
			System.out.print("   ");
		}
		else
			System.out.print("    ");
		}
	
	public float height() {
		return 0;
	}
	
	/*****************************************************************************/
	
//	private ArrayList<Entity> stack = new ArrayList<Entity>();
//	private float stackSize = 0;
//	public AbstractEntity abstractEntity;
	
	public static final int tileSpace = 1;
//	public static final int abstractSpace = 1;
	
//	public boolean attemptPlacement(Entity entity) {
//		if (stack.get(0).getTeamID() != entity.getTeamID())
//			return false;
//		
//		if (entity.size + stackSize > tileSpace)
//			return false;
//		
//		place(entity);
//		return true;
//	}
	
//	public boolean has(Entity question) {
//		for (Entity e : stack)
//			if (e.getClass().isAssignableFrom(question.getClass()))
//				return true;
//		
//		return false;
//	}
	
/*	public boolean hasAny(Entity[] question) {
		for (Entity q : question)
			if (this.has(q))
				return true;
		
		return false;
	}
	
	public boolean hasAll(Entity[] question) {
		return false;
	}
*/	
//	public boolean hasThis(Entity target) {
//		return stack.contains(target);
//	}
	
/*	public boolean hasAnyOne(Entity[] targets) {
		return false;
	}
	
	public boolean hasEach(Entity[] targets) {
		return false;
	}
*/	
//	public boolean abstractHas(AbstractEntity e) {
//		return e.getClass().isAssignableFrom(abstractEntity.getClass());
//	}
//	
//	public boolean abstractHasThis(AbstractEntity e) {
//		return e.getClass().equals(abstractEntity.getClass());
//	}
//	
//	public void place(Entity entity) {
//		if (entity.getClass().isAssignableFrom(AbstractEntity.class))  //TODO: Figure out if works.
//			abstractEntity = (AbstractEntity) entity;
//		else {
//			stack.add(entity);
//			stackSize += entity.size;
//		}
//	}
//	
//	public void remove(Entity entity) {
//		stack.remove(entity);
//	}
	
	/*************************************************************************************/
//	private int flammability;
//	//private int[] teamBanList = new int[8];
//	
//	public boolean canFireSpreadTo(float fireSize, float fireSpeed) {
//		return fireSize >= flammability & (this.has(new Fire()) ? fireSpeed > abstractEntity.speed() : true);
//	}
}

package com.archonica;

import java.util.ArrayList;

import com.archonica.elementals.AbstractEntity;
import com.archonica.elementals.Fire;

public class Tile {
	public int x;  //Self-explanatory
	public int z;
	
	Tile(int x, int z) {
		this.x = x;
		this.z = z;
	}
	
	//Stack of entities on this tile.
	private ArrayList<Entity> stack = new ArrayList<Entity>();
	private boolean occupied = false;
	/********************************************************************************/
	public AbstractEntity abstractEntity;  //Currently useless.
	/********************************************************************************/
	
	public boolean attemptPlacement(Entity entity) {
		if (stack.get(0).getTeamID() != entity.getTeamID())
			return false;
		
		if (occupied)
			return false;
		
		place(entity);
		return true;
	}
	
	public boolean has(Entity question) {
		for (Entity e : stack)
			if (e.getClass().isAssignableFrom(question.getClass()))
				return true;
		
		return false;
	}
	
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
	public boolean hasThis(Entity target) {
		return stack.contains(target);
	}
	
/*	public boolean hasAnyOne(Entity[] targets) {
		return false;
	}
	
	public boolean hasEach(Entity[] targets) {
		return false;
	}
*/	
	public boolean abstractHas(AbstractEntity e) {
		return e.getClass().isAssignableFrom(abstractEntity.getClass());
	}
	
	public boolean abstractHasThis(AbstractEntity e) {
		return e.getClass().equals(abstractEntity.getClass());
	}
	
	public void place(Entity entity) {
		if (entity.getClass().isAssignableFrom(AbstractEntity.class))
			abstractEntity = (AbstractEntity) entity;
		else {
			stack.add(entity);
		}
	}
	
	public void remove(Entity entity) {
		stack.remove(entity);
	}
	
	/*************************************************************************************/
	private int flammability;
	//private int[] teamBanList = new int[8];
	
	public boolean canFireSpreadTo(float fireSize, float fireSpeed) {
		return fireSize >= flammability & (this.has(new Fire()) ? fireSpeed > abstractEntity.speed() : true);
	}
}

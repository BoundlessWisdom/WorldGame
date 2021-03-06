package com.archonica;

import java.util.ArrayList;

import com.archonica.elementals.AbstractEntity;
import com.archonica.elementals.Fire;
import com.game.EntityObject;

public class Tile {
	public int x;  //Self-explanatory
	public int z;
	
	Tile(int x, int z) {
		this.x = x;
		this.z = z;
	}
	
	public ArrayList<EntityObject> stack = new ArrayList<EntityObject>();
	
	private boolean occupied = false;
	/********************************************************************************/
	public AbstractEntity abstractEntity;  //Currently useless.
	/********************************************************************************/
	
	public boolean attemptPlacement(EntityObject entity) {
		place(entity);
		return true;
	}
	
	public boolean has(Entity question) {
		for (int i = 0; i < stack.size(); i++)
			if (stack.get(i).getClass().isAssignableFrom(question.getClass()))
				return true;
		
		return false;
	}
	
	public boolean occupied() 
	{
		return this.stack.size() > 0;
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

	public boolean hasAnyOne(Entity[] targets) {
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
	
	public void place(EntityObject entity) {
		if (entity.getClass().isAssignableFrom(AbstractEntity.class))
			abstractEntity = (AbstractEntity) entity;
		else {
			stack.add(entity);
		}
	}
	
	public void remove(EntityObject entity) {
		stack.remove(entity);
	}
	
	/*************************************************************************************/
	private int flammability;
	//private int[] teamBanList = new int[8];
	
	public boolean canFireSpreadTo(float fireSize, float fireSpeed) {
		return fireSize >= flammability & (this.has(new Fire()) ? fireSpeed > abstractEntity.speed() : true);
	}
}

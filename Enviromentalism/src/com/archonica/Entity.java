package com.archonica;

import static com.archonica.EntClass.*;

public abstract class Entity implements Updateable {
public final EntClass entClass;

public final float size;
public float speed;

World w;
public int x, z;

protected final int maxHealth = 0;
public float health;

Entity(EntClass entClass, float size, Modifier m) {
	this.w = Game.activeWorld;
		this.health = maxHealth;
	this.entClass = entClass;
	this.size = size;
}

Entity(EntClass entClass, float size) {
	this(entClass, size, null);
}

Entity(float size) {
	this(baseEnt, size);
}

boolean placed = false;
public Entity place(int x, int z) {
	if (placed)
		try {
			throw new Exception();
		} catch (Exception e) {}
	this.x = x;
	this.z = z;
	
	//w.getTile(x, z).place(this);
	
	/***********************************************************************************
	 * 									IMPORTANT!!!								   *
	 ***********************************************************************************/
	
	
	return this;
}

public void update(long dtime) {
	
}

public boolean moveTo(int x, int z) {
	this.x = x;
	this.z = z;
	return true;   //Rewrite if necessary.
}

public void attack() {
	
}

public void die() {   //Write this up here.  TODO: Entity.die()

}

/************************************************************************/

private int teamID = -1;

public void joinTeam(int id) {
	if (teamID == -1)
		teamID = id;
}

public int getTeamID() {
	return teamID;
}

/*********************************************************************************************/

public boolean isImmobile() { return entClass.isImmobile(); }

public boolean isNotMortal() { return entClass.isNotMortal(); }

public boolean isIntangible() { return entClass.isIntangible(); }

public boolean isVaporous() { return entClass.isVaporous() > 0; }

}

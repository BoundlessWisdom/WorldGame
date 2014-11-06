package com.archonica;

import static com.archonica.EntClass.*;

import java.util.ArrayList;

import com.game.Archonica;

public abstract class Entity implements Updateable {
public final EntClass entClass;

public final float size;

protected float baseSpeed;
public float speed;

World w;
public int x, z;

protected final int maxHealth = 0;
public float health;

protected Entity(EntClass entClass, float size, float speed) {
	this.w = Archonica.activeWorld;
		this.health = maxHealth;
	this.entClass = entClass;
	this.size = size;
	this.baseSpeed = speed;
	this.speed = this.baseSpeed;
}

protected Entity(float size, float speed) {
	this(baseEnt, size, speed);
}

public Entity modify(Modifier m) {
	return this;
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

public ArrayList<AttachedEffect> attachedEffects = new ArrayList<AttachedEffect>();

public void addEffect(AttachedEffect effect) {
	attachedEffects.add(effect);
}

public void update(long dtime) {
	for (AttachedEffect e : attachedEffects)
		e.update(dtime);
	think(dtime);
}

public abstract void think(long dtime);

public boolean moveTo(int x, int z) {	//Debug function
	this.x = x;
	this.z = z;
	return true;
}

public void attack() {	//Debug function
	
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

public void goRogue() {
	teamID = -1;
}

/*********************************************************************************************/

public boolean isImmobile() { return entClass.isImmobile(); }

public boolean isNotMortal() { return entClass.isNotMortal(); }

public boolean isIntangible() { return entClass.isIntangible(); }

public boolean isVaporous() { return entClass.isVaporous(); }

}

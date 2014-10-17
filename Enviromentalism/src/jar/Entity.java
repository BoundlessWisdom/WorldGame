package jar;

import static jar.EntClass.*;

public abstract class Entity implements Updateable {
protected Base base;

public final EntClass entClass;

public final float size;

World w;
public int x, z;

public final float maxHealth;
public float health;
public final int baseAttack;

Entity(Base b, EntClass entClass, float size, Modifier m) {
	this.w = Game.activeWorld;
	this.base = b;
		this.maxHealth = b.maxHealth;
		this.health = maxHealth;
		this.baseAttack = b.attack;
	this.entClass = entClass;
	this.size = size;
}

Entity(Base b, EntClass entClass, float size) {
	this(b, entClass, size, null);
}

Entity(Base b, float size) {
	this(b, baseEnt, size);
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

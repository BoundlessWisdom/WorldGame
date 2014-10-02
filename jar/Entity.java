package jar;

import static jar.EntClass.*;

public abstract class Entity {
protected Base base;

public final EntClass entClass;

public final float size;

World w;
public int x, z;

Entity(Base b, float size) {
	this(b, baseEnt, size);
}

Entity(Base b, EntClass entClass, float size) {
	this.w = Game.activeWorld;
	this.base = b;
	this.entClass = entClass;
	this.size = size;
}

public Entity place(int x, int z) {
	this.x = x;
	this.z = z;
	
	
	//w.worldMap[x][z].add(this);
	
	/***********************************************************************************
	 * 									IMPORTANT!!!								   *
	 ***********************************************************************************/
	
	
	return this;
}

public boolean move() {
	return false;   //Write this up here.
}

public void die() {   //Write this up here.

}

public int getTeamID() {
	return base.getTeamID();
}

/*********************************************************************************************/

public boolean isImmobile() { return entClass.isImmobile(); }

public boolean isNotMortal() { return entClass.isNotMortal(); }

public boolean isIntangible() { return entClass.isIntangible(); }

public boolean isVaporous() { return entClass.isVaporous() > 0; }

}

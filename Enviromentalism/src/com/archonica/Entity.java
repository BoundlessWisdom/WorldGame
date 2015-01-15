package com.archonica;

import static com.archonica.EntClass.*;

import java.util.ArrayList;

import com.archonica.effects.Modifier;
import com.engine.core.GameObject;
import com.game.Archonica;
import com.game.EntityObject;

public abstract class Entity extends EntityObject {
public final EntClass entClass;

public final float size;

protected float baseSpeed;
protected float speed;

public float baseSpeed() { return baseSpeed; }
public float speed() { return speed; }

protected final int maxHealth = 0;
public float health;

protected Entity(EntClass entClass, float size, float speed) {
	super(new GameObject(), size);
	this.world = Archonica.activeWorld;
		this.health = maxHealth;
	this.entClass = entClass;
	this.size = size;
	this.baseSpeed = speed;
	this.speed = this.baseSpeed;
}

protected Entity(float size, float speed) {
	this(baseEnt, size, speed);
}

boolean placed = false;
public Entity place(int x, int z) {
	if (placed)
		try {
			throw new Exception();
		} catch (Exception e) {}
	this.x = x;
	this.z = z;
	
	//world.getTile(x, z).place(this);
	
	
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

public void die() {   //Write this up here.

}

/************************************************************************/

public void respond() {
	for(Modifier m : modifiers)
	{
		
	}
}

/*********************************************************************************************/

public boolean isImmobile() { return entClass.isImmobile(); }

public boolean isNotMortal() { return entClass.isNotMortal(); }

public boolean isIntangible() { return entClass.isIntangible(); }

public boolean isVaporous() { return entClass.isVaporous(); }

}

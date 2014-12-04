package debug2D.archonica;

import static com.archonica.EntClass.*;

import java.util.ArrayList;

import debug2D.archonica.Debug;
import com.archonica.AttachedEffect;
import com.archonica.Modifier;
import com.archonica.Updateable;
import com.engine.core.EntityObject;
import com.engine.core.GameObject;
import com.game.Archonica;

public class Entity extends EntityObject implements Updateable {
public final com.archonica.EntClass entClass;

public final float size;

protected float baseSpeed;
protected float speed;

public char identifier;

public float baseSpeed() { return baseSpeed; }
public float speed() { return speed; }

public int x, z;

protected final int maxHealth = 0;
public float health;

protected float strength = -1.0f;  //-1 == uninitialized; -2 == permanently zero;

protected float toughness;

protected Entity(com.archonica.EntClass baseEnt, float size, float speed) {
	super(new GameObject(), size, "");
	this.health = maxHealth;
	this.entClass = baseEnt;
	this.size = size;
	this.baseSpeed = speed;
	this.speed = this.baseSpeed;
}

protected Entity(float size, float speed) {
	this(baseEnt, size, speed);
}

protected void setStrength(float s) {
	if (this.strength == -1)  //Uninitialized state
		this.strength = s;
}

public float strength() {
	return strength;
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

public void think(long dtime) {
}

public boolean moveTo(int x, int z) {	//Debug function
	if(Debug.isTile(x,  z))
	{

	((Tile)Debug.worldArray[this.x][this.z]).entities.remove(this);
	
	this.x = x;
	this.z = z;
	
	((Tile)Debug.worldArray[this.x][this.z]).entities.add(this);
	
	return true;
	}
	
	return false;
}

public void attack() {	//Debug function
	
}

public void die() {   //Write this up here.  TODO: Entity.die()

}

/************************************************************************/

private ArrayList<Modifier> modifiers = new ArrayList<Modifier>();

public void affect(Modifier m) {
	modifiers.add(m);
}

public void respond() {
	for(Modifier m : modifiers)
	{
		speed += m.getSpeedChange();
		strength += m.getStrengthChange();
		toughness += m.getToughnessChange();
		health += m.getHealthChange();
		
	}
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

public void print()
{
	Debug.worldArray[(2 * x) - 1][(2 * z) - 1].print(false, false);
}
public boolean isImmobile() { return entClass.isImmobile(); }

public boolean isNotMortal() { return entClass.isNotMortal(); }

public boolean isIntangible() { return entClass.isIntangible(); }

public boolean isVaporous() { return entClass.isVaporous(); }

}

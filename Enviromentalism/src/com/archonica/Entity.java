package com.archonica;

import static com.archonica.EntClass.*;

import java.util.ArrayList;

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

World w;
public int x, z;
public float colrad;

protected final int maxHealth = 0;
public float health;

protected float strength = -1.0f;  //-1 == uninitialized; -2 == permanently zero;

protected float toughness;

protected Entity(EntClass entClass, float size, float speed) {
	super(new GameObject(), size);
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

public void worldmanage()
{
	this.x = (int)(this.GetTransform().GetPos().GetX()) / 2;
	this.z = (int)(this.GetTransform().GetPos().GetZ()) / 2;
	
	boolean add = true;
	
	if(w.worldMap[x][z].stack.size() > 0)
	{
		for(Entity e : w.worldMap[x][z].stack)
		{
			if(e == this)
				add = false;
		}
	}
	
	if(add)
		w.worldMap[x][z].stack.add(this);
	int oldx = -1;
	int oldz = -1;
	for(int i = x - 1; i < x + 2; i++)
	{
		for(int j = z - 1; j < z + 2; j++)
		{
			if(i == x && j == z)
				continue;
			if((x == 0 && i == -1) || (z == 0 && j == -1))
				continue;
			if(w.worldMap[i][j].stack.size() > 0)
			{
				for(Entity e : w.worldMap[i][j].stack)
				{
					if(e == this)
					{
						oldx = i;
						oldz = j;
					}
				}
			}
		}
		if(oldx >= 0 && oldz >= 0)
			w.worldMap[oldx][oldz].stack.remove(this);
	}
}

public void collisioncheck()
{
	ArrayList<int[]> filledtileindex = new ArrayList<int[]>();
	boolean nearbyobjects = false;
	for(int i = x - 1; i < x + 2; i++)
	{
		for(int j = z - 1; j < z + 2; j++)
		{
			if(i == -1 || j == -1)
				continue;
			if(i == x && j == z)
			{
				if(w.worldMap[i][j].stack.size() >= 2)
				{
					nearbyobjects = true;
					int[] index = new int[2];
					index[0] = i;
					index[1] = j;
					filledtileindex.add(index);
				}
			}
			else if(w.worldMap[i][j].stack.size() >= 1)
			{
				nearbyobjects = true;
				int[] index = new int[2];
				index[0] = i;
				index[1] = j;
				filledtileindex.add(index);
			}
		}
	}
	//System.out.println("This");
	//System.out.println(this.x);
	//System.out.println(this.z);
	if(nearbyobjects)
	{
		for(int[] tiles : filledtileindex)
		{
			for(Entity second : w.worldMap[tiles[0]][tiles[1]].stack)
			{
				//System.out.println("Near");
				//System.out.println(tiles[0]);
				//System.out.println(tiles[1]);
				if(this.GetTransform().GetPos().minus(second.GetTransform().GetPos()).Length() <= (this.colrad + second.colrad))
					collide(this, second);
			}
		}
	}
}

public void collide(Entity first, Entity second)
{
	System.out.println("Collision!");
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

public boolean isImmobile() { return entClass.isImmobile(); }

public boolean isNotMortal() { return entClass.isNotMortal(); }

public boolean isIntangible() { return entClass.isIntangible(); }

public boolean isVaporous() { return entClass.isVaporous(); }

}

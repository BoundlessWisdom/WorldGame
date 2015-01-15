package com.game;

import java.util.ArrayList;

import com.archonica.Entity;
import com.archonica.Updateable;
import com.archonica.World;
import com.archonica.effects.LongModifier;
import com.archonica.effects.Modifier;
import com.engine.core.GameObject;

public abstract class EntityObject extends com.engine.core.EntityObject implements Updateable {



protected World world;
public int x, z;

//Adds to world entity arraylist.
public void addThis() {
	Archonica.activeWorld.add(this);
}

public EntityObject() { super(); }
public EntityObject(EntityObject e) { super(e); }
public EntityObject(GameObject o, double m) { super(o, m); }

public float colrad;

public void worldmanage()
{
	this.x = (int)(this.GetTransform().GetPos().GetX()) / 2;
	this.z = (int)(this.GetTransform().GetPos().GetZ()) / 2;
	
	boolean add = true;
	
	world.worldMap[x][z].attemptPlacement(this);
	
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
			if(world.worldMap[i][j].occupied())
			{
				
					if(world.worldMap[i][j].equals(this))
					{
						oldx = i;
						oldz = j;
					}
			}
		}
		if(oldx >= 0 && oldz >= 0)
			world.worldMap[oldx][oldz].stack.remove(this);
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
				if(world.worldMap[i][j].stack.size() >= 2)
				{
					nearbyobjects = true;
					int[] index = new int[2];
					index[0] = i;
					index[1] = j;
					filledtileindex.add(index);
				}
			}
			else if(world.worldMap[i][j].stack.size() >= 1)
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
			for(EntityObject second : world.worldMap[tiles[0]][tiles[1]].stack)
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

public void collide(EntityObject first, EntityObject second)
{
	System.out.println("Collision!");
}

/************************************************************************/

protected ArrayList<Modifier> modifiers = new ArrayList<Modifier>();
protected ArrayList<LongModifier> longModifiers = new ArrayList<LongModifier>();

public void modify(Modifier m) {
	modifiers.add(m);
}

public void modify(LongModifier m) {
	longModifiers.add(m);
}

protected ArrayList<Integer> deleteQueue = new ArrayList<Integer>();

public void respond() {
	modifiers = new ArrayList<Modifier>();
	
	for (int i = 0; i < deleteQueue.size(); i++) {
		longModifiers.remove(deleteQueue.get(i) - i);
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

}

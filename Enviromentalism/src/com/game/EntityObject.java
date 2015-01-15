package com.game;

import java.util.ArrayList;

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

}

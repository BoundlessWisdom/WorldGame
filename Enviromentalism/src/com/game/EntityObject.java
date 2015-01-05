package com.game;

import com.archonica.Updateable;
import com.engine.core.GameObject;

public abstract class EntityObject extends com.engine.core.EntityObject implements Updateable {
	
	public void addThis() {
		Archonica.activeWorld.add(this);
	}
	
	public EntityObject() { super(); }
	public EntityObject(EntityObject e) { super(e); }
	public EntityObject(GameObject o, double m) { super(o, m); }
	
}

package com.archonica.elementals;

import static com.archonica.EntClass.*;

public abstract class AbstractEntity extends Elemental {
	
	protected float abstractSize;
	protected float abstractSpeed;
	protected float abstractStrength;

	public AbstractEntity(float size, int speed) {
		super(abstractEnt, 0.0f, 0);
		this.abstractSize = size;
		this.abstractSpeed = speed;
	}
	
	protected void setStrength(float s) {
		if (this.abstractStrength == -1)  //Uninitialized state
			this.abstractStrength = s;
		super.setStrength(s);
	}
	
	public float size() {
		return abstractSize;
	}
	
	public float speed() {
		return abstractSpeed;
	}
	
	public float strength() {
		return abstractStrength;
	}

}

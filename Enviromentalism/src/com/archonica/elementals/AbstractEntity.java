package com.archonica.elementals;

import static com.archonica.EntClass.*;

public abstract class AbstractEntity extends Elemental {
	
	protected float abstractSize;
	protected float abstractSpeed;

	public AbstractEntity(float size, int speed) {
		super(abstractEnt, 0.0f, 0);
		this.abstractSize = size;
		this.abstractSpeed = speed;
	}
	
	public float size() {
		return abstractSize;
	}
	
	public float speed() {
		return abstractSpeed;
	}

}

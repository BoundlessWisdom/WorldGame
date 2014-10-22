package com.archonica;

public class Explosion extends Entity {
	private float radius;  //TODO: Explosion: How to detect what just entered range.
	final float speed;

	Explosion(float size, Modifier m, float speed) {
		super(EntClass.abstractEnt, size, m);
		this.speed = speed;
	}
	
	Explosion(float size, float speed) {
		this(size, null, speed);
	}
	
	Explosion(float size) {
		this(size, 0);
	}
	
	public void update(long dtime) {
		radius += speed * dtime;
	}
	
	public float radius() {
		return radius;
	}
}

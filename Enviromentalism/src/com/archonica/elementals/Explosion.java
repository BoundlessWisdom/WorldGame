package com.archonica.elementals;

public class Explosion extends AbstractEntity {
	private float radius;

	public Explosion(float size, int speed) { super(size, speed); }
	
	Explosion(float size) {
		this(size, 0);
	}
	
	public float radius() {
		return radius;
	}
	
	public void update(long dtime) {
		super.update(dtime);
		radius += speed * dtime;
	}
	
	public void think(long dtime) {
	}

}

package com.nathandsimon.lwjglgameutils;

import org.lwjgl.util.vector.Vector3f;

public interface IObject {
	public double getMass();
	public String getName();
	public Vector3f getPos();
	public Model getSprite();
	public void move(float x, float y, float z);
	public void setAcceleration(Vector3f accel);
	public Vector3f getAcceleration();
	public void setVelocity(Vector3f vel);
	public Vector3f getVelocity();
}

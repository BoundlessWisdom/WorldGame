package com.nathandsimon.lwjglgameutils;

import org.lwjgl.util.vector.Vector3f;

public interface IObject {
	public double getMass();
	public String getName();
	public Vector3f getPos();
	public Model getSprite();
	public double getMu();
	public double getElasticConstant();
	public Vector3f getMomentum();
	public void move(float x, float y, float z);
	public void setAcceleration(Vector3f accel);
	public Vector3f getAcceleration();
	public void setVelocity(Vector3f vel);
	public Vector3f getVelocity();
	public int getIndex();
	public void setIndex(int index);
	public double getDragConstant();
	public double getCrossSectionArea();
	public boolean isFlying();
	public void setFlying(boolean flying);
	public float getRotation(int axis);
	public void rotate(float rotation, int axis);
	public boolean isCompound();
	static int X_AXIS = 0;
	static int Y_AXIS = 1;
	static int Z_AXIS = 2;
}

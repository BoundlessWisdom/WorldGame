package com.nathandsimon.lwjglgameutils;

import java.io.File;
import java.io.IOException;

import org.lwjgl.util.vector.Vector3f;

public abstract class Item implements IObject{
	private Vector3f m_pos = new Vector3f();
	private Model m_sprite = null;
	//For physics
	private double mass = 0;
	//For indexing and storage, etc.
	private String name = "";
	private Vector3f a = new Vector3f(0,0,0);
	private Vector3f v  = new Vector3f(0,0,0);
	private Vector3f P = new Vector3f(0,0,0);
	private int index;
	private boolean flying = false;
	private boolean updated = false;
	/**
	 * @param x x-coordinate
	 * @param y y-coordinate
	 * @param z z-coordinate
	 * @param mass mass for physics engine
	 * @param model_loc location on disk of the entity's model
	 * @param name name of the entity
	 */
	public Item(float x, float y, float z, double mass, String model_loc, String name)
	{
		m_pos.x = x;
		m_pos.y = y;
		m_pos.z = z;
		try {
			m_sprite = OBJLoader.loadModel(new File(model_loc));
		} catch (IOException e) {
			e.printStackTrace();
			Game.end();
		}
		this.mass = mass;
		this.name = name;
	}
	/**
	 * @return the item's sprite (model).
	 */
	@Override
	public Model getSprite()
	{
		return m_sprite;
	}
	/**
	 * Move around.
	 */
	@Override
	public void move(float x, float y, float z)
	{
		m_pos.x+=x;
		m_pos.y+=y;
		m_pos.z+=z;
	}
	/**
	 * @return the item's position.
	 */
	@Override
	public Vector3f getPos()
	{
		return m_pos;
	}
	/**
	 * @return the item's mass.
	 */
	@Override
	public double getMass()
	{
		return mass;
	}
	/**
	 * @return the item's name.
	 */
	@Override
	public String getName()
	{
		return name;
	}
	/**
	 * Set the acceleration.
	 * @param accel the acceleration vector.
	 */
	@Override
	public void setAcceleration(Vector3f accel)
	{
		a = accel;
	}
	/**
	 * @return the acceleration.
	 */
	@Override
	public Vector3f getAcceleration()
	{
		return a;
	}
	/**
	 * Set the velocity.
	 * @param vel the velocity.
	 */
	@Override
	public void setVelocity(Vector3f vel)
	{
		v = vel;
	}
	/**
	 * @return the velocity.
	 */
	@Override
	public Vector3f getVelocity()
	{
		return v;
	}
	/**
	 * @return the index of the object in the engine.
	 */
	@Override
	public int getIndex()
	{
		return index;
	}
	/**
	 * Set what the item thinks its index is.
	 * @param index the index.
	 */
	@Override
	public void setIndex(int index)
	{
		this.index = index;
	}
	/**
	 * @return the momentum vector.
	 */
	@Override
	public Vector3f getMomentum()
	{
		return P;
	}
	@Override
	public boolean isFlying()
	{
		return flying;
	}
	@Override
	public void setFlying(boolean flying) 
	{
		this.flying  = flying;
		if(flying)
		{
			v.y = 0;
		}
	}
	private float thetax = 0;
	private float thetay = 0;
	private float thetaz = 0;
	public float getRotation(int axis)
	{
		switch(axis)
		{
		case 0: 
			return thetax;
		case 1:
			return thetay;
		case 2:
			return thetaz;
		default: 
			return 0;
		}
	}
	public void rotate(float rotation, int axis)
	{
		switch(axis)
		{
		case 0: 
			thetax += rotation;
			break;
		case 1:
			thetay += rotation;
			break;
		case 2:
			thetaz += rotation;
			break;
		default: 
			break;
		}
		updated = true;
	}
	public boolean isCompound()
	{
		return true;
	}
	public boolean hasUpdated()
	{
		if(updated)
		{
			updated = false;
			return true;
		}
		return false;
	}
}

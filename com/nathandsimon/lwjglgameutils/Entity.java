package com.nathandsimon.lwjglgameutils;

import java.io.File;
import java.io.IOException;

import org.lwjgl.util.vector.Vector3f;
/**
 * @author Nathan
 */
public abstract class Entity implements IObject {
	private Vector3f m_pos = new Vector3f();
	private Model m_sprite = null;
	//For physics
	private double mass = 0;
	//For indexing and storage, etc.
	private String name = "";
	//For the physics engine
	private Vector3f a = new Vector3f(0,0,0);
	private Vector3f v  = new Vector3f(0,0,0);
	private Vector3f P = new Vector3f(0,0,0);
	//For the rendering and physics engines
	private int index;
	//For the game
	private int health = 10000;
	private boolean flying = false;
	/**
	 * @param x x-coordinate.
	 * @param y y-coordinate.
	 * @param z z-coordinate.
	 * @param mass mass for physics engine.
	 * @param model_loc location on disk of the entity's model.
	 * @param name name of the entity.
	 */
	public Entity(float x, float y, float z, double mass, String model_loc, String name)
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
	 * @return the entity's sprite (model).
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
	 * @return the entity's position.
	 */
	@Override
	public Vector3f getPos()
	{
		return m_pos;
	}
	/**
	 * @return the mass of the entity.
	 */
	@Override
	public double getMass()
	{
		return mass;
	}
	/**
	 * @return the name of the entity.
	 */
	@Override
	public String getName()
	{
		return name;
	}
	/**
	 * @return the entity's remaining health.
	 */
	public int getHealth()
	{
		return health;
	}
	/**
	 * Add health.
	 * @param h the amount of health.
	 */
	public void addHealth(int h)
	{
		health += h;
		if(health <=0)
		{
			die();
		}
	}
	/**
	 * Subtract health.
	 * @param h the amount of health.
	 */
	public void subtractHealth(int h)
	{
		health -= h;
		if(health <= 0)
		{
			die();
		}
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
	 * Die, removing the entity from the game.
	 */
	public void die()
	{
		Game.getInstance().removeObject(this);
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
	 * Set what the entity thinks its index is.
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
		this.flying = flying;
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
	}
}

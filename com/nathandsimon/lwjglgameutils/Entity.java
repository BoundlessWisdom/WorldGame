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
	/**
	 * @param x x-coordinate
	 * @param y y-coordinate
	 * @param z z-coordinate
	 * @param mass mass for physics engine
	 * @param model_loc location on disk of the entity's model
	 * @param name name of the entity
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
	public Model getSprite()
	{
		return m_sprite;
	}
	public void move(float x, float y, float z)
	{
		m_pos.x+=x;
		m_pos.y+=y;
		m_pos.z+=z;
	}
	/**
	 * @return the entity's position
	 */
	public Vector3f getPos()
	{
		return m_pos;
	}
	public double getMass()
	{
		return mass;
	}
	public String getName()
	{
		return name;
	}
	public int getHealth()
	{
		return health;
	}
	public void addHealth(int h)
	{
		health += h;
		if(health <=0)
		{
			die();
		}
	}
	public void subtractHealth(int h)
	{
		health -= h;
		if(health <= 0)
		{
			die();
		}
	}
	@Override
	public void setAcceleration(Vector3f accel) {
		a = accel;
	}
	@Override
	public Vector3f getAcceleration() {
		return a;
	}
	@Override
	public void setVelocity(Vector3f vel) {
		v = vel;
	}
	@Override
	public Vector3f getVelocity() {
		return v;
	}
	public abstract void die();
	public int getIndex() {
		return index;
	}
	public void setIndex(int index) {
		this.index = index;
	}
	@Override
	public Vector3f getMomentum()
	{
		return P;
	}
}
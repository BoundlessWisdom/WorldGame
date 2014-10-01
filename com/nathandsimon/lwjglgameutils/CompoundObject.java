package com.nathandsimon.lwjglgameutils;

import java.util.HashMap;

import org.lwjgl.util.vector.Vector3f;


public abstract class CompoundObject<T extends IObject> implements IObject{
	private HashMap<String, T> children = new HashMap<String, T>();
	private Vector3f m_pos = new Vector3f();
	//For physics
	private double mass = 0;
	//For indexing and storage, etc.
	private String name = "";
	private Vector3f a = new Vector3f(0,0,0);
	private Vector3f v  = new Vector3f(0,0,0);
	private Vector3f P = new Vector3f(0,0,0);
	private int index;
	private boolean flying = false;
	public HashMap<String, T> getChildren(){
		return children;
	}
	public void addChild(String key, T child){
		children.put(key, child);
	}
	public T getChild(String key){
		return children.get(key);
	}
	@Override
	public Model getSprite()
	{
		Model ret = new Model();
		for(T t : children.values())
		{
			ret.getVertices().addAll(t.getSprite().getVertices());
			ret.getNormals().addAll(t.getSprite().getNormals());
			ret.getFaces().addAll(t.getSprite().getFaces());
			ret.getMaterials().putAll(t.getSprite().getMaterials());
			ret.getTextureCoordinates().addAll(t.getSprite().getTextureCoordinates());
		}
		return ret;
	}
	@Override
	public Vector3f getPos()
	{
		return m_pos;
	}
	@Override
	public void move(float x, float y, float z)
	{
		m_pos.x+=x;
		m_pos.y+=y;
		m_pos.z+=z;
		for(T t : children.values())
		{
			t.move(x, y, z);
		}
	}
	@Override
	public double getMass()
	{
		return mass;
	}
	@Override
	public String getName()
	{
		return name;
	}
	@Override
	public void setAcceleration(Vector3f accel)
	{
		a = accel;
		for(T t : children.values())
		{
			t.setAcceleration(accel);
		}
	}
	@Override
	public Vector3f getAcceleration()
	{
		return a;
	}
	@Override
	public void setVelocity(Vector3f vel)
	{
		v = vel;
		for(T t : children.values())
		{
			t.setVelocity(vel);
		}
	}
	@Override
	public Vector3f getVelocity()
	{
		return v;
	}
	@Override
	public int getIndex()
	{
		return index;
	}
	@Override
	public void setIndex(int index)
	{
		this.index = index;
	}
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
		for(T t : children.values())
		{
			t.setFlying(flying);
		}
	}
}

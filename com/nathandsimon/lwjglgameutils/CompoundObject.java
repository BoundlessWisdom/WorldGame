package com.nathandsimon.lwjglgameutils;

import java.util.HashMap;

import org.lwjgl.util.vector.Vector3f;

import com.nathandsimon.lwjglgameutils.Model.Face;


public abstract class CompoundObject implements IObject{
	private HashMap<String, IObject> children = new HashMap<String, IObject>();
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
	public CompoundObject(float x, float y, float z, String name, double mass)
	{
		m_pos.set(x, y, z);
		this.name = name;
		this.mass = mass;
	}
	public HashMap<String, IObject> getChildren(){
		return children;
	}
	public void addChild(String key, IObject child){
		children.put(key, child);
	}
	public IObject getChild(String key){
		return children.get(key);
	}
	@Override
	public Model getSprite()
	{
		
		Model ret = new Model();
		for(IObject t : children.values())
		{
			int vcurr = ret.getVertices().size();
			int ncurr = ret.getNormals().size();
			int tccurr = ret.getTextureCoordinates().size();
			for(Vector3f vec : t.getSprite().getVertices())
			{
				vec.x += t.getPos().x;
				vec.y += t.getPos().y;
				vec.z += t.getPos().z;
			}
			ret.getVertices().addAll(t.getSprite().getVertices());
			ret.getNormals().addAll(t.getSprite().getNormals());
			for(Face f : t.getSprite().getFaces())
			{
				f.getVertexIndices()[0] += vcurr;
				f.getVertexIndices()[1] += vcurr;
				f.getVertexIndices()[2] += vcurr;
				f.getNormalIndices()[0] += ncurr;
				f.getNormalIndices()[1] += ncurr;
				f.getNormalIndices()[2] += ncurr;
				f.getTextureCoordinateIndices()[0] += tccurr;
				f.getTextureCoordinateIndices()[1] += tccurr;
				f.getTextureCoordinateIndices()[2] += tccurr;
			}
			ret.getFaces().addAll(t.getSprite().getFaces());
			ret.getMaterials().putAll(t.getSprite().getMaterials());
			ret.getTextureCoordinates().addAll(t.getSprite().getTextureCoordinates());
		}
		float lowest = 9000;
		for(Vector3f vec : ret.getVertices())
		{
			if(vec.y < lowest)
			{
				lowest = vec.y;
			}
		}
		for(Vector3f vec : ret.getVertices())
		{
			vec.y -= lowest;
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
		for(IObject t : children.values())
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
		for(IObject t : children.values())
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
		for(IObject t : children.values())
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
		for(IObject t : children.values())
		{
			t.setFlying(flying);
		}
	}
}

package com.nathandsimon.lwjglgameutils;

import java.util.HashMap;

import org.lwjgl.util.vector.Matrix4f;
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
		for(IObject t : getChildren().values())
		{
			int vcurr = ret.getVertices().size();
			int ncurr = ret.getNormals().size();
			int tccurr = ret.getTextureCoordinates().size();
			for(Vector3f q : t.getSprite().getVertices())
			{
				Vector3f vec = new Vector3f(q);
				vec.x += t.getPos().x;
				vec.y += t.getPos().y;
				vec.z += t.getPos().z;
				Matrix4f mat = new Matrix4f();
				mat.m03 = vec.x;
				mat.m13 = vec.y;
				mat.m23 = vec.z;
				mat.rotate((float) Math.toRadians(t.getRotation(2)), new Vector3f(0,0,1));
				mat.rotate((float) Math.toRadians(t.getRotation(1)), new Vector3f(0,1,0));
				mat.rotate((float) Math.toRadians(t.getRotation(0)), new Vector3f(1,0,0));
				vec.x = mat.m03;
				vec.y = mat.m13;
				vec.z = mat.m23;
				ret.getVertices().add(new Vector3f(vec));
			}
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
				ret.getFaces().add(new Face(f.getVertexIndices(),f.getNormalIndices(),f.getTextureCoordinateIndices(),f.getMaterial()));
			}
			for(Face f : t.getSprite().getFaces())
			{
				f.getVertexIndices()[0] -= vcurr;
				f.getVertexIndices()[1] -= vcurr;
				f.getVertexIndices()[2] -= vcurr;
				f.getNormalIndices()[0] -= ncurr;
				f.getNormalIndices()[1] -= ncurr;
				f.getNormalIndices()[2] -= ncurr;
				f.getTextureCoordinateIndices()[0] -= tccurr;
				f.getTextureCoordinateIndices()[1] -= tccurr;
				f.getTextureCoordinateIndices()[2] -= tccurr;
			}
			ret.getMaterials().putAll(t.getSprite().getMaterials());
			ret.getTextureCoordinates().addAll(t.getSprite().getTextureCoordinates());
		}
		float lowesty = 9000;
		for(Vector3f vec : ret.getVertices())
		{
			if(vec.y < lowesty)
			{
				lowesty = vec.y;
			}
		}
		for(Vector3f vec :  ret.getVertices())
		{
			vec.y -= lowesty;
		}
		float lowestx = 9000;
		for(Vector3f vec : ret.getVertices())
		{
			if(vec.x < lowestx)
			{
				lowestx = vec.x;
			}
		}
		for(Vector3f vec : ret.getVertices())
		{
			vec.x -= lowestx;
		}
		float lowestz = 9000;
		for(Vector3f vec : ret.getVertices())
		{
			if(vec.z < lowestz)
			{
				lowestz = vec.z;
			}
		}
		for(Vector3f vec : ret.getVertices())
		{
			vec.z -= lowestz;
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
			for(IObject t : children.values())
			{
				t.rotate(rotation,0);
			}
			break;
		case 1:
			thetay += rotation;
			for(IObject t : children.values())
			{
				t.rotate(rotation,1);
			}
			break;
		case 2:
			thetaz += rotation;
			for(IObject t : children.values())
			{
				t.rotate(rotation,2);
			}
			break;
		default: 
			break;
		}
	}
	public boolean isCompound()
	{
		return true;
	}
}

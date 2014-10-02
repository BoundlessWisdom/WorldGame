package com.nathandsimon.lwjglgameutils;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

import com.nathandsimon.lwjglgameutils.Model.Face;

public abstract class CompoundEntity extends CompoundObject implements IAlive {

	public CompoundEntity(float x, float y, float z, String name, double mass, int health) {
		super(x, y, z, name, mass);
		this.health = health;
	}
	private int health;
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
	public void subtractHealth(int h)
	{
		health -= h;
		if(health <= 0)
		{
			die();
		}
	}
	public void die() 
	{
		Game.getInstance().removeObject(this);	
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
			for(Vector3f vec : t.getSprite().getVertices())
			{
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
		float lowesty = 9000;
		for(Vector3f vec : ret.getVertices())
		{
			if(vec.y < lowesty)
			{
				lowesty = vec.y;
			}
		}
		for(Vector3f vec : ret.getVertices())
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
}

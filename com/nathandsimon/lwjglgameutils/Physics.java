package com.nathandsimon.lwjglgameutils;

import java.util.ArrayList;

import org.lwjgl.util.vector.Vector3f;
public class Physics {
	private static ArrayList<Vector3f> forces = new ArrayList<Vector3f>();
	private static ArrayList<IObject> objs = new ArrayList<IObject>();
	public static void add(IObject o)
	{
		objs.add(o);
		forces.add(new Vector3f(0,0,0));
	}
	public static void applyForce(int index, Vector3f force, boolean add)
	{
		if(index < objs.size())
		{
			if(add)
			{
				forces.get(index).set(forces.get(index).x + force.x, forces.get(index).y + force.y, forces.get(index).z + force.z);
			}
			else
			{
				forces.get(index).set(force);
			}
		}
		else return;
	}
	public static void zeroForce(int index)
	{
		if(index < objs.size())
		{
			forces.get(index).set(0,0,0);
		}
		else return;
	}
	public static void applyGravity()
	{
		for(int i = 0; i < objs.size(); i++)
		{
				applyForce(i, new Vector3f(0f, -0.17f*(float)objs.get(i).getMass(),0f), false);	
		}
	}
	public static void update(long elapsedTime)
	{
			for(int i = 0; i < objs.size(); i++)
			{
				if(objs.get(i).getVelocity().y <= -objs.get(i).getPos().y&&forces.get(i).y < 0)
				{
					forces.get(i).y = 0;
					objs.get(i).getVelocity().setY(0);
					objs.get(i).getPos().y = 0;
				}				
				objs.get(i).getAcceleration().x = (float) (forces.get(i).x/objs.get(i).getMass());
				objs.get(i).getAcceleration().y = (float) (forces.get(i).y/objs.get(i).getMass());
				objs.get(i).getAcceleration().z = (float) (forces.get(i).z/objs.get(i).getMass());
				objs.get(i).getVelocity().x += objs.get(i).getAcceleration().x;
				objs.get(i).getVelocity().y += objs.get(i).getAcceleration().y;
				objs.get(i).getVelocity().z += objs.get(i).getAcceleration().z;
				objs.get(i).move(objs.get(i).getVelocity().x, objs.get(i).getVelocity().y, objs.get(i).getVelocity().z);
			}
		
	}
}

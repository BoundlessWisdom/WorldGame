package com.nathandsimon.lwjglgameutils;

import java.util.ArrayList;

import org.lwjgl.util.vector.Vector3f;
/**
 * A simple physics engine.
 * @author Nathan
 *
 */
public class PhysicsEngine extends EngineComponent {
	private ArrayList<Vector3f> forces;
	private ArrayList<IObject> objs;
	public PhysicsEngine()
	{
		init();
	}
	public void init()
	{
		forces  = new ArrayList<Vector3f>();
		objs  = new ArrayList<IObject>();
	}
	/**
	 * Adds an object to the physics engine.
	 * @param o object to add.
	 */
	public void add(IObject o)
	{
		o.setIndex(objs.size());
		objs.add(o);
		forces.add(new Vector3f(0,0,0));
	}
	public void remove(IObject o)
	{
		objs.remove(o.getIndex());
		forces.remove(o.getIndex());
		for(int i = o.getIndex(); i < objs.size(); i++)
		{
			objs.get(i).setIndex(i);
		}
	}
	/**
	 * Applies a force to an object. Assumes not to apply impulse.
	 * @param index the object's index.
	 * @param force the force vector.
	 * @param add whether to add the force to the current value(true) or to set the current value to the value provided(false).
	 */
	public void applyForce(int index, Vector3f force, boolean add)
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
	/**
	 * Applies a force to an object.
	 * @param index index the object's index.
	 * @param force the force vector.
	 * @param add whether to add the force to the current value(true) or to set the current value to the value provided(false).
	 * @param impulse whether to apply impulse.
	 */
	public void applyForce(int index, Vector3f force, boolean add, boolean impulse)
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
			if(impulse)
			{
				applyImpulse(index, force);
			}
		}
		else return;
	}
	/**
	 * Applies friction to a moving object.
	 * @param index the object's index.
	 * @param gndHt The height of the ground.
	 */
	public void applyMovingFriction(int index, float gndHt)
	{
		if(index < objs.size())
		{
			
			double mu = objs.get(index).getMu();
			double fN = objs.get(index).getPos().y <= gndHt ? forces.get(index).getY() : 0.1;
			
			double percentXMotion = objs.get(index).getAcceleration().x  != 0 ? objs.get(index).getAcceleration().x / (objs.get(index).getAcceleration().x +objs.get(index).getAcceleration().y  + objs.get(index).getAcceleration().z) : 0;
			double percentYMotion = objs.get(index).getAcceleration().y  != 0 ? objs.get(index).getAcceleration().y / (objs.get(index).getAcceleration().x +objs.get(index).getAcceleration().y  + objs.get(index).getAcceleration().z) : 0;
			double percentZMotion = objs.get(index).getAcceleration().z  != 0 ? objs.get(index).getAcceleration().z / (objs.get(index).getAcceleration().x +objs.get(index).getAcceleration().y  + objs.get(index).getAcceleration().z) : 0;
			double finalX = -mu * fN * percentXMotion;
			double finalY = -mu * fN * percentYMotion;
			double finalZ = -mu * fN * percentZMotion;
			if(objs.get(index).getVelocity().x == 0)
			{
				finalX=0;
			}
			if(objs.get(index).getVelocity().y == 0)
			{
				finalY=0;
			}
			if(objs.get(index).getVelocity().z == 0)
			{
				finalZ=0;
			}
			if(Math.abs(finalX / objs.get(index).getMass()) > Math.abs(objs.get(index).getVelocity().x))
			{
				objs.get(index).getVelocity().x = 0;
				finalX = 0;
			}
			if(Math.abs(finalY / objs.get(index).getMass()) > Math.abs(objs.get(index).getVelocity().y))
			{
				objs.get(index).getVelocity().y = 0;
				finalY = 0;
			}
			if(Math.abs(finalZ / objs.get(index).getMass()) > Math.abs(objs.get(index).getVelocity().z))
			{
				objs.get(index).getVelocity().z = 0;
				finalZ = 0;
			}
			applyForce(index, new Vector3f((float)(finalX), (float)(finalY), (float)(finalZ)), true);
		}
	}
	/**
	 * Sets the force on an object to zero.
	 * @param index the object's index.
	 */
	public void zeroForce(int index)
	{
		if(index < objs.size())
		{
			forces.get(index).set(0,0,0);
		}
		else return;
	}
	/**
	 * Applies gravity to all objects.
	 */
	public void applyGravity()
	{
		for(int i = 0; i < objs.size(); i++)
		{
				applyGravity(i);	
		}
	}
	/**
	 * Applies gravity to a specific object.
	 * @param i the object's index.
	 */
	public void applyGravity(int i)
	{
		applyForce(i, new Vector3f(0f, -0.17f*(float)objs.get(i).getMass(),0f), false);	
	}
	/**
	 * Applies an impulse to an object.
	 * @param index the object's index.
	 * @param impulse the impulse to apply.
	 */
	public void applyImpulse(int index, Vector3f impulse)
	{
		objs.get(index).getMomentum().x += impulse.x;
		objs.get(index).getMomentum().y += impulse.y;
		objs.get(index).getMomentum().z += impulse.z;
		objs.get(index).getVelocity().x = (float) (impulse.x / objs.get(index).getMass());
		objs.get(index).getVelocity().y = (float) (impulse.y / objs.get(index).getMass());
		objs.get(index).getVelocity().z = (float) (impulse.z / objs.get(index).getMass());
	}
	/**
	 * Updates the momentum values of an object.
	 * @param index the object's index.
	 */
	public void updateMomentum(int index)
	{
		objs.get(index).getMomentum().x = (float) (objs.get(index).getVelocity().x * objs.get(index).getMass());
		objs.get(index).getMomentum().y = (float) (objs.get(index).getVelocity().y * objs.get(index).getMass());
		objs.get(index).getMomentum().z = (float) (objs.get(index).getVelocity().z * objs.get(index).getMass());
	}
	/**
	 * Updates the world.
	 * @param elapsedTime the time elapsed since the start of the game, in frames.
	 */
	public void run(long elapsedTime)
	{
			for(int i = 0; i < objs.size(); i++)
			{
				applyGravity(i);
				updateMomentum(i);
				applyMovingFriction(i, 0);
				if(objs.get(i).getVelocity().y <= -objs.get(i).getPos().y && forces.get(i).y < 0)
				{
					if(Math.abs(objs.get(i).getVelocity().y) < 0.2 || objs.get(i).getPos().y < 0.1)
					{
						objs.get(i).getMomentum().y = 0;
						objs.get(i).getVelocity().y = 0;
					}
					applyForce(i, new Vector3f((float) (forces.get(i).x),(float) (-objs.get(i).getMomentum().y*objs.get(i).getElasticConstant()),(float) (forces.get(i).z)) ,false,true);
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
	public void dispose()
	{
		for(int i = 0; i < objs.size(); i++)
		{
			remove(objs.get(i));
		}
	}
}

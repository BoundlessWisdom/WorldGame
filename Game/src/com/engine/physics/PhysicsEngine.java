package com.engine.physics;

import com.engine.core.*;

import java.util.*;

public class PhysicsEngine 
{
	private ArrayList<Vector3f> forces;
	private ArrayList<EntityObject> objs;
	private boolean gravityEnabled = true;
	private boolean airEnabled = false;
	private boolean frictionEnabled = false;
	public static final double rho = .02;
	public static final float g = .17f;
	//boolean freeFall = false;
	
	private static PhysicsEngine instance = new PhysicsEngine();
	
	private PhysicsEngine()
	{
		init();
	}
	
	public static PhysicsEngine getInstance()
	{
		return instance;
	}
	
	/**
	 * Initialize the engine.
	 */
	public void init()
	{
		forces  = new ArrayList<Vector3f>();
		objs  = new ArrayList<EntityObject>();
	}
	/**
	 * Adds an object to the physics engine.
	 * @param o object to add.
	 */
	public void add(EntityObject o)
	{
		o.setIndex(objs.size());
		objs.add(o);
		forces.add(new Vector3f(0,0,0));
	}
	/**
	 * Remove an object from the physics engine.
	 * @param o object to remove.
	 */
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
	 * Updates the world.
	 * @param elapsedTime the time elapsed since the start of the game, in frames.
	 */
	public ArrayList<EntityObject> run(double elapsedTime)
	{
			for(int i = 0; i < objs.size(); i++)
			{
				/*if(objs.get(i).getPos().getY() > .1 && Math.abs(objs.get(i).getVelocity().getY()) > 0)
				{
					freeFall = true;
				}
				else
					freeFall = false;*/
				
				//if(objs.get(i).getVelocity().getY() < .1 && objs.get(i).getPos().getY() > 9.5 && objs.get(i).getPos().getY() < 10.5)
					//System.out.println("Woo");
				
				if(gravityEnabled && !objs.get(i).isFlying())
					applyGravity(i);
				
				if(frictionEnabled)
					applyMovingFriction(i);
				if(airEnabled)
					applyAirResistance(i);
				updateMomentum(i);
				if(objs.get(i).getVelocity().getY() <= -objs.get(i).getPos().getY() && forces.get(i).getY() < 0)
				{
					float ec = (float)objs.get(i).getElasticConstant();
					float fy = (float)-forces.get(i).getY();//-forces.get(i).getY();
					float nY = Math.abs(objs.get(i).getVelocity().getY()) > 0.15  ? fy * ec : 0;
					
					applyForce(i, 
							new Vector3f((float)(forces.get(i).getX()), nY/*(float)(-forces.get(i).getY()*objs.get(i).getElasticConstant())*/,(float)(forces.get(i).getZ())),
							false,true);
					
					objs.get(i).getVelocity().setY(0);
					objs.get(i).getPos().setY(0);
				}
				//Vector3f velocity = objs.get(i).getVelocity();
				
				objs.get(i).getAcceleration().set(new Vector3f(
						(float)(forces.get(i).getX()/objs.get(i).getMass()),
						(float)(forces.get(i).getY()/objs.get(i).getMass()),
						(float)(forces.get(i).getZ()/objs.get(i).getMass())
				));
				
				objs.get(i).getVelocity().add(new Vector3f(
						objs.get(i).getAcceleration().getX(),
						objs.get(i).getAcceleration().getY()* .1f,
						objs.get(i).getAcceleration().getZ()
				));
				
				objs.get(i).move(objs.get(i).getVelocity().getX(), 
						objs.get(i).getVelocity().getY(), 
						objs.get(i).getVelocity().getZ());
			}
			
			return objs;
	}
	/**
	 * Clean up.
	 */
	public void dispose()
	{
		for(int i = 0; i < objs.size(); i++)
		{
			remove(objs.get(i));
		}
	}
	/**
	 * Apply a force.
	 * @param obj the object.
	 * @param force the force vector.
	 */
	public void force(IObject obj, Vector3f force)
	{
		applyForce(obj.getIndex(), force, true);
	}
	public boolean isGravityEnabled() 
	{
		return gravityEnabled;
	}
	public void setGravityEnabled(boolean enabled) 
	{
		this.gravityEnabled = enabled;
	}
	public boolean isAirResistanceEnabled() 
	{
		return airEnabled;
	}
	public void setAirResistanceEnabled(boolean enabled) 
	{
		this.airEnabled = enabled;
	}
	public boolean isFrictionEnabled() 
	{
		return frictionEnabled;
	}
	public void setFrictionEnabled(boolean enabled) 
	{
		this.frictionEnabled = enabled;
	}
	/*@Override
	public ComponentType getType() 
	{
		return EngineComponent.ComponentType.physics;
	}*/
	/**
	 * Applies a force to an object. Assumes not to apply impulse.
	 * @param index the object's index.
	 * @param force the force vector.
	 * @param add whether to add the force to the current value(true) or to set the current value to the value provided(false).
	 */
	private void applyForce(int index, Vector3f force, boolean add)
	{
		if(index < objs.size())
		{
			if(add)
			{
				forces.get(index).set(forces.get(index).getX() + force.getX(), 
						forces.get(index).getY() + force.getY(), 
						forces.get(index).getZ() + force.getZ());
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
	private void applyForce(int index, Vector3f force, boolean add, boolean impulse)
	{
		if(index < objs.size())
		{
			if(add)
			{
				forces.get(index).add(new Vector3f(
						force.getX(),
						force.getY(),
						force.getZ()
				));
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
	 * Applies an impulse to an object.
	 * @param index the object's index.
	 * @param impulse the impulse to apply.
	 */
	private void applyImpulse(int index, Vector3f impulse)
	{
		objs.get(index).getMomentum().add(new Vector3f(
				impulse.getX(),
				impulse.getY(),
				impulse.getZ()
		));
		
		Vector3f momentum = objs.get(index).getMomentum();
		
		objs.get(index).getVelocity().set((float) (momentum.getX() / objs.get(index).getMass()), 
				(float) (momentum.getY() / objs.get(index).getMass()), 
				(float) (momentum.getZ() / objs.get(index).getMass()));
	}
	/**
	 * Applies friction to a moving object.
	 * @param index the object's index.
	 * @param gndHt The height of the ground.
	 */
	private void applyMovingFriction(int index)
	{
		if(index < objs.size())
		{
			double mu = objs.get(index).getMu();
			double fN = objs.get(index).getPos().getY() <= 0 ? forces.get(index).getY() : 0;
			double percentXMotion = objs.get(index).getVelocity().getX()  != 0 ? Math.cos(Math.atan(objs.get(index).getVelocity().getY()/objs.get(index).getVelocity().getX())) : 0;
			double percentYMotion = objs.get(index).getVelocity().getY()  != 0 ? Math.sin(Math.atan(objs.get(index).getVelocity().getY()/objs.get(index).getVelocity().getX())) : 0;
			double percentZMotion = objs.get(index).getVelocity().getZ()  != 0 ? Math.sin(Math.atan(objs.get(index).getVelocity().getZ()/objs.get(index).getVelocity().getX())) : 0;
			double finalX = mu * fN * percentXMotion;
			double finalY = mu * fN * percentYMotion;
			double finalZ = mu * fN * percentZMotion;
			if(objs.get(index).getVelocity().getX() == 0)
			{
				finalX=0;
			}
			if(objs.get(index).getVelocity().getY() == 0)
			{
				finalY=0;
			}
			if(objs.get(index).getVelocity().getZ() == 0)
			{
				finalZ=0;
			}
			if(Math.abs(finalX / objs.get(index).getMass()) > Math.abs(objs.get(index).getVelocity().getX()))
			{
				objs.get(index).getVelocity().setX(0);
				finalX = 0;
			}
			if(Math.abs(finalY / objs.get(index).getMass()) > Math.abs(objs.get(index).getVelocity().getY()))
			{
				objs.get(index).getVelocity().setY(0);
				finalY = 0;
			}
			if(Math.abs(finalZ / objs.get(index).getMass()) > Math.abs(objs.get(index).getVelocity().getZ()))
			{
				objs.get(index).getVelocity().setZ(0);
				finalZ = 0;
			}
			applyForce(index, new Vector3f((float)(finalX), (float)(finalY), (float)(finalZ)), true);
		}
	}
	/**
	 * Applies air resistance to an object.
	 * @param index the object's index.
	 */
	private void applyAirResistance(int index)
	{
		float dotprod = objs.get(index).getVelocity().dot(objs.get(index).getVelocity());
		//float dotprod = Vector3f.dot(objs.get(index).getVelocity(), objs.get(index).getVelocity());
		double halfpcda = .5 * rho * objs.get(index).getDragConstant() * objs.get(index).getCrossSectionArea();
		double percentXMotion = objs.get(index).getVelocity().getX()  != 0 ? Math.cos(Math.atan(objs.get(index).getVelocity().getZ()/objs.get(index).getVelocity().getX())) : 0;
		double percentYMotion = objs.get(index).getVelocity().getY()  != 0 ? Math.sin(Math.atan(objs.get(index).getVelocity().getY()/objs.get(index).getVelocity().getX())) : 0;
		double percentZMotion = objs.get(index).getVelocity().getZ()  != 0 ? Math.sin(Math.atan(objs.get(index).getVelocity().getZ()/objs.get(index).getVelocity().getX())) : 0;
		float Finalx = (float) (-dotprod*halfpcda*percentXMotion);
		float Finaly = (float) (-dotprod*halfpcda*percentYMotion);
		float Finalz = (float) (-dotprod*halfpcda*percentZMotion);
		applyForce(index, new Vector3f(Finalx, Finaly, Finalz), true);
	}
	/**
	 * Applies gravity to a specific object.
	 * @param i the object's index.
	 */
	private void applyGravity(int i)
	{
		applyForce(i, new Vector3f(forces.get(i).getX(), (float) (-g*objs.get(i).getMass()),
				forces.get(i).getZ()), false);	
	}
	/**
	 * Updates the momentum values of an object.
	 * @param index the object's index.
	 */
	private void updateMomentum(int index)
	{
		objs.get(index).getMomentum().set(new Vector3f(
				(float)(objs.get(index).getVelocity().getX() * objs.get(index).getMass()),
				(float)(objs.get(index).getVelocity().getY() * objs.get(index).getMass()),
				(float)(objs.get(index).getVelocity().getZ() * objs.get(index).getMass())
		));
    }
	
	public ArrayList<EntityObject> getObjects()
	{
		return objs;
	}
}

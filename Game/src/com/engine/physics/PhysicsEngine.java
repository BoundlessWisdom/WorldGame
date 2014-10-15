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
	public static final float g = .163f;
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
				//Streamlining variable gets.
				Vector3f force = forces.get(i);
				
				EntityObject obj = objs.get(i);
				Vector3f velocity = obj.getVelocity();
				Vector3f acceleration = obj.getAcceleration();
				
				if(gravityEnabled && !obj.isFlying())
					applyGravity(i);
				
				if(frictionEnabled)
					applyMovingFriction(i);
				if(airEnabled)
					applyAirResistance(i);
				updateMomentum(i);
				if(velocity.getY() <= -obj.getPos().getY() && force.getY() < 0)
				{
					float ec = (float)obj.getElasticConstant();
					float fy = (float)-force.getY(); //-force.getY();
					float nY = Math.abs(velocity.getY()) > 0.15  ? fy * ec : 0;
					
					applyForce(i, new Vector3f((float) (force.getX()), nY /*(float)(-force.getY()*obj.getElasticConstant())*/, (float) (force.getZ())),
							false, true);
					
					velocity.setY(0);
					obj.getPos().setY(0);
				}
				
				acceleration.set(new Vector3f(
						(float)(force.getX() / obj.getMass()),
						(float)(force.getY() / obj.getMass()),
						(float)(force.getZ() / obj.getMass())
				));
				
				velocity.add(new Vector3f(
						acceleration.getX(),
						acceleration.getY() * .1f,
						acceleration.getZ()
				));
				
				obj.move(velocity.getX(), 
						 velocity.getY(), 
						 velocity.getZ()
				);
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
	
	public void zeroForce(IObject obj)
	{
		int i = obj.getIndex();
		applyForce(i, new Vector3f(0,0,0), false);
		if(isGravityEnabled())
		{
			applyGravity(i);
		}
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
		Vector3f netForce = forces.get(index);
		
		if(index < objs.size())
		{
			if(add)
			{
				netForce.set(netForce.getX() + force.getX(), 
							 netForce.getY() + force.getY(), 
							 netForce.getZ() + force.getZ());
			}
			else
			{
				netForce.set(force);
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
		Vector3f netForce = forces.get(index);
		
		if(index < objs.size())
		{
			if(add)
			{
				netForce.add(new Vector3f(
						force.getX(),
						force.getY(),
						force.getZ()
				));
			}
			else
			{
				netForce.set(force);
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
		EntityObject obj = objs.get(index);
		
		obj.getMomentum().add(new Vector3f(
				impulse.getX(),
				impulse.getY(),
				impulse.getZ()
		));
		
		Vector3f momentum = obj.getMomentum();
		double mass = obj.getMass();
		
		obj.getVelocity().set((float) (momentum.getX() / mass), 
							  (float) (momentum.getY() / mass), 
							  (float) (momentum.getZ() / mass));
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
			EntityObject obj = objs.get(index);
			Vector3f velocity = obj.getVelocity();
			Double mass = obj.getMass();
			
			double mu = obj.getMu();
			double fN = obj.getPos().getY() <= 0 ? forces.get(index).getY() : 0;
			double percentXMotion = velocity.getX()  != 0 ? Math.cos(Math.atan(velocity.getY() / velocity.getX())) : 0;
			double percentYMotion = velocity.getY()  != 0 ? Math.sin(Math.atan(velocity.getY() / velocity.getX())) : 0;
			double percentZMotion = velocity.getZ()  != 0 ? Math.sin(Math.atan(velocity.getZ() / velocity.getX())) : 0;
			double finalX = mu * fN * percentXMotion;
			double finalY = mu * fN * percentYMotion;
			double finalZ = mu * fN * percentZMotion;
			if(velocity.getX() == 0)
			{
				finalX=0;
			}
			if(velocity.getY() == 0)
			{
				finalY=0;
			}
			if(velocity.getZ() == 0)
			{
				finalZ=0;
			}
			if(Math.abs(finalX / mass) > Math.abs(velocity.getX()))
			{
				velocity.setX(0);
				finalX = 0;
			}
			if(Math.abs(finalY / mass) > Math.abs(velocity.getY()))
			{
				velocity.setY(0);
				finalY = 0;
			}
			if(Math.abs(finalZ / mass) > Math.abs(velocity.getZ()))
			{
				velocity.setZ(0);
				finalZ = 0;
			}
			applyForce(index, new Vector3f((float)finalX, (float)finalY, (float)finalZ) , true);
		}
	}
	
	/**
	 * Applies air resistance to an object.
	 * @param index the object's index.
	 */
	private void applyAirResistance(int index)
	{
		EntityObject obj = objs.get(index);
		Vector3f velocity = obj.getVelocity();
		
		float dotprod = velocity.dot(velocity);
		//float dotprod = Vector3f.dot(velocity, velocity);
		double halfpcda = .5 * rho * obj.getDragConstant() * obj.getCrossSectionArea();
		double percentXMotion = velocity.getX() != 0 ? Math.cos(Math.atan(velocity.getZ()/velocity.getX())) : 0;
		double percentYMotion = velocity.getY() != 0 ? Math.sin(Math.atan(velocity.getY()/velocity.getX())) : 0;
		double percentZMotion = velocity.getZ() != 0 ? Math.sin(Math.atan(velocity.getZ()/velocity.getX())) : 0;
		float FinalX = (float) (-dotprod * halfpcda * percentXMotion);
		float FinalY = (float) (-dotprod * halfpcda * percentYMotion);
		float FinalZ = (float) (-dotprod * halfpcda * percentZMotion);
		applyForce(index, new Vector3f(FinalX, FinalY, FinalZ), true);
		//Uhh, Nathan, I just capitalized the coordinate, 'cause Finaly looked like Finally.
	}
	
	/**
	 * Applies gravity to a specific object.
	 * @param i the object's index.
	 */
	private void applyGravity(int i)
	{
		applyForce(i, new Vector3f(forces.get(i).getX(), (float) (-g * objs.get(i).getMass()),
				forces.get(i).getZ()), false);	
	}
	//TODO: Ask Nathan about applyGravity logic.
	
	/**
	 * Updates the momentum values of an object.
	 * @param index the object's index.
	 */
	private void updateMomentum(int index)
	{
		EntityObject obj = objs.get(index);
		Vector3f velocity = obj.getVelocity();
		double mass = obj.getMass();
		
		obj.getMomentum().set(new Vector3f(
				(float)(velocity.getX() * mass),
				(float)(velocity.getY() * mass),
				(float)(velocity.getZ() * mass)
		));
    }
	
	public ArrayList<EntityObject> getObjects()
	{
		return objs;
	}
}

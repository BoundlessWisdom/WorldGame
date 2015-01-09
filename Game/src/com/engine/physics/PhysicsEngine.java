package com.engine.physics;

import com.engine.components.terrain.CompleteTerrain;
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
	
	public static CompleteTerrain terrain = null;
	
	private static PhysicsEngine instance = new PhysicsEngine();
	
	private PhysicsEngine()
	{
		init();
	}
	/**
	 * @return the current instance.
	 */
	public static PhysicsEngine GetInstance()
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
		o.SetIndex(objs.size());
		objs.add(o);
		forces.add(new Vector3f(0,0,0));
	}
	
	/**
	 * Remove an object from the physics engine.
	 * @param o object to remove.
	 */
	public void remove(EntityObject o)
	{
		objs.remove(o.GetIndex());
		forces.remove(o.GetIndex());
		for(int i = o.GetIndex(); i < objs.size(); i++)
		{
			objs.get(i).SetIndex(i);
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
				//Streamlining variable Gets.
				Vector3f force = forces.get(i);
				
				EntityObject obj = objs.get(i);
				Vector3f velocity = obj.GetVelocity();
				Vector3f acceleration = obj.GetAcceleration();
				//If the object is not flying
				if(gravityEnabled && !obj.isFlying())
					ApplyGravity(i);
				//If there is friction
				if(frictionEnabled)
					ApplyMovingFriction(i);
				//If there is air resistance
				if(airEnabled)
					ApplyAirResistance(i);
				UpdateMomentum(i);
				//Object's height
				float h = 0.0f;
				//If we are on the ground
				if(-velocity.GetY() <= -obj.GetPos().GetY() + (h = terrain.GetHeight(obj.GetTransform().GetPos().GetXZ())) && force.GetY() < 0)
				{
					float ec = (float)obj.GetElasticConstant();
					float fy = (float)-force.GetY(); //-force.GetY();
					float nY = Math.abs(velocity.GetY()) > 0.15  ? fy * ec : 0;
					//bounce
					ApplyForce(i, new Vector3f((float) (force.GetX()), nY /*(float)(-force.GetY()*obj.GetElasticConstant())*/, (float) (force.GetZ())),
							false, true);
					
					velocity.SetY(0);
					obj.GetPos().SetY(h);
				}
				//Update acceleration
				acceleration.Set(new Vector3f(
						(float)(force.GetX() / obj.GetMass()),
						(float)(force.GetY() / obj.GetMass()),
						(float)(force.GetZ() / obj.GetMass())
				));
				//Update velocity
				velocity.add(new Vector3f(
						acceleration.GetX(),
						acceleration.GetY() * .1f,
						acceleration.GetZ()
				));
				//Update position
				obj.move(velocity.GetX(), 
						 velocity.GetY(), 
						 velocity.GetZ()
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
	public void Force(EntityObject obj, Vector3f force)
	{
		ApplyForce(obj.GetIndex(), force, true);
	}
	/**
	 * Sets the force acting on an object to zero.
	 * @param obj the object.
	 */
	public void ZeroForce(EntityObject obj)
	{
		int i = obj.GetIndex();
		ApplyForce(i, new Vector3f(0,0,0), false);
		if(IsGravityEnabled())
		{
			ApplyGravity(i);
		}
	}
	/**
	 * @return the state of gravity (enabled = true, disabled = false).
	 */
	public boolean IsGravityEnabled() 
	{
		return gravityEnabled;
	}
	/**
	 * Enables or disables gravity.
	 * @param enabled whether it is to be enabled.
	 */
	public void SetGravityEnabled(boolean enabled) 
	{
		this.gravityEnabled = enabled;
	}
	/**
	 * @return the state of air resistance (enabled = true, disabled = false).
	 */
	public boolean IsAirResistanceEnabled() 
	{
		return airEnabled;
	}
	/**
	 * Enables or disables air resistance.
	 * @param enabled whether it is to be enabled.
	 */
	public void SetAirResistanceEnabled(boolean enabled) 
	{
		this.airEnabled = enabled;
	}
	/**
	 * @return the state of friction (enabled = true, disabled = false).
	 */
	public boolean IsFrictionEnabled() 
	{
		return frictionEnabled;
	}
	/**
	 * Enables or disables friction.
	 * @param enabled whether it is to be enabled.
	 */
	public void SetFrictionEnabled(boolean enabled) 
	{
		this.frictionEnabled = enabled;
	}
	
	/*@Override
	public ComponentType GetType() 
	{
		return EngineComponent.ComponentType.physics;
	}*/
	
	/**
	 * Applies a force to an object. Assumes not to apply impulse.
	 * @param index the object's index.
	 * @param force the force vector.
	 * @param add whether to add the force to the current value(true) or to Set the current value to the value provided(false).
	 */
	private void ApplyForce(int index, Vector3f force, boolean add)
	{
		Vector3f netForce = forces.get(index);
		
		if(index < objs.size())
		{
			if(add)
			{
				netForce.Set(netForce.GetX() + force.GetX(), 
							 netForce.GetY() + force.GetY(), 
							 netForce.GetZ() + force.GetZ());
			}
			else
			{
				netForce.Set(force);
			}
		}
		else return;
	}
	
	/**
	 * Applies a force to an object.
	 * @param index index the object's index.
	 * @param force the force vector.
	 * @param add whether to add the force to the current value(true) or to Set the current value to the value provided(false).
	 * @param impulse whether to apply impulse.
	 */
	private void ApplyForce(int index, Vector3f force, boolean add, boolean impulse)
	{
		Vector3f netForce = forces.get(index);
		
		if(index < objs.size())
		{
			if(add)
			{
				netForce.add(new Vector3f(
						force.GetX(),
						force.GetY(),
						force.GetZ()
				));
			}
			else
			{
				netForce.Set(force);
			}
			if(impulse)
			{
				ApplyImpulse(index, force);
			}
		}
		else return;
	}
	
	/**
	 * Applies an impulse to an object.
	 * @param index the object's index.
	 * @param impulse the impulse to apply.
	 */
	private void ApplyImpulse(int index, Vector3f impulse)
	{
		EntityObject obj = objs.get(index);
		
		obj.GetMomentum().add(new Vector3f(
				impulse.GetX(),
				impulse.GetY(),
				impulse.GetZ()
		));
		
		Vector3f momentum = obj.GetMomentum();
		double mass = obj.GetMass();
		
		obj.GetVelocity().Set((float) (momentum.GetX() / mass), 
							  (float) (momentum.GetY() / mass), 
							  (float) (momentum.GetZ() / mass));
	}
	
	/**
	 * Applies friction to a moving object.
	 * @param index the object's index.
	 * @param gndHt The height of the ground.
	 */
	private void ApplyMovingFriction(int index)
	{
		if(index < objs.size())
		{
			EntityObject obj = objs.get(index);
			Vector3f velocity = obj.GetVelocity();
			Double mass = obj.GetMass();
			
			double mu = obj.GetMu();
			double fN = obj.GetPos().GetY() <= terrain.GetHeight(obj.GetTransform().GetPos().GetXZ()) ? forces.get(index).GetY() : 0;
			//Scale vector components.
			double percentXMotion = velocity.GetX()  != 0 ? Math.cos(Math.atan(velocity.GetY() / velocity.GetX())) : 0;
			double percentYMotion = velocity.GetY()  != 0 ? Math.sin(Math.atan(velocity.GetY() / velocity.GetX())) : 0;
			double percentZMotion = velocity.GetZ()  != 0 ? Math.sin(Math.atan(velocity.GetZ() / velocity.GetX())) : 0;
			double finalX = mu * fN * percentXMotion;
			double finalY = mu * fN * percentYMotion;
			double finalZ = mu * fN * percentZMotion;
			//Disregard friction on a motionless object.
			if(velocity.GetX() == 0)
			{
				finalX=0;
			}
			if(velocity.GetY() == 0)
			{
				finalY=0;
			}
			if(velocity.GetZ() == 0)
			{
				finalZ=0;
			}
			//Make sure we don't reverse the direction of the object, just stop it.
			if(Math.abs(finalX / mass) > Math.abs(velocity.GetX()))
			{
				velocity.SetX(0);
				finalX = 0;
			}
			if(Math.abs(finalY / mass) > Math.abs(velocity.GetY()))
			{
				velocity.SetY(0);
				finalY = 0;
			}
			if(Math.abs(finalZ / mass) > Math.abs(velocity.GetZ()))
			{
				velocity.SetZ(0);
				finalZ = 0;
			}
			ApplyForce(index, new Vector3f((float)finalX, (float)finalY, (float)finalZ) , true);
		}
	}
	
	/**
	 * Applies air resistance to an object.
	 * @param index the object's index.
	 */
	private void ApplyAirResistance(int index)
	{
		EntityObject obj = objs.get(index);
		Vector3f velocity = obj.GetVelocity();
		
		float dotprod = velocity.Dot(velocity);
		//Consolidate our constants.
		double halfpcda = .5 * rho * obj.GetDragConstant() * obj.GetCrossSectionArea();
		//Scale vector components.
		double percentXMotion = velocity.GetX() != 0 ? Math.cos(Math.atan(velocity.GetZ()/velocity.GetX())) : 0;
		double percentYMotion = velocity.GetY() != 0 ? Math.sin(Math.atan(velocity.GetY()/velocity.GetX())) : 0;
		double percentZMotion = velocity.GetZ() != 0 ? Math.sin(Math.atan(velocity.GetZ()/velocity.GetX())) : 0;
		float FinalX = (float) (-dotprod * halfpcda * percentXMotion);
		float FinalY = (float) (-dotprod * halfpcda * percentYMotion);
		float FinalZ = (float) (-dotprod * halfpcda * percentZMotion);
		ApplyForce(index, new Vector3f(FinalX, FinalY, FinalZ), true);
	}
	
	/**
	 * Applies gravity to a specific object.
	 * @param i the object's index.
	 */
	private void ApplyGravity(int i)
	{
		ApplyForce(i, new Vector3f(forces.get(i).GetX(), (float) (-g * objs.get(i).GetMass()),
				forces.get(i).GetZ()), false);	
	}
	
	/**
	 * Updates the momentum values of an object.
	 * @param index the object's index.
	 */
	private void UpdateMomentum(int index)
	{
		EntityObject obj = objs.get(index);
		Vector3f velocity = obj.GetVelocity();
		double mass = obj.GetMass();
		
		obj.GetMomentum().Set(new Vector3f(
				(float)(velocity.GetX() * mass),
				(float)(velocity.GetY() * mass),
				(float)(velocity.GetZ() * mass)
		));
    }
	
	public ArrayList<EntityObject> GetObjects()
	{
		return objs;
	}
}

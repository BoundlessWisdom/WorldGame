package com.engine.core;

import com.engine.physics.PhysicsEngine;
import com.engine.rendering.Mesh;

public abstract class EntityObject extends GameObject
{
	String name;
	
	public double mass = 0;
	
	protected Vector3f a = new Vector3f(0,0,0);
	protected Vector3f v = new Vector3f(0,0,0);
	protected Vector3f P = new Vector3f(0,0,0); //momentum
	
	protected int index;
	
	protected int health = 10000;
	protected boolean flying = false;
	
	protected double elasticConstant = 0.0;
	
	protected Vector3f offset = new Vector3f(0, 0, 0);
		
	@Override
	public void Update(float delta) 
	{
		super.Update(delta);
		GetTransform().SetPos(GetTransform().GetPos().Add(offset));
	}
	
	public EntityObject()
	{
		super();
	}
	
	public EntityObject(EntityObject nObj)
	{
		this(nObj, nObj.GetMass(), nObj.GetName());
	}
	
	public EntityObject(GameObject gameObj, double mass, String name)
	{
		super();
		
		this.mass = mass;
		this.name = name;
		
		Set(gameObj);
	}

	public double GetMass() {
		return mass;
	}

	public String GetName() {
		return name;
	}

	public Vector3f GetPos() {
		return GetTransform().GetPos();
	}

	public Mesh GetSprite() {
		return null;
	}

	public Vector3f GetMomentum() {
		return P;
	}

	public void move(float x, float y, float z) {
		GetTransform().GetPos().Added(new Vector3f(x, y, z));
		return;
	}

	public void SetAcceleration(Vector3f accel) {
		a = accel;
	}

	public Vector3f GetAcceleration() {
		return a;
	}

	public void SetVelocity(Vector3f vel) {
		v = vel;
	}

	public Vector3f GetVelocity() {
		return v;
	}

	public int GetIndex() {
		return index;
	}

	public void SetIndex(int index) {
		this.index = index;
	}

	public boolean isFlying() {
		return flying;
	}

	public void SetFlying(boolean flying) {
		this.flying = flying;
		if(flying)
		{
			v.SetY(0);
		}
	}
	
	public int GetHealth()
	{
		return health;
	}
	
	public void AddHealth(int h)
	{
		health += h;
		if(health <=0)
		{
			die();
		}
	}
	
	public void SubtractHealth(int h)
	{
		health -= h;
		if(health <= 0)
		{
			die();
		}
	}
	
	public void die()
	{
		//GameInstance.getInstance().removeEntity(this);
	}
	
	public void Set(GameObject gameObj)
	{
		this.m_components = gameObj.m_components;
		this.m_children = gameObj.m_children;
		this.m_transform = gameObj.m_transform;
	}

	public double GetElasticConstant() 
	{
		return elasticConstant;
	}
	
	public void fixElastic(double elastic)
	{
		this.elasticConstant = 150 * PhysicsEngine.g * elastic;
	}

	public double GetDragConstant() {
		return 0;
	}

	public double GetCrossSectionArea() {
		return 0;
	}	

	public double GetMu() {
		return 0;
	}
}

package com.engine.core;

import info.engine.extra.physics.IObject;

import com.engine.rendering.Mesh;

public abstract class EntityObject extends GameObject implements IObject
{
	String name;
	
	private double mass = 0;
	
	private Vector3f a = new Vector3f(0,0,0);
	private Vector3f v = new Vector3f(0,0,0);
	private Vector3f P = new Vector3f(0,0,0); //momentum
	
	private int index;
	
	private int health = 10000;
	private boolean flying = false;
	
	@Override
	public void update(float delta) {
		super.update(delta);
	}
	
	public EntityObject()
	{
		super();
	}
	
	public EntityObject(EntityObject nObj)
	{
		this(nObj, nObj.getMass(), nObj.getName());
	}
	
	public EntityObject(GameObject gameObj, double mass, String name)
	{
		super();
		
		this.mass = mass;
		this.name = name;
		
		set(gameObj);
	}

	@Override
	public double getMass() {
		return mass;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public Vector3f getPos() {
		return getTransform().getPos();
	}

	@Override
	public Mesh getSprite() {
		return null;
	}

	@Override
	public Vector3f getMomentum() {
		return P;
	}

	@Override
	public void move(float x, float y, float z) {
		getTransform().setPos(getTransform().getPos().add(new Vector3f(x, y, z)));
		return;
	}

	@Override
	public void setAcceleration(Vector3f accel) {
		a = accel;
	}

	@Override
	public Vector3f getAcceleration() {
		return a;
	}

	@Override
	public void setVelocity(Vector3f vel) {
		v = vel;
	}

	@Override
	public Vector3f getVelocity() {
		return v;
	}

	@Override
	public int getIndex() {
		return index;
	}

	@Override
	public void setIndex(int index) {
		this.index = index;
	}

	@Override
	public boolean isFlying() {
		return flying;
	}

	@Override
	public void setFlying(boolean flying) {
		this.flying = flying;
		if(flying)
		{
			v.setY(0);
		}
	}
	
	public int getHealth()
	{
		return health;
	}
	
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
		GameInstance.getInstance().removeEntity(this);
	}
	
	public void set(GameObject gameObj)
	{
		this.components = gameObj.components;
		this.children = gameObj.children;
		this.transform = gameObj.transform;
	}
}

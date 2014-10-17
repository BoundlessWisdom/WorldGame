package com.engine.core;

import com.engine.physics.PhysicsEngine;
import com.engine.rendering.Mesh;

public abstract class EntityObject extends GameObject
{
	String name;
	
	private double mass = 0;
	
	private Vector3f a = new Vector3f(0,0,0);
	private Vector3f v = new Vector3f(0,0,0);
	private Vector3f P = new Vector3f(0,0,0); //momentum
	
	private int index;
	
	private int health = 10000;
	private boolean flying = false;
	
	protected double elasticConstant = 0.0;
	
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

	public double getMass() {
		return mass;
	}

	public String getName() {
		return name;
	}

	public Vector3f getPos() {
		return getTransform().getPos();
	}

	public Mesh getSprite() {
		return null;
	}

	public Vector3f getMomentum() {
		return p;
	}

	public void move(float x, float y, float z) {
		getTransform().getPos().add(new Vector3f(x, y, z));
		return;
	}

	public void setAcceleration(Vector3f accel) {
		a = accel;
	}

	public Vector3f getAcceleration() {
		return a;
	}

	public void setVelocity(Vector3f vel) {
		v = vel;
	}

	public Vector3f getVelocity() {
		return v;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public boolean isFlying() {
		return flying;
	}

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

	public double getElasticConstant() 
	{
		return elasticConstant;
	}
	
	public void fixElastic(double elastic)
	{
		this.elasticConstant = 150 * PhysicsEngine.g * elastic;
	}

	public double getDragConstant() {
		return 0;
	}

	public double getCrossSectionArea() {
		return 0;
	}	

	public double getMu() {
		return 0;
	}
}

package com.engine.core;

import com.engine.physics.PhysicsEngine;
import com.engine.rendering.Mesh;

public abstract class EntityObject extends GameObject
{
	protected Vector3f cameraLock;
	
	String name;
	
	public double mass = 0;
	
	private Vector3f a = new Vector3f(0,0,0);
	private Vector3f v = new Vector3f(0,0,0);
	private Vector3f P = new Vector3f(0,0,0); //momentum
	
	private int index;
	
	private boolean flying = false;
	
	protected double elasticConstant = 0.0;
	
	@Override
	public void Update(float delta) {
		super.Update(delta);
	}
	
	public EntityObject()
	{
		super();
		lockCamera();
	}
	
	public EntityObject(EntityObject nObj)
	{
		this(nObj, nObj.GetMass(), nObj.GetName());
	}
	
	public EntityObject(GameObject gameObj, double mass, String name)
	{
		super();
		lockCamera();
		
		this.mass = mass;
		this.name = name;
		
		Set(gameObj);
	}
	
	protected abstract void lockCamera();  //Initializes locking point for camera track.
	
	/*****************************************************************************/

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
	
	public void move(Vector3f r) {
		GetTransform().GetPos().Added(r);
		return;
	}
	
	public void move(Vector3f r, float movAmt) {
		GetTransform().GetPos().Added(r.Mul(movAmt));
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
	
	public void die()
	{
		//GameInstance.getInstance().removeEntity(this);
	}
	
	public void Set(GameObject gameObj)
	{
		this.m_components = gameObj.m_components;
		this.m_currentRenderSet = gameObj.m_currentRenderSet;
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

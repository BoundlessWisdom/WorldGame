package info.engine.extra.physics;

import com.engine.core.Vector3f;
import com.engine.rendering.Mesh;

public interface IObject 
{	
	public double getMass();
	public String getName();
	public Vector3f getPos();
	public Mesh getSprite();
	public double getMu();
	public double getElasticConstant();
	public Vector3f getMomentum();
	public void move(float x, float y, float z);
	public void setAcceleration(Vector3f accel);
	public Vector3f getAcceleration();
	public void setVelocity(Vector3f vel);
	public Vector3f getVelocity();
	public int getIndex();
	public void setIndex(int index);
	public double getDragConstant();
	public double getCrossSectionArea();
	public boolean isFlying();
	public void setFlying(boolean flying);
}

package com.engine.components.lighting;

import com.engine.core.Vector3f;
import com.engine.rendering.shaders.ForwardSpot;

public class SpotLight extends PointLight
{
	private Vector3f direction;
	private float cutoff;
	
	public SpotLight(Vector3f color, float intensity, Vector3f atten, 
			Vector3f direction, float cutoff) 
	{
		super(color, intensity, atten);
		this.direction = direction.normalize();
		this.cutoff = cutoff;
		
		setShader(ForwardSpot.getInstance());
	}
	
	public Vector3f getDirection() {
		return direction;
	}
	public void setDirection(Vector3f direction) {
		this.direction = direction.normalize();
	}
	public float getCutoff() {
		return cutoff;
	}
	public void setCutoff(float cutoff) {
		this.cutoff = cutoff;
	}
}

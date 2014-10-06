package com.engine.components;

import com.engine.core.Vector3f;
import com.engine.rendering.shaders.ForwardDirectional;

public class DirectionalLight extends BaseLight 
{
	private Vector3f direction;
	
	public DirectionalLight(Vector3f color, float intensity, Vector3f direction)
	{
		super(color, intensity);
		this.direction = direction.normalize();
		
		setShader(ForwardDirectional.getInstance());
	}

	public Vector3f getDirection() {
		return direction;
	}

	public void setDirection(Vector3f direction) {
		this.direction = direction;
	}
}

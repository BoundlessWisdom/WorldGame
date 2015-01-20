package com.engine.components.lighting;

import com.engine.core.Vector3f;
import com.engine.rendering.Shader;

public class DirectionalLight extends BaseLight
{
	public DirectionalLight(Vector3f color, float intensity)
	{
		super(color, intensity);

		SetShader(new Shader("forward-directional"));
	}

	public Vector3f GetDirection()
	{
		return GetTransform().GetTransformedRot().GetForward();
	}
}

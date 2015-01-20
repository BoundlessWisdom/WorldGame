package com.engine.components.lighting;

import com.engine.components.GameComponent;
import com.engine.core.CoreEngine;
import com.engine.core.Vector3f;
import com.engine.rendering.Shader;

public class BaseLight extends GameComponent
{
	private Vector3f m_color;
	private float    m_intensity;
	private Shader   m_shader;
	
	public BaseLight(Vector3f color, float intensity)
	{
		this.m_color = color;
		this.m_intensity = intensity;
	}

	@Override
	public void AddToEngine()
	{
		CoreEngine.GetRenderingEngine().AddLight(this);
	}

	public void SetShader(Shader shader)
	{
		this.m_shader = shader;
	}

	public Shader GetShader()
	{
		return m_shader;
	}

	public Vector3f GetColor()
	{
		return m_color;
	}

	public void SetColor(Vector3f color)
	{
		this.m_color = color;
	}

	public float GetIntensity()
	{
		return m_intensity;
	}

	public void SetIntensity(float intensity)
	{
		this.m_intensity = intensity;
	}
}

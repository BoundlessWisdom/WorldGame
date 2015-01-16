package com.engine.components;

import com.engine.core.*;

public class Camera extends GameComponent
{
	private Matrix4f m_projection;

	public Camera(Matrix4f projection)
	{
		super();
		this.m_projection = projection;
	}

	public Matrix4f GetViewProjection()
	{
		Matrix4f cameraRotation = GetTransform().GetTransformedRot().Conjugate().ToRotationMatrix();
		Vector3f cameraPos = GetTransform().GetTransformedPos().Mul(-1);

		Matrix4f cameraTranslation = new Matrix4f().InitTranslation(cameraPos.GetX(), cameraPos.GetY(), cameraPos.GetZ());

		return m_projection.Mul(cameraRotation.Mul(cameraTranslation));
	}

	@Override
	public void AddToEngine()
	{
		CoreEngine.GetRenderingEngine().AddCamera(this);
	}
	
	
}

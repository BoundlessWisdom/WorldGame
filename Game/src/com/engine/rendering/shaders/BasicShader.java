package com.engine.rendering.shaders;

import com.engine.core.Matrix4f;
import com.engine.core.Transform;
import com.engine.rendering.Material;

public class BasicShader extends Shader
{
	private static BasicShader instance = new BasicShader();
	
	private BasicShader()
	{
		super();
		
		addVertexShaderFromFile("vertexBasic.vs");
		addFragmentShaderFromFile("fragBasic.fs");
		CompileShader();
		
		addUniform("transform");
		addUniform("color");
	}
	
	public static BasicShader getInstance()
	{
		return instance;
	}
	
	@Override
	public void updateUniforms(Transform transform, Material material) {
		super.updateUniforms(transform, material);
		
		material.getTexture().bind();
		
		Matrix4f WorldMat = transform.getTransformation();
		Matrix4f projMat = getRenderingEngine().getMainCamera().getViewProjection().mul(WorldMat);
		
		setUniform("transform", projMat);
		setUniform("color", material.getColor());
	}
}

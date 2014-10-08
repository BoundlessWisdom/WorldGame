package com.engine.rendering.shaders;

import com.engine.core.Matrix4f;
import com.engine.core.Transform;
import com.engine.rendering.Material;

public class ForwardAmbient extends Shader 
{
	private static ForwardAmbient instance = new ForwardAmbient();
	
	private ForwardAmbient()
	{
		super();
		
		addVertexShaderFromFile("forward-ambient.vs");
		addFragmentShaderFromFile("forward-ambient.fs");
		
		CompileShader();
		
		addUniform("MVP");
		addUniform("ambientIntensity");
	}
	
	public static ForwardAmbient getInstance()
	{
		return instance;
	}
	
	@Override
	public void updateUniforms(Transform transform, Material material) {
		super.updateUniforms(transform, material);
		
		if(material.getTexture() != null)
			material.getTexture().bind();
		
		Matrix4f WorldMat = transform.getTransformation();
		Matrix4f projMat = getRenderingEngine().getMainCamera().getViewProjection().mul(WorldMat);
		
		setUniform("MVP", projMat);
		setUniform("ambientIntensity", getRenderingEngine().getAmbientLight());
	}

}

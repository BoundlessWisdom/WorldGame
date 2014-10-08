package com.engine.rendering.shaders;

import com.engine.components.lighting.BaseLight;
import com.engine.components.lighting.DirectionalLight;
import com.engine.core.Matrix4f;
import com.engine.core.Transform;
import com.engine.rendering.Material;

public class ForwardDirectional extends Shader
{
private static ForwardDirectional instance = new ForwardDirectional();
	
	private ForwardDirectional()
	{
		super();
		
		addVertexShaderFromFile("forward-directional.vs");
		addFragmentShaderFromFile("forward-directional.fs");
		
		CompileShader();
		
		addUniform("model");
		addUniform("MVP");
		
		addUniform("specularIntensity");
		addUniform("specularExponent");
		addUniform("eyePos");
		
		addUniform("directionalLight.base.color");
		addUniform("directionalLight.base.intensity");
		addUniform("directionalLight.direction");
	}
	
	public static ForwardDirectional getInstance()
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
		
		setUniform("model", WorldMat);
		setUniform("MVP", projMat);
		
		setUniformf("specularIntensity", material.getSpecularIntensity());
		setUniformf("specularExponent", material.getSpecularExponent());
		
		setUniform("eyePos", getRenderingEngine().getMainCamera().getPos());
		
		setUniformDirectionalLight("directionalLight", (DirectionalLight)getRenderingEngine().getActiveLight());
	}
	
	public void setUniformBaseLight(String name, BaseLight baseLight)
	{
		setUniform(name + ".color", baseLight.getColor());
		setUniformf(name + ".intensity", baseLight.getIntensity());
	}
	
	public void setUniformDirectionalLight(String name, DirectionalLight directionalLight)
	{
		setUniformBaseLight(name + ".base", (BaseLight)directionalLight);
		setUniform(name + ".direction", directionalLight.getDirection());
	}
}

package com.engine.rendering.shaders;

import com.engine.components.BaseLight;
import com.engine.components.PointLight;
import com.engine.core.Matrix4f;
import com.engine.core.Transform;
import com.engine.rendering.Material;

public class ForwardPoint extends Shader
{
private static ForwardPoint instance = new ForwardPoint();
	
	private ForwardPoint()
	{
		super();
		
		addVertexShaderFromFile("forward-point.vs");
		addFragmentShaderFromFile("forward-point.fs");
		
		CompileShader();
		
		addUniform("model");
		addUniform("MVP");
		
		addUniform("specularIntensity");
		addUniform("specularExponent");
		addUniform("eyePos");
		
		addUniform("pointLight.base.color");
		addUniform("pointLight.base.intensity");
		addUniform("pointLight.atten.constant");
		addUniform("pointLight.atten.linear");
		addUniform("pointLight.atten.exponent");
		addUniform("pointLight.position");
		addUniform("pointLight.range");
	}
	
	public static ForwardPoint getInstance()
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
		
		setUniformPointLight("pointLight", (PointLight)getRenderingEngine().getActiveLight());
	}
	
	public void setUniformBaseLight(String name, BaseLight baseLight)
	{
		setUniform(name + ".color", baseLight.getColor());
		setUniformf(name + ".intensity", baseLight.getIntensity());
	}
	
	public void setUniformPointLight(String name, PointLight pointLight)
	{
		setUniformBaseLight(name + ".base", (BaseLight)pointLight);
		setUniformf(name + ".atten.constant", pointLight.getConstant());
		setUniformf(name + ".atten.linear", pointLight.getLinear());
		setUniformf(name + ".atten.exponent", pointLight.getExponent());
		setUniform(name + ".position", pointLight.getTransform().getPos());
		setUniformf(name + ".range", pointLight.getRange());
	}
}

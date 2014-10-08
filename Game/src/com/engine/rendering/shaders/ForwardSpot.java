package com.engine.rendering.shaders;

import com.engine.components.lighting.BaseLight;
import com.engine.components.lighting.PointLight;
import com.engine.components.lighting.SpotLight;
import com.engine.core.Matrix4f;
import com.engine.core.Transform;
import com.engine.rendering.Material;

public class ForwardSpot extends Shader
{
private static ForwardSpot instance = new ForwardSpot();
	
	private ForwardSpot()
	{
		super();
		
		addVertexShaderFromFile("forward-spotlight.vs");
		addFragmentShaderFromFile("forward-spotlight.fs");
		
		CompileShader();
		
		addUniform("model");
		addUniform("MVP");
		
		addUniform("specularIntensity");
		addUniform("specularExponent");
		addUniform("eyePos");
		
		addUniform("spotLight.pointLight.base.color");
		addUniform("spotLight.pointLight.base.intensity");
		addUniform("spotLight.pointLight.atten.constant");
		addUniform("spotLight.pointLight.atten.linear");
		addUniform("spotLight.pointLight.atten.exponent");
		addUniform("spotLight.pointLight.position");
		addUniform("spotLight.pointLight.range");
		
		addUniform("spotLight.direction");
		addUniform("spotLight.cutOff");
	}
	
	public static ForwardSpot getInstance()
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
		
		setUniformSpotLight("spotLight", (SpotLight)getRenderingEngine().getActiveLight());
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
	
	public void setUniformSpotLight(String name, SpotLight spotLight)
	{
		setUniformPointLight(name + ".pointLight", (PointLight)spotLight);
		setUniform(name + ".direction", spotLight.getDirection());
		setUniformf(name + ".cutOff", spotLight.getCutoff());
	}
}

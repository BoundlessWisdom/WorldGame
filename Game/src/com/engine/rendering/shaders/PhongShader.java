package com.engine.rendering.shaders;

import com.engine.components.lighting.BaseLight;
import com.engine.components.lighting.DirectionalLight;
import com.engine.components.lighting.PointLight;
import com.engine.components.lighting.SpotLight;
import com.engine.core.Matrix4f;
import com.engine.core.Transform;
import com.engine.core.Vector3f;
import com.engine.rendering.Material;

public class PhongShader extends Shader
{
	private static final int MAX_POINT_LIGHTS = 4;
	private static final int MAX_SPOT_LIGHTS = 4;
	
	private static PhongShader instance = new PhongShader();
	
	private static Vector3f ambientLight = new Vector3f(0.1f, 0.1f, 0.1f);
	private static DirectionalLight dLight = new DirectionalLight( 
			new Vector3f(1, 1, 1), 0, new Vector3f(0, 0, 0));
	private static PointLight[] pointLights = new PointLight[] {};
	private static SpotLight[] spotLights = new SpotLight[] {};
	
	private PhongShader()
	{
		super();
		
		addVertexShaderFromFile("phongVert.vs");
		addFragmentShaderFromFile("phongFrag.fs");
		CompileShader();
		
		addUniform("transform");
		addUniform("transformProjected");
		addUniform("baseColor");
		addUniform("ambientLight");
		
		addUniform("specularIntensity");
		addUniform("specularExponent");
		addUniform("eyePos");
		
		addUniform("directionalLight.base.color");
		addUniform("directionalLight.base.intensity");
		addUniform("directionalLight.direction");
		
		for(int i = 0; i < MAX_POINT_LIGHTS; i++)
		{
			addUniform("pointLights[" + i + "].base.color");
			addUniform("pointLights[" + i + "].base.intensity");
			addUniform("pointLights[" + i + "].atten.constant");
			addUniform("pointLights[" + i + "].atten.linear");
			addUniform("pointLights[" + i + "].atten.exponent");
			addUniform("pointLights[" + i + "].position");
			addUniform("pointLights[" + i + "].range");
		}
		
		for(int i = 0; i < MAX_SPOT_LIGHTS; i++)
		{
			addUniform("spotLights[" + i + "].pointLight.base.color");
			addUniform("spotLights[" + i + "].pointLight.base.intensity");
			addUniform("spotLights[" + i + "].pointLight.atten.constant");
			addUniform("spotLights[" + i + "].pointLight.atten.linear");
			addUniform("spotLights[" + i + "].pointLight.atten.exponent");
			addUniform("spotLights[" + i + "].pointLight.position");
			addUniform("spotLights[" + i + "].pointLight.range");
			
			addUniform("spotLights[" + i + "].direction");
			addUniform("spotLights[" + i + "].cutOff");
		}
	}
	
	public static PhongShader getInstance()
	{
		return instance;
	}
	
	@Override
	public void updateUniforms(Transform transform, Material material) {
		super.updateUniforms(transform, material);

		Matrix4f WorldMat = transform.getTransformation();
		Matrix4f projMat = getRenderingEngine().getMainCamera().getViewProjection().mul(WorldMat);
		
		material.getTexture().bind();
		
		setUniform("transform", WorldMat);
		setUniform("transformProjected", projMat);
		
		setUniform("baseColor", material.getColor());
		setUniform("ambientLight", ambientLight);
		setUniform("directionalLight", dLight);
		
		for(int i = 0; i < pointLights.length; i++)
		{
			setUniform("pointLights[" + i + "]", pointLights[i]);
		}
		
		for(int i = 0; i < spotLights.length; i++)
		{
			setUniform("spotLights[" + i + "]", spotLights[i]);
		}
		
		setUniformf("specularIntensity", material.getSpecularIntensity());
		setUniformf("specularExponent", material.getSpecularExponent());
		
		setUniform("eyePos", getRenderingEngine().getMainCamera().getPos());
	}

	public static Vector3f getAmbientLight() {
		return ambientLight;
	}

	public static void setAmbientLight(Vector3f ambientLight) {
		PhongShader.ambientLight = ambientLight;
	}
	
	public static void setDirectionalLight(DirectionalLight directionalLight) {
		PhongShader.dLight = directionalLight;
	}
	
	public static void setPointLights(PointLight[] pointLights)
	{
		if(pointLights.length > MAX_POINT_LIGHTS)
		{
			System.err.println("Error: too many point lights! -> " + pointLights.length + " > 4");
			new Exception().printStackTrace();
			System.exit(1);
		}
		
		PhongShader.pointLights = pointLights;
	}
	
	public static void setSpotLights(SpotLight[] spotLights)
	{
		if(pointLights.length > MAX_SPOT_LIGHTS)
		{
			System.err.println("Error: too many spot lights! -> " + spotLights.length + " > 4");
			new Exception().printStackTrace();
			System.exit(1);
		}
		
		PhongShader.spotLights = spotLights;
	}
	
	public void setUniform(String name, BaseLight baseLight)
	{
		setUniform(name + ".color", baseLight.getColor());
		setUniformf(name + ".intensity", baseLight.getIntensity());
	}
	
	public void setUniform(String name, DirectionalLight directionalLight)
	{
		setUniform(name + ".base", directionalLight);
		setUniform(name + ".direction", directionalLight.getDirection());
	}
	
	public void setUniform(String name, PointLight pointLight)
	{
		setUniform(name + ".base", (BaseLight)pointLight);
		setUniformf(name + ".atten.constant", pointLight.getConstant());
		setUniformf(name + ".atten.linear", pointLight.getLinear());
		setUniformf(name + ".atten.exponent", pointLight.getExponent());
		setUniform(name + ".position", pointLight.getTransform().getPos());
		setUniformf(name + ".range", pointLight.getRange());
	}
	
	public void setUniform(String name, SpotLight spotLight)
	{
		setUniform(name + ".pointLight", (PointLight)spotLight);
		setUniform(name + ".direction", spotLight.getDirection());
		setUniformf(name + ".cutOff", spotLight.getCutoff());
	}
}

package com.engine.components.lighting;

import com.engine.core.Vector3f;
import com.engine.rendering.shaders.ForwardPoint;

public class PointLight extends BaseLight
{
	private static final int COLOR_DEPTH = 256;
	
	private Vector3f atten;
	private float range;
	
	public PointLight(Vector3f color, float intensity, Vector3f atten)
	{
		super(color, intensity);
		this.atten = atten;
		range = calcRange();
		
		setShader(ForwardPoint.getInstance());
	}
	
	private float calcRange()
	{
		float r = 0f;
		
		float a = atten.getZ();
		float b = atten.getY();
		float c = atten.getX() - COLOR_DEPTH * getIntensity() * getColor().max();
		
		r = (float)(-b + Math.sqrt(b*b - 4*a*c)) / (2*a); //want positive part (nothing negative in atten)
		return r;
	}
	
	public float calcRange(Vector3f attenuation)
	{
		float r = 0f;
		
		float a = attenuation.getZ();
		float b = attenuation.getY();
		float c = attenuation.getX() - COLOR_DEPTH * getIntensity() * getColor().max();
		
		r = (float)(-b + Math.sqrt(b*b - 4*a*c)) / (2*a); //want positive part (nothing negative in atten)
		return r;
	}
	
	public float getConstant()
	{
		return atten.getX();
	}
	
	public void setConstant(float c)
	{
		atten.setX(c);
	}
	
	public float getLinear()
	{
		return atten.getY();
	}
	
	public void setLinear(float l)
	{
		atten.setY(l);
	}
	
	public float getExponent()
	{
		return atten.getZ();
	}
	
	public void setExponent(float e)
	{
		atten.setZ(e);
	}
	
//	public Vector3f getPos() {
//		return pos;
//	}
//	public void setPos(Vector3f pos) {
//		this.pos = pos;
//	}

	public float getRange() {
		return range;
	}

	public void setRange(float range) {
		this.range = range;
	}
}

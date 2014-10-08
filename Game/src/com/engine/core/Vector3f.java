package com.engine.core;

public class Vector3f 
{
	private float x, y, z;
	
	public Vector3f(float x, float y, float z) 
	{
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public void set(Vector3f val)
	{
		x = val.getX();
		y = val.getY();
		z = val.getZ();
	}
	
	public void set(float x, float y, float z) 
	{
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public Vector3f rotate(float angle, Vector3f axis)
	{
		float sinHalfAngle = (float)Math.sin(Math.toRadians(angle / 2));
		float cosHalfAngle = (float)Math.cos(Math.toRadians(angle / 2));
		
		float rX = axis.getX() * sinHalfAngle;
		float rY = axis.getY() * sinHalfAngle;
		float rZ = axis.getZ() * sinHalfAngle;
		float rW = cosHalfAngle;
		
		Quaternion rot = new Quaternion(rX, rY, rZ, rW);
		Quaternion conjugate = rot.conjugate();
		
		Quaternion res = rot.mul(this).mul(conjugate);
		
		x = res.getX();
		y = res.getY();
		z = res.getZ();
		
		return this;
	}
	
	public Vector3f lerp(Vector3f dest, float lerpFactor)
	{
		return dest.sub(this).mul(lerpFactor).add(this);
	}
	
	public float length()
	{
		return (float)Math.sqrt(x*x + y*y + z*z);
	}
	
	public float dot(Vector3f r)
	{
		return x * r.getX() + y * r.getY() + z * r.getZ();
	}
	
	public Vector3f cross(Vector3f r)
	{
		float x_ = y * r.getZ() - z * r.getY();
		float y_ = z * r.getX() - x * r.getZ();
		float z_ = x * r.getY() - y * r.getX();
		
		return new Vector3f(x_, y_, z_);
	}
	
	public Vector3f normalize()
	{
		float l = length();
		
		x /= l;
		y /= l;
		z /= l;
				
		return this;
	}
	
	public Vector3f add(Vector3f r)
	{
		this.x += r.getX();
		this.y += r.getY();
		this.z += r.getZ();
		
		return this;
	}
	
	public Vector3f added(Vector3f r)
	{
		return new Vector3f(x + r.getX(), y + r.getY(), z + r.getZ());
	}
	
	public Vector3f add(float r)
	{
		return new Vector3f(x + r, y + r, z + r);
	}
	
	public Vector3f sub(Vector3f r)
	{
		return new Vector3f(x - r.getX(), y - r.getY(), z - r.getZ());
	}
	
	public Vector3f sub(float r)
	{
		return new Vector3f(x - r, y - r, z - r);
	}
	
	public Vector3f mul(Vector3f r)
	{
		return new Vector3f(x * r.getX(), y * r.getY(), z * r.getZ());
	}
	
	public Vector3f mul(float r)
	{
		return new Vector3f(x * r, y * r, z * r);
	}
	
	public Vector3f div(Vector3f r)
	{
		return new Vector3f(x / r.getX(), y / r.getY(), z / r.getZ());
	}
	
	public Vector3f div(float r)
	{
		return new Vector3f(x / r, y / r, z / r);
	}
	
	public float max()
	{
		return Math.max(x, Math.max(y, z));
	}
	
	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}

	public float getZ() {
		return z;
	}

	public void setZ(float z) {
		this.z = z;
	}
	
	public Vector3f abs()
	{
		return new Vector3f(Math.abs(x), Math.abs(y), Math.abs(z));
	}
	
	@Override
	public String toString() {
		return "(" + x + "," + y + "," + z + ")";
	}
	
	//swizzling
	public Vector2f GetXY(){return new Vector2f(x, y);}
	public Vector2f GetYZ(){return new Vector2f(y, z);}
	public Vector2f GetXZ(){return new Vector2f(x, z);}
	
	public Vector2f GetYX(){return new Vector2f(y, x);}
	public Vector2f GetZY(){return new Vector2f(z, y);}
	public Vector2f GetZX(){return new Vector2f(z, x);}
	
	public boolean equals(Vector3f r)
	{
		return x == r.getX() && y == r.getY() && z == r.getZ();
	}
}

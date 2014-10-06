package com.engine.core;

public class Transform 
{
	//private static Camera camera;
	
//	private static float zNear; //how near object can be without clipping
//	private static float zFar; //vice versa
//	private static float width, height; //screen
//	private static float fov; //field of view for perspective proj
	
	private Vector3f pos;
	private Vector3f rot;
	private Vector3f scale;
	
	public Transform()
	{
		pos = new Vector3f(0f, 0f, 0f);
		rot = new Vector3f(0f, 0f, 0f);
		scale = new Vector3f(1f, 1f, 1f);
	}
	
	public Matrix4f getTransformation()
	{
		Matrix4f trans = new Matrix4f().initTranslation(pos.getX(), pos.getY(), pos.getZ());
		Matrix4f rotation = new Matrix4f().initRotation(rot.getX(), rot.getY(), rot.getZ());
		Matrix4f scal = new Matrix4f().initScale(scale.getX(), scale.getY(), scale.getZ());
		
		return trans.mul(rotation.mul(scal));
	}
	
//	public Matrix4f getProjectedTransformation(Camera camera) //prevent squashed ortho projection when screenx != screeny
//	{
//		return camera.getViewProjection().mul(getTransformation());
//	}
	
//	public static void setProjection(float fov, float width, float height, float zNear, float zFar)
//	{
//		Transform.fov = fov;
//		Transform.width = width;
//		Transform.height = height;
//		Transform.zNear = zNear;
//		Transform.zFar = zFar;
//	}

	public Vector3f getPos() 
	{
		return pos;
	}

	public void setPos(Vector3f pos) 
	{
		this.pos = pos;
	}
	
	public void setPos(float x, float y, float z) 
	{
		this.pos = new Vector3f(x, y, z);	
	}
	
	public Vector3f getRot() {
		return rot;
	}

	public void setRot(Vector3f rot) {
		this.rot = rot;
	}

	public void setRotation(float x, float y, float z)
	{
		this.rot = new Vector3f(x, y, z);
	}

	public Vector3f getScale() {
		return scale;
	}

	public void setScale(Vector3f scale) {
		this.scale = scale;
	}

	public void setScale(float x, float y, float z)
	{
		this.scale = new Vector3f(x, y, z);
	}

//	public static Camera getCamera() {
//		return camera;
//	}
//
//	public static void setCamera(Camera camera) {
//		Transform.camera = camera;
//	}
}

package com.game;

import com.engine.components.Camera;
import com.engine.core.GameObject;
import com.engine.core.Quaternion;
import com.engine.core.Vector3f;

public class CameraInfo 
{	
	public Vector3f pos;
	public Quaternion rot;
	
	public CameraInfo(Camera camera) 
	{
		pos = camera.GetTransform().GetPos();
		rot = camera.GetTransform().GetRot();
	}
	
	public CameraInfo(GameObject camera) 
	{
		pos = camera.GetTransform().GetPos();
		rot = camera.GetTransform().GetRot();
	}

	public void Set(Vector3f pos, Quaternion rot)
	{
		this.pos = pos;
		this.rot = rot;
	}
	
	public void Reset(Camera camera)
	{
		pos = camera.GetTransform().GetPos();
		rot = camera.GetTransform().GetRot();
	}
	
	public void Reset(GameObject camera)
	{
		pos = camera.GetTransform().GetPos();
		rot = camera.GetTransform().GetRot();
	}
}

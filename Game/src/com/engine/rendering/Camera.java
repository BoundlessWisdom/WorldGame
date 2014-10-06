package com.engine.rendering;


import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import com.engine.core.Input;
import com.engine.core.Matrix4f;
import com.engine.core.Vector2f;
import com.engine.core.Vector3f;

public class Camera 
{
	public static final Vector3f yAxis = new Vector3f(0, 1, 0);
	
	private Vector3f pos;
	private Vector3f forward;	
	private Vector3f up;
	
	private Matrix4f projection;
	
	private static float totalAngleX = 0.0f;
	private static final float sensitivity = 0.4f;
	
	private static boolean grabbed = false;
	
	/**
	 * 
	 * @param fov
	 * @param zNear -the near clipping plane (how near an object can be without clipping)
	 * @param zFar	-the far clipping plane (how far away an object can be without clipping)
	 */
	public Camera(float fov, float aspectRatio, float zNear, float zFar)
	{
		this.pos = new Vector3f(0, 0, 0);
		this.forward = new Vector3f(0, 0, 1).normalize();
		this.up = new Vector3f(0, 1, 0).normalize();
		
		this.projection = new Matrix4f().initPerspective(fov, aspectRatio, zNear, zFar);
	}
	
	public Matrix4f getViewProjection()
	{
		Matrix4f cameraRotation = new Matrix4f().initCamera(forward, up);
		Matrix4f cameraTranslation = new Matrix4f().initTranslation(-pos.getX(), 
				-pos.getY(), -pos.getZ()); //makes world move in opposite direction
		
		return projection.mul(cameraRotation.mul(cameraTranslation));
	}
	
	public void move(Vector3f dir, float amount)
	{
		pos = pos.add(dir.mul(amount));
	}
	
	private float changeTotalAngle(float amt, boolean xAxis)
	{
		float ret = amt;
		
		if(xAxis)
		{
			totalAngleX += amt;
		} else {
		}
		
		if(totalAngleX > 90f)
		{
			ret = 90f - totalAngleX;
			totalAngleX = 90f;
		}
		else if(totalAngleX < -90f)
		{
			ret = -90f - totalAngleX;
			totalAngleX = -90f;
		}
		/*if(totalAngleY > 90f)
		{
			ret = 90f - totalAngleY;
			totalAngleY = 90f;
		}
		else if(totalAngleY < -90f)
		{
			ret = -90f - totalAngleY;
			totalAngleY = -90f;
		}*/
		
		return ret;
	}
	
	public void input(float delta)
	{
		if(Input.getMouseDown(0))
		{
			grabbed = !grabbed;
			Mouse.setGrabbed(grabbed);
		}
		
		float mvAmt = 10f * delta;
		float rotAmt = 100f * delta;
		
		if(Input.getKey(Keyboard.KEY_W))
			move(getForward(), mvAmt);
		if(Input.getKey(Keyboard.KEY_S))
			move(getForward(), -mvAmt);
		if(Input.getKey(Keyboard.KEY_A))
			move(getLeft(), mvAmt);
		if(Input.getKey(Keyboard.KEY_D))
			move(getRight(), mvAmt);
		
		if(Input.getKey(Keyboard.KEY_UP))
		{
			rotAmt = changeTotalAngle(-rotAmt, true);
			rotateX(rotAmt);
		}
		if(Input.getKey(Keyboard.KEY_DOWN))
		{
			rotAmt = changeTotalAngle(rotAmt, true);
			rotateX(rotAmt);
		}
		if(Input.getKey(Keyboard.KEY_LEFT))
		{
			rotAmt = changeTotalAngle(-rotAmt, false);
			rotateY(rotAmt);
		}
		if(Input.getKey(Keyboard.KEY_RIGHT))
		{
			rotAmt = changeTotalAngle(rotAmt, false);
			rotateY(rotAmt);
		}
		
		if(Mouse.isGrabbed())
		{
			Vector2f centerPosition = new Vector2f(Window.getWidth() / 2, Window.getHeight() / 2);
			Vector2f dPos = Input.getMousePosition().sub(centerPosition);
			
			boolean rotX = dPos.getX() != 0;
			boolean rotY = dPos.getY() != 0;
			
			if(rotX)
			{
				float amt = -dPos.getY() * sensitivity;
				changeTotalAngle(amt, true);
				rotateX(amt);
			}
			
			if(rotY)
			{
				float amt = dPos.getX() * sensitivity;
				changeTotalAngle(amt, false);
				rotateY(amt);
			}
			
			if(rotX || rotY)
			{
				Input.setMousePosition(new Vector2f(Window.getWidth() / 2, Window.getHeight() / 2));
			}
		}
	}
	
	public void rotateY(float angle)
	{
		Vector3f hAxis = yAxis.cross(forward);
		hAxis.normalize();
		
		forward.rotate(angle, yAxis);
		forward.normalize();
		
		up = forward.cross(hAxis);
		up.normalize();
	}
	
	public void rotateX(float angle)
	{
		Vector3f hAxis = yAxis.cross(forward);
		hAxis.normalize();
		
		forward.rotate(angle, hAxis);
		forward.normalize();
		
		up = forward.cross(hAxis);
		up.normalize();
	}
	
	public Vector3f getLeft()
	{
		Vector3f left = forward.cross(up);
		left.normalize();
		return left;
	}
	
	public Vector3f getRight()
	{
		Vector3f right = up.cross(forward);
		right.normalize();
		return right;
	}

	public Vector3f getPos() {
		return pos;
	}

	public void setPos(Vector3f pos) {
		this.pos = pos;
	}

	public Vector3f getForward() {
		return forward;
	}

	public void setForward(Vector3f forward) {
		this.forward = forward;
	}

	public Vector3f getUp() {
		return up;
	}

	public void setUp(Vector3f up) {
		this.up = up;
	}
	
	
}

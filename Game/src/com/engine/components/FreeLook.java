package com.engine.components;

import java.util.ArrayList;

import org.lwjgl.input.Mouse;

import com.engine.core.EntityObject;
import com.engine.core.Input;
import com.engine.core.Vector2f;
import com.engine.core.Vector3f;
import com.engine.rendering.Window;

public class FreeLook extends GameComponent
{
	public static final Vector3f Y_AXIS = new Vector3f(0,1,0);

	private boolean m_mouseLocked = false;
	private float   m_sensitivity;
	private int     m_unlockMouseKey;
	
	private boolean CanMove = true;
	
	private boolean zoomMode = false;
	
	private float zoomRadius;
	private boolean zoom;
	final float zoomTick = 1;
	
	private float yDist;
	private Vector3f relativePos = new Vector3f(0, 0, 0);
	public float distanceFromObj;
	
	public static EntityObject obj;
	private boolean watchingArchon;
	
	public static float comp_radius = 5f;
	public static Vector2f radius = new Vector2f(10f, 10f); //legs of the right triangle created by the radius between the archon and the camera
	public static Vector3f dhArchon = new Vector3f(0f, 2f, 0f); //the archon's position is his butt! We don't want to look at his butt!
	public static Function func;
	//boolean set = false;
	
	public class Function
	{
		public int highDegree = 0;
		public float[] coef;
		
		public Function(float[] coef)
		{
			this.coef = coef;
			highDegree = coef.length - 1;
		}
		
		public float Execute(float x)
		{
			float res = 0.0f;
			
			for(int i = 0; i < coef.length; i++)
			{
				res += coef[i] * Math.pow(x, i);
			}
			
			return res;
		}
	}

	public FreeLook(float sensitivity)
	{
		this(sensitivity, Input.KEY_ESCAPE);
		func = new Function(new float[] {1/2, 0, 0});
	}

	public FreeLook(float sensitivity, int unlockMouseKey)
	{
		//radius = (float) (Math.sqrt(2) * comp_radius);
		this.m_sensitivity = sensitivity;
		this.m_unlockMouseKey = unlockMouseKey;
	}
	
	@Override
	public void Input(float delta)
	{
		if(!CanMove)
			return;
		
		Vector2f centerPosition = new Vector2f(Window.GetWidth()/2, Window.GetHeight()/2);

		if(Input.GetKey(m_unlockMouseKey))
		{
			Input.SetCursor(true);
			m_mouseLocked = false;
		}
		if(Input.GetMouseDown(0))
		{
			Input.SetMousePosition(centerPosition);
			Input.SetCursor(false);
			m_mouseLocked = true;
		}
		
		if (Input.GetKey(Input.KEY_Z)) {
			if (zoomMode)
				disableZoom();
			else
				enableZoom();
		}
		
		
		int zoomVal = Mouse.getDWheel();
		
		/*if(zoomVal != 0)
		{
			//System.out.println(zoomVal);
			float z = (float)zoomVal * 0.01f;
			
			radius.plus(new Vector3f(
					z, 
					Math.abs(func.Execute(z + radius.m_x) - radius.m_y),
					z));
			
			GetTransform().LookAt(obj.GetTransform().GetPos().plus(dhArchon), Y_AXIS);
		}*/

		if(m_mouseLocked)
		{
			//GetTransform().SetPos(obj.GetTransform().m_pos.plus(relativePos));
			
			Vector2f deltaPos = Input.GetMousePosition().minus(centerPosition);
			
			boolean rotY = deltaPos.GetX() != 0;
			boolean rotX = deltaPos.GetY() != 0;
			
			GetTransform().LookAt(new Vector3f(
					obj.GetTransform().GetPos().m_x, 
					obj.GetTransform().GetPos().m_y + 2f, 
					obj.GetTransform().GetPos().m_z), 
					Y_AXIS);
			
			if (zoomMode) {
				if (zoom)
					reposition(Mouse.getDWheel());
				
				if(rotY)
				{
					yAxisRotate(deltaPos);
				}
			}
			else
			{
				if(rotY)
				{
					yAxisRotate(deltaPos);
				}
				//if(rotX)
				//	GetTransform().Rotate(GetTransform().GetRot().GetRight(), (float) Math.toRadians(-deltaPos.GetY() * m_sensitivity));
			}
			
			//GetTransform().SetPos(obj.GetTransform().GetPos().GetX(), obj.GetTransform().GetPos().GetY() + offset, obj.GetTransform().GetPos().GetZ() - radius);
				
			if(rotY || rotX)
				Input.SetMousePosition(centerPosition);
		}
		
	}
	
	private void yAxisRotate(Vector2f deltaPos) 
	{
		Move(GetTransform().GetRot().GetForward(), radius.Length());
		GetTransform().Rotate(Y_AXIS, (float)Math.toRadians(deltaPos.GetX() * m_sensitivity));
		Move(GetTransform().GetRot().GetForward(), -radius.Length());
		//radius = obj.GetTransform().GetPos().minus(GetTransform().GetPos());
	}
	
	private void reposition(int dWheel) 
	{
		zoomRadius -= zoomTick * dWheel;
		yDist = (float) Math.pow(zoomRadius, 2);

		relativePos = new Vector3f(yDist, zoomRadius, GetTransform().GetRot().GetForward().GetXZ());
		
		distanceFromObj = relativePos.Length();		
	}
	
	void enableZoom() 
	{
		zoomMode = true;
	}
	
	void disableZoom() 
	{
		zoomMode = false;
	}
	
	public void lockMouse() 
	{
		Input.SetMousePosition(new Vector2f(Window.GetWidth()/2, Window.GetHeight()/2));
		Input.SetCursor(false);
		m_mouseLocked = true;
	}
	
	private void Move(Vector3f dir, float amt)
	{
		GetTransform().SetPos(GetTransform().GetPos().plus(dir.Mul(amt)));
	}
	
	public void SetMove(boolean CanMove)
	{
		this.CanMove = CanMove;
	}
	
	public void SwitchMoveState()
	{
		CanMove = !CanMove;
	}
}

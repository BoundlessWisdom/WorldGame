package com.engine.components;

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

	public static Vector2f radius = new Vector2f(10f, 10f); //legs of the right triangle created by the radius between the archon and the camera
	public static Vector3f dhArchon = new Vector3f(0f, 2f, 0f); //the archon's position is his butt! We don't want to look at his butt!

	
	//boolean set = false;

	public FreeLook(float sensitivity)
	{
		this(sensitivity, Input.KEY_ESCAPE);
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
		
		
		zoom = Mouse.getDWheel() != 0;

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
			}
			
			if(rotY || rotX)
				Input.SetMousePosition(centerPosition);
		}
		
	}
	
	private void Move(Vector3f dir, float amt)
	{
		GetTransform().SetPos(GetTransform().GetPos().plus(dir.Mul(amt)));
	}

	private void yAxisRotate(Vector2f deltaPos) 
	{
		Move(GetTransform().GetRot().GetForward(), radius.Length());
		GetTransform().Rotate(Y_AXIS, (float)Math.toRadians(deltaPos.GetX() * m_sensitivity));
		Move(GetTransform().GetRot().GetBack(), radius.Length());
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
	
	public void SetMove(boolean CanMove)
	{
		this.CanMove = CanMove;
	}
	
	public void SwitchMoveState()
	{
		CanMove = !CanMove;
	}
}

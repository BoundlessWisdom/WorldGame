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
	
	private boolean zoomMode = true;
	
	private float zoomRadius;
	private boolean zoom;
	final float zoomTick = 1;
	
	private float yDist;
	private Vector3f relativePos = new Vector3f(0, 0, 0);
	public float distanceFromObj;
	
	public static EntityObject obj;
	private boolean watchingArchon;
	
	public static float comp_radius = 5f;
	public static float radius = 5f;
	
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
				if(rotX)
					GetTransform().Rotate(GetTransform().GetRot().GetRight(), (float) Math.toRadians(-deltaPos.GetY() * m_sensitivity));
			}
			
			//GetTransform().SetPos(obj.GetTransform().GetPos().GetX(), obj.GetTransform().GetPos().GetY() + offset, obj.GetTransform().GetPos().GetZ() - radius);
				
			if(rotY || rotX)
				Input.SetMousePosition(centerPosition);
		}
		
	}
	
	private void yAxisRotate(Vector2f deltaPos) {
		float y_0 = GetTransform().GetPos().m_y;
		
		GetTransform().SetPos(new Vector3f(
				GetTransform().GetPos().m_x, 
				obj.GetTransform().GetPos().m_y, 
				GetTransform().GetPos().m_z));
		
		GetTransform().LookAt(obj.GetTransform().GetPos(), Y_AXIS);
		
		//Move(GetTransform().GetRot().GetForward(), radius.Length());
		GetTransform().SetPos(obj.GetTransform().GetPos());
		
		GetTransform().Rotate(Y_AXIS, (float) Math.toRadians(deltaPos.GetX() * m_sensitivity));
		
		Move(GetTransform().GetRot().GetForward(), -distanceFromObj * (float)Math.cos(Math.PI / 4));
		
		//y_0 += y_0 - obj.GetTransform().GetPos().m_y;
		GetTransform().SetPos(new Vector3f(
				GetTransform().GetPos().m_x, 
				y_0, 
				GetTransform().GetPos().m_z));
		
		GetTransform().LookAt(obj.GetTransform().GetPos(), Y_AXIS);
	}
	
	private void reposition(int dWheel) {
		zoomRadius -= zoomTick * dWheel;
		yDist = (float) Math.pow(zoomRadius, 2);

		relativePos = new Vector3f(yDist, zoomRadius, GetTransform().GetRot().GetForward().GetXZ());
		
		//relativePos.add(new Vector3f(0, 500f, 0));
		
		distanceFromObj = relativePos.Length();
	}
	
	void enableZoom() {
		zoomMode = true;
	}
	
	void disableZoom() {
		zoomMode = false;
	}
	
	public void lockMouse() {
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

package com.engine.components;
/*
 * Copyright (C) 2014 Benny Bobaganoosh
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */



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
	
	private int zoomRadius;
	private boolean zoom;
	
	private Vector3f relativePos = new Vector3f(0, 0, 0);
	private float distanceFromObj;
	
	public static EntityObject obj;
	private boolean watchingArchon;
	
	public static float comp_radius = 5f;
	//public static float radius = 5f;
	
	public static Vector2f radius = new Vector2f(10f, 10f);
	
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

		if(m_mouseLocked)
		{
			Vector2f deltaPos = Input.GetMousePosition().minus(centerPosition);
			
			GetTransform().LookAt(new Vector3f(
					obj.GetTransform().GetPos().m_x, 
					obj.GetTransform().GetPos().m_y + 2f, 
					obj.GetTransform().GetPos().m_z), 
					Y_AXIS);
			
			boolean rotY = deltaPos.GetX() != 0;
			boolean rotX = deltaPos.GetY() != 0;

			if(rotY)
			{
				Move(GetTransform().GetRot().GetForward(), radius.Length());
				GetTransform().Rotate(Y_AXIS, (float)Math.toRadians(deltaPos.GetX() * m_sensitivity));
				Move(GetTransform().GetRot().GetBack(), radius.Length());
				//GetTransform().Rotate(Y_AXIS, (float) Math.toRadians(-deltaPos.GetY() * m_sensitivity));
			}
			/*if(rotX)
				GetTransform().Rotate(GetTransform().GetRot().GetRight(), (float) Math.toRadians(-deltaPos.GetY() * m_sensitivity));*/
				

			if(rotY || rotX)
				Input.SetMousePosition(centerPosition);
		}
		
	}
	
	private void Move(Vector3f dir, float amt)
	{
		GetTransform().SetPos(GetTransform().GetPos().plus(dir.Mul(amt)));
	}
	
	public void lockMouse() {
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

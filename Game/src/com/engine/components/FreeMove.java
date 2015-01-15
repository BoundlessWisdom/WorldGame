package com.engine.components;



import com.engine.core.EntityObject;
import com.engine.core.Input;
import com.engine.core.Quaternion;
import com.engine.core.Vector2f;
import com.engine.core.Vector3f;

public class FreeMove extends GameComponent
{
	private float m_speed;
	private int   m_forwardKey;
	private int   m_backKey;
	private int   m_leftKey;
	private int   m_rightKey;
	
	private boolean CanMove = true;
	
	public static EntityObject obj;
	
	public static Vector2f radius = new Vector2f(3f, 3f);

	public FreeMove(float speed)
	{
		this(speed, Input.KEY_W, Input.KEY_S, Input.KEY_A, Input.KEY_D);
	}

	public FreeMove(float speed, int forwardKey, int backKey, int leftKey, int rightKey)
	{
		this.m_speed = speed;
		this.m_forwardKey = forwardKey;
		this.m_backKey = backKey;
		this.m_leftKey = leftKey;
		this.m_rightKey = rightKey;
	}

	@Override
	public void Input(float delta)
	{
		if(!CanMove)
			return;
		
		float movAmt = m_speed * delta;
		
		if(Input.GetKey(m_forwardKey))
		{
			//GetTransform().SetRot(GetTransform().GetRot().);
			Vector3f pos = new Vector3f(GetTransform().GetPos().m_x, GetTransform().GetPos().m_y,
					GetTransform().GetPos().m_z);
			Quaternion quat = new Quaternion(GetTransform().GetRot().GetX(), GetTransform().GetRot().GetY(), 
					GetTransform().GetRot().GetZ(), GetTransform().GetRot().GetW());
			GetTransform().SetPos(new Vector3f(pos.m_x, obj.GetTransform().GetPos().m_y, pos.m_z));
			GetTransform().LookAt(obj.GetTransform().GetPos(), FreeLook.Y_AXIS);
			obj.GetTransform().addPos(GetTransform().GetRot().GetForward().Mul(movAmt));
			GetTransform().SetPos(pos);
			//GetTransform().LookAt(obj.GetTransform().GetPos(), FreeLook.Y_AXIS);
			GetTransform().SetRot(quat);
			Move(GetTransform().GetRot().GetForward(), movAmt);
		}
		
		if(Input.GetKey(m_backKey))
		{
			//GetTransform().SetRot(GetTransform().GetRot().);
			Vector3f pos = new Vector3f(GetTransform().GetPos().m_x, GetTransform().GetPos().m_y,
					GetTransform().GetPos().m_z);
			Quaternion quat = new Quaternion(GetTransform().GetRot().GetX(), GetTransform().GetRot().GetY(), 
					GetTransform().GetRot().GetZ(), GetTransform().GetRot().GetW());
			GetTransform().SetPos(new Vector3f(pos.m_x, obj.GetTransform().GetPos().m_y, pos.m_z));
			GetTransform().LookAt(obj.GetTransform().GetPos(), FreeLook.Y_AXIS);
			obj.GetTransform().addPos(GetTransform().GetRot().GetForward().Mul(-movAmt));
			GetTransform().SetPos(pos);
			//GetTransform().LookAt(obj.GetTransform().GetPos(), FreeLook.Y_AXIS);
			GetTransform().SetRot(quat);
			Move(GetTransform().GetRot().GetForward(), -movAmt);
		}
		
		if(Input.GetKey(m_leftKey))
		{
			//GetTransform().SetRot(GetTransform().GetRot().);
			Vector3f pos = new Vector3f(GetTransform().GetPos().m_x, GetTransform().GetPos().m_y,
					GetTransform().GetPos().m_z);
			Quaternion quat = new Quaternion(GetTransform().GetRot().GetX(), GetTransform().GetRot().GetY(), 
					GetTransform().GetRot().GetZ(), GetTransform().GetRot().GetW());
			GetTransform().SetPos(new Vector3f(pos.m_x, obj.GetTransform().GetPos().m_y, pos.m_z));
			GetTransform().LookAt(obj.GetTransform().GetPos(), FreeLook.Y_AXIS);
			obj.GetTransform().addPos(GetTransform().GetRot().GetLeft().Mul(movAmt));
			GetTransform().SetPos(pos);
			//GetTransform().LookAt(obj.GetTransform().GetPos(), FreeLook.Y_AXIS);
			GetTransform().SetRot(quat);
			Move(GetTransform().GetRot().GetLeft(), movAmt);
		}
		
		if(Input.GetKey(m_rightKey))
		{
			//GetTransform().SetRot(GetTransform().GetRot().);
			Vector3f pos = new Vector3f(GetTransform().GetPos().m_x, GetTransform().GetPos().m_y,
					GetTransform().GetPos().m_z);
			Quaternion quat = new Quaternion(GetTransform().GetRot().GetX(), GetTransform().GetRot().GetY(), 
					GetTransform().GetRot().GetZ(), GetTransform().GetRot().GetW());
			GetTransform().SetPos(new Vector3f(pos.m_x, obj.GetTransform().GetPos().m_y, pos.m_z));
			GetTransform().LookAt(obj.GetTransform().GetPos(), FreeLook.Y_AXIS);
			obj.GetTransform().addPos(GetTransform().GetRot().GetRight().Mul(movAmt));
			GetTransform().SetPos(pos);
			//GetTransform().LookAt(obj.GetTransform().GetPos(), FreeLook.Y_AXIS);
			GetTransform().SetRot(quat);
			Move(GetTransform().GetRot().GetRight(), movAmt);
		}
		
		//float r = radius.Length();
		GetTransform().SetPos(GetTransform().GetPos().m_x, 
				obj.GetTransform().GetPos().m_y + FreeLook.radius.m_y + FreeLook.dhArchon.m_y, 
				GetTransform().GetPos().m_z);
		
		
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

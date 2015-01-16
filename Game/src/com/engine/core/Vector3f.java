package com.engine.core;

public class Vector3f 
{
	public float m_x;
	public float m_y;
	public float m_z;
	
	public Vector3f(float x, float y, float z)
	{
		this.m_x = x;
		this.m_y = y;
		this.m_z = z;
	}
	
	public Vector3f(float x, float y, float z, float length) {
		this(x, y, z);
		normalize().scale(length);
	}
	
	public Vector3f(float height, float r, Vector2f dir2D) {
		m_y = height;
		
		m_x = r * dir2D.normalize().m_x;
		m_z = r * dir2D.normalize().m_y;
	}
	
	public float Length()
	{
		return (float)Math.sqrt(m_x * m_x + m_y * m_y + m_z * m_z);
	}

	public float Max()
	{
		return Math.max(m_x, Math.max(m_y, m_z));
	}

	public float Dot(Vector3f r)
	{
		return m_x * r.GetX() + m_y * r.GetY() + m_z * r.GetZ();
	}
	
	public Vector3f Cross(Vector3f r)
	{
		float x_ = m_y * r.GetZ() - m_z * r.GetY();
		float y_ = m_z * r.GetX() - m_x * r.GetZ();
		float z_ = m_x * r.GetY() - m_y * r.GetX();
		
		return new Vector3f(x_, y_, z_);
	}
	
	public Vector3f Normalized()
	{
		float length = Length();
		
		return new Vector3f(m_x / length, m_y / length, m_z / length);
	}
	
	public Vector3f normalize()
	{
		float length = Length();
		
		this.m_x /= length;
		this.m_y /= length;
		this.m_z /= length;
		
		return this;
	}

	public Vector3f Rotate(Vector3f axis, float angle)
	{
		float sinAngle = (float)Math.sin(-angle);
		float cosAngle = (float)Math.cos(-angle);

		return this.Cross(axis.Mul(sinAngle)).plus(           //Rotation on local X
				(this.Mul(cosAngle)).plus(                     //Rotation on local Z
						axis.Mul(this.Dot(axis.Mul(1 - cosAngle))))); //Rotation on local Y
	}

	public Vector3f Rotate(Quaternion rotation)
	{
		Quaternion conjugate = rotation.Conjugate();

		Quaternion w = rotation.Mul(this).Mul(conjugate);

		return new Vector3f(w.GetX(), w.GetY(), w.GetZ());
	}

	public Vector3f Lerp(Vector3f dest, float lerpFactor)
	{
		return dest.minus(this).Mul(lerpFactor).plus(this);
	}
	
	public Vector3f plus2D(Vector2f v)
	{
		return new Vector3f(m_x + v.m_x, m_y, m_z + v.m_y);
	}

	public Vector3f plus(Vector3f v)
	{
		return new Vector3f(m_x + v.m_x, m_y + v.m_y, m_z + v.m_z); 	
	}
	
	public Vector3f plus(float r)
	{
		return new Vector3f(m_x + r, m_y + r, m_z + r);
	}
	
	public Vector3f add2D(Vector2f v)
	{
		m_x += v.m_x;
		m_z += v.m_y;
		return this;
	}
	
	public Vector3f add(Vector3f v)
	{
		m_x += v.m_x;
		m_y += v.m_y;
		m_z += v.m_z;
		return this;
	}
	
	public Vector3f minus(Vector3f v)
	{
		return new Vector3f(m_x - v.GetX(), m_y - v.GetY(), m_z - v.GetZ());
	}
	
	public Vector3f minus(float r)
	{
		return new Vector3f(m_x - r, m_y - r, m_z - r);
	}
	
	public Vector3f sub(Vector3f v) {
		m_x -= v.m_x;
		m_y -= v.m_y;
		m_z -= v.m_z;
		return this;
	}
	
	public Vector3f Mul(Vector3f r)
	{
		return new Vector3f(m_x * r.GetX(), m_y * r.GetY(), m_z * r.GetZ());
	}
	
	public Vector3f Mul(float r)
	{
		return new Vector3f(m_x * r, m_y * r, m_z * r);
	}
	
	public Vector3f scale(float r)
	{
		m_x *= r;
		m_y *= r;
		m_z *= r;
		return this;
	}
	
	public Vector3f div(Vector3f r)
	{
		return new Vector3f(m_x / r.GetX(), m_y / r.GetY(), m_z / r.GetZ());
	}
	
	public Vector3f div(float r)
	{
		return new Vector3f(m_x / r, m_y / r, m_z / r);
	}
	
	public Vector3f downscale(float r)
	{
		return scale(1/r);
	}
	
	public Vector3f Abs()
	{
		return new Vector3f(Math.abs(m_x), Math.abs(m_y), Math.abs(m_z));
	}
	
	public String toString()
	{
		return "(" + m_x + ", " + m_y + ", " + m_z + ")";
	}

	public Vector2f GetXY() { return new Vector2f(m_x, m_y); }
	public Vector2f GetYZ() { return new Vector2f(m_y, m_z); }
	public Vector2f GetZX() { return new Vector2f(m_z, m_x); }

	public Vector2f GetYX() { return new Vector2f(m_y, m_x); }
	public Vector2f GetZY() { return new Vector2f(m_z, m_y); }
	public Vector2f GetXZ() { return new Vector2f(m_x, m_z); }

	public Vector3f Set(float x, float y, float z) { this.m_x = x; this.m_y = y; this.m_z = z; return this; }
	public Vector3f Set(Vector3f r) { Set(r.GetX(), r.GetY(), r.GetZ()); return this; }

	public float GetX()
	{
		return m_x;
	}

	public void SetX(float x)
	{
		this.m_x = x;
	}

	public float GetY()
	{
		return m_y;
	}

	public void SetY(float y)
	{
		this.m_y = y;
	}

	public float GetZ()
	{
		return m_z;
	}

	public void SetZ(float z)
	{
		this.m_z = z;
	}

	public boolean equals(Vector3f r)
	{
		return m_x == r.GetX() && m_y == r.GetY() && m_z == r.GetZ();
	}
	
	/*****************************************************************************/
	
	private boolean zero;
	
	public void setZero()
	{
		m_x = 0;
		m_y = 0;
		m_z = 0;
		zero = true;
	}
	
	public boolean zeroLength()
	{
		return zero;
	}
}

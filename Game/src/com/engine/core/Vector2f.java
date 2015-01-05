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

package com.engine.core;

public class Vector2f 
{
	public float m_x;
	public float m_y;
	
	public Vector2f(float x, float y)
	{
		this.m_x = x;
		this.m_y = y;
	}

	public float Length()
	{
		return (float)Math.sqrt(m_x * m_x + m_y * m_y);
	}

	public float Max()
	{
		return Math.max(m_x, m_y);
	}

	public float Dot(Vector2f r)
	{
		return m_x * r.GetX() + m_y * r.GetY();
	}
	
	public Vector2f Normalized()
	{
		float length = Length();
		
		return new Vector2f(m_x / length, m_y / length);
	}
	
	public Vector2f normalize()
	{
		float length = Length();
		
		m_x /= length;
		m_y /= length;
		return this;
	}

	public float Cross(Vector2f r)
	{
		return m_x * r.GetY() - m_y * r.GetX();
	}

	public Vector2f Lerp(Vector2f dest, float lerpFactor)
	{
		return dest.minus(this).Mul(lerpFactor).plus(this);
	}

	public Vector2f Rotate(float angle)
	{
		double rad = Math.toRadians(angle);
		double cos = Math.cos(rad);
		double sin = Math.sin(rad);
		
		return new Vector2f((float)(m_x * cos - m_y * sin),(float)(m_x * sin + m_y * cos));
	}
	
	public Vector2f plus(Vector2f v)
	{
		return new Vector2f(m_x + v.GetX(), m_y + v.GetY());
	}
	
	public Vector2f plus(float r)
	{
		return new Vector2f(m_x + r, m_y + r);
	}
	
	public Vector2f add(Vector2f v) {
		m_x += v.m_x;
		m_y += v.m_y;
		return this;
	}
	
	public Vector2f minus(Vector2f v)
	{
		return new Vector2f(m_x - v.GetX(), m_y - v.GetY());
	}
	
	public Vector2f minus(float r)
	{
		return new Vector2f(m_x - r, m_y - r);
	}
	
	public Vector2f sub(Vector2f v) {
		m_x -= v.m_x;
		m_y -= v.m_y;
		return this;
	}
	
	public Vector2f Mul(Vector2f r)
	{
		return new Vector2f(m_x * r.GetX(), m_y * r.GetY());
	}
	
	public Vector2f Mul(float r)
	{
		return new Vector2f(m_x * r, m_y * r);
	}
	
	public Vector2f scale(float r)
	{
		m_x *= r;
		m_y *= r;
		return this;
	}
	
	public Vector2f Div(Vector2f r)
	{
		return new Vector2f(m_x / r.GetX(), m_y / r.GetY());
	}
	
	public Vector2f Div(float r)
	{
		return new Vector2f(m_x / r, m_y / r);
	}
	
	public Vector2f downscale(float r)
	{
		return scale(1/r);
	}
	
	public Vector2f Abs()
	{
		return new Vector2f(Math.abs(m_x), Math.abs(m_y));
	}
	
	public String toString()
	{
		return "(" + m_x + " " + m_y + ")";
	}

	public Vector2f Set(float x, float y)
	{
		this.m_x = x;
		this.m_y = y;
		if (x == 0 && y == 0)
			zero = true;
		else
			zero = false;
		return this;	
	}
	public Vector2f Set(Vector2f r)
	{
		Set(r.GetX(), r.GetY());
		return this;
	}

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

	public boolean equals(Vector2f r)
	{
		return m_x == r.GetX() && m_y == r.GetY();
	}
	
	/*****************************************************************************/
	
	private boolean zero;
	
	public void setZero()
	{
		m_x = 0;
		m_y = 0;
		zero = true;
	}
	
	public boolean zeroLength()
	{
		return zero;
	}
}

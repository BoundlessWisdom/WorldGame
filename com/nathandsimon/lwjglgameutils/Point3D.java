package com.nathandsimon.lwjglgameutils;

public class Point3D 
{
	public int x, y, z;
	public Point3D(int i, int j, int k) 
	{
		x = i;
		y = j;
		z = k;
	}
	public Point3D(Point3D pt)
	{
		x = pt.x;
		y = pt.y;
		z = pt.z;
	}
	@Override
	public String toString()
	{
		return "(" + x + ", " + y + ", " + z + ")";
	}
	@Override
	public boolean equals(Object obj)
	{
		return this.toString() == obj.toString();
	}
}

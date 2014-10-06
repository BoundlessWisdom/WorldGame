package com.engine.core;

public class Time 
{
	private static final long SECOND = 1000000000L;
	
	public static double getTime()
	{
		return System.nanoTime() / (double)SECOND;
	}
	
//	public static double getDelta(){return delta;}
//	
//	public static void setDelta(double delta)
//	{
//		Time.delta = delta;
//	}
}

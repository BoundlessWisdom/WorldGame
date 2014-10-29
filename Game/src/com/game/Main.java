package com.game;

import com.engine.core.CoreEngine;

public class Main 
{
	public static void main(String[] args) 
	{
		CoreEngine.CreateEngine(800, 600, 60, new OurGame());
		CoreEngine.CreateWindow("Yo");
		CoreEngine.Start();
	}
}

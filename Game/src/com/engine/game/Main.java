package com.engine.game;

import com.engine.core.CoreEngine;

public class Main 
{
	public static void main(String[] args) 
	{
		CoreEngine engine = CoreEngine.initiate(800, 600, 60, new OurGame());
		engine.createWindow("Yo");
		engine.start();
	}
}
	
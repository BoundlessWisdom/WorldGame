package com.nathandsimon.lwjglgameutils.test;

import com.nathandsimon.lwjglgameutils.Game;

public class CompoundTest extends Game {
	public void init()
	{
		super.init();
		CompoundEggBunny eb = new CompoundEggBunny(0,10,0,new EntityBunny(0,15,0), new EntityEggCrack(0,10,0));
		addObject(eb);
	}
	public static void main(String[] args) {
		new CompoundTest();
	}

}

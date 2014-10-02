package com.nathandsimon.lwjglgameutils.test;

import org.lwjgl.input.Keyboard;

import com.nathandsimon.lwjglgameutils.Game;

public class CompoundTest extends Game {
	CompoundEggBunny eb;
	public void init()
	{
		super.init();
		eb = new CompoundEggBunny(0,10,0,new EntityBunny(0,15,0), new EntityEggCrack(0,10,0));
		addObject(eb);
	}
	public void update(long elapsedTime)
	{
		super.update(elapsedTime);
		if(Keyboard.isKeyDown(Keyboard.KEY_RETURN))
		{
			eb.die();
			eb = new CompoundEggBunny(0,10,0, new EntityBunny(0,15,0), new EntityEggCrack(0,10,0));
			addObject(eb);
		}
	}
	public static void main(String[] args) {
		new CompoundTest();
	}

}

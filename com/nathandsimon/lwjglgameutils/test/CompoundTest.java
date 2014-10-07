package com.nathandsimon.lwjglgameutils.test;

import org.lwjgl.input.Keyboard;

import com.nathandsimon.lwjglgameutils.Game;
import com.nathandsimon.lwjglgameutils.IObject;

public class CompoundTest extends Game {
	CompoundEggBunny eb;
	public void init()
	{
		super.init();
		eb = new CompoundEggBunny(0,10,0,new EntityBunny(0,15,0), new EntityEggCrack(0,0,0));
		addObject(eb);
	}
	public void update(long elapsedTime)
	{
		if(Keyboard.isKeyDown(Keyboard.KEY_RETURN))
		{
			eb.die();
			eb = new CompoundEggBunny(0,10,0, new EntityBunny(0,15,0), new EntityEggCrack(0,10,0));
			eb.getChild("bunny").rotate(50, 2);
			addObject(eb);
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_Z))
		{
			eb.getChild("bunny").rotate(15, IObject.Z_AXIS);
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_X))
		{
			eb.getChild("bunny").rotate(15, IObject.X_AXIS);
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_Y))
		{
			eb.getChild("bunny").rotate(15, IObject.Y_AXIS);
		}
		super.update(elapsedTime);
	}
	public static void main(String[] args) {
		new CompoundTest();
	}

}

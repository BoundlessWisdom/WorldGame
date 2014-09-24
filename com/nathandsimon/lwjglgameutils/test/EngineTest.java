package com.nathandsimon.lwjglgameutils.test;

import org.lwjgl.input.Keyboard;

import com.nathandsimon.lwjglgameutils.Game;

public class EngineTest extends Game {
	private static EntityEggCrack crack;
	@Override
	public void init() {
		super.init();
		crack = new EntityEggCrack(0,10,0);
		addObject(crack);
	}
	@Override
	public void update(long elapsedTime){
		super.update(elapsedTime);
		if(Keyboard.isKeyDown(Keyboard.KEY_DELETE))
		{
			crack.die();
			crack = new EntityEggCrack(0,10,0);
			addObject(crack);
		}
	}
	public static void main(String[] args) {
		new EngineTest();
	}

}

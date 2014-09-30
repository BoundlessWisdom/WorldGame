package com.nathandsimon.lwjglgameutils.test;

import org.lwjgl.input.Keyboard;
import org.lwjgl.util.vector.Vector3f;

import com.nathandsimon.lwjglgameutils.Game;

public class EngineTest extends Game {
	private static EntityEggCrack crack;
	private static EntityBunny bunny;
	@Override
	public void init() {
		super.init();
		crack = new EntityEggCrack(0,10,0);
		bunny = new EntityBunny(10,10,0);
		addObject(crack);
		addObject(bunny);
	}
	@Override
	public void update(long elapsedTime){
		super.update(elapsedTime);
		if(Keyboard.isKeyDown(Keyboard.KEY_DELETE))
		{
			crack.die();
			crack = new EntityEggCrack(0,10,0);
			addObject(crack);
			bunny.die();
			bunny = new EntityBunny(10,10,0);
			addObject(bunny);
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_F))
		{
			getPhysicsEngine().force(bunny, new Vector3f(1,0,0));
			getPhysicsEngine().force(crack, new Vector3f(-1,0,0));
		}
	}
	public static void main(String[] args) {
		new EngineTest();
	}

}

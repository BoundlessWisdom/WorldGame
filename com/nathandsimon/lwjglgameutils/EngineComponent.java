package com.nathandsimon.lwjglgameutils;

public abstract class EngineComponent {
	public abstract void init();
	public abstract void add(IObject o);
	public abstract void remove(IObject o);
	public abstract void run(long elapsedTime);
	public abstract void dispose();
	public EngineComponent()
	{
		init();
	}
}

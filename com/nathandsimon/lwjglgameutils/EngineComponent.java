package com.nathandsimon.lwjglgameutils;

public abstract class EngineComponent {
	public static enum ComponentType
	{
		physics,
		render,
	}
	public abstract void init();
	public abstract void add(IObject o);
	public abstract void remove(IObject o);
	public abstract void run(long elapsedTime);
	public abstract void dispose();
	public abstract ComponentType getType();
	public EngineComponent()
	{
		init();
	}
}

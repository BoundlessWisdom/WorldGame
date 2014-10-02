package com.nathandsimon.lwjglgameutils;

public interface IAlive extends IObject {
	public int getHealth();
	public void addHealth(int h);
	public void subtractHealth(int h);
	public void die();
}

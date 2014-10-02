package com.nathandsimon.lwjglgameutils;

public abstract class CompoundEntity extends CompoundObject implements IAlive {

	public CompoundEntity(float x, float y, float z, String name, double mass, int health) {
		super(x, y, z, name, mass);
		this.health = health;
	}
	private int health;
	/**
	 * @return the entity's remaining health.
	 */
	public int getHealth()
	{
		return health;
	}
	/**
	 * Add health.
	 * @param h the amount of health.
	 */
	public void addHealth(int h)
	{
		health += h;
		if(health <=0)
		{
			die();
		}
	}
	public void subtractHealth(int h)
	{
		health -= h;
		if(health <= 0)
		{
			die();
		}
	}
	public void die() 
	{
		Game.getInstance().removeObject(this);	
	}

}

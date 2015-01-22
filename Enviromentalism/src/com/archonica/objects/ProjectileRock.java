package com.archonica.objects;

import com.archonica.sparks.SparkProjectile;
import com.game.EntityObject;

public class ProjectileRock extends Projectile
{

	protected ProjectileRock(float spd) 
	{
		super(spd);
	}

	@Override
	protected void onCollision(EntityObject otherObject) 
	{
		
	}

	@Override
	public void form(SparkProjectile spark) 
	{

	}

	@Override
	public void fly(long dtime) {}

}

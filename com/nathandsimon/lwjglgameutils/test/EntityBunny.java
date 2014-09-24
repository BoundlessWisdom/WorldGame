package com.nathandsimon.lwjglgameutils.test;

import com.nathandsimon.lwjglgameutils.Entity;

public class EntityBunny extends Entity {
	public EntityBunny(float x, float y, float z) {
		super(x, y, z, 10, "src/res/bunny.obj", "Bunny");
	}
	@Override
	public double getMu() {
		return 0.5;
	}
	@Override
	public double getElasticConstant() {
		return 0.8;
	}
	@Override
	public double getDragConstant() {
		return 0.8;
	}
	@Override
	public double getCrossSectionArea() {
		return 1;
	}

}

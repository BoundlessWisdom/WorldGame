package com.nathandsimon.lwjglgameutils.test;

import com.nathandsimon.lwjglgameutils.Entity;

public class EntityEggCrack extends Entity {

	public EntityEggCrack(float x, float y, float z) {
		super(x, y, z, 2, "res/egg_crack.obj", "egg_crack");
	}

	@Override
	public double getMu() {
		return 0.5;
	}

	@Override
	public double getElasticConstant() {
		return 0.5;
	}

	@Override
	public double getDragConstant() {
		return .47;
	}

	@Override
	public double getCrossSectionArea() {
		return .01;
	}

}

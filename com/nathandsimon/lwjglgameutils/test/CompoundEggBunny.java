package com.nathandsimon.lwjglgameutils.test;

import com.nathandsimon.lwjglgameutils.CompoundObject;

public class CompoundEggBunny extends CompoundObject {

	public CompoundEggBunny(float x, float y, float z, EntityBunny bun, EntityEggCrack crack) {
		super(x, y, z,  "Eggbunny", 10.0);
		addChild("bunny", bun);
		addChild("egg", crack);
	}

	@Override
	public double getMu() {
		return .10;
	}

	@Override
	public double getElasticConstant() {
		return .5;
	}

	@Override
	public double getDragConstant() {
		return .8;
	}

	@Override
	public double getCrossSectionArea() {
		return 10;
	}
	
}

package com.archonica.effects;

public abstract class LongModifier extends Modifier {

protected int burnStrength;
protected int firePD;

//Intensity of burning - exact damage.
public int burn() {
	return 0;
}

//Level of fire particle density
public int fire() {
	return 0;
}

}

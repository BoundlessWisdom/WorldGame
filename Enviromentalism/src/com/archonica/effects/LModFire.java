package com.archonica.effects;

public class LModFire extends LongModifier {

	public LModFire(int burn, int flame) {
		this.burnStrength = burn;
		this.firePD = flame;
	}
	
	public LModFire() {
		this(0, 0);
	}
	
	public int burn() {
		return burnStrength;
	}
	
	public int flame() {
		return firePD;
	}

}

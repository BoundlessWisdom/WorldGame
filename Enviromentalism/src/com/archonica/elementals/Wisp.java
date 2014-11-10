package com.archonica.elementals;

import static com.archonica.EntClass.*;

public class Wisp extends Elemental {

	public Wisp(float size, int speed) { super(vaporousEnt, size, speed); }
	
	public Wisp() {
		super(0.1f, 8);
	}

	public void think(long dtime) {
	}

}

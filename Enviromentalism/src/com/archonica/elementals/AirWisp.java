package com.archonica.elementals;

import static com.archonica.EntClass.*;

public class AirWisp extends Elemental {

	public AirWisp(float size, int speed) { super(vaporousEnt, size, speed); }
	
	public AirWisp() {
		super(0.1f, 8);
	}

	public void think(long dtime) {
	}

}

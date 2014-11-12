package com.archonica.elementals;

import static com.game.Archonica.activeWorld;

public class FireQuick extends Fire {

	public FireQuick(float size, int speed) { super(size, speed); }
	
	public FireQuick() {
		super(1.0f, 9);
	}
	
	protected void fireSpread(int x, int z) {
		activeWorld.add(new FireQuick().place(x, z));
	}

}

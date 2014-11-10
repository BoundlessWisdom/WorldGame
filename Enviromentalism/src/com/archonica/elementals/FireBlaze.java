package com.archonica.elementals;

import static com.game.Archonica.activeWorld;

import com.archonica.Tile;

public class FireBlaze extends Fire {

	public FireBlaze(float size, int speed) { super(size, speed); }

	public FireBlaze() {
		super(1.0f, 6);
		setStrength(2);
	}
	
	protected void fireSpread(int x, int z) {
		activeWorld.add(new FireBlaze().place(x, z));
	}
	
	protected boolean correctSurrounding(Tile t) {
		return t.abstractEntity instanceof FireBlaze;
	}

}

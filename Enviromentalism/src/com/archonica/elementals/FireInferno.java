package com.archonica.elementals;

import static com.game.Archonica.activeWorld;

import com.archonica.Tile;

public class FireInferno extends Fire {

	public FireInferno(float size, int speed) { super(size, speed); }

	public FireInferno() {
		super(1.0f, 8);
		setStrength(3);
	}
	
	protected void fireSpread(int x, int z) {
		activeWorld.add(new FireInferno().place(x, z));
	}
	
	protected boolean correctSurrounding(Tile t) {
		return t.abstractEntity instanceof FireInferno;
	}

}

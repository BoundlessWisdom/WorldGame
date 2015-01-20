package com.archonica.elementals;

import static com.game.Archonica.activeWorld;

import com.archonica.EDirection;
import com.archonica.Tile;

public class Fire extends AbstractEntity {

	public Fire(float size, int speed) { super(size, speed); }
	
	public Fire() {
		super(1.0f, 5);
	}

	
	public void think(long dtime) {
		int surroundCount = 0;  //For purposes of calculating growth speed.
								//Later, need to replace with smoother factor.
		if (abstractSize > 5)
			for (int i = 0; i < 4; i++) {
				EDirection d = EDirection.directions[i];
				int x2 = x + d.x;
				int z2 = z + d.z;  //Unsure of whether or not this saves calculations.  Hoping so.
				
				Tile t = activeWorld.worldMap[x2][z2];
				//Test if fire passes interaction and next tile fire spread potentials.
				//If so, spread.
				if (t.canFireSpreadTo(abstractSize, abstractSpeed))
					fireSpread(x2, z2);
				
				if (correctSurrounding(t))
					surroundCount++;
			}
		else
			for (int i = 0; i < 4; i++) {
				EDirection d = EDirection.directions[i];
				Tile t = activeWorld.worldMap[x + d.x][z + d.z];
				if (t.has(new Fire()) && !t.has(new FireSlow()))
					surroundCount++;
			}
		
		abstractSize += abstractSpeed * (1 + surroundCount) /*Growth multiplier*/ * dtime;
	}
	
	protected void fireSpread(int x, int z) {
		activeWorld.add(new Fire().place(x, z));
	}
	
	protected boolean correctSurrounding(Tile t) {
		return t.abstractEntity instanceof Fire && !(t.abstractEntity instanceof FireSlow);
	}

	public void act() {  //Burn things.
		
	}

}

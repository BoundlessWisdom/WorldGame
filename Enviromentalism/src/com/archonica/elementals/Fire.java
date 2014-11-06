package com.archonica.elementals;

import static com.game.Archonica.activeWorld;

import com.archonica.EDirection;
import com.archonica.Interaction;

public class Fire extends AbstractEntity {

	public Fire(float size, int speed) { super(size, speed); }
	
	public Fire() {
		super(1.0f, 5);
	}

	
	@SuppressWarnings("unused")
	public void think(long dtime) {
		int surroundCount = 0;  //For purposes of calculating growth speed.
								//Later, need to replace with smoother factor.
		for (int i = 0; i < 4; i++) {
			EDirection d = EDirection.directions[i];
			Interaction ia = activeWorld.getInteraction(d, x, z, x + d.x, z + d.z);
			//Test if fire passes interaction and next tile fire spread potentials.
			//If so, spread.
			if (true)
				activeWorld.add(new Fire());
			
			if (true)
				surroundCount++;
		}
	}

	public void act() {
		
	}

}

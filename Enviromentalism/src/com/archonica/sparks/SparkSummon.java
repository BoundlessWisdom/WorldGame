package com.archonica.sparks;

import com.archonica.Caster;
import com.archonica.Tile;
import com.archonica.elementals.Elemental;

public class SparkSummon extends Spark {
	Tile target;
	Elemental summonElemental;
	
	public void query(Caster caster) {
		target = (Tile) caster.target;
		summonElemental = caster.summoningElemental;
		caster.target = null;
		caster.summoningElemental = null;
		activate();
	}

	protected void activate() {
		
		//Place summonElemental on Tile;
		target.attemptPlacement(null);
	}

}

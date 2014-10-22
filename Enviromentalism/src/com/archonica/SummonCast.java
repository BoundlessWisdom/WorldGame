package com.archonica;

public class SummonCast extends Cast {
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

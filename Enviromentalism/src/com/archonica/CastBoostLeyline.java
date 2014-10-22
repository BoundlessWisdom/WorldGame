package com.archonica;

public class CastBoostLeyline extends SustainedCast {
	Leyline targetLeyline;
	
	//By god, this is genius!  This is phone lines.  Transmission!
	//Leyline Boosts can increase leyline power!  Variable power levels!

	public void query(Caster caster) {
		this.targetLeyline = caster.targetLeyline;
		caster.targetLeyline = null;
		activate();
	}

	protected void activate() {
		
	}

	public void update(long dtime) {
	}

	public void terminate() {
	}

}

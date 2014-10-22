package com.archonica;

public class CastLeyline extends MultiphaseCast {
	ECastType castType;
	Caster caster;
	Caster secondCaster;

	public void update(long dtime) {
	}

	public void query(Caster caster) {
		castType = caster.castingType;
		secondCaster = caster.fellowCaster;
		this.caster = caster;
		caster.castingType = null;
		caster.fellowCaster = null;
		activate();
	}

	protected void activate() {
		Game.activeWorld.add(new Leyline(this, castType, caster.x, caster.z, secondCaster.x, secondCaster.z));
		//TODO: CastLeyline
		//Reminder: This needs to be fixed for effect and aesthetics.
		
	}

	protected void onPhaseEnd() {
	}

	protected void onNextPhase() {
	}

	protected void hibernate(long htime) {
	}

	public void terminate() {
		//Explode!
	}

}

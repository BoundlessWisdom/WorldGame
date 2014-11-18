package com.archonica.sparks;

import com.archonica.Caster;
import com.archonica.ECastType;
import com.archonica.objects.Leyline;
import com.game.Archonica;;

public class SparkLeyline extends MultiphaseSpark {
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
		Archonica.activeWorld.add(new Leyline(this, castType, caster.x, caster.z, secondCaster.x, secondCaster.z));
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

	public void respond() {
	}

}

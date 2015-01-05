package com.archonica.sparks;

import com.archonica.Caster;
import com.archonica.objects.Projectile;
import com.archonica.sparks.MultiphaseSpark;

public class SparkProjectile extends MultiphaseSpark {
	Projectile projectile;

	public SparkProjectile() {
	}
	
	public void update(long dtime) {
	}
	
	public void respond() {
	}
	
	public void phaseEnd() {
		nextPhase();
	}
	
	protected void onNextPhase() {
		switch (phaseNumber) {
		case 0:
			generateProjectile();
		}
	}
	
	private void generateProjectile() {
		projectile.form(this);
	}
	
	public void terminate() {
	}

	public void query(Caster caster) {
	}

}

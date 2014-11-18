package com.archonica.sparks;

public abstract class MultiphaseSpark extends SustainedSpark {
	protected boolean hibernating;

	protected abstract void onPhaseEnd();
	
	protected abstract void onNextPhase();
	
	protected void hibernate(long htime) {
		hibernating = true;
	}
	
	public boolean isHibernating() {
		return hibernating;
	}

}

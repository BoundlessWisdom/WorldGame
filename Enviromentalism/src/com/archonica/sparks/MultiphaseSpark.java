package com.archonica.sparks;

public abstract class MultiphaseSpark extends SustainedSpark {
	protected int phaseNumber = 0;
	
	protected boolean hibernating;

	public abstract void phaseEnd();
	
	protected void nextPhase() {
		phaseNumber++;
		onNextPhase();
	}
	
	protected abstract void onNextPhase();
	
	protected void hibernate(long htime) {
		hibernating = true;
	}
	
	public boolean isHibernating() {
		return hibernating;
	}
	
	protected void activate() {
		onNextPhase();
	}

}

package com.archonica;

public abstract class MultiphaseCast extends SustainedCast {
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

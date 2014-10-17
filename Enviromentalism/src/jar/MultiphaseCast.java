package jar;

public abstract class MultiphaseCast extends SustainedCast {
	protected boolean hibernating;
	
	public MultiphaseCast(Caster caster) {
		super(caster);
	}

	protected abstract void onPhaseEnd();
	
	protected abstract void onNextPhase();
	
	protected void hibernate(long htime) {
		hibernating = true;
	}
	
	public boolean isHibernating() {
		return hibernating;
	}

}

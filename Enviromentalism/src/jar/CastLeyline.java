package jar;

public class CastLeyline extends SustainedCast {
	Caster secondCaster;

	public CastLeyline(Caster caster) {
		super(caster);
	}

	public void update(long dtime) {
	}

	protected void query() {
		secondCaster = caster.peerCaster;
		caster.peerCaster = null;
		activate();
	}

	protected boolean activate() {
		return false;
	}

}

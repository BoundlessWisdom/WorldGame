package jar;

public class CastBoostLeyline extends SustainedCast {
	Leyline targetLeyline;
	
	//By god, this is genius!  This is phone lines.  Transmission!
	//Leyline Boosts can increase leyline power!  Variable power levels!

	public CastBoostLeyline(Caster caster) {
		super(caster);
	}

	protected void query() {
		this.targetLeyline = caster.targetLeyline;
	}

	protected boolean activate() {
		return false;
	}

	public void update(long dtime) {
	}

	public void terminate() {
	}

}

package jar;

public class CastLeyline extends SustainedCast {
	ECastType castType;
	Caster secondCaster;

	public CastLeyline(Caster caster) {
		super(caster);
	}

	public void update(long dtime) {
	}

	protected void query() {
		castType = caster.castingType;
		secondCaster = caster.peerCaster;
		caster.castingType = null;
		caster.peerCaster = null;
		activate();
	}

	protected boolean activate() {
		Game.activeWorld.add(new Leyline(this, castType, caster.x, caster.z, secondCaster.x, secondCaster.z));
		//TODO: CastLeyline
		//Reminder: This needs to be fixed for effect and aesthetics.
		
		return true;
	}

}

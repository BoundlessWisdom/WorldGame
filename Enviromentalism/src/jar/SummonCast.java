package jar;

public class SummonCast extends Cast {
	Tile target;
	Elemental summonElemental;

	public SummonCast(Caster caster) {
		super(caster);
	}
	
	protected void query() {
		target = (Tile) caster.target;
		summonElemental = caster.summoningElemental;
		caster.summoningElemental = null;
		activate();
	}

	@SuppressWarnings("unused")
	protected boolean activate() {
		if (false /*if target passes false for attemptCast*/)
			return false;
		
		//Place summonElemental on Tile;
		return target.attemptPlacement(null);
	}

}

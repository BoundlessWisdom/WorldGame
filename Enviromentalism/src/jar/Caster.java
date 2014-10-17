package jar;

import java.util.HashMap;

public abstract class Caster extends Entity {

	float power = 1.0f;
	
	//Update control bars: power * regenspeed * regenscale * update time in ?
	
	public Caster(Base b, EntClass e, float f, Modifier m) { super(b, e, f, m); }
	public Caster(Base b, EntClass e, float f) { super(b, e, f); }
	public Caster(Base b, float f) { super(b, f); }
	
	public void updatePower() {
		if (health/maxHealth >= 0.8f) {
			power = 1.0f;
			return;
		}
		power = (float) (1.0f - 1.0f/Math.pow(80, 4) * Math.pow(0.8f - (health/maxHealth), 4));
	}
	
	/*****************************************************/
	
	HashMap<String, Boolean> control = new HashMap<String, Boolean>();
	
	public boolean canCast(String attempt) {
		return control.get(attempt);
	}

	public void cast(String cast) {
		
	}
	
	public Location target;
	public Location[] targets;
	
	public ECastType castingType;
	public ECastType[] castingTypes;
	
	public Caster peerCaster;
	public Caster[] peerCasters;
	
	public Leyline targetLeyline;
	public Leyline[] targetLeylineConvergence;
	
	public Elemental summoningElemental;
	public Elemental[] summoningElementalGroup;
	
	public Projectile launchingProjectile;
	public Projectile[] launchingProjectiles;
	
	public int stat1;
	public int stat2;
	public int stat3;
	
	public int[] statArray1;
	public int[] statArray2;
	public int[] statArray3;
	
	public int[][] statArrayArray1;
}

package jar;

import java.util.HashMap;

public abstract class CasterBase extends Base {

	float power = 1.0f;
	
	//Update control bars: power * regenspeed * regenscale * update time in ?
	
	public CasterBase(float mh, int attack) {
		super(mh, attack);
	}
	
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
	
	public Elemental summoningElemental;
	public Elemental[] summoningElementalGroup;
	
	public Projectile launchingProjectile;
	public Projectile[] launchingProjectiles;
}

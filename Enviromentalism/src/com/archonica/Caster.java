package com.archonica;

import com.archonica.elementals.Elemental;
import com.archonica.objects.Leyline;
import com.archonica.objects.Projectile;

public abstract class Caster extends Entity {

	float power = 1.0f;
	
	//Update control bars: power * regenspeed * regenscale * update time in ?
	
	public Caster(EntClass e, float sz, float spd) { super(e, sz, spd); }
	public Caster(float sz, float spd) { super(sz, spd); }
	
	public void updatePower() {
		if (health/maxHealth >= 0.8f) {
			power = 1.0f;
			return;
		}
		power = (float) (1.0f - 1.0f/Math.pow(80, 4) * Math.pow(0.8f - (health/maxHealth), 4));
	}
	
	/*****************************************************/
	
	boolean[] control = new boolean[512];
	
	public boolean canCast(int attempt) {
		return control[attempt];
	}

	public void cast(int spark) {
		
	}
	
	public Tile target;
	public Tile[] targets;
	
	public ECastType castingType;
	public ECastType[] castingTypes;
	
	public Caster fellowCaster;
	public Caster[] fellowCasters;
	
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
	
	public int[][] statDoubleArray1;
}

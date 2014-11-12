package com.archonica.elementals;

import com.archonica.EntClass;
import com.archonica.Entity;

public abstract class Elemental extends Entity {
	
	
	public static final Elemental wisp			= new Wisp(0.1f, 0);
	public static final Elemental heavyWisp		= new WispHeavy(0.3f, 0);
	public static final Elemental massiveWisp	= new WispMassive(0.8f, 0);
	
	public static final Elemental wasp 			= new Wasp(0.1f, 0);
	
	
	
	public static final Elemental mite 			= new Mite(0.5f, 0);
	
	
	
	public static final Elemental imp 			= new Imp(0.5f, 0);
	
	public static final Elemental fire			= new Fire(0.0f, 1);
	public static final Elemental quickFire 	= new FireQuick(0.0f, 0);
	public static final Elemental slowFire		= new FireSlow(0.0f, 0);
	public static final Elemental blaze			= new FireBlaze(0.0f, 0);
	public static final Elemental inferno		= new FireInferno(0.0f, 0);
	
	
	
	public static final Elemental sprayfish 	= new Sprayfish(0.2f, 0);
	
	
	
	/*
	 * Air and Earth combined would unleash plagues and liquid death. Fast-moving
	 * 		area-controlling elementals.
	 * Air and Water would generate gales and hurricanes. Massive area-controlling elementals.
	 * Fire and Earth would raise volcanoes and magma giants. Tanking elementals and localized
	 * 		total control.
	 * A delicate combining of Fire and Water would have the potential to create life.
	 * 		Fast surgical area-control.
	 */
	
	int range = 0;  //For ranged attackers.
	
	public Elemental(EntClass e, float size, int speed) { super(e, size, speed); }
	public Elemental(float size, int speed) { super(size, speed); }
	
	protected Elemental range(int r) {
		this.range = r;
		return this;
	}
	
	/********************************************************************************************/
	
	public void respond() {
		
	}
	
}

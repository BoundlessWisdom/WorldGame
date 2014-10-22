package com.archonica;

import static com.archonica.ECastType.*;

public abstract class Elemental extends Entity {
	
	public static final Elemental[] airElemental = new Elemental[256];
	
	public static final Elemental wisp			= new ElementalWisp(AIR, 0, 0.1f, 0);
	public static final Elemental heavyWisp		= new ElementalWisp(AIR, 1, 0.3f, 0);
	public static final Elemental massiveWisp	= new ElementalWisp(AIR, 2, 0.8f, 0);
	
	public static final Elemental wasp = new ElementalWasp(AIR, 127, 0.1f, 0);
	
	
	public static final Elemental[] earthElemental = new Elemental[256];
	
	public static final Elemental mite = new ElementalMite(EARTH, 0, 0.5f, 0);
	
	
	public static final Elemental[] fireElemental = new Elemental[256];
	
	public static final Elemental imp = new ElementalImp(FIRE, 0, 0.5f, 0);
	
	public static final Elemental fire		= new EntityFire(FIRE, 253, 0.0f, 0);
	public static final Elemental blaze		= new EntityFire(FIRE, 254, 0.0f, 0);
	public static final Elemental inferno	= new EntityFire(FIRE, 255, 0.0f, 0);
	
	
	public static final Elemental[] waterElemental = new Elemental[256];
	
	public static final Elemental sprayfish = new ElementalSprayfish(WATER, 0, 0.2f, 0, 0);
	
	
	public static final Elemental[] iceElementals = new Elemental[256];
	public static final Elemental[] cloudElementals = new Elemental[256];
	public static final Elemental[] magmaElementals = new Elemental[256];
	
	/*
	 * Air and Earth combined would unleash plagues and liquid death. Fast-moving
	 * 		area-controlling elementals.
	 * Air and Water would generate gales and hurricanes. Massive area-controlling elementals.
	 * Fire and Earth would raise volcanoes and magma giants. Tanking elementals and localized
	 * 		total control.
	 * A delicate combining of Fire and Water would have the potential to create life.
	 * 		Fast surgical area-control.
	 */
	
	int range = 0;
	
	public Elemental(ECastType type, int i, float size, int speed) {
		super(size);
		
		switch (type) {
		case AIR: 
			airElemental[i] = this;
			break;
		case EARTH:
			earthElemental[i] = this;
			break;
		case FIRE:
			fireElemental[i] = this;
			break;
		case WATER:
			waterElemental[i] = this;
			break;
		case ICE:
			iceElementals[i] = this;
			break;
		case CLOUD:
			cloudElementals[i] = this;
			break;
		case MAGMA:
			magmaElementals[i] = this;
			break;
		default:
			throw new IllegalArgumentException("Null element: " + type.name() + ", " + i);
		}
	}
	
	protected Elemental range(int r) {
		this.range = r;
		return this;
	}
	
	public void update() {
		//The elemental should either carry out instructions or follow default AI;
	}

	public void update(long dtime) {
		//Not necessarily necessary.
	}
	
}

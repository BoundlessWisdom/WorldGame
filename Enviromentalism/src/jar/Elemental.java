package jar;

import static jar.ECastType.*;

public /*abstract*/ class Elemental extends Base {
	
	public static final Elemental[] airElementals = new Elemental[256];
	
	public static final Elemental wisp = new Elemental(AIR, 0, 0.1f, 0, 0, 0);
	public static final Elemental heavyWisp = new Elemental(AIR, 1, 0.3f, 0, 0, 0);
	public static final Elemental massiveWisp = new Elemental(AIR, 2, 0.8f, 0, 0, 0);
	
	public static final Elemental wasp = new Elemental(AIR, 127, 0.1f, 0, 0, 0);
	
	
	public static final Elemental[] earthElementals = new Elemental[256];
	
	public static final Elemental mite = new Elemental(EARTH, 0, 0.5f, 0, 0, 0);
	
	
	public static final Elemental[] fireElementals = new Elemental[256];
	
	public static final Elemental imp = new Elemental(FIRE, 0, 0.5f, 0, 0, 0);
	
	public static final Elemental fire = new Elemental(FIRE, 253, 0.0f, 0, 0, 0);
	
	
	public static final Elemental[] waterElementals = new Elemental[256];
	
	public static final Elemental sprayfish = new Elemental(WATER, 0, 0.2f, 0, 0, 0);
	
	
	public static final Elemental[] iceElementals = new Elemental[256];
	public static final Elemental[] cloudElementals = new Elemental[256];
	public static final Elemental[] magmaElementals = new Elemental[256];
	
	/*
	 * Air and Earth combined would unleash plagues and materialized death. Fast-moving
	 * 		area-controlling elementals.
	 * Air and Water would generate gales and hurricanes. Massive area-controlling elementals.
	 * Fire and Earth would raise volcanoes and magma giants. Tanking elementals and localized
	 * 		total control.
	 * A delicate combining of Fire and Water would have the potential to create life.
	 * 		Fast surgical area-control.
	 */
	
	int range;
	
	public Elemental(ECastType type, int i, float size, int health, int attack, int speed) {
		super(health, attack);
		range = 0;
		
		switch (type) {
		case AIR: 
			airElementals[i] = this;
			break;
		case EARTH:
			earthElementals[i] = this;
			break;
		case FIRE:
			fireElementals[i] = this;
			break;
		case WATER:
			waterElementals[i] = this;
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

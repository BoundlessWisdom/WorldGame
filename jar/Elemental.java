package jar;

import static jar.ECastType.*;
public class Elemental extends Base {
	
	public static final Elemental[] airElementals = new Elemental[256];
	
	public static final Elemental wisp = new Elemental(AIR, 0, 0.1f);
	public static final Elemental wasp = new Elemental(AIR, 127, 0.1f);
	
	public static final Elemental[] earthElementals = new Elemental[256];
	
	public static final Elemental mite = new Elemental(EARTH, 0, 0.5f);
	
	public static final Elemental[] fireElementals = new Elemental[256];
	
	public static final Elemental imp = new Elemental(FIRE, 0, 0.5f);
	
	public static final Elemental[] waterElementals = new Elemental[256];
	
	public static final Elemental sprayfish = new Elemental(WATER, 0, 0.2f);
	
	public static final Elemental[] iceElementals = new Elemental[256];
	public static final Elemental[] cloudElementals = new Elemental[256];
	
	/*
	 * Air and Earth combined would unleash plagues and gravel storms.
	 * Air and Water would generate gales and hurricanes.
	 * Fire and Earth would raise volcanoes and magma giants.
	 * Fire and Water 
	 */
	
	public Elemental(ECastType type, int i, float size) {
		
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
		default:
//			throw new NullElementException();
		}
		
		this.entity = new ElementalEntity(this, size);
	}
	
	public void update() {
		//The elemental should either carry out instructions or follow default AI;
	}
	
}
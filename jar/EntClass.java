package jar;

public class EntClass {
	
	public static EntClass baseEnt = new EntClass();
	public static EntClass vaporousEnt = new EntClass().vaporous();
	
	public static EntClass abstractEnt = new EntClass().notMortal().intangible();
	public static EntClass construct = new EntClass().immobile();

	
	boolean immobile = false;
	boolean notMortal = false;
	boolean intangible = false;
	
	int ethereality = 0;
	
	/************************************************************************/
	
	private EntClass immobile() {
		this.immobile = true;
		return this;
	}
	
	private EntClass notMortal() {
		this.notMortal = true;
		return this;
	}
	
	private EntClass intangible() {
		this.intangible = true;
		return this;
	}
	
	private EntClass vaporous() {
		this.ethereality = 1;
		return this;
	}
	
	public boolean setVaporous(int ethereality) {
		if (this.ethereality == 0)
			return false;
		
		this.ethereality = ethereality;
		return true;
	}
	
	/************************************************************************/
	
	public boolean isImmobile() {
		return immobile;
	}
	
	public boolean isNotMortal() {
		return notMortal;
	}
	
	public boolean isIntangible() {
		return intangible;
	}
	
	public int isVaporous() {
		return ethereality;
	}
}

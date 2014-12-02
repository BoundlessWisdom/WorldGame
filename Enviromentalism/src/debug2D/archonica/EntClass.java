package debug2D.archonica;

public class EntClass {
	
	public static EntClass baseEnt = new EntClass();
	public static EntClass vaporousEnt = new EntClass().vaporous();
	
	public static EntClass abstractEnt = new EntClass().notMortal().intangible();
	public static EntClass construct = new EntClass().immobile();

	
	private boolean immobile = false;
	private boolean notMortal = false;
	private boolean intangible = false;
	
	private int ethereality = 0;
	
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
	
	public boolean isVaporous() {
		return ethereality > 0;
	}
	
	public int howVaporous() {
		return ethereality;
	}
}

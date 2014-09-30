package jar;

import java.util.HashMap;

public abstract class CasterBase extends Base {
	
	HashMap<String, Boolean> control = new HashMap<String, Boolean>();
	
	boolean canCastControl(String attempt) {
		return control.get(attempt);
	}

	public void cast(String cast) {
		
	}
	
	public Location target;
	public Elemental summoningElemental;
	public Elemental[] summoningElementalGroup;
}

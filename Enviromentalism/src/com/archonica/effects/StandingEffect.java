package com.archonica.effects;

import com.archonica.Updateable;

public abstract class StandingEffect implements Updateable {
	
	public void onResolve(){
		//Do nothing, standing effects never resolve
	}
	
}

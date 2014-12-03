package com.archonica.effects;

import com.archonica.Entity;
import com.archonica.Updateable;

public abstract class StandingEffect extends Effect implements Updateable {
	protected int m_duration = 1000;
	public StandingEffect(int duration){
		m_duration = duration;	
	}
	public void onResolve(){
		//Do nothing, standing effects never resolve
	}
}

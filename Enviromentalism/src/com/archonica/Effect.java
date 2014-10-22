package com.archonica;

public abstract class Effect implements Updateable {
	@SuppressWarnings("unused")
	private Caster caster;
	
	public boolean expired;
	public final int index;
	
	public Effect(int i) {
		index = i;
	}
	
	public void casted(Caster c) {
		caster = c;
	}
	
	public void update(long dtime) {
		
	}
	
	void expire() {
		expired = true;
	}
}

package com.archonica;

public abstract class Cast {
	
	public static final Cast[] cast = new Cast[512];
	
	
	
	
	public abstract void query(Caster caster);
	
	protected abstract void activate();
}

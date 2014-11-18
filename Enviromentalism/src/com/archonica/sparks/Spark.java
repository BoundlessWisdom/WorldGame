package com.archonica.sparks;

import com.archonica.Caster;

public abstract class Spark {
	
	public static final Spark[] spark = new Spark[512];
	
	
	
	
	public abstract void query(Caster caster);
	
	protected abstract void activate();
}

package com.archonica.objects;

import com.archonica.Updateable;

public abstract class Trap implements Updateable {
	
	public abstract void trigger();
	
	public abstract void release();

}

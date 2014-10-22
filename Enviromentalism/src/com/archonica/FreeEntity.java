package com.archonica;

public abstract class FreeEntity implements Updateable {
	
	/*
	 * This is a placeholder for flying objects and anything else that temporarily leaves the coordinate system.
	 */
	
	protected abstract void onLanding();
}

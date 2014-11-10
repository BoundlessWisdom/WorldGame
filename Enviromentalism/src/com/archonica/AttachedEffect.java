package com.archonica;

public abstract class AttachedEffect extends Effect {
	public final Entity host;

	AttachedEffect(int i, Entity h) {
		super(i);
		this.host = h;
	}

}

package com.archonica.effects;

import com.archonica.Entity;
import com.archonica.Modifier;

public abstract class AttachedEffectCurse extends AttachedEffect {

	public AttachedEffectCurse(Entity host, int duration) {
		super(host, duration);
	}
	public abstract Modifier GetModifier();
	
}

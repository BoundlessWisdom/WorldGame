package com.archonica.effects;

import com.archonica.Entity;

public abstract class InstantEffect extends Effect {
	protected Entity m_target;
	public InstantEffect(Entity target){
		m_target = target;
	}
}

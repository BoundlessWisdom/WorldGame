package com.archonica.effects;

import com.archonica.Entity;

public abstract class AttachedEffect extends Effect {
	protected Entity m_host;
	public int m_duration= 0;
	public AttachedEffect(Entity host, int duration){
		m_host = host;
		m_duration = duration;
	}
}

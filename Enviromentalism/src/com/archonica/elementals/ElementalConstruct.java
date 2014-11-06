package com.archonica.elementals;

import java.util.ArrayList;

import com.archonica.EntClass;

public abstract class ElementalConstruct extends Elemental {
	
	public ElementalConstruct(EntClass e, float size, int speed) { super(e, size, speed); }
	public ElementalConstruct(float size, int speed) { super(size, speed); }
	
	public ArrayList<Elemental> citizens;
	
	public abstract boolean canAdd(Elemental candidate);

	public void think(long dtime) {
	}

}

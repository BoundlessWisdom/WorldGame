package com.archonica;

import java.util.ArrayList;

public abstract class ElementalConstruct extends Elemental {

	public ElementalConstruct(ECastType type, int i, float size, int health, int speed) {
		super(type, i, size, speed);
	}
	
	public ArrayList<Elemental> citizens = new ArrayList<Elemental>();
	
	public abstract boolean canAdd();
	
}
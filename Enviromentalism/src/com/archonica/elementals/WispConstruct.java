package com.archonica.elementals;

import static com.archonica.EntClass.*;

import java.util.ArrayList;

@SuppressWarnings("unused")
public class WispConstruct extends ElementalConstruct {

	public WispConstruct(float size, int speed) {
		super(vaporousEnt, size, speed);
//		citizens = new ArrayList<Wisp>();
	}

	public boolean canAdd(Elemental candidate) {
		return candidate instanceof Wisp;
	}

}

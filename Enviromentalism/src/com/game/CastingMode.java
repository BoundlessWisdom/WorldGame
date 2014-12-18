package com.game;

import com.engine.core.GameObject;

public class CastingMode extends GameObject {
	
	public CastingMode() {
		super();
		
		for(int i = 0; i < 4; i++) {
			AddChild(new CastingLine());
		}
	}
	
	public void enterMode() {
		
	}
	
	public void exitMode() {
		
	}
	
}

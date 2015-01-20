package com.archonica;

import com.engine.components.GameComponent;
import com.engine.components.MeshRenderer;
import com.engine.rendering.Material;
import com.engine.rendering.Mesh;

public class Archon extends Caster {
	
	public Archon(EntClass e, float sz, float spd) 
	{
		super(e, sz, spd); 
	}
	
	public Archon(float sz, float spd) 
	{ 
		super(sz, spd, "Archon Placeholder.obj"); 
		fixElastic(0.2);
		this.colrad = .982875f;
	}

	public void update(long dtime) {
		
	}
	
	public void think(long dtime) {
		
	}

	public void act(long dtime) {
		
	}
	
	
	
	protected void lockCamera() {
		cameraLock = GetTransform().GetPos();
	}
}

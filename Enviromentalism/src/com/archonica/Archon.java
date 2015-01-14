package com.archonica;

import com.engine.components.GameComponent;
import com.engine.components.MeshRenderer;
import com.engine.rendering.Material;
import com.engine.rendering.Mesh;

public class Archon extends Caster {
	
	private Mesh mesh = null;

	public Archon(EntClass e, float sz, float spd) 
	{
		super(e, sz, spd); 
	}
	
	public Archon(float sz, float spd) 
	{ 
		super(sz, spd); 
		mesh = new Mesh("Archon Placeholder.obj");
		fixElastic(0.2);
		this.colrad = .982875f;
	}

	public void update(long dtime) {
		
	}
	
	public void think(long dtime) {
		
	}

	public void act(long dtime) {
		
	}
	
	public void AddMaterial(Material material)
	{
		int index = 0;
		for(GameComponent component : m_components)
		{
			if(component instanceof MeshRenderer)
			{
				m_components.set(index, new MeshRenderer(mesh, material));
				return;
			}
			
			index++;
		}
		
		AddComponent(new MeshRenderer(mesh, material));
	}
	
	protected void lockCamera() {
		cameraLock = GetTransform().GetPos();
	}
}

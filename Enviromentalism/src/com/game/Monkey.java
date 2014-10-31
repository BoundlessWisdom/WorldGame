package com.game;

import com.engine.components.GameComponent;
import com.engine.components.MeshRenderer;
import com.engine.core.EntityObject;
import com.engine.core.GameObject;
import com.engine.rendering.Material;
import com.engine.rendering.Mesh;

public class Monkey extends EntityObject 
{	
	private Mesh mesh = null;
	
	public Monkey(GameObject gameObj, double mass, String name) {
		super(gameObj, mass, name);
		mesh = new Mesh("test1.obj");
		fixElastic(0.2);
	}

	/*@Override
	public void update(float delta) {
		super.update(delta);
		if(Keyboard.isKeyDown(Keyboard.KEY_F))
		{
			Game.getPhysicsEngine().force(this, new Vector3f(1,0,0));
			//System.out.println("pressing");
			//getPhysicsEngine().force(crack, new Vector3f(-1,0,0));
		}
	}*/
	
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
	
	@Override
	public double GetMu() {
		return .5;
	}

	@Override
	public double GetElasticConstant() {
		return elasticConstant;
	}

	@Override
	public double GetDragConstant() {
		return 0;
	}

	@Override
	public double GetCrossSectionArea() {
		return 0;
	}	
}

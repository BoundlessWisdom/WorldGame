package com.game;
import com.engine.components.GameComponent;
import com.engine.components.MeshRenderer;
import com.engine.core.GameObject;
import com.engine.rendering.Material;
import com.engine.rendering.Mesh;

public class Icicle extends com.engine.core.EntityObject{
	
	//Mesh mesh = null;
	
	public Icicle(GameObject gameObj, double mass) {
		// TODO Auto-generated constructor stub
		super(gameObj, mass);
	}

	@Override
	protected void lockCamera() {
		// TODO Auto-generated method stub
		
	}
	
	public void AddMaterial(Material material, Mesh mesh) {
		
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
}

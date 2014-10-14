package com.game;

import org.lwjgl.input.Keyboard;

import com.engine.components.lighting.BaseLight;
import com.engine.components.lighting.SpotLight;
import com.engine.components.renderObjs.MeshRenderer;
import com.engine.components.renderObjs.terrain.HeightMap;
import com.engine.components.renderObjs.terrain.Terrain;
import com.engine.components.renderObjs.terrain.Terrain.OriginGravity;
import com.engine.core.GameInstance;
import com.engine.core.GameObject;
import com.engine.core.Input;
import com.engine.core.Vector3f;
import com.engine.rendering.Camera;
import com.engine.rendering.Material;
import com.engine.rendering.Mesh;
import com.engine.rendering.Texture;

public class OurGame extends GameInstance 
{
	Monkey monkey = new Monkey(new GameObject(), 100.0, "Monkey");
	GameObject lightObj;
	Terrain terr;
	
	public OurGame() {
		super();
	}
	
	@Override
	public void init() 
	{
		super.init();
		Mesh mesh = new Mesh("test1.obj");
		Material material = new Material(new Texture("texture.png"), new Vector3f(1.0f, 1.0f, 1.0f), 2, 8);
		
		MeshRenderer renderer = new MeshRenderer(mesh, material);
	
		monkey.addComponent(renderer);
		BaseLight light = new SpotLight(new Vector3f(0, 1, 1), 0.4f, new Vector3f(0, 0, 0.1f),
				new Vector3f(0, 0, 1), 0.7f);
		monkey.getTransform().setPos(0, 5f, 10f);
		
		lightObj = new GameObject();
		lightObj.addComponent(light);
		
		//entityIndicies.add(getRootObject().addChild(monkey));
		addEntity(monkey);
		addObject(lightObj);
		
		terr = new HeightMap("heightmap2.png", "texture.png");
		terr.compile();
		getRootObject().addChild(terr);
		
		getRenderingEngine().getMainCamera().setPos(new Vector3f(0, HeightMap.getHeight(0, 0, terr) + 1f, 0));
	}
	
	@Override
	public void update(float delta) 
	{
		super.update(delta);
		lightObj.getTransform().setPos(getRenderingEngine().getMainCamera().getPos());
		//monkey.getTransform().setPos(new Vector3f(x, y, 0));
		
		Vector3f pos = getRenderingEngine().getMainCamera().getPos();
		float h = HeightMap.getHeight(pos.getX(), pos.getZ(), terr);
		
//		if(Math.abs(pos.getY() - h) > 1f)
//		{
			getRenderingEngine().getMainCamera().setPos(new Vector3f(pos.getX(), 
					h + 1f, pos.getY()));
//			System.out.println(pos.getY());
//			System.out.println(HeightMap.getHeight(pos.getX(), pos.getZ(), terr));
//		}
		
		//monkey.getTransform().setPos(pos.getX(), h + 1f, pos.getZ() + 1f);	
			
		if(Input.getKey(Keyboard.KEY_F))
		{
			getPhysicsEngine().force(monkey, new Vector3f(0.01f, 0f, 0f));
		}
		else
		{
			getPhysicsEngine().zeroForce(monkey);
		}
	}
}

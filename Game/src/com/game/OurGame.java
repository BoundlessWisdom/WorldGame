package com.game;

import java.util.ArrayList;
import java.util.Collection;

import org.lwjgl.input.Keyboard;

import com.engine.components.lighting.BaseLight;
import com.engine.components.lighting.SpotLight;
import com.engine.components.renderObjs.MeshRenderer;
import com.engine.components.renderObjs.terrain.HeightMap;
import com.engine.components.renderObjs.terrain.Terrain;
import com.engine.core.GameInstance;
import com.engine.core.GameObject;
import com.engine.core.Input;
import com.engine.core.Vector2f;
import com.engine.core.Vector3f;
import com.engine.rendering.Material;
import com.engine.rendering.Mesh;
import com.engine.rendering.Mesh.DRAW_WAY;
import com.engine.rendering.Texture;
import com.engine.rendering.meshLoading.InputModel;
import com.engine.rendering.meshLoading.OBJIndex;

public class OurGame extends GameInstance 
{
	Monkey monkey = new Monkey(new GameObject(), 100.0, "Monkey");
	GameObject lightObj;
	
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
		
		Terrain terr = new HeightMap("heightmap.png", "texture.png");
		getRootObject().addChild(terr);
	}
	
	@Override
	public void update(float delta) 
	{
		super.update(delta);
		lightObj.getTransform().setPos(getRenderingEngine().getMainCamera().getPos());
		//monkey.getTransform().setPos(new Vector3f(x, y, 0));
		
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

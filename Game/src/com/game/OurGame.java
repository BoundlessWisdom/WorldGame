package com.game;

import org.lwjgl.input.Keyboard;

import com.engine.rendering.Attenuation;
import com.engine.rendering.Material;
import com.engine.rendering.Mesh;
import com.engine.rendering.Texture;
import com.engine.components.MeshRenderer;
import com.engine.components.lighting.BaseLight;
import com.engine.components.lighting.SpotLight;
import com.engine.components.terrain.CompleteTerrain;
import com.engine.components.terrain.HeightMap;
import com.engine.components.terrain.Terrain;
import com.engine.components.terrain.TerrainTile;
import com.engine.core.GameInstance;
import com.engine.core.GameObject;
import com.engine.core.Input;
import com.engine.core.Vector3f;

public class OurGame extends GameInstance 
{
	Monkey monkey = new Monkey(new GameObject(), 100.0, "Monkey");
	GameObject lightObj;
	Terrain terr;
	CompleteTerrain terrain = CompleteTerrain.getInstance();
	
	public OurGame() {
		super();
	}
	
	@Override
	public void Init() 
	{
		super.Init();
		Mesh mesh = new Mesh("test1.obj");
		Material material = new Material(new Texture("texture.png"), 1f, 8f, null, null, 0.03f, -0.5f);
		
		MeshRenderer renderer = new MeshRenderer(mesh, material);
	
		monkey.AddComponent(renderer);
		BaseLight light = new SpotLight(new Vector3f(0, 1, 0), 0.4f, new Attenuation(0,0,0.1f), 0.7f);
		monkey.GetTransform().SetPos(0f, 5f, 10f);
		
		lightObj = new GameObject();
		lightObj.AddComponent(light);
		
		AddEntity(monkey);
		AddObject(lightObj);
		
		terr = new HeightMap("heightmap.png", "texture.png").compile();
		
		TerrainTile t = new TerrainTile();
		if(!t.addTerrain(terr))
		{
			System.out.println("Ouch");
		}
		
		terrain.addTile(t);
		
		terrain.compile();
		
		getRenderingEngine().GetMainCamera().GetTransform().SetPos(new Vector3f(0, HeightMap.GetHeight(0, 0, terr) + 7f, 0));
		lightObj.GetTransform().SetPos(getRenderingEngine().GetMainCamera().GetTransform().GetPos());
	}
	
	@Override
	public void Update(float delta) 
	{
		super.Update(delta);
		
		lightObj.GetTransform().SetPos(getRenderingEngine().GetMainCamera().GetTransform().GetPos());
		//monkey.GetTransform().setPos(new Vector3f(x, y, 0));
		
		/*Vector3f pos = getRenderingEngine().GetMainCamera().GetTransform().GetPos();
		float h = HeightMap.GetHeight(pos.GetX(), pos.GetZ(), terr);
		
		if(Math.abs(pos.GetY() - h) > 9f)
		{
			getRenderingEngine().GetMainCamera().GetTransform().SetPos(new Vector3f(pos.GetX(), 
					h + 7f, pos.GetZ()));
			System.out.println(pos.GetY());
			System.out.println(HeightMap.GetHeight(pos.GetX(), pos.GetZ(), terr));
		}*/
		
		//monkey.GetTransform().setPos(pos.GetX(), h + 1f, pos.GetZ() + 1f);	
			
		if(Input.GetKey(Keyboard.KEY_F))
		{
			getPhysicsEngine().Force(monkey, new Vector3f(0.01f, 0f, 0f));
		}
		else
		{
			getPhysicsEngine().ZeroForce(monkey);
		}
	}
}

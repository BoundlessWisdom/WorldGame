package com.game;

import com.engine.rendering.Texture;
import com.engine.components.lighting.BaseLight;
import com.engine.components.lighting.SpotLight;
import com.engine.components.terrain.CompleteTerrain;
import com.engine.components.terrain.HeightMap;
import com.engine.components.terrain.Terrain;
import com.engine.components.terrain.TerrainTile;
import com.engine.core.GameInstance;
import com.engine.core.GameObject;
import com.engine.core.Vector3f;
import com.engine.rendering.Attenuation;
import com.engine.rendering.Material;

public class ArchonicaApp extends GameInstance
{	
	CompleteTerrain terrain = CompleteTerrain.getInstance();
	Monkey monkey;
	GameObject lightObj = new GameObject();
	
	
	@Override
	public void Init() 
	{
		super.Init();
		
		Terrain t = new HeightMap("heightmap.png", "bricks.jpg").compile();
		TerrainTile tile = new TerrainTile();
		tile.addTerrain(t);
		terrain.addTile(tile);
		terrain.compile();
		
		BaseLight light = new SpotLight(new Vector3f(0, 1, 0), 0.4f, new Attenuation(0,0,0.1f), 0.7f);
		lightObj.AddComponent(light);
		AddObject(lightObj);
		
		monkey = new Monkey(new GameObject(), 100.0, "Monkey");
		monkey.AddMaterial(new Material(new Texture("bricks.jpg"), 1, 8,
				new Texture("bricks_normal.jpg"), new Texture("bricks_disp.png"), 0.03f, -0.5f));
		monkey.GetTransform().SetPos(getRenderingEngine().GetMainCamera().GetTransform().GetPos());
		AddObject(monkey);
		
		lightObj.GetTransform().SetPos(getRenderingEngine().GetMainCamera().GetTransform().GetPos());
	}
	
	@Override
	public void Update(float delta) 
	{
		super.Update(delta);
		lightObj.GetTransform().SetPos(getRenderingEngine().GetMainCamera().GetTransform().GetPos());
		lightObj.GetTransform().SetRot(getRenderingEngine().GetMainCamera().GetTransform().GetRot());
	}
}

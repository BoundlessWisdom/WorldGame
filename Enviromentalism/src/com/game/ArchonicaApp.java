package com.game;

import ui.Button;
import ui.Menu;

import com.engine.rendering.Texture;
import com.engine.components.MeshRenderer;
import com.engine.components.lighting.BaseLight;
import com.engine.components.lighting.SpotLight;
import com.engine.components.terrain.CompleteTerrain;
import com.engine.components.terrain.HeightMap;
import com.engine.components.terrain.Terrain;
import com.engine.components.terrain.TerrainTile;
import com.engine.components.terrain.Terrain.OriginGravity;
import com.engine.core.CoreEngine;
import com.engine.core.EntityObject;
import com.engine.core.GameInstance;
import com.engine.core.GameObject;
import com.engine.core.Vector3f;
import com.engine.rendering.Attenuation;
import com.engine.rendering.Material;
import com.engine.rendering.Mesh;
import com.engine.rendering.RenderingEngine;

@SuppressWarnings("unused")
public class ArchonicaApp extends GameInstance
{	
	CompleteTerrain terrain = CompleteTerrain.getInstance();
	Monkey monkey;
	GameObject lightObj = new GameObject();
	Menu menu;
	Button button;
	GameObject sphere = new GameObject();
	
	@Override
	public boolean Precursor() //init menu 
	{
		/*button = new Button(Menu.rootMenu, 0,100,0,100,
				new Material(new Texture("menubg.png"), 1, 1, null, null, 
						1, 1)) {
			
			@Override
			public Button setVariable(Object par) {
				// TODO Auto-generated method stub
				return this;
			}
			
			@Override
			public void function() {
				// TODO Auto-generated method stub
				
			}
		}.Compile();*/
		//menu = Menu.rootMenu;
		//SET STUFF
		menu = Menu.rootMenu;
		return true;
	}
	
	@Override
	public boolean UpdatePrecursor() //render, hover, stuff
	{
		//button.Render(shader, renderingEngine);
		//menu = Menu.rootMenu;
		//return true;
		menu.Update();
		return false;
	}
	
	@Override
	public void Init() 
	{
		super.Init();
		
		Terrain t = new HeightMap("flatheight.png", "bricks.jpg", new Vector3f(1,1,1), OriginGravity.LEFT_DOWN);	
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
		//monkey.GetTransform().SetPos(getRenderingEngine().GetMainCamera().GetTransform().GetPos());
		monkey.GetTransform().SetPos(0,0,0);
		AddEntity(monkey);
		
		lightObj.GetTransform().SetPos(getRenderingEngine().GetMainCamera().GetTransform().GetPos());
		
		Ball ball = new Ball(new GameObject(), 1000, "Ball");
		ball.AddMaterial(new Material(new Texture("bricks.jpg"), 1, 8,
				new Texture("bricks_normal.jpg"), new Texture("bricks_disp.png"), 0.03f, -0.5f));
		ball.GetTransform().SetPos(0, HeightMap.GetHeight(0, 0, t),0);
		//AddEntity(ball);
	}
	
	@Override
	public void Update(float delta) 
	{
		super.Update(delta);
		getPhysicsEngine().run(delta);
		lightObj.GetTransform().SetPos(getRenderingEngine().GetMainCamera().GetTransform().GetPos());
		lightObj.GetTransform().SetRot(getRenderingEngine().GetMainCamera().GetTransform().GetRot());
	}
}

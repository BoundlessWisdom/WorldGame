package com.game;

import java.util.ArrayList;

import ui.Button;
import ui.Menu;
import ui.QuitButton;

import com.engine.rendering.Texture;
import com.engine.components.Camera;
import com.engine.components.FreeLook;
import com.engine.components.FreeMove;
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
import com.engine.core.Game;
import com.engine.core.GameInstance;
import com.engine.core.GameObject;
import com.engine.core.Matrix4f;
import com.engine.core.Vector3f;
import com.engine.rendering.Attenuation;
import com.engine.rendering.Material;
import com.engine.rendering.Mesh;
import com.engine.rendering.RenderingEngine;
import com.engine.rendering.Window;

@SuppressWarnings("unused")
public class ArchonicaApp extends GameInstance
{	
	CompleteTerrain terrain = CompleteTerrain.getInstance();
	Monkey monkey;
	GameObject lightObj = new GameObject();
	Menu menu;
	Button button;
	GameObject sphere = new GameObject();
	//GameObject root;
	//GameObject gObj;
	
	ArrayList<ArrayList<GameObject>> childrens = new ArrayList<ArrayList<GameObject>>();
	
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
		Menu.quitButton0.SetMaterial(new Material(new Texture("menubg.png"), 1, 8, null, null, 0.03f, -0.5f));
		Menu.quitButton0.GetTransform().SetPos(new Vector3f(0,0,0));
		
		menu.Compile();
		
		/*root = new GameObject();
		root.SetEngine(CoreEngine.getInstance());
		
		gObj = new GameObject().AddComponent(new FreeLook(0.5f)).AddComponent(new FreeMove(10.0f))
				.AddComponent(new Camera(new Matrix4f().InitPerspective(
						(float) Math.toRadians(70.0f), (float) Window.GetWidth() / (float) Window.GetHeight(), 
						0.01f, 1000.0f)));
		gObj.GetTransform().SetPos(new Vector3f(0,0,0));
		gObj.GetTransform().Update();
		
		
		
		root.AddChild(gObj);
		root.AddChild(lightObj);
		root.AddChild(Menu.quitButton0);*/
		
		return true;
	}
	
	@Override
	public boolean UpdatePrecursor() //render, hover, stuff
	{
		//button.Render(shader, renderingEngine);
		//menu = Menu.rootMenu;
		//return true;
		/*if(GetRootObject() != RootObjects.get(1))
			SetCurrentRootObject(RootObjects.get(1));*/
		//menu.Update();
		
		return true;
	}
	
	@Override
	public void Init() 
	{
		super.Init();
		SetCurrentRootObject(RootObjects.get(0));
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
		
		//childrens.add(GetRootObject().GetAllAttached());
		//ArrayList<GameObject> c2 = new ArrayList<GameObject>();
		
		//c2.add(childrens.get(0).get(0));
		//c2.add(childrens.get(0).get(childrens.get(0).indexOf(lightObj)));
		//c2.add(Menu.quitButton0);
		//childrens.add(c2);
		
		GetRootObject().NewChildren();
		int[] keep = new int[]{
				0, 2
		};
		GetRootObject().TransferChildren(0, 1, keep);
		GetRootObject().AddChild(Menu.quitButton0);
		//GetRootObject().SetChildren(0);
		/*int[] keep = new int[]{
			0, 1	
		};*/
		//GetRootObject().TransferChildren(0, 1, {0, 0});
		//AddAndSetRootObject(root);
		//SetCurrentRootObject(RootObjects.get(1));
		//GetRootObject().UpdateAll(60f);
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

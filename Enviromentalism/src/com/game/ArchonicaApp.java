package com.game;

import java.util.ArrayList;

import ui.Button;
import ui.Menu;
import ui.QuitButton;

import com.archonica.Archon;
import com.archonica.EntClass;
import com.archonica.archons.User;
import com.engine.rendering.Texture;
import com.engine.components.Camera;
import com.engine.components.FreeLook;
import com.engine.components.FreeMove;
import com.engine.components.GameComponent;
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
	
	User MainUser;
	ArrayList<ArrayList<GameObject>> childrens = new ArrayList<ArrayList<GameObject>>();
	
	@Override
	public boolean Precursor() //init menu 
	{
		menu = Menu.rootMenu;
		Menu.quitButton0.SetMaterial(new Material(new Texture("menubg.png"), 1, 8, null, null, 0.03f, -0.5f));
		Menu.quitButton0.GetTransform().SetPos(new Vector3f(0,0,0));
		
		menu.Compile();
		
		return true;
	}
	
	@Override
	public boolean UpdatePrecursor() //render, hover, stuff
	{
		
		return true;
	}
	
	@Override
	public void Init() 
	{
		super.Init();
		
		MainUser = new User("BRAN", this, new Archon(new EntClass(), 1, 1));
		
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
		
		GetRootObject().NewChildren();
		int[] keep = new int[]{
				0, 2
		};
		GetRootObject().TransferChildren(0, 1, keep);
		GetRootObject().AddChild(Menu.quitButton0);
		CanMoveCamera(false);
	}
	
	@Override
	public void Update(float delta) 
	{
		super.Update(delta);
		getPhysicsEngine().run(delta);
		lightObj.GetTransform().SetPos(getRenderingEngine().GetMainCamera().GetTransform().GetPos());
		lightObj.GetTransform().SetRot(getRenderingEngine().GetMainCamera().GetTransform().GetRot());
	}
	
	
	public void CanMoveCamera(boolean CameraMove)
	{
		GameComponent comp = GetCameraObject().GetComponent(1);
		if(comp  instanceof FreeMove)
			((FreeMove)comp).SetMove(CameraMove);
		else
		{
			new Exception("FreeMove has disapeared from it's normal slot! "
					+ "Instead, there is " + comp.toString() + " in it's place.").printStackTrace();
			CoreEngine.Stop();
		}
	}
	
	public GameObject GetCameraObject()
	{
		return GetRootObject().GetAllAttached().get(0);
	}
}

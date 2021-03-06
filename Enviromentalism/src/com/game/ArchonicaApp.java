package com.game;

import java.util.HashMap;
import java.util.Timer;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;

import ui.Button;
import ui.Menu;

import com.archonica.Archon;
import com.archonica.EntClass;
import com.archonica.archons.User;
import com.archonica.objects.ProjectileFireball;
import com.engine.physics.PhysicsEngine;
import com.engine.rendering.Texture;
import com.engine.components.FreeLook;
import com.engine.components.FreeMove;
import com.engine.components.GameComponent;
import com.engine.components.MeshRenderer;
import com.engine.components.lighting.BaseLight;
import com.engine.components.lighting.PointLight;
import com.engine.components.lighting.SpotLight;
import com.engine.components.terrain.CompleteTerrain;
import com.engine.components.terrain.HeightMap;
import com.engine.components.terrain.Terrain;
import com.engine.components.terrain.TerrainTile;
import com.engine.components.terrain.Terrain.OriginGravity;
import com.engine.core.CoreEngine;
import com.engine.core.GameInstance;
import com.engine.core.GameObject;
import com.engine.core.Input;
import com.engine.core.Vector2f;

import static com.engine.core.Input.*;

import com.engine.core.Vector3f;
import com.engine.rendering.Attenuation;
import com.engine.rendering.Material;
import com.engine.rendering.Mesh;
import com.engine.rendering.Window;


public class ArchonicaApp extends GameInstance
{	
	CompleteTerrain terrain = CompleteTerrain.getInstance();
	Monkey monkey;
	Archon archon;
	Archon archoff;
	GameObject lightObj = new GameObject();
	GameObject l2 = new GameObject();
	Menu menu;
	Button button;
	GameObject sphere = new GameObject();
	Icicle icicle;
	Icicle ice;
	
	boolean FullScreen = true;
	
	boolean t = false;
	
	User MainUser;
	public HashMap<String, CameraInfo> DifCameraInfo = new HashMap<String, CameraInfo>();
	
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
	public boolean UpdatePrecursor(float delta) //render, hover, stuff
	{
		boolean render = true;
		
		if(Menu.OneIsRunning)
			menu.Update(delta);
		
		if(Input.GetKey(KEY_F1))
		{
			Window.SetDisplayMode(800, 600, !FullScreen);
			Display.update();
		}	
		
		if(Input.GetKeyDown(KEY_P))
		{
			Menu.LoadMenu(KEY_P, this, GetRootObject());
		}
		
		return render;
	}
	
	@Override
	public void Init() 
	{
		super.Init();
		
		MainUser = new User("BRAN", this, new Archon(new EntClass(), 1, 1));
		
		SetCurrentRootObject(RootObjects.get(0));
		Terrain t = new HeightMap("flat2.png", 
				new Material(new Texture("bricks.jpg"), 1, 8, new Texture("bricks_normal.jpg"), 
						new Texture("bricks_disp.png"), 
						0.03f, -0.5f),
				new Vector3f(1, 1, 1),
				OriginGravity.LEFT_DOWN);
		
		TerrainTile tile = new TerrainTile();
		tile.addTerrain(t);
		terrain.addTile(tile);
		terrain.compile();
		
		BaseLight light = new SpotLight(new Vector3f(0, 1, 0), 0.4f, new Attenuation(0,0,0.1f), 0.7f);
		lightObj.AddComponent(light);
		AddObject(lightObj);
		
		BaseLight light2 = new PointLight(new Vector3f(1f, 1f, 1f), 1f, new Attenuation(0, 0, 1f));
		l2.AddComponent(light2);
		AddObject(l2);
		
		archon = new Archon(100.0f, 0f);
		archon.AddMaterial(new Material(new Texture("menubg.png"), 1, 8,
				new Texture("menubg.png"), new Texture("menubg.png"), 0.03f, -0.5f));
		archon.mass = 100;
		archon.GetTransform().SetPos(15f, terrain.GetHeight(new Vector2f(10f, 10f)), 15f);
		
		archoff = new Archon(100.0f, 0f);
		archoff.AddMaterial(new Material(new Texture("menubg.png"), 1, 8,
				new Texture("menubg.png"), new Texture("menubg.png"), 0.03f, -0.5f));
		archoff.mass = 100;
		archoff.GetTransform().SetPos(20f, 15f, 20f);
		
		FreeMove.obj = archon;
		FreeLook.obj = archon;
		//GetCameraObject().GetTransform().SetPos(0, terrain.GetHeight(new Vector2f(0f, 0f)), 0);
		GetCameraObject().GetTransform().SetPos(archon.GetTransform().GetPos().GetX(), 
				archon.GetTransform().GetPos().GetY() + FreeLook.radius.m_y + 2f, archon.GetTransform().GetPos().GetZ() - FreeLook.radius.m_y);

		
		AddEntity(archon);
		AddEntity(archoff);
		ProjectileFireball fire = new ProjectileFireball(3);
		fire.GetTransform().SetPos(0, 0, 0);
		fire.AddMaterial(new Material(new Texture("menubg.png"), 1, 8,
				new Texture("menubg.png"), new Texture("menubg.png"), 0.03f, -0.5f));
		AddEntity(fire);
		fire.launch(new Vector2f(1,1));
		
		/*monkey = new Monkey(new GameObject(), 100.0);
		monkey.AddMaterial(new Material(new Texture("bricks.jpg"), 1, 8,
				new Texture("bricks_normal.jpg"), new Texture("bricks_disp.png"), 0.03f, -0.5f));
		//monkey.GetTransform().SetPos(getRenderingEngine().GetMainCamera().GetTransform().GetPos());
		monkey.GetTransform().SetPos(0,10f,0);
		AddEntity(monkey);*/
		
		lightObj.GetTransform().SetPos(
				getRenderingEngine().GetMainCamera()
				.GetTransform()
				.GetPos());
		l2.GetTransform().SetPos(
				archon
				.GetTransform()
				.GetPos());
		
		/*Ball ball = new Ball(new GameObject(), 1000);
		ball.AddMaterial(new Material(new Texture("grass.jpg"), 1, 8,
				new Texture("grass_normal.jpg"), new Texture("grass_disp.png"), 0.03f, -0.5f));
		ball.GetTransform().SetPos(10f, 10f, 10f);
		AddEntity(ball);*/
		Mesh m = new Mesh("icicle.obj", 0.1f);
		
		icicle = new Icicle(new GameObject(), 100.0f);
		icicle.AddMaterial(new Material(
				new Texture("bricks.jpg"), 1, 8, new Texture("bricks_normal.jpg"), 
				new Texture("bricks_disp.png"), 0.03f, -0.5f), m);
		icicle.GetTransform().SetPos(new Vector3f(15f, 15f, 15f));
		AddEntity(icicle);
		
		Mesh m2 = new Mesh("icicle.obj");
		
		ice = new Icicle(new GameObject(), 1000f);
		ice.AddMaterial(new Material(
			new Texture("bricks.jpg"), 1, 8, new Texture("bricks_normal.jpg"), 
			new Texture("bricks_disp.png"), 0.03f, -0.5f), m2);
		AddEntity(ice);
		
		DifCameraInfo.put("Game", new CameraInfo(GetCameraObject()));
		
		GetRootObject().newRenderSet();
		int[] keep = new int[]{
				0, 2
		};
		GetRootObject().TransferChildren(0, 1, keep);
		GetRootObject().AddChild(Menu.quitButton0);
		GetCameraObject().GetTransform().SetPos(0, 0, 0);
		//GetCameraObject().GetTransform().LookAt(new Vector3f(0, 0, 0), FreeLook.Y_AXIS);
		CanMoveCamera(false);
		DifCameraInfo.put("Menu", new CameraInfo(GetCameraObject()));
		
		PhysicsEngine.terrain = terrain;
		
		//System.out.println(terrain.GetHeight(archon.GetTransform().GetPos().GetXZ()));
	}
	
	@Override
	public void Update(float delta) 
	{
		if(Menu.OneIsRunning)
		{
			return;
		}
		//System.out.println(GetCameraObject().GetTransform().GetPos().toString());

		super.Update(delta);
		getPhysicsEngine().run(delta);
		lightObj.GetTransform().SetPos(getRenderingEngine().GetMainCamera().GetTransform().GetPos());
		lightObj.GetTransform().SetRot(getRenderingEngine().GetMainCamera().GetTransform().GetRot());
		
		archon.worldmanage();
		archon.collisioncheck();
		archoff.worldmanage();
		
		if(!t)
		{
			
			
			AddEntity(ice);
			//icicle = new Icicle(new GameObject(), )
			t = true;
		}

	}
	
	
	public void CanMoveCamera(boolean CameraMove)
	{
		GameComponent freelook = GetCameraObject().GetComponent(0);
		GameComponent freemove = GetCameraObject().GetComponent(1);
		
		if(freelook instanceof FreeLook)
			((FreeLook)freelook).SetMove(CameraMove);
		
		else
		{
			new Exception("FreeMove has disapeared from it's normal slot! "
					+ "Instead, there is " + freelook.toString() + " in it's place.").printStackTrace();
			CoreEngine.Stop();
		}
		
		if(freemove  instanceof FreeMove)
			((FreeMove)freemove).SetMove(CameraMove);
		else
		{
			new Exception("FreeMove has disapeared from it's normal slot! "
					+ "Instead, there is " + freemove.toString() + " in it's place.").printStackTrace();
			CoreEngine.Stop();
		}
	}
	
	public GameObject GetCameraObject()
	{
		return GetRootObject().GetAllAttached().get(0);
	}
}

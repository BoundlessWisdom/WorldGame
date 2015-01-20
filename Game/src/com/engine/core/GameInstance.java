package com.engine.core;

import java.util.ArrayList;

import com.engine.components.Camera;
import com.engine.components.FreeLook;
import com.engine.components.FreeMove;
import com.engine.core.GameObject;
import com.engine.core.Matrix4f;
import com.engine.rendering.Window;
import com.engine.components.terrain.CompleteTerrain;
import com.engine.physics.PhysicsEngine;
import com.engine.rendering.RenderingEngine;

public class GameInstance extends Game
{	
	//private static GameInstance instance = new GameInstance();
	public static ArrayList<GameObject> RootObjects = new ArrayList<GameObject>(); //Allows for multiple root game objects
	private static ArrayList<EntityObject> entities = new ArrayList<EntityObject>(); //stores entities (Game Objects that are affected by physics) in the game
	private static ArrayList<Integer> entityIndicies = new ArrayList<Integer>(); //since entities extend GameObject, we want a way to find the location of an 
										//EntityObject from entities in the place where the GameObjects are stored (in GameObject itself)
	
	/*public static GameInstance getInstance()
	{
		return instance;
	}*/
	
	public GameInstance()
	{
		
	}
	
	@Override
	public void Init()
	{
		super.Init();
		
		AddObject(new GameObject().AddComponent(new FreeLook(0.5f)).AddComponent(new FreeMove(10.0f))
				.AddComponent(new Camera(new Matrix4f().InitPerspective(
						(float) Math.toRadians(70.0f), (float) Window.GetWidth() / (float) Window.GetHeight(), 
						0.01f, 1000.0f)))); //adds camera, freemove, and freelook to a game object,
									//which will represent a world camera
		
		GetRootObject().AddChild(CompleteTerrain.getInstance()); //Actually adds camera object to the game
		RootObjects.add(GetRootObject());
	}
	
	@Override
	public void Update(float delta) 
	{
		super.Update(delta);
	}
	
	public void updateEntities()
	{
		for(int i = 0; i < entities.size(); i++)
		{
			int index = entityIndicies.get(i);
			GetRootObject().setChild(index, entities.get(i));
		}
	}
		
	public void AddEntity(EntityObject entity)
	{
		entities.add(entity);
		GetRootObject().AddChild(entity);
		getPhysicsEngine().add(entity);
		entityIndicies.add(GetRootObject().getNumberChildrenInSet() - 1);
	}
	
	public void RemoveEntity(EntityObject entity) 
	{
		entities.remove(entity);
		getPhysicsEngine().remove(entity);
	}
	
	public static ArrayList<EntityObject> addtoEntities(EntityObject entity)
	{
		entities.add(entity);
		return entities;
	}
	
	public static ArrayList<EntityObject> getEntities()
	{
		return entities;
	}
	
	public static void setEntities(ArrayList<EntityObject> entities)
	{
		GameInstance.entities = entities;
	}
	
	public static PhysicsEngine getPhysicsEngine()
	{
		return CoreEngine.GetPhysicsEngine();
	}
	
	public static RenderingEngine getRenderingEngine()
	{
		return CoreEngine.GetRenderingEngine();
	}
	
	public void AddAndSetRootObject(GameObject root)
	{
		SetCurrentRootObject(root);
		RootObjects.add(root);
	}
}

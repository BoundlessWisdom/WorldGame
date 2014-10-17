package com.engine.core;

import java.util.ArrayList;

import com.engine.components.renderObjs.terrain.CompleteTerrain;
import com.engine.physics.PhysicsEngine;
import com.engine.rendering.RenderingEngine;

public class GameInstance extends Game
{	
	private static GameInstance instance = new GameInstance();
	
	private static ArrayList<EntityObject> entities = new ArrayList<EntityObject>();
	private static ArrayList<Integer> entityIndicies = new ArrayList<Integer>();
	
	public static GameInstance getInstance()
	{
		return instance;
	}
	
	protected GameInstance()
	{
		
	}
	
	public void init()
	{
		getRootObject().addChild(CompleteTerrain.getInstance());
	}
	
	@Override
	public void update(float delta) 
	{
		super.update(delta);
	}
	
	public void updateEntities()
	{
		for(int i = 0; i < entities.size(); i++)
		{
			int index = entityIndicies.get(i);
			getRootObject().setChild(index, entities.get(i));
		}
	}
	
	public void addEntity(EntityObject entity)
	{
		entities.add(entity);
		getRootObject().addChild(entity);
		getPhysicsEngine().add(entity);
		entityIndicies.add(getRootObject().childrenSize() - 1);
	}
	
	public void removeEntity(EntityObject entity) 
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
		return CoreEngine.getPhysicsEngine();
	}
	
	public static RenderingEngine getRenderingEngine()
	{
		return CoreEngine.getRenderingEngine();
	}
	
	public void addObject(GameObject obj)
	{
		getRootObject().addChild(obj);
	}
}

/*
 * Copyright (C) 2014 Benny Bobaganoosh
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.engine.core;

import com.engine.components.GameComponent;
import com.engine.rendering.RenderingEngine;
import com.engine.rendering.Shader;

import java.util.ArrayList;

public class GameObject
{
	protected ArrayList<ArrayList<GameObject>> m_renderSets;
	protected ArrayList<GameObject> m_currentRenderSet;
	protected ArrayList<GameComponent> m_components;
	protected Transform m_transform;
	protected CoreEngine m_engine;

	public GameObject()
	{
		m_renderSets = new ArrayList<ArrayList<GameObject>>();
		m_currentRenderSet = new ArrayList<GameObject>();
		m_components = new ArrayList<GameComponent>();
		m_transform = new Transform();
		m_engine = null;
		
		m_renderSets.add(m_currentRenderSet);
	}

	public void AddChild(GameObject child)
	{
		m_currentRenderSet.add(child);
		child.SetEngine(m_engine);
		child.GetTransform().SetParent(m_transform);
	}

	public GameObject AddComponent(GameComponent component)
	{
		m_components.add(component);
		component.SetParent(this);

		return this;
	}

	public void InputAll(float delta)
	{
		Input(delta);

		for(GameObject child : m_currentRenderSet)
			child.InputAll(delta);
	}

	public void UpdateAll(float delta)
	{
		Update(delta);

		for(GameObject child : m_currentRenderSet)
			child.UpdateAll(delta);
	}

	public void RenderAll(Shader shader, RenderingEngine renderingEngine)
	{
		Render(shader, renderingEngine);

		for(GameObject child : m_currentRenderSet)
			child.RenderAll(shader, renderingEngine);
	}

	public void Input(float delta)
	{
		m_transform.Update();

		for(GameComponent component : m_components)
			component.Input(delta);
	}

	public void Update(float delta)
	{
		for(GameComponent component : m_components)
			component.Update(delta);
	}

	public void Render(Shader shader, RenderingEngine renderingEngine)
	{
		for(GameComponent component : m_components)
			component.Render(shader, renderingEngine);
	}

	public ArrayList<GameObject> GetAllAttached()
	{
		ArrayList<GameObject> result = new ArrayList<GameObject>();

		for(GameObject child : m_currentRenderSet)
			result.addAll(child.GetAllAttached());

		result.add(this);
		return result;
	}

	public Transform GetTransform()
	{
		return m_transform;
	}

	public void SetEngine(CoreEngine engine)
	{
		if(this.m_engine != engine)
		{
			this.m_engine = engine;

			for(GameComponent component : m_components)
				component.AddToEngine(engine);

			for(GameObject child : m_currentRenderSet)
				child.SetEngine(engine);
		}
	}
	
	public void privatize()
	{
		new Exception("This method is private for this extender!").printStackTrace();
	}
	
	public void setChild(int index, GameObject obj)
	{
		m_currentRenderSet.set(index, obj);
	}
	
	public int getNumberChildrenInSet()
	{
		return m_currentRenderSet.size();
	}
	
	public void newRenderSet()
	{
		m_renderSets.add(new ArrayList<GameObject>());
	}
	
	public void TransferChildren(int from, int to, int[] keep)
	{
		for(int i = 0; i < m_renderSets.get(from).size(); i++)
		{
			for(int j = 0; j < keep.length; j++)
			{
				if(i != keep[j])
					continue;
				else
				{
					m_renderSets.get(to).add(m_renderSets.get(from).get(i));
					break;
				}
			}
		}
		
		m_currentRenderSet = m_renderSets.get(to);
	}
	
	public void SetChildren(int index)
	{
		m_currentRenderSet = m_renderSets.get(index);
	}
	
	public GameObject GetAttached(int index)
	{
		if(index >= m_currentRenderSet.size())
			return null;
		
		return m_currentRenderSet.get(index);
	}
	
	public GameComponent GetComponent(int index)
	{
		if(index >= m_components.size())
			return null;
		
		return m_components.get(index);
	}
}

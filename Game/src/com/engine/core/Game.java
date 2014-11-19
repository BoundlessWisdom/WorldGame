package com.engine.core;

import com.engine.rendering.RenderingEngine;

public abstract class Game
{
	private GameObject m_root;
	
	public boolean Precursor() {return true;};
	public boolean UpdatePrecursor() {return true;};
	
	public void Init() {}

	public void Input(float delta)
	{
		GetRootObject().InputAll(delta);
	}

	public void Update(float delta)
	{
		GetRootObject().UpdateAll(delta);
	}

	public void Render(RenderingEngine renderingEngine)
	{
		renderingEngine.Render(GetRootObject());
	}

	public void AddObject(GameObject object)
	{
		GetRootObject().AddChild(object);
	}

	protected GameObject GetRootObject()
	{
		if(m_root == null)
			m_root = new GameObject();

		return m_root;
	}
	
	public void SetCurrentRootObject(GameObject root)
	{
		this.m_root = root;
	}

	public void SetEngine(CoreEngine engine) { GetRootObject().SetEngine(engine); }
}

package com.engine.components;

import com.engine.core.GameObject;
import com.engine.rendering.RenderingEngine;
import com.engine.core.Transform;
import com.engine.rendering.Shader;

@SuppressWarnings("unused")
public abstract class GameComponent
{
	private GameObject m_parent;
	private RenderingEngine render = null;
	
	public GameComponent() {
		AddToEngine();
	}
	
	public void Input(float delta) {}
	public void Update(float delta) {}
	public void Render(Shader shader, RenderingEngine renderingEngine) {}

	public void SetParent(GameObject parent)
	{
		this.m_parent = parent;
	}

	public Transform GetTransform()
	{
		return m_parent.GetTransform();
	}
	
	public void AddToEngine() {}
}


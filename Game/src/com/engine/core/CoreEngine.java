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

import org.lwjgl.input.Keyboard;

import com.engine.physics.PhysicsEngine;
import com.engine.rendering.RenderingEngine;
import com.engine.rendering.Window;

public class CoreEngine
{
	private static final CoreEngine instance = new CoreEngine();
	
	private static boolean         m_isRunning = false;
	private static Game    			m_game;
	private static RenderingEngine m_renderingEngine;
	private static RenderingEngine m_ExtRenderingEngine;
	private static PhysicsEngine m_physicsEngine;
	private static int             m_width;
	private static int             m_height;
	private static double          m_frameTime;
	
	private CoreEngine()
	{
		
	}
	
	private CoreEngine(int width, int height, double framerate, Game game)
	{
	
	}
	
	public static CoreEngine getInstance()
	{
		return instance;
	}
	
	public static void CreateEngine(int width, int height, double framerate, Game game)
	{
		m_isRunning = false;
		m_game = game;
		m_width = width;
		m_height = height;
		m_frameTime = 1.0/framerate;
		m_game.SetEngine(CoreEngine.getInstance());
	}

	public static void CreateWindow(String title)
	{
		Window.CreateWindow(m_width, m_height, title);
		m_renderingEngine = new RenderingEngine();
		m_ExtRenderingEngine = new RenderingEngine();
		m_physicsEngine = PhysicsEngine.GetInstance();
	}

	public static void Start()
	{
		if(m_isRunning)
			return;
		
		Run();
	}
	
	public static void Stop()
	{
		if(!m_isRunning)
			return;
		
		m_isRunning = false;
	}
	
	public static void Ext()
	{
		m_game.Precursor();
	}
	
	public static boolean ExtUpdate()
	{
		return m_game.UpdatePrecursor();
	}
	
	public static void EngineUpdate(float delta)
	{
		m_game.Input(delta);
		Input.Update();
		
		m_game.Update(delta);
	}
	
	private static void Run()
	{
		m_isRunning = true;
		
		int frames = 0;
		double frameCounter = 0;
		
		Ext();
		
		m_game.Init();

		double lastTime = Time.GetTime();
		double unprocessedTime = 0;
		
		while(m_isRunning)
		{
			if(Input.GetKey(Keyboard.KEY_ESCAPE))
			{
				m_isRunning = false;
			}
			
			boolean render = false;

			double startTime = Time.GetTime();
			double passedTime = startTime - lastTime;
			lastTime = startTime;
			
			unprocessedTime += passedTime;
			frameCounter += passedTime;
			
			render = ExtUpdate();
			
			while(unprocessedTime > m_frameTime)
			{
				render = true;
				
				unprocessedTime -= m_frameTime;
				
				if(Window.IsCloseRequested())
					Stop();

				EngineUpdate((float)m_frameTime);
				
				if(frameCounter >= 1.0)
				{
					System.out.println(frames);
					frames = 0;
					frameCounter = 0;
				}
			}
			if(render)
			{
				m_game.Render(m_renderingEngine);
				Window.Render();
				frames++;
			}
			else
			{
				try
				{
					Thread.sleep(1);
				}
				catch (InterruptedException e)
				{
					e.printStackTrace();
				}
			}
		}
		
		CleanUp();
	}

	private static void CleanUp()
	{
		Window.Dispose();
	}

	public static RenderingEngine GetRenderingEngine() {
		return m_renderingEngine;
	}
	
	public static PhysicsEngine GetPhysicsEngine()
	{
		return m_physicsEngine;
	}
	
	public static void AddSecondaryRenderingEngine(RenderingEngine re)
	{
		m_ExtRenderingEngine = re;
	}
	
	public static RenderingEngine GetSecondaryEngine()
	{
		return m_ExtRenderingEngine; 
	}
}

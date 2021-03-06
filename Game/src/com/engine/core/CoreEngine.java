package com.engine.core;

import org.lwjgl.input.Keyboard;

import com.engine.physics.PhysicsEngine;
import com.engine.rendering.RenderingEngine;
import com.engine.rendering.Window;

public class CoreEngine
{
	static final CoreEngine instance = new CoreEngine();
	
	private static boolean         m_isRunning = false;
	private static Game    			m_game;
	private static RenderingEngine m_renderingEngine;
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
	}
	public static void CreateWindow(String title)
	{
		CreateWindow(title, true);
	}
	public static void CreateWindow(String title, boolean fullscreen)
	{
		Window.CreateWindow(m_width, m_height, title, fullscreen);
		m_renderingEngine = new RenderingEngine();
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
	
	private static void Run()
	{
		m_isRunning = true;
		
		int frames = 0;
		double frameCounter = 0;
		
		m_game.Precursor();
		
		m_game.Init();

		double lastTime = Time.GetTime();
		double unprocessedTime = 0;
		
		while(m_isRunning)
		{
			if(Input.GetKey(Keyboard.KEY_ESCAPE))
			{
				m_isRunning = false;
			}
			
			boolean RenderWindow = false;
			boolean RenderEngine = false;

			double startTime = Time.GetTime();
			double passedTime = startTime - lastTime;
			lastTime = startTime;
			
			unprocessedTime += passedTime;
			frameCounter += passedTime;
			
			while(unprocessedTime > m_frameTime)
			{
				//render = true;
				RenderEngine = m_game.UpdatePrecursor((float)m_frameTime);
				RenderWindow = true;
				
				unprocessedTime -= m_frameTime;
				
				if(Window.IsCloseRequested())
					Stop();

				m_game.Input((float)m_frameTime);
				Input.Update();
				
				m_game.Update((float)m_frameTime);
				
				if(frameCounter >= 1.0)
				{
					System.out.println(frames);
					frames = 0;
					frameCounter = 0;
				}
			}
			
			if(RenderWindow)
			{
				Window.Render();
				frames++;
			}
			
			if(RenderEngine)
				m_game.Render(m_renderingEngine);
			
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
}

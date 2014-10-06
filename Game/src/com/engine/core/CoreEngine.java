package com.engine.core;

import info.engine.extra.physics.PhysicsEngine;

import org.lwjgl.input.Keyboard;

import com.engine.rendering.RenderingEngine;
import com.engine.rendering.Window;
import com.game.OurGame;

public class CoreEngine 
{	
	private boolean isRunning;
	private OurGame game;
	private int w, h;
	private double frameTime;
	
	private static CoreEngine instance = null;
	private static RenderingEngine renderEngine = null;
	private static PhysicsEngine physicsEngine = null;
	
	public static CoreEngine getInstance()
	{
		return instance;
	}
	
	public static RenderingEngine getRenderingEngine()
	{
		return renderEngine;
	}
	
	public static PhysicsEngine getPhysicsEngine()
	{
		return physicsEngine;
	}
	
	public static CoreEngine initiate(int w, int h, double frameRate, OurGame game)
	{
		instance = new CoreEngine();
		instance.game = game;
		instance.w = w;
		instance.h = h;
		instance.frameTime = 1.0 / frameRate;
		
		return instance;
	}
	
	private CoreEngine()
	{
		this.isRunning = false;
		physicsEngine = PhysicsEngine.getInstance();
	}
	
	public void createWindow(String title)
	{
		Window.createWindow(w, h, title);
		renderEngine = RenderingEngine.getInstance();
		System.out.println(RenderingEngine.getOpenGLVersion());
	}
	
	public void start()
	{
		if(isRunning)
			return;
		
		run();
	}
	
	public void stop()
	{
		if(!isRunning)
			return;
		
		isRunning = false;
	}
	
	private void run()
	{
		isRunning = true;
		
		int frame = 0;
		double frameCounter = 0;
		
		game.init();
		
		double lastTime = Time.getTime();
		double unprocessedTime = 0; //how much time i still need to process		
		
		while(isRunning)
		{
			if(Input.getKey(Keyboard.KEY_ESCAPE))
				stop();
			
			boolean render = false;
			
			double startTime = Time.getTime();
			double passedTime = startTime - lastTime;
			lastTime = startTime;
			
			unprocessedTime += passedTime;
			frameCounter += passedTime;
			
			while(unprocessedTime > frameTime)
			{
				render = true;
				
				unprocessedTime -= frameTime;
				
				if(Window.isCloseRequested())
					stop();
				
				Input.update();
				
				game.input((float)frameTime);
				renderEngine.input((float)frameTime);
				
				Input.update();
				
				physicsEngine.run(frameTime);
				
				game.update((float)frameTime);
				
				if(frameCounter >= 1)
				{
					System.out.println(frame);
					frame = 0;
					frameCounter = 0;
				}				
			}
			if(render)
			{
				renderEngine.render(game.getRootObject());
				Window.render();
//				render();
				frame++;
			}
			else
			{
				try {
					Thread.sleep(1);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
		
		cleanup();
	}
	
//	private void render()
//	{
//		RenderUtil.clearScreen();
//		game.render();
//		Window.render();
//	}
	
	private void cleanup()
	{
		Window.dispose();
	}
}

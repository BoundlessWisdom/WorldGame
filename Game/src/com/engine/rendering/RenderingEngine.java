package com.engine.rendering;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL32.GL_DEPTH_CLAMP;

import java.util.ArrayList;

import com.engine.components.lighting.BaseLight;
import com.engine.core.GameObject;
import com.engine.core.Vector3f;
import com.engine.rendering.shaders.ForwardAmbient;
import com.engine.rendering.shaders.ForwardDirectional;
import com.engine.rendering.shaders.ForwardPoint;
import com.engine.rendering.shaders.ForwardSpot;
import com.engine.rendering.shaders.Shader;

public class RenderingEngine 
{
	private Camera mainCamera;
	private Vector3f ambientLight;
	
	private ArrayList<BaseLight> lights;
	private BaseLight activeLight;
	
	private static RenderingEngine instance = new RenderingEngine();
	
	public static RenderingEngine getInstance()
	{
		return instance;
	}
		
	private RenderingEngine()
	{
		initGraphics();
		 
		lights = new ArrayList<BaseLight>();
		
		mainCamera = new Camera((float)Math.toRadians(70.0f), (float)Window.getWidth() / (float)Window.getHeight(), 
				0.01f, 1000.0f);
		
		ambientLight = new Vector3f(0.2f, 0.2f, 0.2f);
	}
	
	public void input(float delta)
	{
		mainCamera.input(delta);
	}
	
	public void render(GameObject object)
	{
		clearScreen();
		//float sin = (float)Time.getTime();
		//Shader.setRenderingEngine(this);
		
		//Shader.setGlobalRenderingEngine(this);
	//	clearLightList();
		lights.clear();
		object.addToRenderingEngine(this);
		
		Shader forwardAmbient = ForwardAmbient.getInstance(); 		
		forwardAmbient.setRenderingEngine(this);
		object.render(forwardAmbient);
		
		glEnable(GL_BLEND);		
		glBlendFunc(GL_ONE, GL_ONE); //first * 1 + second * one
		glDepthMask(false);
		glDepthFunc(GL_EQUAL); //will only add light to things in final image
		
		for(BaseLight light : lights)
		{
			light.getShader().setRenderingEngine(this);
			activeLight = light;
			object.render(light.getShader());		
		}
		
		glDepthFunc(GL_LESS);
		glDepthMask(true);
		glDisable(GL_BLEND);
}
	
//	private void clearLightList()
//	{
//		directionalLights.clear();
//		pointLights.clear();
//	}
	
	private static void clearScreen()
	{
		//TODO: stencil buffer
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
	}
	
	private static void setTextures(boolean enabled)
	{
		if(enabled)
			glEnable(GL_TEXTURE_2D);
		else
			glDisable(GL_TEXTURE_2D);
	}
	
	private static void initGraphics()
	{
		glClearColor(0f, 0f, 0f, 0f);
		
		glFrontFace(GL_CW);
		glCullFace(GL_BACK);
		glEnable(GL_CULL_FACE);
		glEnable(GL_DEPTH_TEST);
		
		glEnable(GL_DEPTH_CLAMP);
		
		glEnable(GL_TEXTURE_2D);
		//glEnable(GL_FRAMEBUFFER_SRGB);
	}
	
	public static String getOpenGLVersion()
	{
		return glGetString(GL_VERSION);
	}
	
	private static void setClearColor(Vector3f color)
	{
		glClearColor(color.getX(), color.getY(), color.getZ(), 1.0f);
	}
	
	private static void unbindTextures()
	{
		glBindTexture(GL_TEXTURE_2D, 0);
	}

	public Camera getMainCamera() 
	{
		return mainCamera;
	}

	public void setMainCamera(Camera mainCamera) 
	{
		this.mainCamera = mainCamera;
	}
	
	public Vector3f getAmbientLight()
	{
		return ambientLight;
	}
		
	public void addLight(BaseLight light)
	{
		lights.add(light);
	}
	
	public BaseLight getActiveLight()
	{
		return activeLight;
	}
}

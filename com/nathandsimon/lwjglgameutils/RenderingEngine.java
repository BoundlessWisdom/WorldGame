package com.nathandsimon.lwjglgameutils;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;

import java.util.ArrayList;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
/**
 * A simple rendering engine.
 * @author nathan
 */
public class RenderingEngine extends EngineComponent{
	private ArrayList<IObject> m_objs = new ArrayList<IObject>();
	private static final String VERTEX_SHADER_LOCATION = "res/vertex_phong_lighting.vs";
	private static final String FRAGMENT_SHADER_LOCATION = "res/vertex_phong_lighting.fs";
	private int shaderProgram;
	private ArrayList<Integer> vboVertexHandle = new ArrayList<Integer>();
	private ArrayList<Integer> vboNormalHandle = new ArrayList<Integer>();
	public EulerCamera cam;
	public RenderingEngine()
	{
		init();
	}
	/**
	 * Initialize the renderer. ALWAYS CALL IN INITIALIZATION CODE.
	 */
	public void init()
	{
		 cam = new EulerCamera((float) Display.getWidth() / (float) Display.getHeight(), -2.19f, 1.36f, 11.45f);
	     cam.setFieldOfView(70);
	     cam.applyPerspectiveMatrix();
	     glEnable(GL_DEPTH_TEST);
	     glEnable(GL_LIGHTING);
	     glEnable(GL_LIGHT0);
	     glLightModel(GL_LIGHT_MODEL_AMBIENT, BufferTools.asFlippedFloatBuffer(new float[]{0.05f, 0.05f, 0.05f, 1f}));
	     glLight(GL_LIGHT0, GL_POSITION, BufferTools.asFlippedFloatBuffer(new float[]{0, 0, 0, 1}));
	     glEnable(GL_CULL_FACE);
	     glCullFace(GL_BACK);
	     shaderProgram = ShaderLoader.loadShaderPair(VERTEX_SHADER_LOCATION, FRAGMENT_SHADER_LOCATION);
	     glUseProgram(shaderProgram);
	     glShadeModel(GL_SMOOTH);
	     glMaterialf(GL_FRONT, GL_SHININESS, 120);
	     glMaterial(GL_FRONT, GL_DIFFUSE, BufferTools.asFlippedFloatBuffer(0.4f, 0.27f, 0.17f, 0));
	}
	/**
	 * Add an object to the renderer.
	 * @param obj the object to add.
	 */
	public void add(IObject obj)
	{
		m_objs.add(obj);
		int[] vbos;
        vbos = OBJLoader.createVBO(obj.getSprite());
		vboVertexHandle.add(vbos[0]);
		vboNormalHandle.add(vbos[1]);
	}
	/**
	 * Remove an object from the renderer.
	 * @param obj the object to remove.
	 */
	public void remove(IObject obj)
	{
		dispObj(obj.getIndex());
		m_objs.remove(obj.getIndex());
		for(int i = obj.getIndex(); i < m_objs.size(); i++)
		{
			m_objs.get(i).setIndex(i);
		}
	}
	
	/**
	 * Run the renderer.
	 */
	public void run(long elapsedTime)
	{
		cam.processMouse(1, 80, -80);
        cam.processKeyboard(16, 1, 1, 1);
        if (Mouse.isButtonDown(0)) {
            Mouse.setGrabbed(true);
        } else if (Mouse.isButtonDown(1)) {
            Mouse.setGrabbed(false);
        }
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
		for(int i = 0; i < m_objs.size(); i++)
		{
			prepare(i);
			render(i);
		}
	}
	
	/**
	 * Clean up.
	 */
	public void dispose() {
		glDeleteProgram(shaderProgram);
		for(int i = 0; i < vboVertexHandle.size(); i++)
		{
			dispObj(i);
		}
	}
	@Override
	public ComponentType getType() 
	{
		return EngineComponent.ComponentType.render;
	}
	/**
	 * Dispose an object's buffers.
	 * @param i the object's index.
	 */
	private void dispObj(int i)
	{
		glDeleteBuffers(vboVertexHandle.get(i));
        glDeleteBuffers(vboNormalHandle.get(i));
        vboVertexHandle.remove(i);
        vboNormalHandle.remove(i);
	}
	/**
	 * Loads the object at an index to be rendered. Use run() instead of calling directly.
	 * @param objectindex the object's index.
	 */
	private void prepare(int objectindex)
	{
		glBindBuffer(GL_ARRAY_BUFFER, vboVertexHandle.get(objectindex));
		glVertexPointer(3, GL_FLOAT, 0, 0L);
		glBindBuffer(GL_ARRAY_BUFFER, vboNormalHandle.get(objectindex));
		glNormalPointer(GL_FLOAT, 0, 0L);
		glBindBuffer(GL_ARRAY_BUFFER,0);
		glEnableClientState(GL_VERTEX_ARRAY);
		glEnableClientState(GL_NORMAL_ARRAY);
	}
	/**
	 * Render an object. Use run() instead of calling directly.
	 * @param i the object's index.
	 */
	private void render(int i) {
		IObject obj = m_objs.get(i);
		glLoadIdentity();
	    cam.applyTranslations();
	    glTranslatef(obj.getPos().x, obj.getPos().y, obj.getPos().z);
	    glLight(GL_LIGHT0, GL_POSITION, BufferTools.asFlippedFloatBuffer(cam.x(), cam.y(), cam.z(), 1));
	    glDrawArrays(GL_TRIANGLES, 0, obj.getSprite().getFaces().size() * 3);
	}
}

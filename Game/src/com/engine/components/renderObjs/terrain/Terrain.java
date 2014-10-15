package com.engine.components.renderObjs.terrain;

import java.util.ArrayList;

import com.engine.components.renderObjs.MeshRenderer;
import com.engine.core.GameObject;
import com.engine.core.Vector2f;
import com.engine.core.Vector3f;
import com.engine.rendering.Material;
import com.engine.rendering.Mesh;
import com.engine.rendering.Mesh.DRAW_WAY;
import com.engine.rendering.meshLoading.InputModel;

public class Terrain extends GameObject 
{
	private Vector3f posOrigin;
	private OriginGravity gravity = OriginGravity.LEFT_DOWN;
	private OriginGravity prevGravity = OriginGravity.LEFT_DOWN;
	protected Vector3f scale = new Vector3f(1, 1, 1);
	protected Vector3f trans;
	protected Vector2f terrainRadius;
	protected ArrayList<Float> heights;
	protected float width = 0, depth = 0;

	public static enum OriginGravity //what is terrain psoition based on?
	{
		LEFT_UP,
		RIGHT_UP,
		CENTER_UP,
		LEFT_DOWN,
		RIGHT_DOWN,
		CENTER_DOWN,
		LEFT_CENTER,
		RIGHT_CENTER,
		CENTER_CENTER
	}
	
	public Terrain()
	{ 
		posOrigin = new Vector3f(0, 0, 0);
		heights = new ArrayList<Float>();
	}
	
	public Terrain(Mesh mesh, Material material)
	{
		this();
		createMeshRenderer(mesh, material);
	}
	
	public Terrain(Mesh mesh, Material material, Vector3f originPos)
	{
		this(mesh, material);
		setOriginPos(originPos);
	}
	
	public void setOriginPos(Vector3f pos)
	{
		this.posOrigin = pos;
	}
	
	public boolean createMeshRenderer(InputModel inputModel, Material material)
	{
		if(!createMeshRenderer(inputModel.toIndexedModel().toMesh(), material))
			return false;
		
		return true;
	}
	
	public boolean createMeshRenderer(Mesh mesh, Material material)
	{
		if(material == null)
			return false;
		if(mesh == null)
			return false;
		
		mesh.setDrawWay(DRAW_WAY.TRIANGLE_STRIP);
		
		MeshRenderer meshRenderer = new MeshRenderer(mesh, material);
		
		addComponent(meshRenderer);
				
		return true;
	}
	
	/**
	 * @param g1 current gravity
	 * @param g2 target gravity
	 * @return
	 */
	
	private Vector3f getTranslate(OriginGravity g1, OriginGravity g2)
	{
		Vector3f translate = new Vector3f(0, 0, 0);
		
		switch (g2) //from left_down
		{
		case LEFT_UP:
			translate.set(0, 0, -2 * terrainRadius.getY());
			break;
		case LEFT_DOWN:
			break;
		case LEFT_CENTER:
			translate.set(0, 0, -terrainRadius.getY());
			break;
		case RIGHT_UP:
			translate.set(-terrainRadius.getX() * 2, 0, -terrainRadius.getY() * 2);
			break;
		case RIGHT_DOWN:
			translate.set(-terrainRadius.getX() * 2, 0, 0);
			break;
		case RIGHT_CENTER:
			translate.set(-terrainRadius.getX() * 2, 0, -terrainRadius.getY());
			break;
		case CENTER_UP:
			translate.set(-terrainRadius.getX(), 0,- terrainRadius.getY() * 2);
			break;
		case CENTER_DOWN:
			translate.set(-terrainRadius.getX(), 0, 0);
			break;
		case CENTER_CENTER:
			translate.set(-terrainRadius.getX(), 0, -terrainRadius.getY());
			break;
		}
		
		switch (g1) //from left_down
		{
		case LEFT_UP:
			translate.set(translate.sub(0, 0, -2 * terrainRadius.getY()));
			break;
		case LEFT_DOWN:
			break;
		case LEFT_CENTER:
			translate.set(translate.sub(0, 0, -terrainRadius.getY()));
			break;
		case RIGHT_UP:
			translate.set(translate.sub(-terrainRadius.getX() * 2, 0, -terrainRadius.getY() * 2));
			break;
		case RIGHT_DOWN:
			translate.set(translate.sub(-terrainRadius.getX() * 2, 0, 0));
			break;
		case RIGHT_CENTER:
			translate.set(translate.sub(-terrainRadius.getX() * 2, 0, -terrainRadius.getY()));
			break;
		case CENTER_UP:
			translate.set(translate.sub(-terrainRadius.getX(), 0,- terrainRadius.getY() * 2));
			break;
		case CENTER_DOWN:
			translate.set(translate.sub(-terrainRadius.getX(), 0, 0));
			break;
		case CENTER_CENTER:
			translate.set(translate.sub(-terrainRadius.getX(), 0, -terrainRadius.getY()));
			break;
		}
		
		return translate;
	}
	
	protected void build() 
	{
	}
	
	public void compile()
	{
		build();
		trans = getTranslate(prevGravity, gravity);
		transform.setPos(transform.getPos().added(trans));
	}
	
	public void setOriginGravity(OriginGravity gravity)
	{
		this.prevGravity = this.gravity;
		this.gravity = gravity;
	}
	
	public Vector3f getPosOrigin()
	{
		return posOrigin;
	}
	
	public OriginGravity getOriginGravity()
	{
		return gravity;
	}
	
	public void setScale(Vector3f scale)
	{
		this.scale = scale;
	}
	
	public float getHeight(int index)
	{
		if(index >= heights.size())
		{
			return 0;
		}
		
		return heights.get(index);
	}
	
	public Vector3f getScale() {
		return scale;
	}

	public float getWidth() {
		return width;
	}

	public float getDepth() {
		return depth;
	}
}
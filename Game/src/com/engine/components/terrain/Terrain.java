package com.engine.components.terrain;

import java.util.ArrayList;

import com.engine.components.MeshRenderer;
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
	public boolean built = false;

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
		SetOriginPos(originPos);
	}
	
	public void SetOriginPos(Vector3f pos)
	{
		this.posOrigin = pos;
	}
	
	public boolean createMeshRenderer(InputModel inputModel, Material material)
	{
		if(!createMeshRenderer(inputModel.toIndexedModel().ToMesh(), material))
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
		
		AddComponent(meshRenderer);
				
		return true;
	}
	
	/**
	 * @param g1 current gravity
	 * @param g2 tarGet gravity
	 * @return
	 */
	
	private Vector3f GetTranslate(OriginGravity g1, OriginGravity g2)
	{
		Vector3f translate = new Vector3f(0, 0, 0);
		
		switch (g2) //from left_down
		{
		case LEFT_UP:
			translate.Set(0, 0, -2 * terrainRadius.GetY());
			break;
		case LEFT_DOWN:
			break;
		case LEFT_CENTER:
			translate.Set(0, 0, -terrainRadius.GetY());
			break;
		case RIGHT_UP:
			translate.Set(-terrainRadius.GetX() * 2, 0, -terrainRadius.GetY() * 2);
			break;
		case RIGHT_DOWN:
			translate.Set(-terrainRadius.GetX() * 2, 0, 0);
			break;
		case RIGHT_CENTER:
			translate.Set(-terrainRadius.GetX() * 2, 0, -terrainRadius.GetY());
			break;
		case CENTER_UP:
			translate.Set(-terrainRadius.GetX(), 0,- terrainRadius.GetY() * 2);
			break;
		case CENTER_DOWN:
			translate.Set(-terrainRadius.GetX(), 0, 0);
			break;
		case CENTER_CENTER:
			translate.Set(-terrainRadius.GetX(), 0, -terrainRadius.GetY());
			break;
		}
		
		switch (g1) //from left_down
		{
		case LEFT_UP:
			translate.Set(translate.minus(new Vector3f(0, 0, -2 * terrainRadius.GetY())));
			break;
		case LEFT_DOWN:
			break;
		case LEFT_CENTER:
			translate.Set(translate.minus(new Vector3f(0, 0, -terrainRadius.GetY())));
			break;
		case RIGHT_UP:
			translate.Set(translate.minus(new Vector3f(-terrainRadius.GetX() * 2, 0, -terrainRadius.GetY() * 2)));
			break;
		case RIGHT_DOWN:
			translate.Set(translate.minus(new Vector3f(-terrainRadius.GetX() * 2, 0, 0)));
			break;
		case RIGHT_CENTER:
			translate.Set(translate.minus(new Vector3f(-terrainRadius.GetX() * 2, 0, -terrainRadius.GetY())));
			break;
		case CENTER_UP:
			translate.Set(translate.minus(new Vector3f(-terrainRadius.GetX(), 0,- terrainRadius.GetY() * 2)));
			break;
		case CENTER_DOWN:
			translate.Set(translate.minus(new Vector3f(-terrainRadius.GetX(), 0, 0)));
			break;
		case CENTER_CENTER:
			translate.Set(translate.minus(new Vector3f(-terrainRadius.GetX(), 0, -terrainRadius.GetY())));
			break;
		}
		
		return translate;
	}
	
	protected void build() 
	{
	}
	
	public float GetHeight()
	{
		return 0;
	}
	
	public Terrain compile()
	{
		build();
		trans = GetTranslate(prevGravity, gravity);
		m_transform.SetPos(m_transform.GetPos().plus(trans));
		built = true;
		return this;
	}
	
	public void SetOriginGravity(OriginGravity gravity)
	{
		this.prevGravity = this.gravity;
		this.gravity = gravity;
	}
	
	public Vector3f GetPosOrigin()
	{
		return posOrigin;
	}
	
	public OriginGravity GetOriginGravity()
	{
		return gravity;
	}
	
	public void SetScale(Vector3f scale)
	{
		this.scale = scale;
	}
	
	public float GetHeight(int index)
	{
		if(index >= heights.size())
		{
			return 0;
		}
		
		return heights.get(index);
	}
	
	public Vector3f GetScale() {
		return scale;
	}

	public float GetWidth() {
		return width;
	}

	public float GetDepth() {
		return depth;
	}
}
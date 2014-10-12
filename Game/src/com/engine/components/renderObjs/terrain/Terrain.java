package com.engine.components.renderObjs.terrain;

import com.engine.components.renderObjs.MeshRenderer;
import com.engine.core.GameObject;
import com.engine.core.Vector3f;
import com.engine.rendering.Material;
import com.engine.rendering.Mesh;
import com.engine.rendering.Mesh.DRAW_WAY;
import com.engine.rendering.meshLoading.IndexedModel;
import com.engine.rendering.meshLoading.InputModel;

public class Terrain extends GameObject 
{
	private Vector3f posOrigin;
	private OriginGravity gravity = OriginGravity.LEFT_DOWN;
	
	public static enum OriginGravity //what is terrain psoition based on?
	{
		LEFT_UP_,
		RIGHT_UP,
		LEFT_DOWN,
		RIGHT_DOWN,
		CENTER
	}
	
	public Terrain()
	{ 
		posOrigin = new Vector3f(0, 0, 0);
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
		if(material == null)
			return false;
		Mesh mesh = inputModel.toIndexedModel().toMesh();
		if(mesh == null)
			return false;
		
		mesh.setDrawWay(DRAW_WAY.TRIANGLE_STRIP);
		
		MeshRenderer meshRenderer = new MeshRenderer(mesh, material);
		
		addComponent(meshRenderer);
		
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
	
	public void setOriginGravity(OriginGravity gravity)
	{
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
}
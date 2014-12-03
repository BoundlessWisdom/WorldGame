package ui;

import java.util.ArrayList;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;

import com.engine.components.GameComponent;
import com.engine.components.MeshRenderer;
import com.engine.core.GameObject;
import com.engine.core.Transform;
import com.engine.core.Vector2f;
import com.engine.core.Vector3f;
import com.engine.rendering.Material;
import com.engine.rendering.Mesh;
import com.engine.rendering.Mesh.DRAW_WAY;
import com.engine.rendering.RenderingEngine;
import com.engine.rendering.Shader;
import com.engine.rendering.meshLoading.InputModel;
import com.engine.rendering.meshLoading.OBJIndex;
import com.engine.core.Input;

public abstract class Button extends GameObject{
	//public final Texture texture;
	
	//public final int x;
	public final int xlength;
	
	//public final int y;
	public final int ylength;
	
	public boolean IsCompiled = false;
	
	MeshRenderer MeshRender;
	Material mat;
	
	public Button(Menu menu, int x, int xlength, int y, int ylength) {
		menu.buttons.add(this);
		this.xlength = xlength;
		this.ylength = ylength;
		m_transform.SetPos(x, y, 0);
	}
	
	public Button(Menu menu, int x, int xlength, int y, int ylength, Material mat) {
		menu.buttons.add(this);
		this.xlength = xlength;
		this.ylength = ylength;
		m_transform.SetPos(x, y, 0);
		this.mat = mat;
	}
	
	public boolean Hover()
	{		
		if(Mouse.isInsideWindow())
		{
			
		float x = (float)Mouse.getX() / (float)Display.getWidth();
		float y = (float)Mouse.getY() / (float)Display.getHeight();
		float lx = (float)xlength / (float)Display.getWidth();
		float ly = (float)ylength / (float)Display.getHeight();
		float nx = GetX();
		float ny = GetY();
		
		Vector2f mouse = new Vector2f(x, y);
		mouse = mouse.Sub(0.5f).Mul(2f);
		
		return (mouse.GetX() > nx) && (mouse.GetX() < nx + lx) && (mouse.GetY() < ny) && (mouse.GetY() > ny - ly);
		}
		
		return false;
	}
	
	public abstract Button setVariable(Object par);
	
/*	public Button setBackground(Texture t) {
		texture = t;
		return this;
	}*/
	
	public abstract void function();
	
	public void SetMaterial(Material mat)
	{
		this.mat = mat;
	}
	
	public Button Compile()
	{
		ArrayList<Vector3f> vertices = new ArrayList<Vector3f>();
		ArrayList<OBJIndex> indices = new ArrayList<OBJIndex>();
		ArrayList<Vector2f> texCoords = new ArrayList<Vector2f>();
		ArrayList<Vector3f> normals = new ArrayList<Vector3f>();
		
		Vector3f v1 = new Vector3f(GetX(), GetY() - (float)ylength / (float)Display.getHeight(), 1f);
		Vector3f v2 = new Vector3f(GetX(), GetY(), 1f);
		Vector3f v3 = new Vector3f(GetX() + (float)xlength / (float)Display.getWidth(), GetY() - (float)ylength / (float)Display.getHeight(), 1f);
		Vector3f v4 = new Vector3f(GetX() + (float)xlength / (float)Display.getWidth(), GetY(), 1f);
		
		Vector2f t1 = new Vector2f(0, 1);
		Vector2f t2 = new Vector2f(0, 0);
		Vector2f t3 = new Vector2f(1, 1);
		Vector2f t4 = new Vector2f(1, 0);
		
		OBJIndex i1 = new OBJIndex(0, 0, 0);
		OBJIndex i2 = new OBJIndex(1, 1, 0);
		OBJIndex i3 = new OBJIndex(2, 2, 0);
		OBJIndex i4 = new OBJIndex(3, 3, 0);
		
		Vector3f n1 = new Vector3f(0, 0, 0);
		
		vertices.add(v1);
		vertices.add(v2);
		vertices.add(v3);
		vertices.add(v4);
		
		texCoords.add(t1);
		texCoords.add(t2);
		texCoords.add(t3);
		texCoords.add(t4);
		
		indices.add(i1);
		indices.add(i2);
		indices.add(i3);
		indices.add(i4);
		
		normals.add(n1);
		normals.add(n1);
		normals.add(n1);
		normals.add(n1);
		
		InputModel model = new InputModel(vertices, texCoords, normals, indices);
		model.setBooleans(true, true);
		Mesh m = model.toIndexedModel().ToMesh();
		m.setDrawWay(DRAW_WAY.TRIANGLE_STRIP);
		
		MeshRender = new MeshRenderer(m, mat);
		AddComponent(MeshRender);
		
		IsCompiled = true;
		
		return this;
		//model.setPositions();
	}
	
	@Override
	public void Render(Shader shader, RenderingEngine renderingEngine) 
	{
		//super.Render(shader, renderingEngine);
		shader = new Shader("forward-ambient");
		for(GameComponent component : m_components)
			component.Render(shader, renderingEngine);
	}
	
	public void Uncompile()
	{
		MeshRender = null;
		m_components.clear();
		m_children.clear();
		m_transform = new Transform();
	}
	
	public float GetX()
	{
		return m_transform.GetPos().GetX();
	}

	public float GetY()
	{
		return m_transform.GetPos().GetY();
	}
}

package ui;

import java.util.ArrayList;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;

import com.engine.components.MeshRenderer;
import com.engine.core.GameObject;
import com.engine.core.Vector2f;
import com.engine.core.Vector3f;
import com.engine.rendering.Material;
import com.engine.rendering.Mesh;
import com.engine.rendering.meshLoading.InputModel;
import com.engine.rendering.meshLoading.OBJIndex;
import com.engine.core.Input;

public abstract class Button extends GameObject{
	//public final Texture texture;
	
	//public final int x;
	public final int xlength;
	
	//public final int y;
	public final int ylength;
	
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
	
	public boolean hover()
	{
		//return (Input.GetMousePosition().GetX() > GetX() && Input.GetMousePosition().GetX() < (GetX() + xlength) &&
			//	Input.GetMousePosition().GetY() < GetY() && Input.GetMousePosition().GetX() > (GetY() + ylength));
		
		if(Mouse.isInsideWindow())
		{
			
		float x = (float)Mouse.getX() / (float)Display.getWidth();
		float y = (float)Mouse.getY() / (float)Display.getHeight();
		float lx = (float)xlength / (float)Display.getWidth();
		float ly = (float)ylength / (float)Display.getHeight();
		float nx = GetX();
		float ny = GetY();
		//return true;
		/*boolean x1 = x > nx;
		boolean x2 = x < nx + lx;
		boolean y1 = y > ny;
		boolean y2 = y < ny + ly;*/
		
		return (x > nx) && (x < nx + lx) && (y > ny) && (y < ny + ly);
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
		
		Vector3f v1 = new Vector3f(GetX(), GetY() - (float)ylength / (float)Display.getHeight(), 0f);
		Vector3f v2 = new Vector3f(GetX(), GetY(), 0f);
		Vector3f v3 = new Vector3f(GetX() + (float)xlength / (float)Display.getWidth(), GetY() - (float)ylength / (float)Display.getHeight(), 0f);
		Vector3f v4 = new Vector3f(GetX() + (float)xlength / (float)Display.getWidth(), GetY(), 0f);
		
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
		
		MeshRender = new MeshRenderer(m, mat);
		AddComponent(MeshRender);
		return this;
		//model.setPositions();
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

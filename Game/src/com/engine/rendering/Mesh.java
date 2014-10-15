package com.engine.rendering;


import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*; 
import static org.lwjgl.opengl.GL20.*; 

import com.engine.core.Util;
import com.engine.core.Vector3f;
import com.engine.rendering.meshLoading.IndexedModel;
import com.engine.rendering.meshLoading.OBJModel;

public class Mesh 
{
	private int vbo = 0;
	private int ibo = 0;
	private int size = 0;
	
	public static enum DRAW_WAY
	{
		TRIANGLE_STRIP,
		TRIANGLES
	}
	
	private int drawWay = GL_TRIANGLES;
	
	public Mesh()
	{
		initMeshData();
	}

	public Mesh(Mesh mesh)
	{		
		vbo = mesh.getVbo();
		ibo = mesh.getIbo();
		size = mesh.getSize();
	}
	
	public Mesh(String file)
	{
		this(loadMesh(file));
		//initMeshData();
	}
	
	public Mesh(Vertex[] verticies, int[] indicies)
	{
		this(verticies, indicies, false);
	}
	
	public Mesh(Vertex[] verticies, int[] indicies, boolean calcNormals)
	{
		initMeshData();		
		addVerticies(verticies, indicies, calcNormals);
	}
	
	private void initMeshData()
	{
		vbo = glGenBuffers();
		ibo = glGenBuffers();
		size = 0;
	}
	
	public void addVerticies(Vertex[] verticies, int[] indicies)
	{		
		addVerticies(verticies, indicies, false);
	}
	
	private void addVerticies(Vertex[] verticies, int[] indicies, boolean calcNormals)
	{
		if(calcNormals)
		{
			calcNormals(verticies, indicies);
		}
		
		size = indicies.length;
		
		glBindBuffer(GL_ARRAY_BUFFER, vbo);
		glBufferData(GL_ARRAY_BUFFER, Util.createFlippedBuffer(verticies), GL_STATIC_DRAW);
		
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, ibo);
		glBufferData(GL_ELEMENT_ARRAY_BUFFER, Util.createFlippedBuffer(indicies), GL_STATIC_DRAW);
	}
	
	private void calcNormals(Vertex[] verticies, int[] indicies)
	{
		for(int i = 0; i < indicies.length; i += 3)
		{
			int i0 = indicies[i];
			int i1 = indicies[i + 1];
			int i2 = indicies[i + 2];
			
			Vector3f v1 = verticies[i1].getPos().sub(verticies[i0].getPos());
			Vector3f v2 = verticies[i2].getPos().sub(verticies[i0].getPos());
			
			Vector3f normal = v1.cross(v2).normalize();
			
			verticies[i0].setNormal(verticies[i0].getNormal().add(normal));
			verticies[i1].setNormal(verticies[i1].getNormal().add(normal));
			verticies[i2].setNormal(verticies[i2].getNormal().add(normal));
		}
		
		for(int i = 0; i < verticies.length; i++)
		{
			verticies[i].setNormal(verticies[i].getNormal().normalize());
		}
	}
	
	private static Mesh loadMesh(String file)
	{
		String[] splitArray = file.split("\\.");
		String ext = splitArray[splitArray.length - 1];
		
		if(!ext.equals("obj"))
		{
			System.err.println("\"" + file + "\" is the wrong filetype: " + ext);
			new Exception().printStackTrace();
			System.exit(1);
		}
		
		OBJModel test = new OBJModel("./res/models/" + file);
		IndexedModel model = test.toIndexedModel();
		model.calcNormals();		
		
		Mesh res = new Mesh(model.toMesh());
		
		return res;
	}

	
	public void draw()
	{
		glEnableVertexAttribArray(0);
		glEnableVertexAttribArray(1);
		glEnableVertexAttribArray(2);
		
		glBindBuffer(GL_ARRAY_BUFFER, vbo);
		glVertexAttribPointer(0, 3, GL_FLOAT, false, Vertex.SIZE * 4, 0);
		glVertexAttribPointer(1, 2, GL_FLOAT, false, Vertex.SIZE * 4, 12); //offset in bytes
		glVertexAttribPointer(2, 3, GL_FLOAT, false, Vertex.SIZE * 4, 20);
		
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, ibo);
		glDrawElements(drawWay, size, GL_UNSIGNED_INT, 0);
		
		glDisableVertexAttribArray(0);
		glDisableVertexAttribArray(1);
		glDisableVertexAttribArray(2);
	}

	public int getVbo() {
		return vbo;
	}

	public int getIbo() {
		return ibo;
	}
	
	public int getSize() {
		return size;
	}
	
	public void setDrawWay(DRAW_WAY way)
	{
		switch(way)
		{
		case TRIANGLES:
			drawWay = GL_TRIANGLES;
			break;
		case TRIANGLE_STRIP:
			drawWay = GL_TRIANGLE_STRIP;
			break;
		}
	}
}

package com.engine.rendering;




import com.engine.core.Util;
import com.engine.core.Vector3f;
import com.engine.rendering.meshLoading.IndexedModel;
import com.engine.rendering.meshLoading.OBJModel;
import com.engine.rendering.resourceManagement.MeshResource;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;

import java.util.ArrayList;
import java.util.HashMap;

public class Mesh
{
	private static HashMap<String, MeshResource> s_loadedModels = new HashMap<String, MeshResource>();
	private MeshResource m_resource;
	private String       m_fileName;
	private int dWay = GL_TRIANGLES;
	private IndexedModel m_origin;
	public enum DRAW_WAY
	{
		TRIANGLES,
		TRIANGLE_STRIP
	}
	
	public Mesh(String fileName)
	{
		this.m_fileName = fileName;
		MeshResource oldResource = s_loadedModels.get(fileName);

		if(oldResource != null)
		{
			m_resource = oldResource;
			m_resource.AddReference();
		}
		else
		{
			LoadMesh(fileName);
			s_loadedModels.put(fileName, m_resource);
		}
	}
	
	public Mesh(Vertex[] vertices, int[] indices)
	{
		this(vertices, indices, false);
	}
	
	public Mesh(Vertex[] vertices, int[] indices, boolean calcNormals)
	{
		m_fileName = "";
		AddVertices(vertices, indices, calcNormals);
	}

	@Override
	protected void finalize()
	{
		if(m_resource.RemoveReference() && !m_fileName.isEmpty())
		{
			s_loadedModels.remove(m_fileName);
		}
	}
	
	private void AddVertices(Vertex[] vertices, int[] indices, boolean calcNormals)
	{
		if(calcNormals)
		{
			CalcNormals(vertices, indices);
		}

		m_resource = new MeshResource(indices.length);
		
		glBindBuffer(GL_ARRAY_BUFFER, m_resource.GetVbo());
		glBufferData(GL_ARRAY_BUFFER, Util.CreateFlippedBuffer(vertices), GL_STATIC_DRAW);
		
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, m_resource.GetIbo());
		glBufferData(GL_ELEMENT_ARRAY_BUFFER, Util.CreateFlippedBuffer(indices), GL_STATIC_DRAW);
	}
	
	public void Draw()
	{
		glEnableVertexAttribArray(0);
		glEnableVertexAttribArray(1);
		glEnableVertexAttribArray(2);
		glEnableVertexAttribArray(3);
		
		glBindBuffer(GL_ARRAY_BUFFER, m_resource.GetVbo());
		glVertexAttribPointer(0, 3, GL_FLOAT, false, Vertex.SIZE * 4, 0);
		glVertexAttribPointer(1, 2, GL_FLOAT, false, Vertex.SIZE * 4, 12);
		glVertexAttribPointer(2, 3, GL_FLOAT, false, Vertex.SIZE * 4, 20);
		glVertexAttribPointer(3, 3, GL_FLOAT, false, Vertex.SIZE * 4, 32);
		
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, m_resource.GetIbo());
		glDrawElements(dWay, m_resource.GetSize(), GL_UNSIGNED_INT, 0);
		
		glDisableVertexAttribArray(0);
		glDisableVertexAttribArray(1);
		glDisableVertexAttribArray(2);
		glDisableVertexAttribArray(3);
	}
	
	private void CalcNormals(Vertex[] vertices, int[] indices)
	{
		for(int i = 0; i < indices.length; i += 3)
		{
			int i0 = indices[i];
			int i1 = indices[i + 1];
			int i2 = indices[i + 2];
			
			Vector3f v1 = vertices[i1].GetPos().minus(vertices[i0].GetPos());
			Vector3f v2 = vertices[i2].GetPos().minus(vertices[i0].GetPos());
			
			Vector3f normal = v1.Cross(v2).Normalized();
			
			vertices[i0].SetNormal(vertices[i0].GetNormal().plus(normal));
			vertices[i1].SetNormal(vertices[i1].GetNormal().plus(normal));
			vertices[i2].SetNormal(vertices[i2].GetNormal().plus(normal));
		}
		
		for(int i = 0; i < vertices.length; i++)
			vertices[i].SetNormal(vertices[i].GetNormal().Normalized());
	}
	
	private Mesh LoadMesh(String fileName)
	{
		String[] splitArray = fileName.split("\\.");
		String ext = splitArray[splitArray.length - 1];

		if(!ext.equals("obj"))
		{
			System.err.println("Error: '" + ext + "' file format not supported for mesh data.");
			new Exception().printStackTrace();
			System.exit(1);
		}

		OBJModel test = new OBJModel("./res/models/" + fileName);
		IndexedModel model = test.ToIndexedModel();
		m_origin = model;
		ArrayList<Vertex> vertices = new ArrayList<Vertex>();

		for(int i = 0; i < model.GetPositions().size(); i++)
		{
			vertices.add(new Vertex(model.GetPositions().get(i),
					model.GetTexCoords().get(i),
					model.GetNormals().get(i),
					model.GetTangents().get(i)));
		}

		Vertex[] vertexData = new Vertex[vertices.size()];
		vertices.toArray(vertexData);

		Integer[] indexData = new Integer[model.GetIndices().size()];
		model.GetIndices().toArray(indexData);

		AddVertices(vertexData, Util.ToIntArray(indexData), false);
		
		return this;
	}

	public void setDrawWay(DRAW_WAY dWay) 
	{
		switch (dWay) {
		case TRIANGLES:
			this.dWay = GL_TRIANGLES;
			break;
		case TRIANGLE_STRIP:
			this.dWay = GL_TRIANGLE_STRIP;
			break;
		default:
			break;
		}
	}
	public IndexedModel getOriginalModel(){
		return m_origin;
	}
}

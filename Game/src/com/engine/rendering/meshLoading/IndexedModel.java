package com.engine.rendering.meshLoading;

import java.util.ArrayList;

import com.engine.core.Util;
import com.engine.core.Vector2f;
import com.engine.core.Vector3f;
import com.engine.rendering.Mesh;
import com.engine.rendering.Vertex;

public class IndexedModel 
{
	private ArrayList<Vector3f> positions;
	private ArrayList<Vector2f> texCoords;
	private ArrayList<Vector3f> normals;
	private ArrayList<Integer> indicies;
	
	public IndexedModel()
	{
		positions = new ArrayList<Vector3f>();
		texCoords = new ArrayList<Vector2f>();
		normals = new ArrayList<Vector3f>();
		indicies = new ArrayList<Integer>();
	}
	
	public void calcNormals()
	{
		for(int i = 0; i < indicies.size(); i += 3)
		{
			int i0 = indicies.get(i);
			int i1 = indicies.get(i + 1);
			int i2 = indicies.get(i + 2);
			
			Vector3f v1 = positions.get(i1).sub(positions.get(i0));
			Vector3f v2 = positions.get(i2).sub(positions.get(i0));
			
			Vector3f normal = v1.cross(v2).normalize();
			
			normals.get(i0).set(normals.get(i0).add(normal));
			normals.get(i1).set(normals.get(i1).add(normal));
			normals.get(i2).set(normals.get(i2).add(normal));
		}
		
		for(int i = 0; i < normals.size(); i++)
		{
			normals.get(i).set(normals.get(i).normalize());
		}
	}
	
	public Mesh toMesh()
	{
		ArrayList<Vertex> verticies = new ArrayList<Vertex>();
		//ArrayList<Integer> indicies = new ArrayList<Integer>();
		
		for(int i = 0; i < getPositions().size(); i++)
		{
			verticies.add(new Vertex(getPositions().get(i),
					getTexCoords().get(i),
					getNormals().get(i)
					));
		}
		
		Mesh res = new Mesh();
		Vertex[] vertData = new Vertex[verticies.size()];
		verticies.toArray(vertData);
		
		Integer[] indexData = new Integer[getIndicies().size()];
		getIndicies().toArray(indexData);
		
		res.addVerticies(vertData, Util.toIntArray(indexData));
	
		return res;
	}

	public ArrayList<Vector3f> getPositions() {
		return positions;
	}

	public ArrayList<Vector2f> getTexCoords() {
		return texCoords;
	}

	public ArrayList<Vector3f> getNormals() {
		return normals;
	}

	public ArrayList<Integer> getIndicies() {
		return indicies;
	}
}

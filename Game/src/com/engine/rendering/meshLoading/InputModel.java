package com.engine.rendering.meshLoading;

import java.util.ArrayList;
import java.util.HashMap;

import com.engine.core.Vector2f;
import com.engine.core.Vector3f;

public class InputModel 
{
	private ArrayList<Vector3f> positions;
	private ArrayList<Vector2f> texCoords;
	private ArrayList<Vector3f> normals;
	private ArrayList<OBJIndex> indicies;
	
	private boolean hasTexCoord = false;
	private boolean hasNormals = false;
	
	public InputModel(ArrayList<Vector3f> positions, ArrayList<Vector2f> texCoords, ArrayList<Vector3f> normals,
			ArrayList<OBJIndex> indicies) 
	{
		this.positions = positions;
		this.texCoords = texCoords;
		this.normals = normals;
		this.indicies = indicies;
	}
	
	public InputModel()
	{
		positions = new ArrayList<Vector3f>();
		texCoords = new ArrayList<Vector2f>();
		normals = new ArrayList<Vector3f>();
		indicies = new ArrayList<OBJIndex>();
	}
	
	public IndexedModel toIndexedModel()
	{
		IndexedModel res = new IndexedModel();
		IndexedModel normalModel = new IndexedModel();
		
		HashMap<OBJIndex, Integer> resultIndexMap = new HashMap<OBJIndex, Integer>();
		HashMap<Integer, Integer> normalIndexMap = new HashMap<Integer, Integer>();
		HashMap<Integer, Integer> indexMap = new HashMap<Integer, Integer>();
		
		for(int i = 0; i < indicies.size(); i++)
		{
			OBJIndex currentIndex = indicies.get(i);
			
			Vector3f currentPos = positions.get(currentIndex.vertexIndex);
			Vector2f currentTex;
			Vector3f currentNorm;
			
			if(hasTexCoord)
				currentTex = texCoords.get(currentIndex.texIndex);
			else
				currentTex = new Vector2f(0, 0);
			
			if(hasNormals)
				currentNorm = normals.get(currentIndex.normalIndex);
			else
				currentNorm = new Vector3f(0, 0, 0);
			
			Integer modelVertexIndex = resultIndexMap.get(currentIndex);
			
			if(modelVertexIndex == null)
			{
				modelVertexIndex = res.getPositions().size();
				resultIndexMap.put(currentIndex, res.getPositions().size());
				
				res.getPositions().add(currentPos);
				res.getTexCoords().add(currentTex);
				if(hasNormals)
					res.getNormals().add(currentNorm);				
			}
			
			Integer normalModelIndex = normalIndexMap.get(currentIndex.vertexIndex);
			
			if(normalModelIndex == null)
			{
				normalModelIndex = normalModel.getPositions().size();
				normalIndexMap.put(currentIndex.vertexIndex, normalModel.getPositions().size());
				
				normalModel.getPositions().add(currentPos);
				normalModel.getTexCoords().add(currentTex);
				normalModel.getNormals().add(currentNorm);
			}
			
			res.getIndicies().add(modelVertexIndex);
			normalModel.getIndicies().add(normalModelIndex);
			indexMap.put(modelVertexIndex, normalModelIndex);
		}
		
		if(!hasNormals)
		{
			normalModel.calcNormals();
			
			for(int i = 0; i < res.getPositions().size(); i++)
			{
				res.getNormals().add(normalModel.getNormals().get(indexMap.get(i)));
				//res.getNormals().get(i).set(normalModel.getNormals().get(indexMap.get(i)));
			}
		}
		
		return res;
	}
	
	public void setBooleans(boolean hasTexCoord, boolean hasNormals)
	{
		this.hasTexCoord = hasTexCoord;
		
		this.hasNormals = hasNormals;
	}

	public ArrayList<Vector3f> getPositions() {
		return positions;
	}

	public void setPositions(ArrayList<Vector3f> positions) {
		this.positions = positions;
	}

	public ArrayList<Vector2f> getTexCoords() {
		return texCoords;
	}

	public void setTexCoords(ArrayList<Vector2f> texCoords) {
		this.texCoords = texCoords;
	}

	public ArrayList<Vector3f> getNormals() {
		return normals;
	}

	public void setNormals(ArrayList<Vector3f> normals) {
		this.normals = normals;
	}

	public ArrayList<OBJIndex> getIndicies() {
		return indicies;
	}

	public void setIndicies(ArrayList<OBJIndex> indicies) {
		this.indicies = indicies;
	}
	
	
}

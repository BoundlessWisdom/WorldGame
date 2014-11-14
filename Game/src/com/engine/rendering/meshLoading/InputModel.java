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
	private ArrayList<OBJIndex> indices;
	
	private boolean hasTexCoord = false;
	private boolean hasNormals = false;
	//private ArrayList<Vector3f> m_normals;
	
	public InputModel(ArrayList<Vector3f> positions, ArrayList<Vector2f> texCoords, ArrayList<Vector3f> normals,
			ArrayList<OBJIndex> indices) 
	{
		this();
		this.positions = positions;
		this.texCoords = texCoords;
		this.normals = normals;
		this.indices = indices;
	}
	
	public InputModel()
	{
		positions = new ArrayList<Vector3f>();
		texCoords = new ArrayList<Vector2f>();
		normals = new ArrayList<Vector3f>();
		indices = new ArrayList<OBJIndex>();
	}
	
	public int max()
	{
		int m = 0;
		
		for(int i = 0; i < indices.size() - 1; i++)
		{
			OBJIndex currIndex = indices.get(i);
			OBJIndex nextIndex = indices.get(i + 1);
			
			m = Math.max(currIndex.GetVertexIndex(), nextIndex.GetVertexIndex());
		}
		
		return m;
	}
	
	public IndexedModel toIndexedModel()
	{
		IndexedModel result = new IndexedModel();
		IndexedModel normalModel = new IndexedModel();
		HashMap<OBJIndex, Integer> resultIndexMap = new HashMap<OBJIndex, Integer>();
		HashMap<Integer, Integer> normalIndexMap = new HashMap<Integer, Integer>();
		HashMap<Integer, Integer> indexMap = new HashMap<Integer, Integer>();

		for(int i = 0; i < indices.size(); i++)
		{
			OBJIndex currentIndex = indices.get(i);

			Vector3f currentPosition = positions.get(currentIndex.GetVertexIndex());
			Vector2f currentTexCoord;
			Vector3f currentNormal;

			if(hasTexCoord)
				currentTexCoord = texCoords.get(currentIndex.GetTexCoordIndex());
			else
				currentTexCoord = new Vector2f(0,0);

			if(hasNormals)
				currentNormal = normals.get(currentIndex.GetNormalIndex());
			else
				currentNormal = new Vector3f(0,0,0);

			Integer modelVertexIndex = resultIndexMap.get(currentIndex);

			if(modelVertexIndex == null)
			{
				modelVertexIndex = result.GetPositions().size();
				resultIndexMap.put(currentIndex, modelVertexIndex);

				result.GetPositions().add(currentPosition);
				result.GetTexCoords().add(currentTexCoord);
				if(hasNormals)
					result.GetNormals().add(currentNormal);
			}

			Integer normalModelIndex = normalIndexMap.get(currentIndex.GetVertexIndex());

			if(normalModelIndex == null)
			{
				normalModelIndex = normalModel.GetPositions().size();
				normalIndexMap.put(currentIndex.GetVertexIndex(), normalModelIndex);

				normalModel.GetPositions().add(currentPosition);
				normalModel.GetTexCoords().add(currentTexCoord);
				normalModel.GetNormals().add(currentNormal);
				normalModel.GetTangents().add(new Vector3f(0,0,0));
			}

			result.GetIndices().add(modelVertexIndex);
			normalModel.GetIndices().add(normalModelIndex);
			indexMap.put(modelVertexIndex, normalModelIndex);
		}

		if(!hasNormals)
		{
			normalModel.CalcNormals();

			for(int i = 0; i < result.GetPositions().size(); i++)
				result.GetNormals().add(normalModel.GetNormals().get(indexMap.get(i)));
		}

		normalModel.CalcTangents();

		for(int i = 0; i < result.GetPositions().size(); i++)
			result.GetTangents().add(normalModel.GetTangents().get(indexMap.get(i)));

//		for(int i = 0; i < result.GetTexCoords().size(); i++)
//			result.GetTexCoords().Get(i).SetY(1.0f - result.GetTexCoords().Get(i).GetY());

		return result;
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
		return indices;
	}

	public void setIndicies(ArrayList<OBJIndex> indices) {
		this.indices = indices;
	}
	
	
}

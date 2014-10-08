package com.engine.rendering.meshLoading;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;

import com.engine.core.Util;
import com.engine.core.Vector2f;
import com.engine.core.Vector3f;

public class OBJModel 
{
	private ArrayList<Vector3f> positions;
	private ArrayList<Vector2f> texCoords;
	private ArrayList<Vector3f> normals;
	private ArrayList<OBJIndex> indicies;
	
	private	boolean hasTexCoords = false;
	private boolean hasNormals = false;
	
	public OBJModel(String fileName)
	{
		positions = new ArrayList<Vector3f>();
		texCoords = new ArrayList<Vector2f>();
		normals = new ArrayList<Vector3f>();
		indicies = new ArrayList<OBJIndex>();
		
		BufferedReader meshRead = null;
		//Mesh res = new Mesh();
		try 
		{
			meshRead = new BufferedReader(new FileReader(fileName));
			String line;
			while((line = meshRead.readLine()) != null)
			{
				String[] tokens = line.split(" ");
				tokens = Util.removeEmptyString(tokens);
				
				if(tokens.length == 0 || tokens[0].equals("#"))
				{
					continue;
				}
				
				else if(tokens[0].equals("v"))
				{
					positions.add(new Vector3f(Float.valueOf(tokens[1]),
							Float.valueOf(tokens[2]),
							Float.valueOf(tokens[3])));
				}
				
				else if(tokens[0].equals("vt"))
				{
					texCoords.add(new Vector2f(Float.valueOf(tokens[1]),
							Float.valueOf(tokens[2])));
				}
				
				else if(tokens[0].equals("vn"))
				{
					normals.add(new Vector3f(Float.valueOf(tokens[1]),
							Float.valueOf(tokens[2]),
							Float.valueOf(tokens[3])));
				}
				
				else if(tokens[0].equals("f"))
				{
					for(int i = 0; i < tokens.length - 3; i++)
					{
						indicies.add(parseOBJIndex(tokens[1]));
						indicies.add(parseOBJIndex(tokens[2 + i]));
						indicies.add(parseOBJIndex(tokens[3 + i]));
					}
				}
			}
			
			meshRead.close();
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			System.exit(1);
		}
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
			
			if(hasTexCoords)
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
	
	private OBJIndex parseOBJIndex(String token)
	{
		String[] values = token.split("/");
		
		OBJIndex res = new OBJIndex();
		res.vertexIndex = Integer.parseInt(values[0]) - 1;
		
		if(values.length > 1)
		{
			hasTexCoords = true;
			res.texIndex = Integer.parseInt(values[1]) - 1;
			
			if(values.length > 2)
			{
				hasNormals = true;
				res.normalIndex = Integer.parseInt(values[2]) - 1;
			}
		}
		
		return res;
	}
}

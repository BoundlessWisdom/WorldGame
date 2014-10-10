package com.engine.rendering.meshLoading;

public class OBJIndex 
{
	public int vertexIndex;
	public int texIndex;
	public int normalIndex;
	
	public OBJIndex(int v, int t, int n)
	{
		vertexIndex = v;
		texIndex = t;
		normalIndex = n;
	}
	
	public OBJIndex() {
	}

	@Override
	public boolean equals(Object obj)
	{
		OBJIndex index = (OBJIndex)obj;
		
		return vertexIndex == index.vertexIndex
				&& texIndex == index.texIndex
				&& normalIndex == index.normalIndex;
	}
	
	@Override
	public int hashCode()
	{
		final int base = 17;
		final int multiplier = 31;
		
		int res = base;
		
		res = multiplier * res + vertexIndex;
		res = multiplier * res + texIndex;
		res = multiplier * res + normalIndex;
		
		return res;
	}
}



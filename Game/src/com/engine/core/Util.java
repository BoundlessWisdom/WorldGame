package com.engine.core;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;

import org.lwjgl.BufferUtils;

import com.engine.rendering.Vertex;

public class Util 
{
	public static FloatBuffer createFloatBuffer(int size)
	{
		return BufferUtils.createFloatBuffer(size);
	}
	
	public static IntBuffer createIntBuffer(int size)
	{
		return BufferUtils.createIntBuffer(size);
	}
	
	public static FloatBuffer createFlippedBuffer(Vertex[] verticies)
	{
		FloatBuffer buf = createFloatBuffer(verticies.length * Vertex.SIZE);
		
		for(int i = 0; i < verticies.length; i++)
		{
			buf.put(verticies[i].getPos().getX());
			buf.put(verticies[i].getPos().getY());
			buf.put(verticies[i].getPos().getZ());
			buf.put(verticies[i].getTexCoord().getX());
			buf.put(verticies[i].getTexCoord().getY());
			buf.put(verticies[i].getNormal().getX());
			buf.put(verticies[i].getNormal().getY());
			buf.put(verticies[i].getNormal().getZ());
		}
		
		buf.flip();
		
		return buf;
	}
	
	public static FloatBuffer createFlippedBuffer(Matrix4f val)
	{
		FloatBuffer buf = createFloatBuffer(4 * 4);
		
		for(int i = 0; i < 4; i++)
		{
			for(int j = 0; j < 4; j++)
			{
				buf.put(val.get(i, j));
			}
		}
		
		buf.flip();
		
		return buf;
	}
	
	public static IntBuffer createFlippedBuffer(int... values)
	{
		IntBuffer buf = createIntBuffer(values.length);
		
		buf.put(values);
		
		buf.flip();
		
		return buf;
	}

	public static String[] removeEmptyString(String[] data) 
	{
		ArrayList<String> result = new ArrayList<String>();
		
		for(int i = 0; i < data.length; i++)
		{
			if(!data[i].equals(""))
				result.add(data[i]);
		}
		
		String[] res = new String[result.size()];
		result.toArray(res);
		return res;
	}

	public static int[] toIntArray(Integer[] data) 
	{
		int[] res = new int[data.length];
		
		for(int i = 0; i < data.length; i++)
		{
			res[i] = data[i].intValue();
		}
		
		return res;
	}
}

package com.engine.components.terrain;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import com.engine.core.Vector2f;
import com.engine.core.Vector3f;
import com.engine.rendering.Material;
import com.engine.rendering.Texture;
import com.engine.rendering.meshLoading.InputModel;
import com.engine.rendering.meshLoading.OBJIndex;

public class HeightMap extends Terrain
{	
	String png, texture; //files
	Material mat;
	
	public HeightMap()
	{
		super();
	}
	
	public HeightMap(String pngFile, Material mat, Vector3f scale, OriginGravity gravity)
	{
		png = pngFile;
		this.mat = mat;
		SetScale(scale);
		SetOriginGravity(gravity);
	}
	
	public HeightMap(String pngFile, String textureFile)
	{
		this();
		png = pngFile;
		texture = textureFile;
	}
	
	public HeightMap(String pngFile, String textureFile, Vector3f scale)
	{
		this(pngFile, textureFile);
		SetScale(scale);
	}
	
	public HeightMap(String pngFile, String textureFile, Vector3f scale, OriginGravity gravity)
	{
		this(pngFile, textureFile, scale);
		SetOriginGravity(gravity);
		compile();
	}
	
	@Override
	protected void build() 
	{
		super.build();
		loadHeightMap(png, texture);
	}
	
	public boolean loadHeightMap(String pngFile, String textureFile)
	{
		ArrayList<Vector3f> pos = new ArrayList<Vector3f>();
		ArrayList<Vector2f> texCoords = new ArrayList<Vector2f>();
		ArrayList<Vector3f> normals = new ArrayList<Vector3f>();
		ArrayList<OBJIndex> indices = new ArrayList<OBJIndex>();
		
		ArrayList<Integer> modelIndices = new ArrayList<Integer>();
		ArrayList<Integer> textureIndices = new ArrayList<Integer>();
		ArrayList<Integer> normalIndices = new ArrayList<Integer>();
		
		try 
		{
			BufferedImage img = ImageIO.read(new File("./res/maps/" + pngFile));
			
			int w = img.getWidth();
			int h = img.getHeight();
			
			width = (float)w * scale.GetX();
			depth = (float)h * scale.GetZ();
			
			terrainRadius = new Vector2f(w / 2, h / 2);
			
			float dx = (float)(1f / (float)w);
			float dz = (float)(1f / (float)h);
			
			for(int z = 0; z < h; z++)
			{
				for(int x = 0; x < w; x++)
				{
					Color color = new Color(img.getRGB(x, z));
					float height = (float)color.getRed() * scale.GetY();
					heights.add(height);
					pos.add(new Vector3f(x * scale.GetX(), height, z * scale.GetZ()));
					
					texCoords.add(new Vector2f(dx * x, dz * z));
					
					normals.add(new Vector3f(0, 0, 0));
				}
			}
						
			for(int z = 0; z < h - 1; z++)
			{
				for(int x = 0; x < w - 1; x++)
				{					
					int i0 = z * w + x;
					int i1 = (z + 1) * w + x;
					int i2 = z * w + x + 1;
					int i3 = (z + 1) * w + x + 1;
					
					modelIndices.add(i0);
					modelIndices.add(i1);
					modelIndices.add(i2);
					modelIndices.add(i3);
					
					textureIndices.add(i0);
					textureIndices.add(i1);
					textureIndices.add(i2);
					textureIndices.add(i3);
					
					normalIndices.add(i0);
					normalIndices.add(i1);
					normalIndices.add(i2);
					normalIndices.add(i3);
				}
			}
			
			for(int i = 0; i < modelIndices.size(); i++)
			{
				indices.add(new OBJIndex(
						modelIndices.get(i),
						textureIndices.get(i),
						normalIndices.get(i)						
				));
			}
		} 
		
		catch (IOException e) 
		{
			e.printStackTrace();
			return false;
		}
		
		InputModel iModel = new InputModel(pos, texCoords, null, indices);
		
		if(mat == null)
			mat = new Material(new Texture(textureFile), 1, 8, null, null, 0.03f, -0.5f);
		
		if(!createMeshRenderer(iModel, mat))
		{
			return false;
		}
		
		return true;
	}
	
	public float getHeight(float x, float z) //with respect to map
	{
		int i = 0;
		
		i = (int)((z / scale.GetZ()) * (width / scale.GetX()) + (x / scale.GetX()));
		
		return GetHeight(i);
	}
	
	public static float GetHeight(float x, float z, Terrain t) //with respect to map
	{
		int i = 0;
		Vector3f scale = t.GetScale();
		float width = t.GetWidth();
		
		i = (int)((z / scale.GetZ()) * (width / scale.GetX()) + (x / scale.GetX()));
		
		return t.GetHeight(i);
	}
}
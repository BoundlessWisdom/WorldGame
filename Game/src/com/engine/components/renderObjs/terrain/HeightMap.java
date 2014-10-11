package com.engine.components.renderObjs.terrain;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import org.newdawn.slick.opengl.ImageIOImageData;
import org.newdawn.slick.opengl.PNGDecoder;
import org.omg.CORBA.portable.IndirectionException;

import com.engine.core.Vector2f;
import com.engine.core.Vector3f;
import com.engine.rendering.Mesh;
import com.engine.rendering.Texture;
import com.engine.rendering.meshLoading.InputModel;
import com.engine.rendering.meshLoading.OBJIndex;

public class HeightMap extends Terrain
{	
	ArrayList<Float> heights = new ArrayList<Float>();
	
	String png, texture; //files
	
	public HeightMap()
	{
		super();
	}
	
	public HeightMap(String pngFile, String textureFile)
	{
		png = pngFile;
		texture = textureFile;
	}
	
	 
	
	public boolean loadHeightMap(String file)
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
			BufferedImage img = ImageIO.read(new File(file));
			
			int w = img.getWidth();
			int h = img.getHeight();
			
			float dx = (float)(1f / (float)w);
			float dz = (float)(1f / (float)h);
			
			for(int z = 0; z < h; z++)
			{
				for(int x = 0; x < w; x++)
				{
					Color color = new Color(img.getRGB(x, z));
					float height = (float)color.getRed();
					heights.add(height);
					pos.add(new Vector3f(x, height, z));
					
					texCoords.add(new Vector2f(dx * x, dz * z));
					textureIndices.add(texCoords.size() - 1);
					
					normals.add(Vector3f.get0());
					normalIndices.add(normals.size() - 1);
				}
			}
			
			for(int z = 0; z < h; z++)
			{
				for(int x = 0; x < w; x++)
				{
					int i0 = z * x + x;
					int i1 = (z + 1) * x + x;
					int i2 = z * x + x + 1;
					int i3 = (z + 1) * x + x + 1;
					
					modelIndices.add(i0);
					modelIndices.add(i1);
					modelIndices.add(i2);
					modelIndices.add(i3);
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
		}
		
		InputModel iModel = new InputModel(pos, texCoords, null, indices);
		
		createMeshRenderer(iModel, null);
		
		return true;
	}
}
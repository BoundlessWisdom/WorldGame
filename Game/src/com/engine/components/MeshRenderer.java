package com.engine.components;

import com.engine.rendering.Material;
import com.engine.rendering.Mesh;
import com.engine.rendering.shaders.Shader;

public class MeshRenderer extends GameComponent 
{
	private Mesh mesh;
	private Material material;
	
	public MeshRenderer(Mesh mesh, Material material) 
	{
		super();
		this.mesh = mesh;
		this.material = material;
	}
	
	public void render(Shader shader)
	{		
		shader.bind();
		shader.updateUniforms(getTransform(), material);
		mesh.draw();
	}
}

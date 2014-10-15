package com.engine.components.renderObjs;

import com.engine.components.GameComponent;
import com.engine.rendering.Material;
import com.engine.rendering.Mesh;
import com.engine.rendering.shaders.Shader;

public class MeshRenderer extends GameComponent 
{
	private Mesh mesh;
	private Material material;
	
	boolean render = true;
	
	public MeshRenderer(Mesh mesh, Material material) 
	{
		super();
		this.mesh = mesh;
		this.material = material;
	}
	
	public void render(Shader shader)
	{
		if(render)
		{
			shader.bind();
			shader.updateUniforms(getTransform(), material);
			mesh.draw();
		}
	}
}

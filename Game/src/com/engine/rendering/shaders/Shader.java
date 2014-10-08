package com.engine.rendering.shaders;

import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL32.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashMap;

import com.engine.core.Matrix4f;
import com.engine.core.Transform;
import com.engine.core.Util;
import com.engine.core.Vector3f;
import com.engine.rendering.Material;
import com.engine.rendering.RenderingEngine;

public class Shader 
{
	private int program;
	private HashMap<String, Integer> uniforms;
	private RenderingEngine renderingEngine;
	private static RenderingEngine globalRenderingEngine = null;
	
	public Shader()
	{
		program = glCreateProgram();
		uniforms = new HashMap<String, Integer>();
		
		if(program == 0)
		{
			System.err.println("No shader creation possible");
			System.exit(1);
		}
	}
	
	public void addUniform(String uniform)
	{
		int uniformLoc = glGetUniformLocation(program, uniform);
		
		if(uniformLoc == 0xFFFFFFFF) //error check
		{
			System.err.println("Error: Uniform " + uniform + " doesn't exist");
			new Exception().printStackTrace();
			System.exit(1);
		}
		
		uniforms.put(uniform, uniformLoc);
	}
	
	public void bind()
	{
		glUseProgram(program);
	}
	
	@SuppressWarnings("deprecation")
	public void CompileShader()
	{
		glLinkProgram(program);
		
		if(glGetProgram(program, GL_LINK_STATUS) == 0)
		{
			System.err.println(glGetShaderInfoLog(program, 1024));
			System.exit(1);
		}
		
		glValidateProgram(program);
		
		if(glGetProgram(program, GL_VALIDATE_STATUS) == 0)
		{
			System.err.println(glGetShaderInfoLog(program, 1024));
			System.exit(1);
		}
	}
	
	@SuppressWarnings("deprecation")
	public void addProgram(String text, int type)
	{
		int shader = glCreateShader(type);
		
		if(shader == 0)
		{
			System.err.println("Shader creation not possible");
			System.exit(1);
		}
		
		glShaderSource(shader, text);
		glCompileShader(shader);
		
		if(glGetShader(shader, GL_COMPILE_STATUS) == 0)
		{
			System.err.println(glGetShaderInfoLog(shader, 1024));
			System.exit(1);
		}
		
		glAttachShader(program, shader);
	}
	
	private static String loadShader(String file)
	{
		StringBuilder shaderSource = new StringBuilder();
		BufferedReader shaderRead = null;
		
		try 
		{
			shaderRead = new BufferedReader(new FileReader("./res/shaders/" + file));
			String line;
			while((line = shaderRead.readLine()) != null)
			{
				shaderSource.append(line).append("\n");
			}
			
			shaderRead.close();
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			System.exit(-1);
		}
		
		return shaderSource.toString();
	}
	
	public void addVertexShaderFromFile(String file)
	{
		addProgram(loadShader(file), GL_VERTEX_SHADER);
	}
	
	public void addFragmentShaderFromFile(String file)
	{
		addProgram(loadShader(file), GL_FRAGMENT_SHADER);
	}
	
	public void addGeometryShaderFromFile(String file)
	{
		addProgram(loadShader(file), GL_GEOMETRY_SHADER);
	}
	
	public void addVertexShader(String text)
	{
		addProgram(text, GL_VERTEX_SHADER);
	}
	
	public void addFragmentShader(String text)
	{
		addProgram(text, GL_FRAGMENT_SHADER);
	}
	
	public void addGeometryShader(String text)
	{
		addProgram(text, GL_GEOMETRY_SHADER);
	}
	
	public void updateUniforms(Transform transform, Material material)
	{
		
	}
	
	public void setUniformi(String name, int value)
	{
		glUniform1i(uniforms.get(name), value);
	}
	
	public void setUniformf(String name, float value)
	{
		glUniform1f(uniforms.get(name), value);
	}
	
	public void setUniform(String name, Vector3f value)
	{
		glUniform3f(uniforms.get(name), value.getX(), value.getY(), value.getZ());
	}
	
	public void setUniform(String name, Matrix4f value)
	{
		glUniformMatrix4(uniforms.get(name), true, Util.createFlippedBuffer(value));
	}
	
	public void setRenderingEngine(RenderingEngine renderingEngine)
	{
		this.renderingEngine = renderingEngine;
	}
	
	public static void setGlobalRenderingEngine(RenderingEngine globalRenderingEngine)
	{
		Shader.globalRenderingEngine = globalRenderingEngine;
	}
	
	public static RenderingEngine getGlobalRenderingEngine()
	{
		return globalRenderingEngine;
	}
	
	public RenderingEngine getRenderingEngine()
	{
		return renderingEngine;
	}	
}

package com.nathandsimon.lwjglgameutils;


import org.lwjgl.util.vector.Vector3f;

public class Face {
	public Vector3f vertexIndices = new Vector3f();
	public Vector3f normalIndices = new Vector3f();
	public Face(Vector3f vertices, Vector3f normals) {
		normalIndices = normals;
		vertexIndices = vertices;
	}

}

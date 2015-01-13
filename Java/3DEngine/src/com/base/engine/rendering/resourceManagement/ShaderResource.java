package com.base.engine.rendering.resourceManagement;

import com.base.engine.core.Util;

import java.util.ArrayList;
import java.util.HashMap;

import static org.lwjgl.opengl.GL15.glDeleteBuffers;
import static org.lwjgl.opengl.GL20.glCreateProgram;

public class ShaderResource {
	private int program;
	private int refCount;
	private HashMap<String, Integer> uniforms;
	private ArrayList<String> uniformNames;
	private ArrayList<String> uniformTypes;

	public ShaderResource() {
		this.program = glCreateProgram();
		this.refCount = 1;

		uniforms = new HashMap<String, Integer>();
		uniformNames = new ArrayList<String>();
		uniformTypes = new ArrayList<String>();

		if (program == 0) {
			Util.err("Shader creation failed: Could not find valid memory location in constructor");
			System.exit(1);
		}
	}

	@Override
	protected void finalize() {
		glDeleteBuffers(program);
	}

	public void addReference() {
		refCount++;
	}

	public boolean removeReference() {
		refCount--;
		return refCount == 0;
	}

	public int getProgram() {
		return program;
	}

	public HashMap<String, Integer> getUniforms() {
		return uniforms;
	}

	public ArrayList<String> getUniformNames() {
		return uniformNames;
	}

	public ArrayList<String> getUniformTypes() {
		return uniformTypes;
	}
}

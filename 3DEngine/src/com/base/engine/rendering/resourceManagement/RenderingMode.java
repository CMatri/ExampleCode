package com.base.engine.rendering.resourceManagement;

import static org.lwjgl.opengl.GL11.*;

public class RenderingMode {
	public static RenderingMode LINES = new RenderingMode().setMode(GL_LINE);
	public static RenderingMode FULL = new RenderingMode().setMode(GL_FILL);
	public static RenderingMode POINTS = new RenderingMode().setMode(GL_POINT);

	private int MODE;

	public int getMode() {
		return MODE;
	}

	public RenderingMode setMode(int MODE) {
		this.MODE = MODE;

		return this;
	}
}

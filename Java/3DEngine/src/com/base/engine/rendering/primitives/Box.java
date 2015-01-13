package com.base.engine.rendering.primitives;

import com.base.engine.core.Vector2f;
import com.base.engine.core.Vector3f;
import com.base.engine.rendering.Mesh;
import com.base.engine.rendering.Vertex;

import static org.lwjgl.opengl.GL11.GL_TRIANGLE_STRIP;

public class Box extends Mesh {
	public Box(float size) {
		super(
				new Vertex[]{
						new Vertex(new Vector3f(0, 0, size), new Vector2f(0, 0)),
						new Vertex(new Vector3f(size, 0, size), new Vector2f(2, 0)),
						new Vertex(new Vector3f(0, size, size), new Vector2f(0, 2)),
						new Vertex(new Vector3f(size, size, size), new Vector2f(2, 2)),
						new Vertex(new Vector3f(0, 0, 0), new Vector2f(0, 0)),
						new Vertex(new Vector3f(size, 0, 0), new Vector2f(2, 0)),
						new Vertex(new Vector3f(0, size, 0), new Vector2f(0, 2)),
						new Vertex(new Vector3f(size, size, 0), new Vector2f(2, 2)),
				},
				new int[]{
						0, 1, 2, 3, 7, 1, 5, 4, 7, 6, 2, 4, 0, 1
				}
		);

//		setDrawType(GL_TRIANGLE_STRIP);
	}
}

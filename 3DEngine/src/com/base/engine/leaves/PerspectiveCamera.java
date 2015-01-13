package com.base.engine.leaves;

import com.base.engine.core.Matrix4f;

public class PerspectiveCamera extends Camera {
	public PerspectiveCamera(float fov, float aspect, float zNear, float zFar) {
		super(new Matrix4f().initPerspective(fov, aspect, zNear, zFar));
	}
}

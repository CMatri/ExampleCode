package com.base.engine.leaves;

import com.base.engine.core.Matrix4f;

public class OrthographicCamera extends Camera {
	public OrthographicCamera(float left, float right, float bottom, float top, float near, float far) {
		super(new Matrix4f().initOrthographic(left, right, bottom, top, near, far));
	}
}

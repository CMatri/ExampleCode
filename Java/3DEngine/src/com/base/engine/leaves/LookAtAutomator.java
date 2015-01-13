package com.base.engine.leaves;

import com.base.engine.core.GameBranch;
import com.base.engine.core.Quaternion;
import com.base.engine.core.Transform;
import com.base.engine.core.Vector3f;

public class LookAtAutomator extends GameLeaf {
	private Transform target;
	private float speed;

	public LookAtAutomator(GameBranch target) {
		this.target = target.getTransform();
	}

	public LookAtAutomator(GameLeaf target) {
		this.target = target.getTransform();
	}

	public void update(float delta) {
		Quaternion newRot = getTransform().getLookAtRotation(target.getTransformedPos(), new Vector3f(0, 1, 0));
		getTransform().setRot(getTransform().getRot().slerp(newRot, delta * speed, true));
	}

	public LookAtAutomator setSpeed(float speed) {
		this.speed = speed;
		return this;
	}
}

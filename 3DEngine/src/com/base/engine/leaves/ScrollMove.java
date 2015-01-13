package com.base.engine.leaves;

import com.base.engine.core.Input;
import com.base.engine.core.Vector3f;

public class ScrollMove extends GameLeaf {
	private float speed;
	private int leftKey;
	private int rightKey;
	private int upKey;
	private int downKey;

	public ScrollMove() {
		this(10, Input.KEY_A, Input.KEY_D, Input.KEY_SPACE, Input.KEY_LCONTROL);
	}

	public ScrollMove(float speed) {
		this(speed, Input.KEY_A, Input.KEY_D, Input.KEY_SPACE, Input.KEY_LCONTROL);
	}

	public ScrollMove(float speed, int leftKey, int rightKey) {
		this(speed, leftKey, rightKey, Input.KEY_SPACE, Input.KEY_LCONTROL);
	}

	public ScrollMove(float speed, int leftKey, int rightKey, int upKey, int downKey) {
		this.speed = speed;
		this.leftKey = leftKey;
		this.rightKey = rightKey;
		this.upKey = upKey;
		this.downKey = downKey;
	}

	public float getSpeed() {
		return speed;
	}

	public void setSpeed(float speed) {
		this.speed = speed;
	}

	public int getLeftKey() {
		return leftKey;
	}

	public void setLeftKey(int leftKey) {
		this.leftKey = leftKey;
	}

	public int getRightKey() {
		return rightKey;
	}

	public void setRightKey(int rightKey) {
		this.rightKey = rightKey;
	}

	public int getUpKey() {
		return upKey;
	}

	public void setUpKey(int upKey) {
		this.upKey = upKey;
	}

	public int getDownKey() {
		return downKey;
	}

	public void setDownKey(int downKey) {
		this.downKey = downKey;
	}

	@Override
	public void input(float delta) {
		float movAmt = speed * delta;

		if (Input.getKey(leftKey))
			move(getTransform().getRot().getLeft(), movAmt);
		if (Input.getKey(rightKey))
			move(getTransform().getRot().getRight(), movAmt);

		if (Input.getKey(downKey))
			move(getTransform().getRot().getUp(), -movAmt);
		else if (Input.getKey(upKey))
			move(getTransform().getRot().getUp(), movAmt);
	}

	public void move(Vector3f dir, float amt) {
		getTransform().getPos().set(getTransform().getPos().add(dir.mul(amt)));
	}
}

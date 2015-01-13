package com.base.engine.leaves;

import com.base.engine.core.Input;
import com.base.engine.core.Vector3f;

public class FlyMove extends GameLeaf {
	private Vector3f yAxis = new Vector3f(0, 1, 0);

	private float speed;
	private int forwardKey;
	private int backKey;
	private int leftKey;
	private int rightKey;
	private int upKey;
	private int downKey;

	public FlyMove() {
		this(10, Input.KEY_W, Input.KEY_S, Input.KEY_A, Input.KEY_D, Input.KEY_SPACE, Input.KEY_LCONTROL);
	}

	public FlyMove(float speed) {
		this(speed, Input.KEY_W, Input.KEY_S, Input.KEY_A, Input.KEY_D, Input.KEY_SPACE, Input.KEY_LCONTROL);
	}

	public FlyMove(float speed, int forwardKey, int backKey, int leftKey, int rightKey) {
		this(speed, forwardKey, backKey, leftKey, rightKey, Input.KEY_SPACE, Input.KEY_LCONTROL);
	}

	public FlyMove(float speed, int forwardKey, int backKey, int leftKey, int rightKey, int upKey, int downKey) {
		this.speed = speed;
		this.forwardKey = forwardKey;
		this.backKey = backKey;
		this.leftKey = leftKey;
		this.rightKey = rightKey;
		this.upKey = upKey;
		this.downKey = downKey;
	}

	public Vector3f getYAxis() {
		return yAxis;
	}

	public float getSpeed() {
		return speed;
	}

	public void setSpeed(float speed) {
		this.speed = speed;
	}

	public int getForwardKey() {
		return forwardKey;
	}

	public void setForwardKey(int forwardKey) {
		this.forwardKey = forwardKey;
	}

	public int getBackKey() {
		return backKey;
	}

	public void setBackKey(int backKey) {
		this.backKey = backKey;
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

		if (Input.getKey(forwardKey))
			move(getTransform().getRot().getForward(), movAmt);
		if (Input.getKey(backKey))
			move(getTransform().getRot().getForward(), -movAmt);
		if (Input.getKey(leftKey))
			move(getTransform().getRot().getLeft(), movAmt);
		if (Input.getKey(rightKey))
			move(getTransform().getRot().getRight(), movAmt);

		if (Input.getKey(downKey))
			move(yAxis, -movAmt);
		else if (Input.getKey(upKey))
			move(yAxis, movAmt);
	}

	public void move(Vector3f dir, float amt) {
		getTransform().getPos().set(getTransform().getPos().add(dir.mul(amt)));
	}
}

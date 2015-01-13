package com.base.engine.leaves;

import com.base.engine.core.Input;
import com.base.engine.core.Vector2f;
import com.base.engine.core.Vector3f;
import com.base.engine.rendering.Window;

public class FlyLook extends GameLeaf {
	private Vector3f yAxis = new Vector3f(0, 1, 0);
	private boolean mouseLocked = false;
	Vector2f centerPosition = new Vector2f(Window.getWidth() / 2, Window.getHeight() / 2);
	private int unlockMouseKey;
	private boolean rightClickUnlock;
	private float sensitivity;

	public FlyLook() {
		this(0.10f);
	}

	public FlyLook(float sensitivity) {
		this(sensitivity, Input.KEY_ESCAPE);
	}

	public FlyLook(float sensitivity, int unlockMouseKey) {
		this(sensitivity, unlockMouseKey, false);
	}

	public FlyLook(float sensitivity, int unlockMouseKey, boolean rightClickUnlock) {
		this.sensitivity = sensitivity;
		this.unlockMouseKey = unlockMouseKey;
		this.rightClickUnlock = rightClickUnlock;
	}

	public Vector3f getyAxis() {
		return yAxis;
	}

	public void setyAxis(Vector3f yAxis) {
		this.yAxis = yAxis;
	}

	public boolean isMouseLocked() {
		return mouseLocked;
	}

	public void setMouseLocked(boolean mouseLocked) {
		this.mouseLocked = mouseLocked;
	}

	public Vector2f getCenterPosition() {
		return centerPosition;
	}

	public void setCenterPosition(Vector2f centerPosition) {
		this.centerPosition = centerPosition;
	}

	public int getUnlockMouseKey() {
		return unlockMouseKey;
	}

	public void setUnlockMouseKey(int unlockMouseKey) {
		this.unlockMouseKey = unlockMouseKey;
	}

	public boolean isRightClickUnlock() {
		return rightClickUnlock;
	}

	public void setRightClickUnlock(boolean rightClickUnlock) {
		this.rightClickUnlock = rightClickUnlock;
	}

	public float getSensitivity() {
		return sensitivity;
	}

	public void setSensitivity(float sensitivity) {
		this.sensitivity = sensitivity;
	}

	@Override
	public void input(float delta) {
		if (Input.getMouseDown(0)) {
			Input.setCursor(false);
			Input.setMousePosition(centerPosition);
			mouseLocked = true;
		}

		if (Input.getMouseDown(1) && rightClickUnlock) {
			Input.setCursor(true);
			mouseLocked = false;
		}

		if (Input.getKey(unlockMouseKey)) {
			Input.setCursor(true);
			mouseLocked = false;
		}

		if (mouseLocked) {
			Vector2f deltaPos = Input.getMousePosition().sub(centerPosition);

			boolean rotY = deltaPos.getX() != 0;
			boolean rotX = deltaPos.getY() != 0;

			if (rotY)
				getTransform().rotate(yAxis, (float) Math.toRadians(deltaPos.getX() * sensitivity));
			if (rotX)
				getTransform().rotate(getTransform().getRot().getRight(), (float) Math.toRadians(-deltaPos.getY() * sensitivity));

			if (rotY || rotX)
				Input.setMousePosition(new Vector2f(Window.getWidth() / 2, Window.getHeight() / 2));
		}
	}
}

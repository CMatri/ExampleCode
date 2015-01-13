package com.base.engine.leaves;

import com.base.engine.core.GameBranch;
import com.base.engine.core.Util;
import com.base.engine.core.Vector3f;

public class FlyFollow extends GameLeaf {

	private float followSpeed;
	private float maxLength;
	private float startLength;
	private GameBranch target;

	public FlyFollow(GameBranch target) {
		this(target, 1, 10, 5);
	}

	public FlyFollow(GameLeaf target) {
		this(target, 1, 10, 5);
	}

	public FlyFollow(GameLeaf target, float followSpeed, float maxLength, float startLength) {
		this.target = target.getParent();
		this.followSpeed = followSpeed;
		this.maxLength = maxLength;
		this.startLength = startLength;
	}

	public FlyFollow(GameBranch target, float followSpeed, float maxLength, float startLength) {
		this.target = target;
		this.followSpeed = followSpeed;
		this.maxLength = maxLength;
		this.startLength = startLength;
	}

	public void update(float delta) {
		if(target.getTransform().getPos().distance(getTransform().getPos()) > maxLength) {
			getTransform().getPos().set(getTransform().getPos().lerp(target.getTransform().getPos(), followSpeed * delta));
		}
	}
}

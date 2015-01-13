package com.base.engine.leaves;

import com.base.engine.core.CoreEngine;
import com.base.engine.core.GameBranch;
import com.base.engine.rendering.RenderingEngine;
import com.base.engine.core.Transform;
import com.base.engine.rendering.Shader;

public abstract class GameLeaf {
	private GameBranch parent;

	public void input(float delta) {
	}

	public void update(float delta) {
	}

	public void render(Shader shader, RenderingEngine renderingEngine) {
	}

	public void setParent(GameBranch parent) {
		this.parent = parent;
	}

	public GameBranch getParent() {
		return parent;
	}

	public Transform getTransform() {
		return parent.getTransform();
	}

	public void addToEngine(CoreEngine engine) {
	}
}

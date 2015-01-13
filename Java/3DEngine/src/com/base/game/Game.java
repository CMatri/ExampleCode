package com.base.game;

import com.base.engine.core.CoreEngine;
import com.base.engine.core.GameBranch;
import com.base.engine.rendering.RenderingEngine;

public abstract class Game {
	private GameBranch root = new GameBranch();

	public void init() {
	}

	public void input(float delta) {
		getRootBranch().inputAll(delta);
	}

	public void update(float delta) {
		getRootBranch().updateAll(delta);
	}

	public void render(RenderingEngine renderingEngine) {
		renderingEngine.render(getRootBranch());
	}

	public void addObject(GameBranch branch) {
		getRootBranch().addBranch(branch);
	}

	public GameBranch getRootBranch() {
		if (root == null)
			root = new GameBranch();
		return root;
	}

	public void setEngine(CoreEngine engine) {
		getRootBranch().setEngine(engine);
	}
}

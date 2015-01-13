package com.base.engine.core;


import com.base.engine.leaves.GameLeaf;
import com.base.engine.rendering.RenderingEngine;
import com.base.engine.rendering.Shader;

import java.util.ArrayList;

public class GameBranch {
	private ArrayList<GameBranch> children;
	private ArrayList<GameLeaf> leaves;
	private Transform transform;
	private CoreEngine engine;

	public GameBranch() {
		children = new ArrayList<GameBranch>();
		leaves = new ArrayList<GameLeaf>();
		transform = new Transform();
		engine = null;
	}

	public void addBranch(GameBranch child) {
		children.add(child);
		child.setEngine(engine);
		child.getTransform().setParent(transform);
	}

	public GameBranch addLeaf(GameLeaf leaf) {
		leaves.add(leaf);
		leaf.setParent(this);

		return this;
	}

	public void inputAll(float delta) {
		input(delta);

		for (GameBranch child : children) {
			child.inputAll(delta);
		}
	}

	public void updateAll(float delta) {
		update(delta);

		for (GameBranch child : children) {
			child.updateAll(delta);
		}
	}

	public void renderAll(Shader shader, RenderingEngine renderingEngine) {
		render(shader, renderingEngine);

		for (GameBranch child : children) {
			child.renderAll(shader, renderingEngine);
		}
	}

	public void input(float delta) {
		transform.update();

		for (GameLeaf leaf : leaves) {
			leaf.input(delta);
		}
	}

	public void update(float delta) {
		for (GameLeaf leaf : leaves) {
			leaf.update(delta);
		}
	}

	public void render(Shader shader, RenderingEngine renderingEngine) {
		for (GameLeaf leaf : leaves) {
			leaf.render(shader, renderingEngine);
		}
	}

	public ArrayList<GameBranch> getAllAttached() {
		ArrayList<GameBranch> result = new ArrayList<GameBranch>();

		for (GameBranch child : children)
			result.addAll(child.getAllAttached());
		result.add(this);
		return result;
	}

	public Transform getTransform() {
		return transform;
	}

	public void setEngine(CoreEngine engine) {
		if (this.engine != engine) {
			this.engine = engine;

			for (GameLeaf leaf : leaves) {
				leaf.addToEngine(engine);
			}

			for (GameBranch child : children) {
				child.setEngine(engine);
			}
		}
	}
}

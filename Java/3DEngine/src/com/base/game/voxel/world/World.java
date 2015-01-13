package com.base.game.voxel.world;

import com.base.engine.core.GameBranch;
import com.base.engine.core.Input;
import com.base.engine.leaves.Camera;
import com.base.engine.leaves.FlyLook;
import com.base.engine.leaves.FlyMove;
import com.base.engine.leaves.PerspectiveCamera;
import com.base.engine.rendering.Window;
import com.base.game.Game;

public class World {
	private PerspectiveCamera camera;
	private Game game;

	public World(Game game) {
		this.game = game;
	}

	public void initiateCamera() {
		camera = new PerspectiveCamera((float) Math.toRadians(70.0f), (float) Window.getWidth() / (float) Window.getHeight(), 0.01f, 1000.0f);
		game.addObject(new GameBranch().addLeaf(camera).addLeaf(new FlyMove()).addLeaf(new FlyLook(0.1f, Input.KEY_ESCAPE, true)));
	}

	public Camera getCamera() {
		return camera;
	}
}

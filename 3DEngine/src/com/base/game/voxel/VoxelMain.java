package com.base.game.voxel;

import com.base.engine.core.GameBranch;
import com.base.engine.core.Quaternion;
import com.base.engine.core.Vector3f;
import com.base.engine.leaves.DirectionalLight;
import com.base.engine.rendering.Material;
import com.base.engine.rendering.MeshRenderer;
import com.base.engine.rendering.Texture;
import com.base.engine.rendering.primitives.Box;
import com.base.game.Game;
import com.base.game.voxel.world.World;
import com.base.game.voxel.world.chunks.Chunk;

public class VoxelMain extends Game {

	private World world;

	@Override
	public void init() {
		world = new World(this);

		world.initiateCamera();

		DirectionalLight directionalLight = new DirectionalLight(new Vector3f(1, 1, 1), 0.4f);
		addObject(new GameBranch().addLeaf(directionalLight));
		directionalLight.getTransform().setRot(new Quaternion(new Vector3f(1, 0, 0), (float) Math.toRadians(-45)));

		new Chunk(0, 0, 0, this);
	}

	@Override
	public void update(float delta) {
		super.update(delta);
	}

	@Override
	public void input(float delta) {
		super.input(delta);
	}
}

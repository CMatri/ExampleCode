package com.base.game.voxel.world.tiles;

import com.base.engine.core.Color4f;

public class TileGrass extends Tile {
	@Override
	public byte getId() {
		return 1;
	}

	@Override
	public Color4f getColor() {
		return Color4f.GREEN;
	}
}

package com.base.game.voxel.world.tiles;

import com.base.engine.core.Color4f;

public class TileVoid extends Tile {
	@Override
	public byte getId() {
		return 0;
	}

	@Override
	public Color4f getColor() {
		return Color4f.BLACK;
	}
}

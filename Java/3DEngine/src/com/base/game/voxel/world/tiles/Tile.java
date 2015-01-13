package com.base.game.voxel.world.tiles;

import com.base.engine.core.Color4f;

public abstract class Tile {

	public static Tile Void = new TileVoid();
	public static Tile Grass = new TileGrass();

	private boolean isActive;

	public abstract byte getId();

	public abstract Color4f getColor();

	public static Tile getTile(byte id) {
		switch (id) {
			case 1:
				return Tile.Grass;

		}

		return Tile.Void;
	}

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}
}

package com.base.game.voxel.world.chunks;

import com.base.engine.core.GameBranch;
import com.base.engine.core.Util;
import com.base.engine.core.Vector3f;
import com.base.engine.rendering.*;
import com.base.game.Game;
import com.base.game.voxel.world.tiles.Tile;

import static org.lwjgl.opengl.GL11.GL_TRIANGLE_STRIP;

import java.util.ArrayList;

public class Chunk {
	public static final int CHUNKSIZE = 14;
	public static final int BLOCKSIZE = 5;
	private Vector3f pos;

	private boolean isActive;
	private int sizeX, sizeY, sizeZ;
	private byte[][][] tiles;
	private GameBranch chunkBranch;

	private ArrayList<Vertex> vertices;
	private ArrayList<Integer> indices;

	private Mesh chunkMesh;

	public Chunk(float x, float y, float z, Game game) {
		this(new Vector3f(x, y, z), game);
	}

	public Chunk(Vector3f pos, Game game) {
		this.vertices = new ArrayList<Vertex>();
		this.indices = new ArrayList<Integer>();
		this.pos = pos;
		this.chunkBranch = new GameBranch();

		sizeX = (int) pos.getX() + CHUNKSIZE;
		sizeY = (int) pos.getY() + CHUNKSIZE;
		sizeZ = (int) pos.getZ() + CHUNKSIZE;

		tiles = new byte[sizeX][sizeY][sizeZ];

		createChunk();
		rebuild();

		Material barkMaterial = new Material();
		barkMaterial.setTexture("diffuse", new Texture("bark.jpg"));
		barkMaterial.setTexture("normalMap", new Texture("bark_norm.jpg"));
		barkMaterial.setFloat("specularIntensity", 1);
		barkMaterial.setFloat("specularPower", 5);

		game.addObject(chunkBranch.addLeaf(new MeshRenderer(chunkMesh, barkMaterial)));
	}

	public void update() {

	}

	public void render() {

	}

	public void createChunk() {
		for (int x = 0; x < sizeX; x++) {
			for (int y = 0; y < sizeY; y++) {
				for (int z = 0; z < sizeZ; z++) {
					tiles[x][y][z] = Tile.Grass.getId();
				}
			}
		}
	}

	public void rebuild() {
		for (int x = 0; x < sizeX; x++) {
			for (int y = 0; y < sizeY; y++) {
				for (int z = 0; z < sizeZ; z++) {
					vertices.add(new Vertex(new Vector3f(x, y, z + BLOCKSIZE)));
					vertices.add(new Vertex(new Vector3f(x + BLOCKSIZE, y, z + BLOCKSIZE)));
					vertices.add(new Vertex(new Vector3f(0, y + BLOCKSIZE, z + BLOCKSIZE)));
					vertices.add(new Vertex(new Vector3f(x + BLOCKSIZE, y + BLOCKSIZE, z + BLOCKSIZE)));
					vertices.add(new Vertex(new Vector3f(x, y, z)));
					vertices.add(new Vertex(new Vector3f(x + BLOCKSIZE, y, z)));
					vertices.add(new Vertex(new Vector3f(x, y + BLOCKSIZE, z)));
					vertices.add(new Vertex(new Vector3f(x + BLOCKSIZE, y + BLOCKSIZE, z)));

//					indices.add(0);
//					indices.add(1);
//					indices.add(2);
//					indices.add(3);
//					indices.add(7);
//					indices.add(1);
//					indices.add(5);
//					indices.add(4);
//					indices.add(7);
//					indices.add(6);
//					indices.add(2);
//					indices.add(4);
//					indices.add(0);
//					indices.add(1);

				}
			}
		}

		chunkMesh = new Mesh(toVertexArray(vertices), Util.toIntArray(toIntegerArray(indices)));
//		chunkMesh.setDrawType(GL_TRIANGLE_STRIP);
	}

	public void dispose() {

	}

	private void checkTileInView() {

	}

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	public static Integer[] toIntegerArray(ArrayList<Integer> e) {
		Integer[] out = new Integer[e.size()];

		for (int i = 0; i < e.size(); i++)
			out[i] = e.get(i);

		return out;
	}

	public static Vertex[] toVertexArray(ArrayList<Vertex> e) {
		Vertex[] out = new Vertex[e.size()];

		for (int i = 0; i < e.size(); i++)
			out[i] = e.get(i);

		return out;
	}
}

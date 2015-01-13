package com.base.engine.rendering.meshLoading;

public class OBJIndex {
	public int vertexIndex;
	public int texCoordIndex;
	public int normalIndex;
	public int tangentIndex;

	@Override
	public boolean equals(Object obj) {
		OBJIndex index = (OBJIndex) obj;

		return vertexIndex == index.vertexIndex
				&& texCoordIndex == index.texCoordIndex
				&& normalIndex == index.normalIndex
				&& tangentIndex == index.tangentIndex;
	}

	@Override
	public int hashCode() {
		final int BASE = 17;
		final int MULTIPLIER = 31;

		int result = BASE;

		result = MULTIPLIER * result + vertexIndex;
		result = MULTIPLIER * result + texCoordIndex;
		result = MULTIPLIER * result + normalIndex;
		result = MULTIPLIER * result + tangentIndex;

		return result;
	}
}
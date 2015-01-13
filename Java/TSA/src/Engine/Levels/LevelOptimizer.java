package Engine.Levels;

import Engine.Blocks.BlockSuper;
import Engine.Blocks.Block_Air;
import Engine.Utils.Util;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.scene.Mesh;
import com.jme3.scene.VertexBuffer;
import com.jme3.util.BufferUtils;
import java.util.ArrayList;

public class LevelOptimizer {

    private static int[] indices;
    private static Vector3f[] vertices;
    private static Vector2f[] textureCoordinates;
    private static float[] normals;

    private enum FACE {

        TOP, BOTTOM, LEFT, RIGHT, FRONT, BACK;
    }

    private static void loadMeshData(Level level, Vector3f blockLocation) {
        ArrayList<Vector3f> verticeList = new ArrayList();
        ArrayList<Vector2f> textureCoordinateList = new ArrayList();
        ArrayList<Integer> indicesList = new ArrayList();
        ArrayList<Float> normalsList = new ArrayList();

        float blockSize = Util.getMain().getBlockSize();

        Vector3f faceLoc_Bottom_TopLeft = blockLocation.add(new Vector3f(0.0F, 0.0F, 0.0F)).mult(blockSize);
        Vector3f faceLoc_Bottom_TopRight = blockLocation.add(new Vector3f(1.0F, 0.0F, 0.0F)).mult(blockSize);
        Vector3f faceLoc_Bottom_BottomLeft = blockLocation.add(new Vector3f(0.0F, 0.0F, 1.0F)).mult(blockSize);
        Vector3f faceLoc_Bottom_BottomRight = blockLocation.add(new Vector3f(1.0F, 0.0F, 1.0F)).mult(blockSize);
        Vector3f faceLoc_Top_TopLeft = blockLocation.add(new Vector3f(0.0F, 1.0F, 0.0F)).mult(blockSize);
        Vector3f faceLoc_Top_TopRight = blockLocation.add(new Vector3f(1.0F, 1.0F, 0.0F)).mult(blockSize);
        Vector3f faceLoc_Top_BottomLeft = blockLocation.add(new Vector3f(0.0F, 1.0F, 1.0F)).mult(blockSize);
        Vector3f faceLoc_Top_BottomRight = blockLocation.add(new Vector3f(1.0F, 1.0F, 1.0F)).mult(blockSize);

        if (shouldFaceBeAdded(level, blockLocation, FACE.TOP)) {
            addVerticeIndexes(verticeList, indicesList);
            verticeList.add(faceLoc_Top_BottomLeft);
            verticeList.add(faceLoc_Top_BottomRight);
            verticeList.add(faceLoc_Top_TopLeft);
            verticeList.add(faceLoc_Top_TopRight);
            addBlockTextureCoordinates(textureCoordinateList);
            addSquareNormals(normalsList, new float[]{0.0F, 1.0F, 0.0F});
        }
        if (shouldFaceBeAdded(level, blockLocation, FACE.BOTTOM)) {
            addVerticeIndexes(verticeList, indicesList);
            verticeList.add(faceLoc_Bottom_BottomRight);
            verticeList.add(faceLoc_Bottom_BottomLeft);
            verticeList.add(faceLoc_Bottom_TopRight);
            verticeList.add(faceLoc_Bottom_TopLeft);
            addBlockTextureCoordinates(textureCoordinateList);
            addSquareNormals(normalsList, new float[]{0.0F, -1.0F, 0.0F});
        }
        if (shouldFaceBeAdded(level, blockLocation, FACE.LEFT)) {
            addVerticeIndexes(verticeList, indicesList);
            verticeList.add(faceLoc_Bottom_TopLeft);
            verticeList.add(faceLoc_Bottom_BottomLeft);
            verticeList.add(faceLoc_Top_TopLeft);
            verticeList.add(faceLoc_Top_BottomLeft);
            addBlockTextureCoordinates(textureCoordinateList);
            addSquareNormals(normalsList, new float[]{-1.0F, 0.0F, 0.0F});
        }
        if (shouldFaceBeAdded(level, blockLocation, FACE.RIGHT)) {
            addVerticeIndexes(verticeList, indicesList);
            verticeList.add(faceLoc_Bottom_BottomRight);
            verticeList.add(faceLoc_Bottom_TopRight);
            verticeList.add(faceLoc_Top_BottomRight);
            verticeList.add(faceLoc_Top_TopRight);
            addBlockTextureCoordinates(textureCoordinateList);
            addSquareNormals(normalsList, new float[]{1.0F, 0.0F, 0.0F});
        }
        if (shouldFaceBeAdded(level, blockLocation, FACE.FRONT)) {
            addVerticeIndexes(verticeList, indicesList);
            verticeList.add(faceLoc_Bottom_BottomLeft);
            verticeList.add(faceLoc_Bottom_BottomRight);
            verticeList.add(faceLoc_Top_BottomLeft);
            verticeList.add(faceLoc_Top_BottomRight);
            addBlockTextureCoordinates(textureCoordinateList);
            addSquareNormals(normalsList, new float[]{0.0F, 0.0F, 1.0F});
        }
        if (shouldFaceBeAdded(level, blockLocation, FACE.BACK)) {
            addVerticeIndexes(verticeList, indicesList);
            verticeList.add(faceLoc_Bottom_TopRight);
            verticeList.add(faceLoc_Bottom_TopLeft);
            verticeList.add(faceLoc_Top_TopRight);
            verticeList.add(faceLoc_Top_TopLeft);
            addBlockTextureCoordinates(textureCoordinateList);
            addSquareNormals(normalsList, new float[]{0.0F, 0.0F, -1.0F});
        }

        indices = new int[indicesList.size()];
        for (int i = 0;
                i < indicesList.size();
                i++) {
            indices[i] = ((Integer) indicesList.get(i)).intValue();
        }
        vertices = (Vector3f[]) verticeList.toArray(new Vector3f[0]);
        textureCoordinates = (Vector2f[]) textureCoordinateList.toArray(new Vector2f[0]);
        normals = new float[normalsList.size()];
        for (int i = 0;
                i < normalsList.size();
                i++) {
            normals[i] = ((Float) normalsList.get(i)).floatValue();
        }
    }

    private static void addVerticeIndexes(ArrayList<Vector3f> verticeList, ArrayList<Integer> indexesList) {
        int verticesCount = verticeList.size();
        indexesList.add(Integer.valueOf(verticesCount + 2));
        indexesList.add(Integer.valueOf(verticesCount + 0));
        indexesList.add(Integer.valueOf(verticesCount + 1));
        indexesList.add(Integer.valueOf(verticesCount + 1));
        indexesList.add(Integer.valueOf(verticesCount + 3));
        indexesList.add(Integer.valueOf(verticesCount + 2));
    }

    private static void addBlockTextureCoordinates(ArrayList<Vector2f> textureCoordinatesList) {
        textureCoordinatesList.add(getTextureCoordinates(0, 0));
        textureCoordinatesList.add(getTextureCoordinates(1, 0));
        textureCoordinatesList.add(getTextureCoordinates(0, 1));
        textureCoordinatesList.add(getTextureCoordinates(1, 1));
    }

    private static Vector2f getTextureCoordinates(int xUnitsToAdd, int yUnitsToAdd) {
        float textureCount = 16.0F;
        float textureUnit = 1.0F / textureCount;
        float x = xUnitsToAdd * textureUnit;
        float y = (yUnitsToAdd - 1) * textureUnit + 1.0F;
        return new Vector2f(x, y);
    }

    private static void addSquareNormals(ArrayList<Float> normalsList, float[] normal) {
    }

    private static Mesh generateMesh() {
        Mesh mesh = new Mesh();
        mesh.setBuffer(VertexBuffer.Type.Position, 3, BufferUtils.createFloatBuffer(vertices));
        mesh.setBuffer(VertexBuffer.Type.TexCoord, 2, BufferUtils.createFloatBuffer(textureCoordinates));
        mesh.setBuffer(VertexBuffer.Type.Index, 1, BufferUtils.createIntBuffer(indices));
        mesh.setBuffer(VertexBuffer.Type.Normal, 3, BufferUtils.createFloatBuffer(normals));
        mesh.updateBound();
        return mesh;
    }

    private static boolean shouldFaceBeAdded(Level level, Vector3f blockLocation, FACE face) {
        int x, y, z;

        x = (int) blockLocation.x;
        y = (int) blockLocation.x;
        z = (int) blockLocation.x;
        switch (face) {
            case TOP:
                return (level.getCube(x, y + 1, z) == null ? false : !(level.getCube(x, y + 1, z) == Level.airBlock));
            case BOTTOM:
                return (level.getCube(x, y - 1, z) == null ? false : !(level.getCube(x, y - 1, z) == Level.airBlock));
            case LEFT:
                return (level.getCube(x - 1, y, z) == null ? false : !(level.getCube(x - 1, y, z) == Level.airBlock));
            case RIGHT:
                return (level.getCube(x + 1, y, z) == null ? false : !(level.getCube(x + 1, y, z) == Level.airBlock));
            case FRONT:
                return (level.getCube(x, y, z + 1) == null ? false : !(level.getCube(x, y, z + 1) == Level.airBlock));
            case BACK:
                return (level.getCube(x, y, z - 1) == null ? false : !(level.getCube(x, y, z - 1) == Level.airBlock));
        };

        return false;
    }

    public static Mesh genOptimizedCube(Level level, Vector3f position) {
        loadMeshData(level, position);
        return generateMesh();
    }
}

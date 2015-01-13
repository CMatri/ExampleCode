package com.base.engine.rendering.meshLoading;

import com.base.engine.core.Vector2f;
import com.base.engine.core.Vector3f;

import java.util.ArrayList;

public class IndexedModel
{
    private ArrayList<Vector3f> positions;
    private ArrayList<Vector2f> texCoords;
    private ArrayList<Vector3f> normals;
    private ArrayList<Vector3f> tangents;
    private ArrayList<Integer> indices;

    public IndexedModel()
    {
        positions = new ArrayList<Vector3f>();
        texCoords = new ArrayList<Vector2f>();
        normals = new ArrayList<Vector3f>();
        tangents = new ArrayList<Vector3f>();
        indices = new ArrayList<Integer>();
    }

    public void calcNormals()
    {
        for(int i = 0; i < indices.size(); i += 3)
        {
            int i0 = indices.get(i);
            int i1 = indices.get(i + 1);
            int i2 = indices.get(i + 2);

            Vector3f v1 = positions.get(i1).sub(positions.get(i0));
            Vector3f v2 = positions.get(i2).sub(positions.get(i0));

            Vector3f normal = v1.cross(v2).normalized();

            normals.get(i0).set(normals.get(i0).add(normal));
            normals.get(i1).set(normals.get(i1).add(normal));
            normals.get(i2).set(normals.get(i2).add(normal));
        }

        for(int i = 0; i < normals.size(); i++)
            normals.get(i).set(normals.get(i).normalized());
    }

    public void calcTangents()
    {
        for(int i = 0; i < positions.size()-2; i += 3)
        {
            Vector3f v0 = positions.get(i+0);
            Vector3f v1 = positions.get(i+1);
            Vector3f v2 = positions.get(i+2);

            Vector2f uv0 = texCoords.get(i+0);
            Vector2f uv1 = texCoords.get(i+1);
            Vector2f uv2 = texCoords.get(i+2);

            Vector3f deltaPos1 = v1.sub(v0);
            Vector3f deltaPos2 = v2.sub(v0);

            Vector2f deltaUV1 = uv1.sub(uv0);
            Vector2f deltaUV2 = uv2.sub(uv0);

            float r = 1.0f / (deltaUV1.getX() * deltaUV2.getY() - deltaUV1.getY() * deltaUV2.getX());
            Vector3f tangent = (deltaPos1.mul(deltaUV2.getY()).sub(deltaPos2.mul(deltaUV1.getY()))).mul(r);

            tangents.get(i).set(tangent.normalized());
            tangents.get(i+1).set(tangent.normalized());
            tangents.get(i+2).set(tangent.normalized());
        }
    }

    public ArrayList<Vector3f> getPositions() { return positions; }
    public ArrayList<Vector2f> getTexCoords() { return texCoords; }
    public ArrayList<Vector3f> getNormals() { return normals; }
    public ArrayList<Vector3f> getTangents() { return tangents; }
    public ArrayList<Integer> getIndices() { return indices; }
}
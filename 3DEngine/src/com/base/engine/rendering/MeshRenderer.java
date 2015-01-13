package com.base.engine.rendering;

import com.base.engine.leaves.GameLeaf;
import com.base.engine.rendering.Material;
import com.base.engine.rendering.Mesh;
import com.base.engine.rendering.RenderingEngine;
import com.base.engine.rendering.Shader;

public class MeshRenderer extends GameLeaf {
    private Mesh mesh;
    private Material material;

    public MeshRenderer(Mesh mesh, Material material) {
        this.material = material;
        this.mesh = mesh;
    }

    public void render(Shader shader, RenderingEngine renderingEngine) {
        shader.bind();
        shader.updateUniforms(getTransform(), material, renderingEngine);
        mesh.draw();
    }

    public Material getMaterial() {
        return material;
    }
}

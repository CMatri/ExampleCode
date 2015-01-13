package Engine.Lights;

import Engine.Main;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.texture.Texture;
import jme3tools.optimize.TextureAtlas;

public class MaterialManager {

    Main main;
    Material mat, projectileMat;

    public MaterialManager(Main main) {
        Texture wallTex = main.getAssetManager().loadTexture("Textures/wall.png");

        this.main = main;
        mat = new Material(main.getAssetManager(), "Common/MatDefs/Light/Lighting.j3md");
        mat.setTexture("DiffuseMap", wallTex);
        mat.setTexture("NormalMap", main.getAssetManager().loadTexture("Textures/wallnormal.png"));
        mat.setColor("Diffuse", ColorRGBA.White);
        mat.setColor("Specular", ColorRGBA.White);
//        mat.setFloat("Shininess", 10f);
        mat.setBoolean("VertexLighting", false);

        projectileMat = new Material(main.getAssetManager(), "Common/MatDefs/Misc/Unshaded.j3md");
        projectileMat.setColor("Color", ColorRGBA.White);
    }

    public Material getBlockMat() {
        return mat;
    }

    public Material getProjectileMat() {
        return projectileMat;
    }
}

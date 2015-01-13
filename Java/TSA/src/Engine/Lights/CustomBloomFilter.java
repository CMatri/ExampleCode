package Engine.Lights;

import Engine.Main;
import com.jme3.asset.AssetManager;
import com.jme3.material.Material;
import com.jme3.post.Filter;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.texture.Image;
import java.util.ArrayList;

public class CustomBloomFilter extends Filter {

    Main main;
    private float blurScale = 1.5f;
    Material vGBlur, hGBlur;
    Filter.Pass vGPass = new Filter.Pass(), hGPass = new Filter.Pass();
    float downSamplingFactor = 1;
    float screenWidth;
    float screenHeight;

    public CustomBloomFilter(Main main) {
        super("CustomBloomFilter");
        this.main = main;
    }

    @Override
    protected Material getMaterial() {
        return material;
    }

    @Override
    protected void initFilter(final AssetManager manager, final RenderManager renderManager, final ViewPort vp, final int w, final int h) {
        screenWidth = Math.max(1, (w / downSamplingFactor));
        screenHeight = Math.max(1, (h / downSamplingFactor));
        postRenderPasses = new ArrayList<Filter.Pass>();

        hGBlur = new Material(manager, "MatDefs/HGaussianBlur.j3md");
        hGBlur.setFloat("blur", blurScale / screenWidth);
        hGPass = new Filter.Pass() {
            @Override
            public boolean requiresSceneAsTexture() {
                return true;
            }
        };
        hGPass.init(renderManager.getRenderer(), (int) screenWidth, (int) screenHeight, Image.Format.RGBA8, Image.Format.Depth, 1, hGBlur);
        postRenderPasses.add(hGPass);

        vGBlur = new Material(manager, "MatDefs/VGaussianBlur.j3md");
        vGBlur.setFloat("blur", blurScale / screenHeight);
        vGBlur.setTexture("Texture", hGPass.getRenderedTexture());

        vGPass = new Filter.Pass();
        vGPass.init(renderManager.getRenderer(), (int) screenWidth, (int) screenHeight, Image.Format.RGBA8, Image.Format.Depth, 1, vGBlur);
        postRenderPasses.add(vGPass);

        material = new Material(manager, "MatDefs/BloomFilter.j3md");
        material.setTexture("BloomTex", vGPass.getRenderedTexture());

        System.out.println(screenWidth + " : " + screenHeight);
    }
}

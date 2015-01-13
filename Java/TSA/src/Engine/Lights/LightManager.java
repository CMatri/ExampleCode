package Engine.Lights;

import Engine.Main;
import com.jme3.collision.CollisionResult;
import com.jme3.collision.CollisionResults;
import com.jme3.math.Ray;
import com.jme3.math.Vector3f;
import com.jme3.post.FilterPostProcessor;
import com.jme3.post.filters.DepthOfFieldFilter;
import com.jme3.post.filters.FadeFilter;
import com.jme3.scene.Node;

public class LightManager {

    private Sun sun;
    private Node lights;
    private Main main;
    private DepthOfFieldFilter dof;
    private SimpleSSAO ssaoFilter;
    private FilterPostProcessor fpp;
    private FadeFilter fadeFilter;
    final int SHADOWMAP_SIZE = 2000;
    Vector3f origC;

    public LightManager(Main main) {
        this.main = main;

        //sun = new Sun(new Vector3f(0.5f, -0.5f, -0.5f).normalizeLocal(), main, main.getRootNode());
        //sun.setColor(ColorRGBA.LightGray.add(new ColorRGBA(0f, 0.25f, 0.38f, 1f)));

        fpp = new FilterPostProcessor(main.getAssetManager());
        main.getViewPort().addProcessor(fpp);

//        DirectionalLightShadowRenderer dlsr = new DirectionalLightShadowRenderer(main.getAssetManager(), SHADOWMAP_SIZE, 1);
//        dlsr.setLight(sun.sun);
//        main.getViewPort().addProcessor(dlsr);
//
//        DirectionalLightShadowFilter dlsf = new DirectionalLightShadowFilter(main.getAssetManager(), SHADOWMAP_SIZE, 1);
//        dlsf.setLight(sun.sun);
//        dlsf.setEnabled(true);
//        dlsf.setEdgeFilteringMode(EdgeFilteringMode.PCF8);
//        dlsf.setEdgesThickness(6);
//        dlsf.setShadowIntensity(.5f);
//        fpp.addFilter(dlsf);

//        origC = sun.getSun().getDirection();


        initSSAO();
        initDOF();
        initFade();
    }

    private void initSSAO() {
//        ssaoFilter = new SSAOFilter(0.35f, 5.5f, 5.5f, 0.02f);

        ssaoFilter = new SimpleSSAO();
        ssaoFilter.setSmoothMore(true);
        ssaoFilter.setUseSmoothing(true);
        fpp.addFilter(ssaoFilter);
    }

    private void initDOF() {
        dof = new DepthOfFieldFilter();
        dof.setBlurScale(2.0f);
        dof.setFocusRange(1.0f);
        fpp.addFilter(dof);
    }

    private void initFade() {
        fadeFilter = new FadeFilter(2);
        fpp.addFilter(fadeFilter);
    }

    public void update(float tpf) {
        Ray ray = new Ray(main.getCamera().getLocation(), main.getCamera().getDirection());
        CollisionResults collisionResults = new CollisionResults();
        int numCollisions = main.getRootNode().collideWith(ray, collisionResults);
        if (numCollisions > 0) {
            CollisionResult hit = collisionResults.getClosestCollision();
            dof.setFocusDistance(hit.getDistance() / 10.0f);
        }
    }

    public void setSSAO(boolean b) {
        ssaoFilter.setEnabled(b);
    }

    public void setDOF(boolean b) {
        dof.setEnabled(b);
    }

    public boolean ssaoEnabled() {
        return ssaoFilter.isEnabled();
    }

    public boolean dofEnabled() {
        return dof.isEnabled();
    }
    
    public void fadeIn(){
        fadeFilter.fadeIn();
    }
    
    public void fadeOut(){
        fadeFilter.fadeOut();
    }
    
    public boolean fullyFaded(){
        return fadeFilter.getValue() == 0.0f;
    }
    
    public FadeFilter getFadeFilter(){
        return fadeFilter;
    }
}

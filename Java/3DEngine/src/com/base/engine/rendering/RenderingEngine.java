package com.base.engine.rendering;

import com.base.engine.core.*;
import com.base.engine.leaves.BaseLight;
import com.base.engine.leaves.Camera;
import com.base.engine.leaves.PerspectiveCamera;
import com.base.engine.rendering.resourceManagement.MappedValues;

import java.util.ArrayList;
import java.util.HashMap;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL30.*;
import static org.lwjgl.opengl.GL32.GL_DEPTH_CLAMP;

public class RenderingEngine extends MappedValues {

    private HashMap<String, Integer> samplerMap;
    private ArrayList<BaseLight> lights;
    private BaseLight activeLight;

    private Shader forwardAmbient;
    private Shader shadowMapShader;
    private Shader nullFilter;
    private Shader gausBlurFilter;

    private Camera mainCamera;

    private Camera altCamera;
    private GameBranch altCameraBranch;
    private Transform altTransform;
    private Material altMaterial;
    private Mesh altPlane;
    private Texture altTexture;
    private Texture reflectTex;

    private Matrix4f lightMatrix;

    private static final Matrix4f biasMatrix = new Matrix4f().initScale(0.5f, 0.5f, 0.5f).mul(new Matrix4f().initTranslation(1.0f, 1.0f, 1.0f));

    private static final int numShadowMaps = 10;

    private Texture[] shadowMaps = new Texture[numShadowMaps];
    private Texture[] shadowMapTempTargets = new Texture[numShadowMaps];

    public RenderingEngine() {
        super();

        lights = new ArrayList<BaseLight>();
        samplerMap = new HashMap<String, Integer>();
        samplerMap.put("diffuse", 0);
        samplerMap.put("normalMap", 1);
        samplerMap.put("dispMap", 2);
        samplerMap.put("shadowMap", 3);
        samplerMap.put("reflectMap", 4);
        samplerMap.put("filterTexture", 0);

        setVector3f("ambient", new Vector3f(0.03f, 0.03f, 0.03f));

        forwardAmbient = new Shader("forward-ambient");
        shadowMapShader = new Shader("shadowMapGenerator");
        nullFilter = new Shader("filter-null");
        gausBlurFilter = new Shader("filter-gausBlur7x1");

        glFrontFace(GL_CW);
        glCullFace(GL_BACK);
        glEnable(GL_CULL_FACE);
        glEnable(GL_DEPTH_TEST);
        glEnable(GL_DEPTH_CLAMP);
        glEnable(GL_TEXTURE_2D);

        altTexture = new Texture(Window.getWidth(), Window.getHeight(), null, GL_TEXTURE_2D, GL_LINEAR, GL_RGBA, GL_RGBA, false, GL_COLOR_ATTACHMENT0);
        reflectTex = new Texture(Window.getWidth(), Window.getHeight(), null, GL_TEXTURE_2D, GL_LINEAR, GL_RGBA, GL_RGBA, false, GL_COLOR_ATTACHMENT0);

        altCamera = new Camera(new Matrix4f().initIdentity());
        altCameraBranch = new GameBranch().addLeaf(altCamera);
        altCamera.getTransform().rotate(new Vector3f(0, 1, 0), (float) Math.toRadians(180));
        altMaterial = new Material();
        altMaterial.setTexture("diffuse", altTexture);
        altMaterial.setFloat("specularIntensity", 1);
        altMaterial.setFloat("specularPower", 8);
        altTransform = new Transform();
        altTransform.setScale(1.0f, 1.0f, 1.0f);
        altTransform.rotate(new Vector3f(1, 0, 0), (float) Math.toRadians(90.0f));
        altTransform.rotate(new Vector3f(0, 0, 1), (float) Math.toRadians(180.0f));
        altPlane = new Mesh("plane.obj");

        lightMatrix = new Matrix4f().initScale(0, 0, 0);

        for (int i = 0; i < numShadowMaps; i++) {
            int shadowMapSize = 1 << (i + 1);
            shadowMaps[i] = new Texture(shadowMapSize, shadowMapSize, null, GL_TEXTURE_2D, GL_LINEAR_MIPMAP_NEAREST, GL_RG32F, GL_RGBA, true, GL_COLOR_ATTACHMENT0);
            shadowMapTempTargets[i] = new Texture(shadowMapSize, shadowMapSize, null, GL_TEXTURE_2D, GL_LINEAR_MIPMAP_NEAREST, GL_RG32F, GL_RGBA, true, GL_COLOR_ATTACHMENT0);
        }
    }

    public void updateUniformStruct(Transform transform, Material material, Shader shader, String uniformName, String uniformType) {
        throw new IllegalArgumentException(uniformName + " is not a supported type in Rendering Engine");
    }

    public void render(GameBranch branch) {
        glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

        Window.bindAsRenderTarget();

        glViewport(0, 0, Window.getWidth(), Window.getHeight());
        glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

        branch.renderAll(forwardAmbient, this);

        for (BaseLight light : lights) {
            activeLight = light;
            BaseLight.ShadowInfo shadowInfo = light.getShadowInfo();

            int shadowMapIndex = 0;
            if (shadowInfo != null)
                shadowMapIndex = shadowInfo.getShadowMapSizeAsPowerOf2() - 1;
            setTexture("shadowMap", shadowMaps[shadowMapIndex]);
            shadowMaps[shadowMapIndex].bindAsRenderTarget();
            glClearColor(1.0f, 0.0f, 0.0f, 0.0f);
            glClear(GL_DEPTH_BUFFER_BIT | GL_COLOR_BUFFER_BIT);

            if (shadowInfo != null) {
                altCamera.setProjection(shadowInfo.getProjection());
                BaseLight.ShadowCameraTransform shadowCameraTransform = activeLight.calcShadowCameraTransform(mainCamera.getTransform().getTransformedPos(), mainCamera.getTransform().getTransformedRot());
                altCamera.getTransform().setPos(shadowCameraTransform.pos);
                altCamera.getTransform().setRot(shadowCameraTransform.rot);

                lightMatrix = biasMatrix.mul(altCamera.getViewProjection());

                setFloat("shadowVarianceMin", shadowInfo.getVarianceMin());
                setFloat("shadowLightBleedingReduction", shadowInfo.getLightBleedReductionAmount());

                Camera temp = mainCamera;
                mainCamera = altCamera;

                if (shadowInfo.isFlipFaces())
                    glCullFace(GL_FRONT);
                branch.renderAll(shadowMapShader, this);
                if (shadowInfo.isFlipFaces())
                    glCullFace(GL_BACK);

                mainCamera = temp;

                if (shadowInfo.getShadowSoftness() != 0)
                    blurShadowMap(shadowMapIndex, shadowInfo.getShadowSoftness());
            } else {
                lightMatrix = new Matrix4f().initScale(0, 0, 0);
                setFloat("shadowVarianceMin", 0.00002f);
                setFloat("shadowLightBleedingReduction", 0.0f);
            }

            Window.bindAsRenderTarget();

            glEnable(GL_BLEND);
            glBlendFunc(GL_ONE, GL_ONE);
            glDepthMask(false);
            glDepthFunc(GL_EQUAL);

            branch.renderAll(light.getShader(), this);

            glDepthMask(true);
            glDepthFunc(GL_LESS);
            glDisable(GL_BLEND);
        }
    }

    private void applyFilter(Shader filter, Texture source, Texture dest) {
        if (source == dest) {
            Util.err("You can't pass in the same texture for applying filters!");
            System.exit(1);
        }

        if (dest == null)
            Window.bindAsRenderTarget();
        else
            dest.bindAsRenderTarget();

        setTexture("filterTexture", source);

        altCamera.setProjection(new Matrix4f().initIdentity());
        altCamera.getTransform().setPos(new Vector3f(0, 0, 0));
        altCamera.getTransform().setRot(new Quaternion(new Vector3f(0, 1, 0), (float) Math.toRadians(180)));

        Camera temp = mainCamera;
        mainCamera = altCamera;

        glClear(GL_DEPTH_BUFFER_BIT);
        filter.bind();
        filter.updateUniforms(altTransform, altMaterial, this);
        altPlane.draw();

        mainCamera = temp;

        setTexture("filterTexture", null);
    }

    private void blurShadowMap(int shadowMapIndex, float blurAmount) {
        Texture shadowMap = shadowMaps[shadowMapIndex];
        Texture tempTarget = shadowMapTempTargets[shadowMapIndex];

        setVector3f("blurScale", new Vector3f(blurAmount / (shadowMap.getWidth()), 0, 0));
        applyFilter(gausBlurFilter, shadowMap, tempTarget);

        setVector3f("blurScale", new Vector3f(0, blurAmount / (shadowMap.getHeight()), 0));
        applyFilter(gausBlurFilter, tempTarget, shadowMap);
    }

    public static String getOpenGLVersion() {
        return glGetString(GL_VERSION);
    }

    public void addLight(BaseLight light) {
        lights.add(light);
    }

    public void addCamera(Camera camera) {
        mainCamera = camera;
    }

    public BaseLight getActiveLight() {
        return activeLight;
    }

    public Camera getMainCamera() {
        return mainCamera;
    }

    public Matrix4f getLightMatrix() {
        return lightMatrix;
    }

    public void setMainCamera(Camera mainCamera) {
        this.mainCamera = mainCamera;
    }

    public int getSamplerSlot(String samplerName) {
        return samplerMap.get(samplerName);
    }

    public void setClearColor(float r, float g, float b, float a) {
        glClearColor(r, g, b, a);
    }
}

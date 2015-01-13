package com.base.game;

import com.base.engine.core.*;
import com.base.engine.leaves.*;
import com.base.engine.rendering.*;
import com.base.engine.rendering.Window;
import com.base.engine.rendering.resourceManagement.RenderingMode;

import java.awt.*;
import java.awt.image.BufferedImage;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL30.GL_COLOR_ATTACHMENT0;

public class TestGame extends Game {

    private GameBranch planeObject;

    private PointLight pointLight, pointLight2, pointLight3;
    private SpotLight spotLight;
    private PerspectiveCamera camera;
    private GameBranch lP1 = new GameBranch();
    private GameBranch lP2 = new GameBranch();
    private GameBranch lP3 = new GameBranch();
    private GameBranch lS1 = new GameBranch();

    private GameBranch monkeyBranch, bunnyBranch;
    private DirectionalLight directionalLight;

    private Mesh monkeyMesh;

    private GameBranch boxBranch;

    @Override
    public void init() {
        MaterialBag.instantiate();

        float fieldDepth = 10.0f;
        float fieldWidth = 10.0f;

//		@formatter:off
        Vertex[] vertices = new Vertex[]{new Vertex(new Vector3f(-fieldWidth, 0.0f, -fieldDepth), new Vector2f(0.0f, 0.0f)),
                new Vertex(new Vector3f(-fieldWidth, 0.0f, fieldDepth), new Vector2f(0.0f, 1.0f)),
                new Vertex(new Vector3f(fieldWidth, 0.0f, -fieldDepth), new Vector2f(1.0f, 0.0f)),
                new Vertex(new Vector3f(fieldWidth, 0.0f, fieldDepth), new Vector2f(1.0f, 1.0f))};

//		  Vertex[] vertices = new Vertex[]{new Vertex(new Vector3f(-fieldWidth, 0.0f, -fieldDepth), new Vector2f(0.0f, 0.0f)),
//                  new Vertex(new Vector3f(-fieldWidth, 0.0f, fieldDepth * 3), new Vector2f(0.0f, 1.0f)),
//                  new Vertex(new Vector3f(fieldWidth * 3, 0.0f, -fieldDepth), new Vector2f(1.0f, 0.0f)),
//                  new Vertex(new Vector3f(fieldWidth * 3, 0.0f, fieldDepth * 3), new Vector2f(1.0f, 1.0f))};

        int indices[] = new int[]{0, 1, 2,
                2, 1, 3};

        Vertex[] vertices2 = new Vertex[]{new Vertex(new Vector3f(-fieldWidth / 10, 0.0f, -fieldDepth / 10), new Vector2f(0.0f, 0.0f)),
                new Vertex(new Vector3f(-fieldWidth / 10, 0.0f, fieldDepth / 10 * 3), new Vector2f(0.0f, 1.0f)),
                new Vertex(new Vector3f(fieldWidth / 10 * 3, 0.0f, -fieldDepth / 10), new Vector2f(1.0f, 0.0f)),
                new Vertex(new Vector3f(fieldWidth / 10 * 3, 0.0f, fieldDepth / 10 * 3), new Vector2f(1.0f, 1.0f))};

        int indices2[] = new int[]{0, 1, 2,
                2, 1, 3};
//		@formatter:on

        camera = new PerspectiveCamera((float) Math.toRadians(70.0f), (float) Window.getWidth() / (float) Window.getHeight(), 0.01f, 1000.0f);

        Material crackedBrickMaterial = MaterialBag.crackedBrick;
        Material brickMaterial = MaterialBag.brick;
        Material brickMaterial2 = MaterialBag.brick2;

        monkeyMesh = new Mesh("monkey.obj");
        Mesh bunnyMesh = new Mesh("bunny.obj");
        Mesh mesh = new Mesh(vertices, indices, true);
        Mesh mesh2 = new Mesh(vertices2, indices2, true);
        Mesh box = new Mesh("cube.obj");

        MeshRenderer planeRenderer = new MeshRenderer(mesh, crackedBrickMaterial);
        MeshRenderer smallPlaneRenderer = new MeshRenderer(mesh2, brickMaterial);
        MeshRenderer bunnyRenderer = new MeshRenderer(bunnyMesh, crackedBrickMaterial);
        MeshRenderer monkeyRenderer = new MeshRenderer(monkeyMesh, MaterialBag.marble);
        MeshRenderer boxRenderer = new MeshRenderer(box, brickMaterial);

        planeObject = new GameBranch();
        planeObject.addLeaf(planeRenderer);
        //planeObject.getTransform().getPos().set(0, -1, 5);

        GameBranch directionalLightBranch = new GameBranch();
        directionalLight = new DirectionalLight(new Vector3f(1, 1, 1), 0.4f, 10, 80, 1.0f, 0.5f);
        directionalLightBranch.addLeaf(directionalLight);

        GameBranch smallPlaneBranch = new GameBranch().addLeaf(smallPlaneRenderer);
        smallPlaneBranch.getTransform().setScale(0.5f, 0.5f, 0.5f);
        smallPlaneBranch.getTransform().rotate(new Vector3f(1, 0, 0), 90f);
        bunnyBranch = new GameBranch().addLeaf(bunnyRenderer);
        monkeyBranch = new GameBranch().addLeaf(monkeyRenderer);

        boxBranch = new GameBranch().addLeaf(boxRenderer);

        pointLight = new PointLight(new Vector3f(0, 1, 0), 0.4f, new Attenuation(0, 0, 1));
        pointLight2 = new PointLight(new Vector3f(0, 1, 1), 0.4f, new Attenuation(0, 0, 1));
        pointLight3 = new PointLight(new Vector3f(1, 1, 0), 0.4f, new Attenuation(0, 0, 1));
        spotLight = new SpotLight(new Vector3f(1, 1, 1), 0.4f, new Attenuation(0, 0, 0.02f), 1.0f, 8, 1.0f, 0.5f);

        lP1.addLeaf(pointLight);
        lP2.addLeaf(pointLight2);
        lP3.addLeaf(pointLight3);
        lS1.addLeaf(spotLight);

        spotLight.getTransform().getPos().set(-1.5f, 4f, 2);
        spotLight.getTransform().setRot(new Quaternion(new Vector3f(0, 1, 0), (float) Math.toRadians(91.1f)));
        spotLight.getTransform().rotate(new Vector3f(0, 0, 1), (float) Math.toRadians(-50.0f));

        addObject(lS1);
//        addObject(lP1);
//        addObject(lP2);
//        addObject(lP3);
        addObject(boxBranch);
        addObject(smallPlaneBranch);
        addObject(new GameBranch().addLeaf(camera).addLeaf(new FlyLook(0.10f, Input.KEY_ESCAPE, true)).addLeaf(new FlyMove(10)));
        addObject(monkeyBranch.addLeaf(new LookAtAutomator(camera).setSpeed(2.5f)).addLeaf(new FlyFollow(camera, 0.25f, 5, 10)));
        addObject(bunnyBranch);
        addObject(planeObject);
        addObject(directionalLightBranch);

        boxBranch.getTransform().getPos().set(new Vector3f(3, 4, 2));
        boxBranch.getTransform().setRot(new Quaternion(new Vector3f(0, 1, 0), (float) Math.toRadians(45)));

        directionalLight.getTransform().setRot(new Quaternion(new Vector3f(1, 0, 0), (float) Math.toRadians(-45)));

        pointLight.getTransform().getPos().set(5, 5, 7);
        pointLight2.getTransform().getPos().set(8, 0, 5);
        pointLight3.getTransform().getPos().set(11, 0, 5);

        smallPlaneBranch.getTransform().setRot(new Quaternion(new Vector3f(0, 1, 0), (float) Math.toRadians(45)));
        smallPlaneBranch.getTransform().getPos().set(1, 2.5f, 1);
        bunnyBranch.getTransform().getPos().set(6.0f, 0.3f, 5);
        bunnyBranch.getTransform().setRot(new Quaternion(new Vector3f(0, 1, 0), (float) Math.toRadians(90)));
        bunnyBranch.getTransform().setScale(1.2f, 1.2f, 1.2f);
        monkeyBranch.getTransform().getPos().set(9.5f, 1.0f, 4.5f);
    }

    private float r = 0f;

    @Override
    public void update(float delta) {
        super.update(delta);
        r += delta;

        pointLight2.getTransform().getPos().set(8, 0, (float) (Math.sin(r) * Math.PI) + 5);
        pointLight3.getTransform().getPos().set(11, 0, (float) (Math.cos(r) * Math.PI) + 5);
        pointLight.getTransform().getPos().set(2, (float) -(Math.sin(r) * Math.PI) + 3f, 7);

        if (follow) {
            spotLight.getTransform().getPos().set(spotLight.getTransform().getPos().lerp(camera.getTransform().getPos().add(camera.getTransform().getRot().getForward().div(2)), delta * 5));//15, -0.655f, (float) (Math.cos(r) * Math.PI) + 5);
            spotLight.getTransform().setRot(spotLight.getTransform().getRot().nlerp(camera.getTransform().getRot(), delta * 17.5f, true));
        }
    }

    boolean follow = false;

    @Override
    public void input(float delta) {
        super.input(delta);
        if (Input.getKeyDown(Input.KEY_K))
            follow = !follow;
        if (Input.getKeyDown(Input.KEY_J)) {
            if (monkeyMesh.renderingMode() == RenderingMode.FULL)
                monkeyMesh.setRenderingMode(RenderingMode.LINES);
            else if (monkeyMesh.renderingMode() == RenderingMode.LINES)
                monkeyMesh.setRenderingMode(RenderingMode.POINTS);
            else if (monkeyMesh.renderingMode() == RenderingMode.POINTS)
                monkeyMesh.setRenderingMode(RenderingMode.FULL);
        }
        if (Input.getKey(Input.KEY_LEFT)) {
            directionalLight.getTransform().rotate(new Vector3f(1, 0, 0), (float) Math.toRadians(2f));
        } else if (Input.getKey(Input.KEY_RIGHT)) {
            directionalLight.getTransform().rotate(new Vector3f(-1, 0, 0), (float) Math.toRadians(2f));
        }
        if (Input.getKey(Input.KEY_UP)) {
            directionalLight.getTransform().rotate(new Vector3f(0, 0, 1), (float) Math.toRadians(2));
        } else if (Input.getKey(Input.KEY_DOWN)) {
            directionalLight.getTransform().rotate(new Vector3f(0, 0, -1), (float) Math.toRadians(2));
        }
    }
}

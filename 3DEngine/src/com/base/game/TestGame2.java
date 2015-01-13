package com.base.game;

import com.base.engine.core.*;
import com.base.engine.leaves.*;
import com.base.engine.rendering.*;
import com.base.engine.rendering.resourceManagement.RenderingMode;

import java.util.Random;

public class TestGame2 extends Game {

	private GameBranch planeObject;

	private PointLight pointLight, pointLight2, pointLight3;
	private SpotLight spotLight;
	private OrthographicCamera camera;
	private GameBranch lP1 = new GameBranch();
	private GameBranch lP2 = new GameBranch();
	private GameBranch lP3 = new GameBranch();
	private GameBranch lS1 = new GameBranch();

	private GameBranch monkeyBranch, bunnyBranch;
	private DirectionalLight directionalLight;

	private Mesh monkeyMesh;

	private Random rand;

	@Override
	public void init() {
		rand = new Random();

		MaterialBag.instantiate();

		float fieldDepth = 10.0f;
		float fieldWidth = 10.0f;

//		@formatter:off
        Vertex[] vertices = new Vertex[]{new Vertex(new Vector3f(-fieldWidth, 0.0f, -fieldDepth), new Vector2f(0.0f, 0.0f)),
                new Vertex(new Vector3f(-fieldWidth, 0.0f, fieldDepth * 3), new Vector2f(0.0f, 10.0f)),
                new Vertex(new Vector3f(fieldWidth * 3, 0.0f, -fieldDepth), new Vector2f(10.0f, 0.0f)),
                new Vertex(new Vector3f(fieldWidth * 3, 0.0f, fieldDepth * 3), new Vector2f(10.0f, 10.0f))};

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

		camera = new OrthographicCamera(0, (float) Window.getWidth(), (float) Window.getHeight(), 0, 0.01f, 1000.0f);

		Material brickMaterial2 = MaterialBag.brick2;

		Mesh mesh = new Mesh(vertices, indices, true);

		MeshRenderer planeRenderer = new MeshRenderer(mesh, brickMaterial2);

		planeObject = new GameBranch();
		planeObject.addLeaf(planeRenderer);
		planeObject.getTransform().getPos().set(0, 0, 0);
		planeObject.getTransform().setScale(10, 10, 10);
//		planeObject.getTransform().getRot().add(new Quaternion(new Vector3f(1, 0, 0), 90));

		GameBranch directionalLightBranch = new GameBranch();
		directionalLight = new DirectionalLight(new Vector3f(1, 1, 1), 0.4f);
		directionalLightBranch.addLeaf(directionalLight);

		addObject(planeObject);
		addObject(directionalLightBranch);
		addObject(new GameBranch().addLeaf(camera).addLeaf(new ScrollMove()));
	}

	@Override
	public void update(float delta) {
		super.update(delta);
	}

	@Override
	public void input(float delta) {
		super.input(delta);
	}
}

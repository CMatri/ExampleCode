package com.base.engine.rendering;

public class MaterialBag {
	public static Material crackedBrick;
	public static Material brick;
    public static Material brick2;
    public static Material marble;

	public static void instantiate() {
		crackedBrick = new Material();
		crackedBrick.setTexture("diffuse", new Texture("swall1.jpg"));
		crackedBrick.setTexture("normalMap", new Texture("swall1_normal.jpg"));
		crackedBrick.setTexture("dispMap", new Texture("swall1_height.jpg"));
		crackedBrick.setFloat("specularIntensity", 2);
		crackedBrick.setFloat("specularPower", 80);

		float offset = 0.0f;
		float scale = 0.02f;
		float baseBias = scale / 2.0f;
		crackedBrick.setFloat("dispScale", scale);
		crackedBrick.setFloat("dispBias", -baseBias + baseBias * offset);

		brick = new Material();
		brick.setTexture("diffuse", new Texture("bricks2.jpg"));
		brick.setTexture("normalMap", new Texture("bricks2_normal.jpg"));
		brick.setTexture("dispMap", new Texture("bricks2_disp.jpg"));
		brick.setFloat("specularIntensity", 2);
		brick.setFloat("specularPower", 8);
		float offset1 = -0.4f;
		float scale1 = 0.05f;
		float baseBias1 = scale1 / 2.0f;
		brick.setFloat("dispScale", scale1);
		brick.setFloat("dispBias", -baseBias1 + baseBias1 * offset1);

		brick2 = new Material();
		brick2.setTexture("diffuse", new Texture("bricks.jpg"));
		brick2.setTexture("normalMap", new Texture("bricks_normal.jpg"));
		brick2.setTexture("dispMap", new Texture("bricks_disp.png"));
		brick2.setFloat("specularIntensity", 1);
		brick2.setFloat("specularPower", 8);
        brick2.setFloat("reflect", 1);
		float offset2 = -0.4f;
		float scale2 = 0.06f;
		float baseBias2 = scale2 / 2.0f;
		brick2.setFloat("dispScale", scale1);
		brick2.setFloat("dispBias", -baseBias2 + baseBias2 * offset2);

        marble = new Material();
        marble.setTexture("diffuse", new Texture("marble.png"));
        marble.setFloat("specularIntensity", 1);
        marble.setFloat("specularPower", 8);
        marble.setFloat("dispScale", scale1);
        marble.setFloat("dispBias", -baseBias2 + baseBias2 * offset2);
	}
}

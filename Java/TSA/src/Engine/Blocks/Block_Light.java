package Engine.Blocks;

import Engine.Balls.PlayerBall;
import Engine.Levels.Level;
import Engine.Main;
import Engine.Utils.Util;
import com.jme3.light.PointLight;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;

public class Block_Light extends BlockTransparent {

    PlayerBall ball;
    PointLight light;
    float radius;
    Main main;

    public Block_Light(Main main, int x, int y, int z, Level level, boolean creating, ColorRGBA lightColor, float radius) {
        super(main, new Vector3f(x, y, z), level, new ColorRGBA(0, 0, 0, 0), "Light", creating, Level.lightBlockFactory);
        this.radius = radius;
        light = new PointLight();
        light.setPosition(getPosition().add(0, 0.5f, 0));
        light.setRadius(radius);
        light.setColor(lightColor);
        this.main = main;
        main.getRootNode().addLight(light);
    }

    public void setLightColor(ColorRGBA color) {
        light.setColor(color);
    }

    public ColorRGBA getLightColor() {
        return light.getColor();
    }

    public float getRadius() {
        return light.getRadius();
    }

    public void setRadius(float radius) {
        light.setRadius(radius);
    }

    public PointLight getLight() {
        return light;
    }

    public void kill() {
        dead = true;
        if (main.isEditor()) {
            fullDead = true;
        }
        main.getRootNode().removeLight(light);
    }

    public void turnOff() {
        light.setRadius(0);
    }

    public void turnOn() {
        light.setRadius(radius);
    }

    public void update() {
        super.update();
    }

    public void collideWithBall(PlayerBall ball) {
        super.collideWithBall(ball);

    }
}

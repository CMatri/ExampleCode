package Engine.Balls;

import AppStates.EditorState;
import AppStates.GameState;
import Engine.Main;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.shape.Box;
import com.jme3.util.TangentBinormalGenerator;

public class ProjectileBall extends RigidBodyControl {

    private Vector3f direction;
    private float mass, lifeBegin = 0, lifeEnd;
    private Geometry ballGeom;
    private ColorRGBA ballColor;
    private Box ballSphere;
    private Main main;
    private String name = "Projectile";
    //Trying box, originally Sphere

    public ProjectileBall(Main main, Vector3f position, Vector3f direction, float mass, float life) {
        this(main, position, direction, mass, life, ColorRGBA.White);
    }

    public ProjectileBall(Main main, Vector3f position, Vector3f direction, float mass, float life, ColorRGBA color) {
        this.direction = direction;
        this.mass = mass;
        this.main = main;
        this.lifeEnd = life;
        this.ballColor = color;

        Material ballMat = main.getMaterialManager().getBlockMat().clone();
        ballMat.setBoolean("UseMaterialColors", true);
        ballMat.setColor("Diffuse", ballColor);


//        ballSphere = new Sphere(10, 10, .1f);
//        ballSphere.setTextureMode(Sphere.TextureMode.Polar);
        ballSphere = new Box(0.1f, 0.1f, 0.1f);

        ballGeom = new Geometry(name, ballSphere);
        ballGeom.setMaterial(ballMat);
        ballGeom.addControl(this);
        ballGeom.setLocalTranslation(position);
        TangentBinormalGenerator.generate(ballGeom);

        main.getRootNode().attachChild(ballGeom);

        setPhysicsLocation(position);
        setMass(mass);
//        setGravity(new Vector3f(0, -9.8f, 0));
        setAngularDamping(.8f);
        setGravity(new Vector3f(0, -0.1f, 0));

        if (!main.isEditor()) {
            main.getStateManager().getState(GameState.class).getBulletAppState().getPhysicsSpace().add(this);
        } else {
            main.getStateManager().getState(EditorState.class).getBulletAppState().getPhysicsSpace().add(this);
        }

        super.applyImpulse(direction, position);
    }

    public void update(float tpf) {
        super.update(tpf);
        if (lifeBegin >= lifeEnd) {
            if (!main.isEditor()) {
                main.getStateManager().getState(GameState.class).getBulletAppState().getPhysicsSpace().remove(this);
                main.getRootNode().detachChild(ballGeom);
            } else {
                main.getStateManager().getState(EditorState.class).getBulletAppState().getPhysicsSpace().remove(this);
                main.getRootNode().detachChild(ballGeom);
            }
        } else {
            if (!main.isEditor()) {
                if (!main.getStateManager().getState(GameState.class).getGameGuiController().isPaused()) {
                    lifeBegin++;
                }
            } else {
                lifeBegin++;
            }
        }
    }

    public void kill() {
    }
}

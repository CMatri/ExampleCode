package Engine.Balls;

import AppStates.GameState;
import AppStates.MainMenuState;
import Engine.Blocks.BlockSuper;
import Engine.Main;
import Engine.Utils.CustomBox;
import Engine.Utils.CustomChaseCamera;
import Engine.Utils.GlowingTextRenderer;
import Engine.Utils.SoundManager;
import Engine.Utils.Util;
import com.jme3.asset.AssetManager;
import com.jme3.bullet.collision.PhysicsCollisionEvent;
import com.jme3.bullet.collision.PhysicsCollisionListener;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.light.PointLight;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.FastMath;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.renderer.queue.RenderQueue;
import com.jme3.renderer.queue.RenderQueue.Bucket;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Sphere;

public abstract class PlayerBall extends RigidBodyControl implements PhysicsCollisionListener {

    Sphere player;
    Geometry ball_geo, electricity;
    Vector3f restartPos;
    private float mass = 1f;
    private float size = .4f;
    private boolean fireInvuln = false;
    private boolean floats = false;
    private float speed = 3f;
    private boolean jumping = false;
    private ColorRGBA lightColor = ColorRGBA.White;
    private boolean onGround = false;
    private String name;
    private Vector3f position;
    private boolean isTrampolining, collisionEnabled = true, deathByFalling = false;
    private float origDamping, origMass, bounce;
    AssetManager assetManager;
    PointLight playerLight;
    Node playerNode;
    Main main;
    private boolean dead = false, restarting = false, restartingBeginning = false;
    private Quaternion defaultRotation;
    private CustomChaseCamera chaseCamera;
    private float rotation = FastMath.DEG_TO_RAD * 180;
    private float startDeathCount = 0, endDeathCount = 2;

    public PlayerBall(Main main, Vector3f restartPos) {
        this.main = main;
        main.setActivePlayer(this);
        this.assetManager = main.getAssetManager();
        this.position = new Vector3f(restartPos.x, restartPos.y, restartPos.z);
        this.restartPos = new Vector3f(restartPos.x, restartPos.y, restartPos.z);
    }

    public void create() {
        if (main.isMainMenu()) {
            main.getStateManager().getState(MainMenuState.class).getBulletAppState().getPhysicsSpace().addCollisionListener(this);
        } else {
            main.getStateManager().getState(GameState.class).getBulletAppState().getPhysicsSpace().addCollisionListener(this);
        }
        playerNode = new Node("playerNode");

        assetManager = main.getAssetManager();

        player = new Sphere(30, 30, size);
        player.setTextureMode(Sphere.TextureMode.Polar);
        ball_geo = new Geometry(name, player);
        ball_geo.setShadowMode(RenderQueue.ShadowMode.Off);

        Material mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        mat.setColor("Color", ColorRGBA.White);

        ball_geo.setMaterial(mat);

        main.getRootNode().attachChild(playerNode);

        ball_geo.setLocalTranslation(position);

        ball_geo.addControl(this);
        setMass(mass);
        setRestitution(bounce);
        origMass = mass;
        setGravity(new Vector3f(0, -9.8f, 0));
        setAngularDamping(.8f);
        origDamping = getAngularDamping();
        if (!main.isMainMenu()) {
            main.getStateManager().getState(GameState.class).getBulletAppState().getPhysicsSpace().add(this);
        } else {
            main.getStateManager().getState(MainMenuState.class).getBulletAppState().getPhysicsSpace().add(this);
        }
        Material mate = assetManager.loadMaterial("ShaderBlow/Materials/Electricity/electricity3_line3.j3m");
        electricity = new Geometry("electrified_" + name);
        electricity.setQueueBucket(Bucket.Translucent);
        electricity.setMesh(ball_geo.getMesh());
        electricity.setLocalScale(electricity.getLocalScale().add(.11f, .11f, .11f));
        electricity.setMaterial(mate);
        playerNode.attachChild(electricity);
        playerNode.attachChild(ball_geo);

        playerLight = new PointLight();
        playerLight.setPosition(position.add(0, 0.85f, 0));
        playerLight.setRadius(8);
        playerLight.setColor(lightColor);
        main.getRootNode().addLight(playerLight);

        if (!main.isEditor()) {
            setUpCamera();
        }
    }

    public void setUpCamera() {
        main.getFlyByCamera().setEnabled(false);
        chaseCamera = new CustomChaseCamera(main, main.getCamera(), ball_geo, main.getInputManager());
        chaseCamera.setSmoothMotion(false);
        chaseCamera.setMinDistance(5f);
        chaseCamera.setDragToRotate(false);
        chaseCamera.setMaxZoom(17);
        chaseCamera.setDefaultDistance(15);
        chaseCamera.setDefaultHorizontalRotation(FastMath.DEG_TO_RAD * 180);
        chaseCamera.setTrailingEnabled(false);
        chaseCamera.setEnabled(true);
        chaseCamera.setUpVector(Vector3f.UNIT_Y);
    }

    public void releaseMouse() {
        rotation = chaseCamera.getVerticalRotation();
        chaseCamera.setEnabled(false);
        main.getInputManager().setCursorVisible(true);
    }

    public void captureMouse() {
        chaseCamera.setEnabled(true);
        chaseCamera.setSmoothMotion(false);
        chaseCamera.setMinDistance(5f);
        chaseCamera.setDragToRotate(false);
        chaseCamera.setMaxZoom(17);
        chaseCamera.setDefaultDistance(15);
        chaseCamera.setDefaultHorizontalRotation(FastMath.DEG_TO_RAD * 180);
        chaseCamera.setTrailingEnabled(false);
        chaseCamera.setEnabled(true);
        chaseCamera.setUpVector(Vector3f.UNIT_Y);
        main.getInputManager().setCursorVisible(false);
    }
    float time = System.currentTimeMillis();

    public void collision(PhysicsCollisionEvent event) {
        if (!main.isMainMenu()) {
            if (!main.isEditor()) {
                if (collisionEnabled) {
                    if (!event.getNodeA().getName().equals("Projectile") && !event.getNodeB().getName().equals("Projectile")) {
                        if (!event.getNodeA().getName().equals(name)) {
                            setIsTrampolining(false);
                            BlockSuper b = ((CustomBox) (main.getStateManager().getState(GameState.class).getCurrMap().getLevel().getNode().getChild(event.getNodeA().getName()))).getFather();
                            b.collideWithBall(this);
                        } else if (!event.getNodeB().getName().equals(name)) {
                            setIsTrampolining(false);
                            BlockSuper b = ((CustomBox) (main.getStateManager().getState(GameState.class).getCurrMap().getLevel().getNode().getChild(event.getNodeB().getName()))).getFather();
                            b.collideWithBall(this);
                        }
                    } else {
                        if (event.getNodeA().getName().equals(name)) {
                            kill(false);
                        } else if (event.getNodeB().getName().equals(name)) {
                            kill(false);
                        }
                    }
                }
            }
        }
    }

    public RigidBodyControl getPhysicsBody() {
        return this;
    }
    private float max = 1.5f, add = 0f, range = 2.0f;

    public void update(float tpf) {
        super.update(tpf);
        this.position = ball_geo.getLocalTranslation();
        playerLight.setPosition(position.add(0, 0.85f, 0));
        electricity.setLocalTranslation(position);
        electricity.setLocalRotation(ball_geo.getLocalRotation());

        if (dead && !main.getStateManager().getState(GameState.class).getGameGuiController().isPaused()) {
            dead = false;
            restarting = true;
            restartingBeginning = true;
            main.getLightManager().fadeOut();
        }
        if (restarting && !main.getStateManager().getState(GameState.class).getGameGuiController().isPaused()) {
            if (!deathByFalling) {
                main.getStateManager().getState(GameState.class).getBulletAppState().setEnabled(false);
            }
//            main.getCamera().setLocation(FastMath.interpolateLinear(5f * tpf, main.getCamera().getLocation(), main.getCamera().getLocation().subtract(main.getCamera().getDirection()).add(position.divide(30).negate())));
//            setGeometryPosition(FastMath.interpolateLinear(1f * tpf, position, restartPos));

            if (restartingBeginning) {
                main.getCamera().lookAt(position, Vector3f.UNIT_Y);
                if (main.getLightManager().fullyFaded()) {
                    main.getStateManager().getState(GameState.class).getBulletAppState().setEnabled(true);
                    main.getLightManager().fadeIn();
                    restartingBeginning = false;
                    restart();
                    restarting = false;
                    captureMouse();
                    deathByFalling = false;
                }
            }

        } else {
            if (position.y < -1 && !main.getStateManager().getState(GameState.class).getCurrMap().getLevel().isCompleted()) {
                chaseCamera.setEnabled(false);
                main.getCamera().lookAt(position, Vector3f.UNIT_Y);
                if (position.y < -10) {
                    kill(true);
                }
            } else if (position.y < -1 && main.getStateManager().getState(GameState.class).getCurrMap().getLevel().isCompleted()) {
                chaseCamera.setEnabled(false);
                main.getCamera().lookAt(position, Vector3f.UNIT_Y);
                if (position.y < 10 && !main.getStateManager().getState(GameState.class).isChangingLevels()) {
                    main.getStateManager().getState(GameState.class).goToNextLevel(true);
                }
            }
        }
    }

    public void kill(boolean deathByFalling) {
        dead = true;
        this.deathByFalling = deathByFalling;
    }

    public void restart() {
        setAngularVelocity(new Vector3f(0, 0, 0));
        setLinearVelocity(new Vector3f(0, 0, 0));
        setPosition(restartPos);
    }

    public void setRestartPos(Vector3f pos) {
        this.restartPos = pos;
        new GlowingTextRenderer(main, "CheckPoint Set!", main.getViewPort().getCamera().getWidth(), 550).kill(100);
    }

    public void setPosition(Vector3f pos) {
        setPhysicsLocation(restartPos);
    }

    public void setGeometryPosition(Vector3f pos) {
        ball_geo.setLocalTranslation(pos);
        electricity.setLocalTranslation(pos);
    }

    public Vector3f getPosition() {
        return ball_geo.getLocalTranslation();
    }

    public float getMass() {
        return mass;
    }

    public void setMass(float mass) {
        this.mass = mass;
    }

    public float getSize() {
        return size;
    }

    public void setSize(float size) {
        this.size = size;
    }

    public boolean isFireInvuln() {
        return fireInvuln;
    }

    public void setFireInvuln(boolean fireInvuln) {
        this.fireInvuln = fireInvuln;
    }

    public boolean canFloat() {
        return floats;
    }

    public void setCanFloat(boolean floats) {
        this.floats = floats;
    }

    public ColorRGBA getLightColor() {
        return lightColor;
    }

    public void setLightColor(ColorRGBA lightColor) {
        this.lightColor = lightColor;
    }

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public boolean isOnGround() {
        return onGround;
    }

    public void setOnGround(boolean onGround) {
        this.onGround = onGround;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public PointLight getLight() {
        return playerLight;
    }

    public boolean onGround() {
        return onGround;
    }

    public CustomChaseCamera getCamera() {
        return chaseCamera;
    }

    public boolean isTrampolining() {
        return isTrampolining;
    }

    public void setIsTrampolining(boolean isTrampolining) {
        this.isTrampolining = isTrampolining;
    }

    public boolean isDead() {
        return dead;
    }

    public float getOrigDamping() {
        return origDamping;
    }

    public float getOrigMass() {
        return origMass;
    }

    public void setBounce(float b) {
        this.bounce = b;
    }

    public void setCollisionEnabled(boolean enabled) {
        this.collisionEnabled = enabled;
    }

    public boolean isJumping() {
        return jumping;
    }

    public void setJumping(boolean jumping) {
        this.jumping = jumping;
    }
}
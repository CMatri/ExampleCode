package Engine.Blocks;

import AppStates.EditorState;
import AppStates.GameState;
import AppStates.MainMenuState;
import Engine.Actions.Action;
import Engine.Actions.DisplayTextAction;
import Engine.Balls.PlayerBall;
import Engine.Levels.Level;
import Engine.Levels.LevelOptimizer;
import Engine.Main;
import Engine.Utils.CustomBox;
import Engine.Utils.Util;
import com.jme3.asset.AssetManager;
import com.jme3.bullet.collision.shapes.BoxCollisionShape;
import com.jme3.bullet.control.GhostControl;
import com.jme3.bullet.control.PhysicsControl;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.material.Material;
import com.jme3.material.RenderState;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.renderer.queue.RenderQueue;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Box;
import com.jme3.util.TangentBinormalGenerator;
import java.util.ArrayList;

public class BlockSuper {

    private Material blockMat;
    private Box box;
    private Vector3f position;
    private Main main;
    private CustomBox boxGeom;
    private AssetManager assetManager;
    private String id;
    private Node parent;
    private ColorRGBA blockColor = ColorRGBA.White;
    private ArrayList<Action> actions;
    private String name;
    boolean dead = false, fullDead = false, grow = false, fullGrow = false, creating, solid;
    private Vector3f center;
    private RigidBodyControl solidPhy;
    private GhostControl ghostPhy;
    private BlockFactory factory;

    public BlockSuper(Main main, Vector3f position, Level level, String id, boolean creating, BlockFactory factory) {
        this(main, position, level, ColorRGBA.White, id, creating, true, factory);
    }

    public BlockSuper(Main main, Vector3f position, Level level, String id, boolean creating, boolean solid, BlockFactory factory) {
        this(main, position, level, ColorRGBA.White, id, creating, solid, factory);
    }

    public BlockSuper(Main main, Vector3f position, Level level, ColorRGBA color, String id, boolean creating, boolean solid, BlockFactory factory) {
        this(main, position, level, color, new Vector3f(0.5f, 0.5f, 0.5f), id, creating, solid, factory);
    }

    public BlockSuper(Main main, Vector3f position, Level level, ColorRGBA color, Vector3f dimensions, String id, boolean creating, boolean solid, BlockFactory factory) {
        this.main = main;
        this.creating = creating;
        this.assetManager = main.getAssetManager();
        this.position = position;
        this.actions = new ArrayList<Action>();
        this.id = id;
        this.factory = factory;
        this.solid = solid;
        if (solid) {
            this.solidPhy = new RigidBodyControl(0);
        } else {
            this.ghostPhy = new GhostControl(new BoxCollisionShape(new Vector3f(main.getBlockSize(), main.getBlockSize(), main.getBlockSize())));
        }

        if (id.equals("Air")) {
            return;
        }

        if (solid) {
            blockMat = main.getBlockMaterial().clone();
            blockMat.setColor("Diffuse", color);
            blockMat.setColor("Specular", color);
            blockMat.setBoolean("UseMaterialColors", true);
            box = new Box(dimensions.x, dimensions.y, dimensions.z);
            name = "Box" + position.toString();
            boxGeom = new CustomBox();
            boxGeom.setData(name, box);
            if (color.a <= 0.0f) {
                boxGeom.setCullHint(Spatial.CullHint.Always);
                boxGeom.setBatchHint(Spatial.BatchHint.Never);
            } else if (color.a < 1 && color.a > 0) {
                boxGeom.setQueueBucket(RenderQueue.Bucket.Transparent);
                blockMat.getAdditionalRenderState().setBlendMode(RenderState.BlendMode.Alpha);
            }
            boxGeom.setLocalTranslation(position.x - dimensions.x / 45, position.y + dimensions.y, position.z - dimensions.z / 45);
            center = new Vector3f(position.x, position.y, position.z);
            boxGeom.setMaterial(blockMat);
            boxGeom.addControl(solidPhy);
            level.getBatchNode().attachChild(boxGeom);
            parent = level.getBatchNode();
            TangentBinormalGenerator.generate(boxGeom);
        } else {
            blockMat = main.getBlockMaterial().clone();
            blockMat.setColor("Diffuse", color);
            blockMat.setColor("Specular", color);
            blockMat.setBoolean("UseMaterialColors", true);
            name = "Box" + position.toString();
            box = new Box(dimensions.x, dimensions.y, dimensions.z);
            boxGeom = new CustomBox();
            boxGeom.setData(name, box);
            if (color.a <= 0.0f) {
                boxGeom.setCullHint(Spatial.CullHint.Always);
                boxGeom.setBatchHint(Spatial.BatchHint.Never);
            } else if (color.a < 1 && color.a > 0) {
                boxGeom.setQueueBucket(RenderQueue.Bucket.Transparent);
                blockMat.getAdditionalRenderState().setBlendMode(RenderState.BlendMode.Alpha);
            }
            boxGeom.setLocalTranslation(position.x - dimensions.x / 45, position.y + dimensions.y, position.z - dimensions.z / 45);
            center = new Vector3f(position.x, position.y, position.z);
            boxGeom.setMaterial(blockMat);
            boxGeom.addControl(ghostPhy);
            level.getBatchNode().attachChild(boxGeom);
            parent = level.getBatchNode();
            TangentBinormalGenerator.generate(boxGeom);
        }

        if (!creating) {
            growFirst = false;
            boxGeom.scale(.01f, .01f, .01f);
            grow();
        }
        setUserData();
    }

    private void setUserData() {
        boxGeom.setFather(this);
    }

    public void grow() {
        grow = true;
    }
    float growStart = 0, growEnd = 1, addGrow = 0, maxGrow = .3f;
    float shrinkStart = 0, shrinkEnd = 1, add = 0, max = 1.5f;
    boolean growFirst = true;
    boolean inRange = false;

    public void update() {
        if (!getId().equals("Light")) {
            if (dead) {
                if (shrinkStart >= shrinkEnd) {
                    if (add >= max) {
                        fullDead = true;
                        if (solid) {
                            solidPhy.destroy();
                        } else {
                            ghostPhy.destroy();
                        }
                        return;
                    }
                    shrinkStart = 0;
                    add += .075;
                    boxGeom.scale(1 - add, 1 - add, 1 - add);
                } else {
                    shrinkStart++;
                }
            }

            if (grow) {
                if (growFirst) {
                    boxGeom.scale(.01f, .01f, .01f);
                    growFirst = false;
                }
                if (growStart >= growEnd) {
                    if (boxGeom.getLocalScale().x >= main.getBlockSize() * 2) {
                        fullGrow = true;
                        boxGeom.setLocalScale(main.getBlockSize() * 2);
                        return;
                    }
                    growStart = 0;
                    addGrow += 0.01f;
                    boxGeom.scale(1.4f + add);
                } else {
                    growStart++;
                }
            }
        }
        if (!main.isEditor()) {
            if (!getId().equals("Light")) {

                Vector3f playerPos = main.getStateManager().getState(GameState.class).getPlayer().getPosition();

                for (Action a : actions) {
                    if (a != null) {
                        if (a.getTag() == Action.ID.NEAR) {
                            if (a.getClass().getName().equals("Engine.Actions.DisplayTextAction")) {
                                DisplayTextAction dta = (DisplayTextAction) a;
                                float ax = position.x - (float) dta.getUserData(1), bx = position.x + (float) dta.getUserData(1);
                                float ay = position.y - (float) dta.getUserData(2), by = position.y + (float) dta.getUserData(2);
                                float az = position.z - (float) dta.getUserData(3), bz = position.z + (float) dta.getUserData(3);
                                float px = playerPos.x, py = playerPos.y, pz = playerPos.z;
                                if (px >= ax && px <= bx && py >= ay && py <= by && pz >= az && pz <= bz) {
                                    inRange = true;
                                    dta.actionPerformed(main);
                                } else {
                                    inRange = false;
                                }
                                if (!inRange) {
                                    if (dta.outOfRangeCall) {
                                        dta.onceOutOfRange();
                                    }

                                }
                            }
                        }
                    }
                }
            }
        }
    }

    public void prePhysicsTick(float tpf) {
    }

    public void collideWithBall(PlayerBall ball) {
        for (Action a : actions) {
            a.actionPerformed(main);
        }
    }

    public Vector3f getPosition() {
        return position;
    }

    public void setPosition(Vector3f position) {
        this.position = position;
    }

    public void setPosition(float x, float y, float z) {
        this.position = new Vector3f(x, y, z);
    }

    public void setMaterial(Material mat) {
        this.blockMat = mat;
    }

    public Material getMaterial() {
        return blockMat;
    }

    public void setUpPhysics() {
        if (!main.isMainMenu()) {
            if (!main.isEditor()) {
                if (solid) {
                    main.getStateManager().getState(GameState.class).getBulletAppState().getPhysicsSpace().add(solidPhy);
                } else {
                    main.getStateManager().getState(GameState.class).getBulletAppState().getPhysicsSpace().add(ghostPhy);
                }
            } else {
                if (solid) {
                    main.getStateManager().getState(EditorState.class).getBulletAppState().getPhysicsSpace().add(solidPhy);
                } else {
                    main.getStateManager().getState(EditorState.class).getBulletAppState().getPhysicsSpace().add(ghostPhy);
                }
            }
        } else {
            main.getStateManager().getState(MainMenuState.class).getBulletAppState().getPhysicsSpace().add(solidPhy);
            solidPhy.setRestitution(1f);
        }
    }

    public ColorRGBA getBlockColor() {
        return blockColor;
    }

    public void setBlockColor(ColorRGBA blockColor) {
        this.blockColor = blockColor;
        blockMat.setColor("Diffuse", blockColor);
        blockMat.setColor("Specular", blockColor);
        boxGeom.setMaterial(blockMat);
    }

    public String getId() {
        return id;
    }

    public Action getAction(int index) {
        return actions.get(index);
    }

    public ArrayList<Action> getActions() {
        return actions;
    }

    public void addAction(Action action) {
        actions.add(action);
    }

    public Geometry getGeometry() {
        return boxGeom;
    }

//    public Box getBox() {
//        return box;
//    }
    public void kill() {
        dead = true;
        if (main.isEditor()) {
            fullDead = true;
        }
    }

    public boolean fullyDead() {
        return fullDead;
    }

    public Vector3f getCenter() {
        return center;
    }

    public PhysicsControl getBody() {
        if (solid) {
            return solidPhy;
        } else {
            return ghostPhy;
        }
    }

    public BlockFactory getFactory() {
        return factory;
    }
    
    public Main getMain() {
        return main;
    }
}

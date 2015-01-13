package Engine.Levels;

import AppStates.EditorState;
import AppStates.GameState;
import AppStates.MainMenuState;
import Engine.Actions.Action;
import Engine.Blocks.BlockFactory;
import Engine.Blocks.BlockFactory.AirFactory;
import Engine.Blocks.BlockFactory.CheckPointFactory;
import Engine.Blocks.BlockFactory.EndGameFactory;
import Engine.Blocks.BlockFactory.HalfWallFactory;
import Engine.Blocks.BlockFactory.LightFactory;
import Engine.Blocks.BlockFactory.PressurePlateFactory;
import Engine.Blocks.BlockFactory.ShooterFactory;
import Engine.Blocks.BlockFactory.TrampolineFactory;
import Engine.Blocks.BlockFactory.WallFactory;
import Engine.Blocks.BlockSuper;
import Engine.Blocks.Block_Air;
import Engine.Blocks.Block_CheckPoint;
import Engine.Blocks.Block_EndGame;
import Engine.Blocks.Block_HalfWall;
import Engine.Blocks.Block_Light;
import Engine.Blocks.Block_PressurePlate;
import Engine.Blocks.Block_Shooter;
import Engine.Blocks.Block_Trampoline;
import Engine.Blocks.Block_Wall;
import Engine.Main;
import Engine.Utils.Util;
import com.jme3.asset.AssetManager;
import com.jme3.export.InputCapsule;
import com.jme3.export.JmeExporter;
import com.jme3.export.JmeImporter;
import com.jme3.export.OutputCapsule;
import com.jme3.export.Savable;
import com.jme3.input.KeyInput;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.scene.BatchNode;
import com.jme3.scene.Geometry;
import com.jme3.scene.Mesh;
import com.jme3.scene.Node;
import com.jme3.scene.VertexBuffer.Type;
import com.jme3.util.BufferUtils;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.util.ArrayList;

public class Level extends Node implements Savable {

    Main main;
    private BatchNode level;
    private Vector3f levelSize;
    private BlockSuper[][][] blocks;
    private AssetManager assetManager;
    private String id;
    public static Block_Air airBlock;
    private boolean creating = true;
    private Vector3f spawnPosition = new Vector3f(0, 5, 0);
    private boolean updateMesh = false;
    private boolean finished = false;
    private ArrayList<Action> sceneActions = new ArrayList<Action>(100);
    private boolean initiated = false;
    public static boolean LOADED_ON = false;
    public static boolean LOADED_OFF = true;
    private boolean removingAll = false;
    private String saveName = "saveGame.j3o";
    private String levelClass;

    public Level() {
    }

    public Level(Level l, Main main, String id) {
        setData(main, id, l.getNode());
    }

    public void setData(Main main, String id, BatchNode b) {
        this.main = main;
        this.id = id;
        if (b == null) {
            this.level = new BatchNode("blocks");
        } else {
            this.level = b;
        }
        this.assetManager = main.getAssetManager();
        this.airBlock = new Block_Air(main, 0, 10, 0, this);
    }

    public BatchNode getNode() {
        return level;
    }

    public void setSaveName(String name) {
        this.saveName = name;
    }

    public String getSaveName() {
        return saveName;
    }

    public void finishSetup() {
        for (int x = 0; x < blocks.length; x++) {
            for (int y = 0; y < blocks[0].length; y++) {
                for (int z = 0; z < blocks[0][0].length; z++) {
                    if (blocks[x][y][z] == null) {
                        blocks[x][y][z] = airBlock;
                    }
                }
            }
        }
        if (!main.isEditor()) {
            creating = false;
        }
        level.batch();
        initiated = true;
    }

    public void doRundown() {
        Util.log("Doing rundown...");
        for (int x = 0; x < blocks.length; x++) {
            for (int y = 0; y < blocks[0].length; y++) {
                for (int z = 0; z < blocks[0][0].length; z++) {
                    if (!blocks[x][y][z].getId().equals("Air")) {
                        Util.log(blocks[x][y][z].getId() + " at " + blocks[x][y][z].getPosition());
                    }
                }
            }
        }
    }

    public void completed() {
        this.finished = true;
    }

    public boolean isCompleted() {
        return finished;
    }

    public void setUpLevel(boolean notLoaded) {
        if (notLoaded) {
            setSpawnPosition(new Vector3f(1, 9, 1));
            setLevelSize(90, 60, 90);
            makeFloor(10, 10, wallBlockFactory);
            addLight(5, 3, 5, ColorRGBA.White, 10);
        }
        addPhysics();
    }

    public void makeBlock(int x, int y, int z, BlockFactory factory) {
        if (blocks[x][y][z] == null || blocks[x][y][z].getId().equals("Air")) {
            blocks[x][y][z] = factory.create(main, new Vector3f(x, y, z), this, creating);
            if (!blocks[x][y][z].getId().equals("Light")) {
                blocks[x][y][z].setUpPhysics();
            }
        }
    }

    public void makeBlock(Vector3f pos, BlockFactory factory) {
        int x = (int) pos.x;
        int z = (int) pos.z;
        int y = (int) pos.y;
        makeBlock(x, y, z, factory);
    }

    public void makeWall(Vector3f startPos, Vector3f endPos, BlockFactory factory) {
        for (int x = (int) startPos.x; x <= endPos.x; x++) {
            for (int y = (int) startPos.y; y <= endPos.y; y++) {
                for (int z = (int) startPos.z; z <= endPos.z; z++) {
                    makeBlock(new Vector3f(x, y, z), factory);
                }
            }
        }
    }

    public void makeWall(Vector3f startPos, Vector3f endPos, BlockFactory factory, boolean casts) {
        for (int x = (int) startPos.x; x <= endPos.x; x++) {
            for (int y = (int) startPos.y; y <= endPos.y; y++) {
                for (int z = (int) startPos.z; z <= endPos.z; z++) {
                    makeBlock(new Vector3f(x, y, z), factory);
                }
            }
        }
    }

    public void setSpawnPosition(Vector3f spawnPosition) {
        this.spawnPosition = spawnPosition;
    }

    public void removeBlock(Vector3f pos) {
        removeBlock((int) pos.x, (int) pos.y, (int) pos.z);
    }

    public void removeBlock(int x, int y, int z) {
        if (blocks[x][y][z] != null) {
            if (!blocks[x][y][z].getId().equals("Air")) {
                blocks[x][y][z].kill();
            }
        }
    }

    public void removeWall(Vector3f startPos, Vector3f endPos) {
        for (int x = (int) startPos.x; x <= endPos.x; x++) {
            for (int y = (int) startPos.y; y <= endPos.y; y++) {
                for (int z = (int) startPos.z; z <= endPos.z; z++) {
                    removeBlock(x, y, z);
                }
            }
        }
    }

    public void addLight(int x, int y, int z, ColorRGBA color, float radius) {
        removeBlock(x, y, z);
        blocks[x][y][z] = new Block_Light(main, x, y, z, this, creating, color, radius);
    }

    public void addActionArea(Vector3f startPos, Vector3f endPos, Class<? extends Action> a, BlockSuper activator) {
        for (int x = (int) startPos.x; x <= endPos.x; x++) {
            for (int y = (int) startPos.y; y <= endPos.y; y++) {
                for (int z = (int) startPos.z; z <= endPos.z; z++) {
                    try {
                        Class<?> clazz = Class.forName(a.getName());
                        Constructor<?> constructor = clazz.getConstructor();
                        Action action = (Action) constructor.newInstance();
                        activator.addAction(action);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public void makeFloor(int x, int z, BlockFactory factory) {
        makeWall(new Vector3f(0, 0, 0), new Vector3f(x, 0, z), factory, false);
    }

    public void setLevelSize(int x, int y, int z) {
        blocks = new BlockSuper[x][y][z];
        levelSize = new Vector3f(x, y, z);
        Util.log(levelSize);
    }

    public void setLevelSize(Vector3f size) {
        setLevelSize((int) size.x, (int) size.y, (int) size.z);
    }
    boolean removedBlocks = false;

    public void update() {
        if (initiated) {
            for (int x = 0; x < blocks.length; x++) {
                for (int y = 0; y < blocks[0].length; y++) {
                    for (int z = 0; z < blocks[0][0].length; z++) {
                        if (blocks[x][y][z] != null) {
                            blocks[x][y][z].update();
                            if (blocks[x][y][z].fullyDead()) {
                                if (!main.isEditor()) {
                                    main.getStateManager().getState(GameState.class).getBulletAppState().getPhysicsSpace().remove(blocks[x][y][z].getBody());
                                } else if (main.isEditor()) {
                                    main.getStateManager().getState(EditorState.class).getBulletAppState().getPhysicsSpace().remove(blocks[x][y][z].getBody());
                                } else if (main.isMainMenu() && !main.isEditor()) {
                                    main.getStateManager().getState(MainMenuState.class).getBulletAppState().getPhysicsSpace().remove(blocks[x][y][z].getBody());
                                }
                                blocks[x][y][z].getGeometry().removeFromParent();
                                level.detachChild(blocks[x][y][z].getGeometry());
                                blocks[x][y][z] = airBlock;
                                removedBlocks = true;
                            }
                        }
                    }
                }
            }
            if (removedBlocks) {
                level.batch();
                removedBlocks = false;
            }
            if (updateMesh) {
                updateMesh = false;
            }
        }
    }

    public BlockSuper getCube(int x, int y, int z) {
        try {
            return blocks[x][y][z];
        } catch (ArrayIndexOutOfBoundsException e) {
            return null;
        }
    }

    public void addPhysics() {
        Util.log("Setting up physics..");
        main.getRootNode().attachChild(level);
        finishSetup();
    }

    public void removeAll() {
        removingAll = true;
        Util.log("Removing all....");
        for (int x = 0; x < blocks.length; x++) {
            for (int y = 0; y < blocks[0].length; y++) {
                for (int z = 0; z < blocks[0][0].length; z++) {
                    removeBlock(x, y, z);
                }
            }
        }
    }

    public boolean isRemovingAll() {
        return removingAll;
    }

    public void addSceneAction(Action a) {
        sceneActions.add(a);
    }

    public Vector3f getSpawnPosition() {
        return spawnPosition;
    }
    public static final BlockFactory<Block_Trampoline> trampolineBlockFactory = new TrampolineFactory();
    public static final BlockFactory<Block_CheckPoint> checkPointBlockFactory = new CheckPointFactory();
    public static final BlockFactory<Block_Light> lightBlockFactory = new LightFactory();
    public static final BlockFactory<Block_HalfWall> halfWallBlockFactory = new HalfWallFactory();
    public static final BlockFactory<Block_PressurePlate> pressurePlateBlockFactory = new PressurePlateFactory();
    public static final BlockFactory<Block_EndGame> endGameBlockFactory = new EndGameFactory();
    public static final BlockFactory<Block_Air> airBlockFactory = new AirFactory();
    public static final BlockFactory<Block_Wall> wallBlockFactory = new WallFactory();
    public static final BlockFactory<Block_Shooter> shooterBlockFactory = new ShooterFactory();
    BlockFactory[] bDefault = {airBlockFactory};
    ColorRGBA[] lDefault = {ColorRGBA.White};
    float[] lRDefault = {5};

    public void write(JmeExporter ex) throws IOException {
        OutputCapsule capsule = ex.getCapsule(this);
        Util.log("Saving Initiated...");

        BlockFactory[] facs = new BlockFactory[blocks.length * blocks[0].length * blocks[0][0].length];
        ColorRGBA[] lights = new ColorRGBA[blocks.length * blocks[0].length * blocks[0][0].length];
        float[] lightRadii = new float[blocks.length * blocks[0].length * blocks[0][0].length];

        Util.log("Saving blocks...");
        int index = 0;
        for (int x = 0; x < blocks.length; x++) {
            for (int y = 0; y < blocks[0].length; y++) {
                for (int z = 0; z < blocks[0][0].length; z++) {
                    if (blocks[x][y][z].getFactory().getID().equals("Light")) {
                        lights[index] = ((Block_Light) (blocks[x][y][z])).getLightColor();
                        lightRadii[index] = ((Block_Light) (blocks[x][y][z])).getRadius();
                        facs[index] = lightBlockFactory;
                    } else {
                        facs[index] = blocks[x][y][z].getFactory();
                    }
                    index++;
                }
            }
        }

        Util.log("Saving level data...");
        capsule.write(level, "level", new BatchNode());
        capsule.write(levelSize, "levelSize", new Vector3f(100, 100, 100));
        capsule.write(facs, "facs", bDefault);
        capsule.write(lights, "lights", lDefault);
        capsule.write(lightRadii, "lightsRadii", lRDefault);
        capsule.write(spawnPosition, "spawnPosition", new Vector3f(1, 5, 1));
        capsule.write(levelClass, "levelClass", "");
        if (main.isEditor()) {
            Util.log("Saving camera data...");
            capsule.write(main.getCamera().getLocation(), "CamLocation", new Vector3f(0, 2, 0));
            capsule.write(main.getCamera().getRotation(), "CamRotation", Quaternion.IDENTITY);
        }
        Util.log("Saving complete.");
    }
    boolean allAir = true;

    public void read(JmeImporter im) throws IOException {
        InputCapsule capsule = im.getCapsule(this);
        Util.log("Loading level data...");
        level = (BatchNode) capsule.readSavable("level", new BatchNode());
        level.detachAllChildren();
        levelSize = (Vector3f) capsule.readSavable("levelSize", new Vector3f(100, 100, 100));
        blocks = new BlockSuper[(int) levelSize.x][(int) levelSize.y][(int) levelSize.z];
        main = Util.getMain();

        Savable[] facs = capsule.readSavableArray("facs", bDefault);
        BlockFactory[][][] copyArrays = new BlockFactory[(int) levelSize.x][(int) levelSize.y][(int) levelSize.z];
        Savable[] lights = capsule.readSavableArray("lights", lDefault);
        float[] lightRadii = capsule.readFloatArray("lightsRadii", lRDefault);
        spawnPosition = (Vector3f) capsule.readSavable("spawnPosition", new Vector3f(1, 5, 1));
        if (spawnPosition == null) {
            spawnPosition = new Vector3f(0, 5, 0);
        }
        Util.log("Spawn Position: " + spawnPosition);

        levelClass = capsule.readString("levelClass", "");
        Util.log("Level Class: " + levelClass);

        Util.log("Loading blocks...");
        int index = 0;
        for (int x = 0; x < copyArrays.length; x++) {
            for (int y = 0; y < copyArrays[0].length; y++) {
                for (int z = 0; z < copyArrays[0][0].length; z++) {
                    copyArrays[x][y][z] = (BlockFactory) facs[index];
                    if (copyArrays[x][y][z] != null && !copyArrays[x][y][z].getID().equals("Air")) {
                        if (copyArrays[x][y][z].getID().equals("Light")) {
                            addLight(x, y, z, (ColorRGBA) lights[index], lightRadii[index]);
                        } else {
                            makeBlock(x, y, z, copyArrays[x][y][z]);
                        }
                    } else if (copyArrays[x][y][z] != null && copyArrays[x][y][z].getID().equals("Air")) {
                        blocks[x][y][z] = airBlock;
                    }
                    index++;
                }
            }
        }

        if (main.isEditor()) {
            Util.log("Loading camera data...");
            main.getCamera().setLocation((Vector3f) capsule.readSavable("CamLocation", new Vector3f(0, 2, 0)));
            main.getCamera().setRotation((Quaternion) capsule.readSavable("CamRotation", Quaternion.IDENTITY));
        } else {
            if (levelClass == null) {
                levelClass = "";
            }
            if (levelClass.equals("Level2")) {
                new Level2(this);
            } else if (levelClass.equals("Level3")) {
                new Level3(this);
            } else if (levelClass.equals("Level4")) {
                new Level4(this);
            } else if (levelClass.equals("Level5")) {
            }
        }
        Util.log("Loading complete.");
        initiated = true;
    }

    public int getSizeX() {
        Util.log(blocks.length);
        return (int) levelSize.getX();
    }

    public int getSizeY() {
        return blocks[0].length;
    }

    public int getSizeZ() {
        return blocks[0][0].length;
    }

    public void setSaveLevel(String saveLevel) {
        this.levelClass = saveLevel;
    }

    public String getLevelClass() {
        return levelClass;
    }
    
    public Node getBatchNode() {
        return level;
    }

    public Level getMe() {
        return this;
    }
}

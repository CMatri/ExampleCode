package AppStates;

import Engine.Blocks.BlockFactory;
import Engine.Gui.EditorGuiController;
import Engine.Levels.Level;
import Engine.Main;
import Engine.Map.Map;
import Engine.Utils.Util;
import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.asset.AssetManager;
import com.jme3.asset.plugins.FileLocator;
import com.jme3.bullet.BulletAppState;
import com.jme3.font.BitmapFont;
import com.jme3.font.BitmapText;
import com.jme3.input.InputManager;
import com.jme3.light.PointLight;
import com.jme3.math.Vector3f;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Node;

public class EditorState extends AbstractAppState {

    private Map editorMap;
    private PointLight pl;
    private BulletAppState bulletAppState;
    private int scroll;
    private BitmapText ch, coords, startPos;
    private String[] scrollables = {"Finish Block", "Half Wall", "Light", "Pressure Plate", "Trampoline", "Wall", "Shooter", "Check Point"};
    private BlockFactory[] factories = new BlockFactory[20];
    private EditorGuiController guiControl;
    private Main app;
    private Node rootNode, guiNode;
    private AssetManager assetManager;
    private AppStateManager stateManager;
    private InputManager inputManager;
    private ViewPort viewPort;
    private EditorInputs editorInputs;
    private Vector3f lookCoords;
    private String newLevelName;
    private boolean created = false, createNew = false;

    public void initialize(AppStateManager stateManager, Application app) {
        super.initialize(stateManager, app);
        this.app = (Main) app;
        this.rootNode = this.app.getRootNode();
        this.assetManager = this.app.getAssetManager();
        this.stateManager = this.app.getStateManager();
        this.inputManager = this.app.getInputManager();
        this.viewPort = this.app.getViewPort();
        this.guiNode = this.app.getGuiNode();
        this.editorInputs = new EditorInputs(this, app);
        this.lookCoords = new Vector3f(0, 0, 0);

        inputManager.deleteMapping(SimpleApplication.INPUT_MAPPING_EXIT);
    }

    public void setUpLevel(boolean createNew, String newLevelName) {
        this.createNew = createNew;
        this.newLevelName = newLevelName;
    }

    public void setLookAtCoords(Vector3f vec) {
        this.lookCoords = vec;
    }

    public void setLookAtCoords(int x, int y, int z) {
        setLookAtCoords(new Vector3f(x, y, z));
    }

    public void update(float tpf) {
        if (!created) {

            Util.log("Setting up Bullet Physics");
            bulletAppState = new BulletAppState();
            bulletAppState.setThreadingType(BulletAppState.ThreadingType.PARALLEL);
            stateManager.attach(bulletAppState);

            factories[0] = Level.endGameBlockFactory;
            factories[1] = Level.halfWallBlockFactory;
            factories[2] = Level.lightBlockFactory;
            factories[3] = Level.pressurePlateBlockFactory;
            factories[4] = Level.trampolineBlockFactory;
            factories[5] = Level.wallBlockFactory;
            factories[6] = Level.shooterBlockFactory;
            factories[7] = Level.checkPointBlockFactory;

            if (!createNew) {
                String userHome = System.getProperty("user.home");
                assetManager.registerLocator(userHome, FileLocator.class);
//                Level toLoad = (Level) (assetManager.loadModel(".slythGame/" + newLevelName + ".j3o"));
                Level toLoad = (Level) (assetManager.loadModel("Models/" + newLevelName + ".j3o"));
//                Level2 toLoad = new Level2();
                toLoad.setData(this.app, "Test", toLoad.getNode());
                toLoad.setSaveName(newLevelName);
                editorMap = new Map(this.app);
                editorMap.setMap(toLoad, Level.LOADED_ON);
            } else {
                Level toLoad = new Level();
                toLoad.setData(this.app, "Test", toLoad.getNode());
                toLoad.setSaveName(newLevelName);
                editorMap = new Map(this.app);
                editorMap.setMap(toLoad, Level.LOADED_OFF);
            }

            guiControl = new EditorGuiController(this, this.app);
            guiControl.goToEditor();
            created = true;

            initCrossHairs();
            initGui();
        }

        editorMap.getLevel().update();
        ch.setText(scrollables[scroll]);
        ch.setLocalTranslation(app.getAppSettings().getWidth() / 2 - ch.getLineWidth() / 2, 100, 0);
        coords.setText("Coords: " + (int) lookCoords.x + ", " + (int) lookCoords.y + ", " + (int) lookCoords.z);
        coords.setLocalTranslation(app.getAppSettings().getWidth() - 90 - coords.getLineWidth() / 2, 40, 0);
        startPos.setText("Spawn Pos: " + editorMap.getLevel().getSpawnPosition().x + ", " + editorMap.getLevel().getSpawnPosition().y + ", " + editorMap.getLevel().getSpawnPosition().z);
        startPos.setLocalTranslation(app.getAppSettings().getWidth() / 6 - startPos.getLineWidth() / 2, 60, 0);
        editorInputs.update(tpf);
    }

    public void cleanup() {
        super.cleanup();
    }

    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        if (enabled) {
        } else {
        }
    }

    protected void initCrossHairs() {
        BitmapFont guiFont = app.getGameFont();
        BitmapText ch = new BitmapText(guiFont, false);
        ch.setSize(guiFont.getCharSet().getRenderedSize() * 2);
        ch.setText("+"); // crosshairs
        ch.setLocalTranslation(app.getAppSettings().getWidth() / 2 - ch.getLineWidth() / 2, app.getAppSettings().getHeight() / 2 + ch.getLineHeight() / 2, 0);
        guiNode.attachChild(ch);
    }

    protected void initGui() {
        BitmapFont guiFont = app.getGameFont();
        ch = new BitmapText(guiFont, false);
        ch.setSize(guiFont.getCharSet().getRenderedSize());
        ch.setText(scrollables[scrollables.length - 1]);
        ch.setLocalTranslation(app.getAppSettings().getWidth() / 2 - ch.getLineWidth() / 2, 100, 0);
        guiNode.attachChild(ch);

        coords = new BitmapText(guiFont, false);
        coords.setSize(guiFont.getCharSet().getRenderedSize() - 10);
        coords.setText(lookCoords.x + ", " + lookCoords.y + ", " + lookCoords.z);
        coords.setLocalTranslation(app.getAppSettings().getWidth() / 6 - coords.getLineWidth() / 2, 60, 0);
        guiNode.attachChild(coords);

        startPos = new BitmapText(guiFont, false);
        startPos.setSize(guiFont.getCharSet().getRenderedSize() - 10);
        startPos.setText("Spawn Pos: " + editorMap.getLevel().getSpawnPosition().x + ", " + editorMap.getLevel().getSpawnPosition().y + ", " + editorMap.getLevel().getSpawnPosition().z);
        startPos.setLocalTranslation(app.getAppSettings().getWidth() / 6 - startPos.getLineWidth() / 2, 60, 0);
        guiNode.attachChild(startPos);
    }

    public void scroll(int val) {
        this.scroll += val;
        if (scroll >= scrollables.length) {
            scroll = 0;
        } else if (scroll < 0) {
            scroll = scrollables.length - 1;
        }
    }

    public Map getMap() {
        return editorMap;
    }

    public BulletAppState getBulletAppState() {
        return bulletAppState;
    }

    public BlockFactory getToPlace() {
        return factories[scroll];
    }

    public EditorGuiController getGui() {
        return guiControl;
    }

    public Node getRootNode() {
        return rootNode;
    }

    public AssetManager getAssetManager() {
        return assetManager;
    }

    public AppStateManager getStateManager() {
        return stateManager;
    }

    public InputManager getInputManager() {
        return inputManager;
    }

    public ViewPort getViewPort() {
        return viewPort;
    }

    public EditorInputs getEditorInputs() {
        return editorInputs;
    }
}

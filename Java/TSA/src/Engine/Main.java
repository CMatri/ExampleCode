package Engine;

import AppStates.EditorState;
import AppStates.GameState;
import AppStates.MainMenuState;
import Engine.Balls.PlayerBall;
import Engine.Lights.LightManager;
import Engine.Lights.MaterialManager;
import Engine.Utils.SoundManager;
import Engine.Utils.Util;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.AbstractAppState;
import com.jme3.export.binary.BinaryExporter;
import com.jme3.font.BitmapFont;
import com.jme3.input.controls.InputListener;
import com.jme3.material.Material;
import com.jme3.math.Vector3f;
import com.jme3.niftygui.NiftyJmeDisplay;
import com.jme3.system.AppSettings;
import java.io.File;
import java.io.IOException;

public class Main extends SimpleApplication {

    private SettingsManager settingsManager;
    private LightManager lightManager;
    private InputListener inputListener;
    private MaterialManager materialManager;
    private BitmapFont gameFont;
    private float blockSize = .5f;
    private boolean EDITOR = true;
    private boolean mainMenu = true;
    private GameState gameState;
    private EditorState editorState;
    private MainMenuState mainMenuState;
    private String saveName = "saveGame";
    private Util util;
    private NiftyJmeDisplay niftyDisplay;
    private SoundManager soundManager;
    private PlayerBall activePlayer;

    public static void main(String[] args) {
        Main app = new Main();
        app.setShowSettings(false);
        app.start();
    }

    @Override
    public void simpleInitApp() {
        Util.log("Starting App");
        Util.log("Setting Settings");
        util = new Util();
        util.setMain(this);
        settingsManager = new SettingsManager();
        settingsManager.initSettings(this, flyCam, 20);
        setDisplayFps(false);
        setDisplayStatView(false);
//        inputManager.setCursorVisible(true);
//        flyCam.setEnabled(false);
//        rootNode.detachAllChildren();

        Util.log("Loading Font");
        gameFont = assetManager.loadFont("Fonts/primer.fnt");

        Util.log("Creating Material Manager");
        materialManager = new MaterialManager(this);

        Util.log("Setting up Lights");
        lightManager = new LightManager(this);

        Util.log("Setting up Music and Sounds");
        soundManager = new SoundManager(this);
        soundManager.startPlayingMusic();

        niftyDisplay = new NiftyJmeDisplay(getAssetManager(), getInputManager(), getAudioRenderer(), getViewPort());

        toMainMenu(null);
//        toEditor(null);
//        toCampaign(null);
    }

    public void toEditor(AbstractAppState from, boolean createNew, String newLevelName) {
        if (from != null) {
            stateManager.detach(from);
        }
        mainMenu = false;
        EDITOR = true;

        editorState = new EditorState();
        stateManager.attach(editorState);
        editorState.setUpLevel(createNew, newLevelName);

//        inputManager.setCursorVisible(false);
//        flyCam.setEnabled(true);
        getCamera().setLocation(new Vector3f(5, 5, 5));
    }

    public void toCampaign(AbstractAppState from) {
        if (from != null) {
            stateManager.detach(from);
        }

        EDITOR = false;
        mainMenu = false;

        gameState = new GameState();
        stateManager.attach(gameState);
    }

    public void toMainMenu(AbstractAppState from) {
        if (from != null) {
            stateManager.detach(from);
        }
        mainMenuState = new MainMenuState(this);
        stateManager.attach(mainMenuState);
        mainMenuState.initScene();
        mainMenuState.getMainMenuInputs().setEnabled(true);
        EDITOR = false;
        mainMenu = true;
    }

    public SettingsManager getSettingsManager() {
        return settingsManager;
    }

    public NiftyJmeDisplay getNiftyDisplay() {
        return niftyDisplay;
    }

    public LightManager getLightManager() {
        return lightManager;
    }

    public MaterialManager getMaterialManager() {
        return materialManager;
    }

    public Material getBlockMaterial() {
        return materialManager.getBlockMat();
    }

    public InputListener getInputListener() {
        return inputListener;
    }

    public SoundManager getSoundManager() {
        return soundManager;
    }

    @Override
    public void simpleUpdate(float tpf) {
        lightManager.update(tpf);
        soundManager.update(tpf);
    }

    public void stop() {
//        if (EDITOR && !mainMenu) {
//            String userHome = System.getProperty("user.home");
//            BinaryExporter exporter = BinaryExporter.getInstance();
//            File file = new File(userHome + "/.slythGame/" + saveName + ".j3o");
//            File file = new File("assets/Models/" + editorState.getMap().getLevel().getSaveName() + ".j3o");
//            try {
//                exporter.save(editorState.getMap().getLevel(), file);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//            Util.log("Finished saving.");
//        }

        super.stop();
    }

    public float getBlockSize() {
        return blockSize;
    }

    public BitmapFont getGameFont() {
        return gameFont;
    }

    public boolean isEditor() {
        return EDITOR;
    }

    public AppSettings getAppSettings() {
        return settings;
    }

    public boolean isMainMenu() {
        return mainMenu;
    }

    public void setSaveName(String name) {
        this.saveName = name;
    }

    public PlayerBall getPlayerBall() {
        return activePlayer;
    }

    public void setActivePlayer(PlayerBall ball) {
        this.activePlayer = ball;
    }
}

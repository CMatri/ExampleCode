package AppStates;

import Engine.Balls.MainMenuBall;
import Engine.Gui.MainMenuGuiController;
import Engine.Levels.Level;
import Engine.Main;
import Engine.Map.Map;
import Engine.Utils.GlowingTextRenderer;
import Engine.Utils.Util;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.asset.AssetManager;
import com.jme3.bullet.BulletAppState;
import com.jme3.input.InputManager;
import com.jme3.math.FastMath;
import com.jme3.math.Vector3f;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Node;
import java.util.ArrayList;
import java.util.Collection;

public class MainMenuState extends AbstractAppState {

    private Main app;
    private Node rootNode;
    private AssetManager assetManager;
    private AppStateManager stateManager;
    private MainMenuInputs mainMenuInputs;
    private InputManager inputManager;
    private ViewPort viewPort;
    private MainMenuGuiController guiControl;
    private Map background;
    private BulletAppState bulletAppState;
    private Vector3f origCameraPos;
    private MainMenuBall ball;
    private boolean enabled = false, isInBeginning = true;
    private Collection<GlowingTextRenderer> displayTexts = new ArrayList<GlowingTextRenderer>(100);
    private Collection<GlowingTextRenderer> displayTextsToRemove = new ArrayList<GlowingTextRenderer>(100);
    private boolean goingToCampaign = false;

    public MainMenuState(SimpleApplication app) {
        super.initialize(stateManager, app);
        this.app = (Main) app;
        this.rootNode = this.app.getRootNode();
        this.assetManager = this.app.getAssetManager();
        this.stateManager = this.app.getStateManager();
        this.inputManager = this.app.getInputManager();
        this.mainMenuInputs = new MainMenuInputs(this);
        this.viewPort = this.app.getViewPort();

        Util.log("Setting up Bullet Physics");
        bulletAppState = new BulletAppState();
        bulletAppState.setThreadingType(BulletAppState.ThreadingType.PARALLEL);
        stateManager.attach(bulletAppState);

        inputManager.setCursorVisible(true);
        this.app.getFlyByCamera().setEnabled(false);
        this.app.getFlyByCamera().setZoomSpeed(0);

        app.getCamera().setLocation(new Vector3f(8, 3, 8));

        guiControl = new MainMenuGuiController(this, this.app);

        origCameraPos = new Vector3f(21.097511f, 2.461708f, 29.366954f);
        target = origCameraPos;
    }

    public void initScene() {
        Level toLoad = (Level) assetManager.loadModel("Models/mainMenuLevel1.j3o");
        toLoad.setData(this.app, "Test", toLoad.getNode());
        toLoad.setSpawnPosition(new Vector3f(23, 3, 23));
        background = new Map(this.app);
        background.setMap(toLoad, Level.LOADED_ON);

        ball = new MainMenuBall(this.app, new Vector3f(25, 5, 24));

        app.getCamera().setLocation(origCameraPos);
//        app.getCamera().setRotation(new Quaternion(0.013040244f, 0.9615908f, -0.04645906f, 0.27021214f));
        app.getCamera().lookAt(ball.getPosition().add(new Vector3f(-1, 0, -1)).setY(1), Vector3f.UNIT_Y);

        mainMenuInputs.setEnabled(true);

        enabled = true;

        new GlowingTextRenderer(app, this, "Check your frames per second in the options menu! If it's below 30 please try turning off SSAO (Screen Space Ambient Occlusion) or Depth of Field to make the game seem smoother. You can do so"
                + " in the options menu.", app.getAppSettings().getWidth(), 100);
    }
    private Vector3f target, offSet = new Vector3f(0, 0, 0);
    private boolean animationBeginning = true;

    public void goToOptions() {
        target = origCameraPos.add(new Vector3f(-3, 0, -10));
        animationBeginning = false;
        isInBeginning = false;
    }

    public void setOffset(Vector3f offSet) {
        this.offSet = offSet;
    }

    public Vector3f getOffset() {
        return offSet;
    }

    public void goToMainMenu() {
        target = origCameraPos;
        animationBeginning = false;
    }

    public void goToCampaign() {
        app.getLightManager().fadeOut();
        guiControl.getNifty().gotoScreen("empty");
        for (GlowingTextRenderer gtr : displayTexts) {
            displayTextsToRemove.add(gtr);
        }
        goingToCampaign = true;
    }

    public void update(float tpf) {
        if (enabled) {

            if (goingToCampaign) {
                app.getCamera().setLocation(FastMath.interpolateLinear(0.006f, app.getCamera().getLocation(), app.getCamera().getLocation().add(new Vector3f(10, 0, 0))));
                if (app.getLightManager().fullyFaded()) {
                    app.toCampaign(this);
                    app.getLightManager().fadeIn();
                }
            }

            for (GlowingTextRenderer gtr : displayTexts) {
                gtr.update(false);
                if (gtr.kill) {
                    displayTextsToRemove.add(gtr);
                }
            }
            for (GlowingTextRenderer gtr : displayTextsToRemove) {
                gtr.txt.setAlpha(0.0f);
                gtr.kill = false;
                gtr.toKill = false;
                displayTexts.remove(gtr);
            }

            ball.update(tpf);
            mainMenuInputs.update(tpf);
            if (!animationBeginning) {
                app.getCamera().setLocation(FastMath.interpolateLinear(1f * tpf, app.getCamera().getLocation(), target));
                app.getCamera().lookAt(ball.getPosition().add(new Vector3f(-1, 0, -1)).setY(1), Vector3f.UNIT_Y);
                if ((app.getCamera().getLocation().x >= target.x - 0.25f && app.getCamera().getLocation().x <= target.x + 0.25f) && (app.getCamera().getLocation().y >= target.y - 0.25f && app.getCamera().getLocation().y <= target.y + 0.25f) && (app.getCamera().getLocation().z >= target.z - 0.25f && app.getCamera().getLocation().z <= target.z + 0.25f)) {
                    animationBeginning = true;
                }
            }
            if (isInBeginning && app.getCamera().getLocation() != origCameraPos) {
                app.getCamera().setLocation(origCameraPos);
                app.getCamera().lookAt(ball.getPosition().add(new Vector3f(-1, 0, -1)).setY(1), Vector3f.UNIT_Y);
                isInBeginning = false;
            }
        }
    }

    public Vector3f getOrigCameraPos() {
        return origCameraPos;
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

    public BulletAppState getBulletAppState() {
        return bulletAppState;
    }

    public MainMenuInputs getMainMenuInputs() {
        return mainMenuInputs;
    }

    public void addGlowingTextRenderer(GlowingTextRenderer gtr) {
        this.displayTexts.add(gtr);
    }

    public void cleanup() {
        super.cleanup();

        for (GlowingTextRenderer gtr : displayTexts) {
            displayTextsToRemove.add(gtr);
        }

        background.getLevel().removeAll();
        rootNode.detachChild(background.getLevel().getNode());
        background = null;
        stateManager.detach(bulletAppState);
        rootNode.detachAllChildren();
        mainMenuInputs.setEnabled(false);
        app.getGuiNode().detachAllChildren();
    }
}

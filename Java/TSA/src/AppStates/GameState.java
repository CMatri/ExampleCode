package AppStates;

import Engine.Balls.PlayerBall;
import Engine.Balls.RegularBall;
import Engine.Gui.CutScene;
import Engine.Gui.GameGuiController;
import Engine.Levels.Level;
import Engine.Levels.Level2;
import Engine.Main;
import Engine.Map.Map;
import Engine.Utils.GlowingTextRenderer;
import Engine.Utils.Util;
import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.asset.AssetManager;
import com.jme3.bullet.BulletAppState;
import com.jme3.input.InputManager;
import com.jme3.math.Vector3f;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Node;
import java.util.ArrayList;
import java.util.Collection;

public class GameState extends AbstractAppState {

    private BulletAppState bulletAppState;
    private PlayerBall player;
    private Map currMap, map1, map2, map3, map4, map5;
    private Collection<GlowingTextRenderer> displayTexts = new ArrayList<GlowingTextRenderer>(100);
    private Collection<GlowingTextRenderer> displayTextsToRemove = new ArrayList<GlowingTextRenderer>(100);
    private Main app;
    private Node rootNode;
    private AssetManager assetManager;
    private AppStateManager stateManager;
    private InputManager inputManager;
    private ViewPort viewPort;
    private GameGuiController gameGuiController;
    private GameInputs gameInputs;
    private boolean changingLevels = false;
    private boolean onDialogue = false;
    private int currLevel = 1;
    private CutScene currCutScene;

    public void initialize(AppStateManager stateManager, Application app) {
        super.initialize(stateManager, app);
        this.app = (Main) app;
        this.rootNode = this.app.getRootNode();
        this.assetManager = this.app.getAssetManager();
        this.stateManager = this.app.getStateManager();
        this.inputManager = this.app.getInputManager();
        this.viewPort = this.app.getViewPort();
        this.gameInputs = new GameInputs(this);
        this.gameGuiController = new GameGuiController(this, app);

        inputManager.deleteMapping(SimpleApplication.INPUT_MAPPING_EXIT);

        Util.log("Setting up Bullet Physics");
        bulletAppState = new BulletAppState();
        bulletAppState.setThreadingType(BulletAppState.ThreadingType.PARALLEL);
        stateManager.attach(bulletAppState);

        Util.log("Setting up level");
        map1 = new Map(this.app);
        map2 = new Map(this.app);
        map3 = new Map(this.app);
        map4 = new Map(this.app);
        map5 = new Map(this.app);

        loadCutscene(1);
    }

    public void loadCutscene(int level) {
        onDialogue = true;
        switch (level) {
            case 1:
                app.getLightManager().fadeOut();
                currLevel = 0;
                currCutScene = new CutScene(Util.getMain(), currLevel, "And so you begin on your journey. Whether you will be triumphant and swim in the joy of your achievement, or fail miserably and have all the work be for naught remains to be determined. \n- ?");
                break;
            case 2:
                app.getLightManager().fadeOut();
                currLevel = 1;
                currCutScene = new CutScene(Util.getMain(), currLevel, "You have done well so far, but how will you fare once the pressure increases? I am interested to see if you will buckle, but I believe in you!. \n-A Friend");
                break;
            case 3:
                app.getLightManager().fadeOut();
                currLevel = 2;
                currCutScene = new CutScene(Util.getMain(), currLevel, 40, "Ah ha, you have emerged triumphant once more, somehow. But ask yourself this: Why do you continue? Do you believe there is a prize, a hero's welcome at wherever the End may"
                        + " be? This is but a game, and you are just another player trying to get to the End, not knowing if you will benefit, not knowing if there is a purpose. \n- ?");
                break;
            case 4:
                app.getLightManager().fadeOut();
                currLevel = 3;
                currCutScene = new CutScene(Util.getMain(), currLevel, "I knew I could believe in you! You may just be the one to get to the End. Keep going, and good luck. \n- A Friend");
                break;
            case 5:
                app.getLightManager().fadeOut();
                currLevel = 4;
                currCutScene = new CutScene(Util.getMain(), currLevel, 35f, "And yet you continue even after I pointed out that there is no purpose? Are you courageous, stubborn, or just plain stupid? "
                        + "There is no reason to finish this game, for it is but a game. The End holds no surpise, no sense of achievement unless you find happiness in beating a simple game created "
                        + "by some simple developer. Now, I am starting to question your sanity! To waste your time on such a worthless, meaningless task like this, how can you be sane? Don't continue, "
                        + "this is simply not a practical use of your precious time. \n- ?");
                break;
            case 6:
                app.getLightManager().fadeOut();
                currLevel = 5;
                currCutScene = new CutScene(Util.getMain(), currLevel, 35f, "You did it! You got to the End, congratulations! I admire you for your persistance and patience. There were many unknown elements,"
                        + " and many tests to pass however you persisted and defeated each and every one. However... I believe there was also an outside force interfering with our communications, "
                        + "something must have been trying to convince you to quit, am I correct? Yes, it has happened before. I am not aware of what or who it is, but know this: your patience overcame the outsider's "
                        + "quick tongue and cunning. But now, continue on with your life, and maybe this small triumph brighten the rest of your day. Thank you. \n- A Friend \n\n\n\n\n\n\n\n\n\n\n\n\n\n\n"
                        + "Made by the\nPatrick Henry High School Technology Student Association Team\nfor the 2014 Season");
                break;
        }
    }

    public void loadLevel(int level) {
        changingLevels = true;
        switch (level) {
            case 1:
                currLevel = 1;
                Level toLoad1 = (Level) assetManager.loadModel("Models/LevelTut1.j3o");
                toLoad1.setData(this.app, "Test", toLoad1.getNode());
                map1.setMap(toLoad1, Level.LOADED_ON);
                currMap = map1;
                app.getLightManager().fadeIn();
                Util.log("Creating Ball");
                player = new RegularBall(this.app, currMap.getLevel().getSpawnPosition());
                break;
            case 2:
                currLevel = 2;
                Level toLoad2 = (Level) assetManager.loadModel("Models/Level1.j3o");
                toLoad2.setData(this.app, "Test", toLoad2.getNode());
                map2.setMap(toLoad2, Level.LOADED_ON);
                currMap = map2;
                app.getLightManager().fadeIn();
                break;
            case 3:
                currLevel = 3;
                Level toLoad3 = (Level) assetManager.loadModel("Models/Level2.j3o");
                toLoad3.setData(this.app, "Test", toLoad3.getNode());
                map3.setMap(toLoad3, Level.LOADED_ON);
                new Level2(toLoad3);
                currMap = map3;
                app.getLightManager().fadeIn();
                break;
            case 4:
                currLevel = 4;
                Level toLoad4 = (Level) assetManager.loadModel("Models/Level4.j3o");
                toLoad4.setData(this.app, "Test", toLoad4.getNode());
                map4.setMap(toLoad4, Level.LOADED_ON);
                currMap = map4;
                app.getLightManager().fadeIn();
                break;
            case 5:
                currLevel = 5;
                Level toLoad5 = (Level) assetManager.loadModel("Models/Level3.j3o");
                toLoad5.setData(this.app, "Test", toLoad5.getNode());
                map5.setMap(toLoad5, Level.LOADED_ON);
                currMap = map5;
                app.getLightManager().fadeIn();
                break;
            case 6:
                currLevel = 6;
                gameGuiController.goToEndGameScreen();
                bulletAppState.setEnabled(false);
                app.getLightManager().fadeIn();
                break;
        }
        changingLevels = false;
        onDialogue = false;
    }

    public void update(float tpf) {
        for (GlowingTextRenderer gtr : displayTexts) {
            gtr.update((player == null ? false : player.isDead()));
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
        if (currCutScene == null) {
            player.update(tpf);
            gameInputs.update(tpf);
            gameGuiController.update(tpf);
            currMap.getLevel().update();
        } else {
            if (currCutScene.isFinished()) {
                displayTextsToRemove.add(currCutScene);
                goToNextLevel(false);
                currCutScene = null;
                changingLevels = false;
            }
        }
    }

    public void goToNextLevel(boolean cutscene) {
        if (currMap != null) {
            currMap.delete();
        }
        for (GlowingTextRenderer gtr : displayTexts) {
            displayTextsToRemove.add(gtr);
        }

        currLevel++;
        Util.log(currLevel);
        changingLevels = true;
        if (cutscene) {
            loadCutscene(currLevel);
        } else {
            loadLevel(currLevel);
            player.captureMouse();
            player.setRestartPos(currMap.getLevel().getSpawnPosition());
            player.setGeometryPosition(currMap.getLevel().getSpawnPosition());
            player.setCollisionEnabled(true);
        }
    }

    public void restartLevel() {
        currMap.delete();
        loadLevel(currLevel);
        player.captureMouse();
        player.setRestartPos(currMap.getLevel().getSpawnPosition());
        player.setPosition(currMap.getLevel().getSpawnPosition());
        player.setCollisionEnabled(true);

        for (GlowingTextRenderer gtr : displayTexts) {
            displayTextsToRemove.add(gtr);
        }
        app.getLightManager().fadeIn();
    }

    public void goToPause() {
    }

    public void goToOptions() {
    }

    public void togglePause() {
        if (gameGuiController.isPaused()) {
            gameGuiController.goToGame();
            setEnabled(true);
            bulletAppState.setEnabled(true);
            player.captureMouse();
        } else {
            gameGuiController.goToPause();
            setEnabled(false);
            bulletAppState.setEnabled(false);
            player.releaseMouse();
        }
    }

    public GameGuiController getGameGuiController() {
        return gameGuiController;
    }

    public void cleanup() {
        super.cleanup();
        gameGuiController.getNifty().gotoScreen("start");
        gameGuiController.getNifty().exit();
        rootNode.detachAllChildren();
        app.getGuiNode().detachAllChildren();
        currMap.getLevel().removeAll();
        rootNode.detachChild(currMap.getLevel().getNode());
        currMap = null;
        stateManager.detach(bulletAppState);
    }

    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        if (enabled) {
        } else {
        }
    }

    public PlayerBall getPlayer() {
        return player;
    }

    public Map getCurrMap() {
        return currMap;
    }

    public void addGlowingTextRenderer(GlowingTextRenderer gtr) {
        this.displayTexts.add(gtr);
    }

    public Collection<GlowingTextRenderer> getRenderedTexts() {
        return displayTexts;
    }

    public BulletAppState getBulletAppState() {
        return bulletAppState;
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

    public GameInputs getGameInputManager() {
        return gameInputs;
    }

    public boolean onDialogue() {
        return onDialogue;
    }
    
    public boolean isChangingLevels() {
        return changingLevels;
    }
}

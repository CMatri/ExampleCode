package Engine.Gui;

import AppStates.GameState;
import Engine.Main;
import Engine.Utils.Util;
import com.jme3.app.Application;
import com.jme3.niftygui.NiftyJmeDisplay;
import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.NiftyEventSubscriber;
import de.lessvoid.nifty.controls.CheckBoxStateChangedEvent;
import de.lessvoid.nifty.effects.EffectEventId;
import de.lessvoid.nifty.elements.Element;
import de.lessvoid.nifty.screen.Screen;
import de.lessvoid.nifty.screen.ScreenController;
import java.io.File;
import java.lang.management.ManagementFactory;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GameGuiController implements ScreenController {

    private Nifty nifty;
    private Main app;
    private Screen screen;
    private GameState gameState;
    private boolean paused = false, canPause = true;

    ;

    public GameGuiController(GameState gameState, Application app) {
        this.gameState = gameState;
        this.app = (Main) app;
        Logger log = Logger.getLogger("de.lessvoid");
        log.setLevel(Level.ALL);

        this.app.getNiftyDisplay().initialize(app.getRenderManager(), app.getGuiViewPort());
        nifty = this.app.getNiftyDisplay().getNifty();
//        nifty.setDebugOptionPanelColors(true);
        nifty.registerScreenController(this);
        nifty.addXml("Interface/gameGui.xml");

        goToGame();

        app.getGuiViewPort().addProcessor(this.app.getNiftyDisplay());
    }

    public boolean isPaused() {
        return paused;
    }

    @Override
    public void bind(Nifty nifty, Screen screen) {
        this.nifty = nifty;
        this.screen = screen;
    }

    @Override
    public void onStartScreen() {
    }

    public void update(float tpf) {
    }

    @Override
    public void onEndScreen() {
    }

    public void quit() {
        app.stop();
    }

    public Nifty getNifty() {
        return nifty;
    }

    public void goToMainMenu() {
        try {
            StringBuilder cmd = new StringBuilder();
            cmd.append(System.getProperty("java.home") + File.separator + "bin" + File.separator + "java ");
            for (String jvmArg : ManagementFactory.getRuntimeMXBean().getInputArguments()) {
                cmd.append(jvmArg + " ");
            }
            cmd.append("-cp ").append(ManagementFactory.getRuntimeMXBean().getClassPath()).append(" ");
            cmd.append(Main.class.getName()).append(" ");
            Runtime.getRuntime().exec(cmd.toString());
            System.exit(0);
        } catch (Exception e) {
        }
    }

    public void goToPause() {
        if (canPause) {
            nifty.gotoScreen("pause");
            for (Element e : nifty.getCurrentScreen().getLayerElements()) {
                e.setVisible(true);
                e.startEffect(EffectEventId.onStartScreen);
            }
            paused = true;
        }
    }

    public void resumeGame() {
        if (canPause) {
            for (Element e : nifty.getCurrentScreen().getLayerElements()) {
                e.setVisible(false);
            }
            nifty.gotoScreen("start0");
            gameState.togglePause();
            paused = false;
        }
    }

    public void goToGame() {
        if (canPause) {
            for (Element e : nifty.getCurrentScreen().getLayerElements()) {
                e.setVisible(false);
            }

            paused = false;
        }
    }

    public void goToEndGameScreen() {
        canPause = false;
        nifty.gotoScreen("endGame");
        gameState.setEnabled(false);
        gameState.getBulletAppState().setEnabled(false);
        Util.log("Completed Game.");
    }
}

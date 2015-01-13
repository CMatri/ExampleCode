package Engine.Gui;

import AppStates.MainMenuState;
import Engine.Main;
import Engine.Utils.Util;
import com.jme3.asset.plugins.FileLocator;
import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.NiftyEventSubscriber;
import de.lessvoid.nifty.controls.Button;
import de.lessvoid.nifty.controls.CheckBoxStateChangedEvent;
import de.lessvoid.nifty.controls.Menu;
import de.lessvoid.nifty.controls.MenuItemActivatedEvent;
import de.lessvoid.nifty.controls.TextField;
import de.lessvoid.nifty.controls.TextFieldChangedEvent;
import de.lessvoid.nifty.controls.button.ButtonControl;
import de.lessvoid.nifty.elements.Element;
import de.lessvoid.nifty.screen.Screen;
import de.lessvoid.nifty.screen.ScreenController;
import de.lessvoid.nifty.tools.SizeValue;
import java.io.File;
import org.bushe.swing.event.EventTopicSubscriber;

public class MainMenuGuiController implements ScreenController {

    private Nifty nifty;
    private Main app;
    private Screen screen;
    private MainMenuState mainMenuState;

    public MainMenuGuiController(MainMenuState mainMenuState, Main app) {
        this.mainMenuState = mainMenuState;
        this.app = (Main) app;
//        Logger log = Logger.getLogger("de.lessvoid");
//        log.setLevel(Level.SEVERE);

        nifty = app.getNiftyDisplay().getNifty();
        app.getNiftyDisplay().initialize(app.getRenderManager(), app.getGuiViewPort());
//        nifty.setDebugOptionPanelColors(true);
        nifty.registerScreenController(this);
        nifty.addXml("Interface/mainMenuGui.xml");
        nifty.gotoScreen("start");

        app.getGuiViewPort().addProcessor(app.getNiftyDisplay());
    }

    @NiftyEventSubscriber(id = "simpleCheckBoxSSAO")
    public void onCheckBoxChangedSSAO(final String id, CheckBoxStateChangedEvent event) {
        app.getLightManager().setSSAO(event.isChecked());
    }

    @NiftyEventSubscriber(id = "simpleCheckBoxDOF")
    public void onCheckBoxChangedDOF(final String id, CheckBoxStateChangedEvent event) {
        app.getLightManager().setDOF(event.isChecked());
    }

    @NiftyEventSubscriber(id = "simpleCheckBoxFPS")
    public void onCheckBoxChangedFPS(final String id, CheckBoxStateChangedEvent event) {
        app.setDisplayFps(event.isChecked());
    }

    @NiftyEventSubscriber(id = "name")
    public void onTextFieldChangedName(final String id, TextFieldChangedEvent event) {
        String userHome = System.getProperty("user.home");
        if (!new File("./assets/Models/" + event.getText() + ".j3o").exists()) {
//        if (!new File(userHome + "/.slythGame/" + event.getText() + ".j3o").exists()) {
            screen.findControl("accept", ButtonControl.class).setEnabled(false);
        } else {
            screen.findControl("accept", ButtonControl.class).setEnabled(true);
        }
    }

    @Override
    public void bind(Nifty nifty, Screen screen) {
        this.nifty = nifty;
        this.screen = screen;
    }

    @Override
    public void onStartScreen() {
    }

    @Override
    public void onEndScreen() {
    }

    public void quit() {
        app.stop();
    }

    public void goToCampaign() {
        mainMenuState.goToCampaign();
    }

    public void goToEditor() {
        nifty.gotoScreen("levelCreate");
//        app.toEditor(mainMenuState, true);
    }

    public void createNewLevel() {
//        nifty.gotoScreen("levelName");
        app.toEditor(mainMenuState, true, "");
    }

    public void loadSavedLevel() {
        nifty.gotoScreen("levelLoad");
    }

    public void beginLoadedLevel() {
        String loadName = screen.findNiftyControl("name", TextField.class).getDisplayedText();
        app.toEditor(mainMenuState, false, loadName);
    }

    public void startLevel() {
        String saveName = screen.findNiftyControl("name", TextField.class).getDisplayedText();
        app.toEditor(mainMenuState, true, saveName);
    }

    public Nifty getNifty() {
        return nifty;
    }

    public void goToOptions() {
        nifty.gotoScreen("options");
        mainMenuState.goToOptions();
    }

    public void goToMain() {
        nifty.gotoScreen("start");
        mainMenuState.goToMainMenu();
    }
}

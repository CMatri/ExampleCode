/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Engine.Gui;

import AppStates.EditorState;
import Engine.Main;
import Engine.Utils.Util;
import com.jme3.app.Application;
import com.jme3.niftygui.NiftyJmeDisplay;
import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.NiftyEventSubscriber;
import de.lessvoid.nifty.controls.Slider;
import de.lessvoid.nifty.controls.SliderChangedEvent;
import de.lessvoid.nifty.controls.TextField;
import de.lessvoid.nifty.elements.render.PanelRenderer;
import de.lessvoid.nifty.elements.render.TextRenderer;
import de.lessvoid.nifty.screen.Screen;
import de.lessvoid.nifty.screen.ScreenController;
import de.lessvoid.nifty.tools.Color;
import java.io.File;
import java.lang.management.ManagementFactory;
import java.text.DecimalFormat;
import java.util.logging.Level;
import java.util.logging.Logger;

public class EditorGuiController implements ScreenController {

    private Nifty nifty;
    private boolean onLights = false;
    private Main app;
    private EditorState editorState;
    private Screen screen;
    private float radius;
    private Color backgroundColor = new Color(Color.WHITE);
    private DecimalFormat dFormatter;

    public EditorGuiController(EditorState editorState, Application app) {
        this.editorState = editorState;
        this.app = (Main) app;
        Logger log = Logger.getLogger("de.lessvoid");
        log.setLevel(Level.SEVERE);
        NiftyJmeDisplay niftyDisplay = this.app.getNiftyDisplay();
        nifty = niftyDisplay.getNifty();
//        nifty.setDebugOptionPanelColors(true);
        nifty.registerScreenController(this);
        nifty.addXml("Interface/editorGuis.xml");
        nifty.gotoScreen("start");

//        app.getGuiViewPort().addProcessor(niftyDisplay);
        dFormatter = new DecimalFormat();
        dFormatter.applyPattern("#.#");
    }

    public void toggleLights(Color color, float radius) {
        this.radius = radius;
        backgroundColor = color;
        if (onLights) {
            nifty.gotoScreen("start");
            onLights = false;
            app.getFlyByCamera().setDragToRotate(false);
            app.getFlyByCamera().setEnabled(true);
            editorState.getEditorInputs().resetMarker();
        } else {
            nifty.gotoScreen("hud");
            app.getFlyByCamera().setDragToRotate(true);
            app.getFlyByCamera().setEnabled(false);
            onLights = true;
        }
    }

    public void saveGame() {
        String saveName = screen.findNiftyControl("name", TextField.class).getDisplayedText();
//        String saveLevel = screen.findNiftyControl("saveLevel", TextField.class).getDisplayedText();
//        editorState.getMap().getLevel().setSaveLevel(saveLevel);
        editorState.getMap().getLevel().setSaveLevel("test");
        app.setSaveName(saveName);
        try {
            StringBuilder cmd = new StringBuilder();
            cmd.append(System.getProperty("java.home") + File.separator + "bin" + File.separator + "java ");
            for (String jvmArg : ManagementFactory.getRuntimeMXBean().getInputArguments()) {
                cmd.append(jvmArg + " ");
            }
            cmd.append("-cp ").append(ManagementFactory.getRuntimeMXBean().getClassPath()).append(" ");
            cmd.append(Main.class.getName()).append(" ");
            Runtime.getRuntime().exec(cmd.toString());
            app.stop();
        } catch (Exception e) {
        }
    }

    public void goToEditor() {
        nifty.gotoScreen("start");
        app.getFlyByCamera().setDragToRotate(false);
        app.getFlyByCamera().setEnabled(true);
    }

    public void goToExit() {
        nifty.gotoScreen("saveGame");
        app.getFlyByCamera().setDragToRotate(true);
        app.getFlyByCamera().setEnabled(false);
    }

    public void quit() {
        app.stop();
    }

    public void toggleLightGui() {
        if (onLights) {
            nifty.gotoScreen("start");
            onLights = false;
            app.getFlyByCamera().setDragToRotate(false);
            app.getFlyByCamera().setEnabled(true);
            editorState.getEditorInputs().resetMarker();
        } else {
            nifty.gotoScreen("hud");
            app.getFlyByCamera().setDragToRotate(true);
            app.getFlyByCamera().setEnabled(false);
            onLights = true;
        }
    }

    public boolean onLights() {
        return onLights;
    }

    public Color getLightColor() {
        return backgroundColor;
    }

    public float getRadius() {
        return radius;
    }

    @NiftyEventSubscriber(id = "sliderRed")
    public void onSliderChangedRed(final String id, SliderChangedEvent event) {
        backgroundColor.setRed(1 - event.getValue());
        nifty.getCurrentScreen().findElementByName("colorPanel").getRenderer(PanelRenderer.class).setBackgroundColor(backgroundColor);
    }

    @NiftyEventSubscriber(id = "sliderGreen")
    public void onSliderChangedGreen(final String id, SliderChangedEvent event) {
        backgroundColor.setGreen(1 - event.getValue());
        nifty.getCurrentScreen().findElementByName("colorPanel").getRenderer(PanelRenderer.class).setBackgroundColor(backgroundColor);
    }

    @NiftyEventSubscriber(id = "sliderBlue")
    public void onSliderChangedBlue(final String id, SliderChangedEvent event) {
        backgroundColor.setBlue(1 - event.getValue());
        nifty.getCurrentScreen().findElementByName("colorPanel").getRenderer(PanelRenderer.class).setBackgroundColor(backgroundColor);
    }

    @NiftyEventSubscriber(id = "sliderRadius")
    public void onSliderChangedRadius(final String id, SliderChangedEvent event) {
        radius = Float.valueOf(dFormatter.format(event.getValue()));
        screen.findElementByName("labelRadius").getRenderer(TextRenderer.class).setText("Radius: " + (radius % 1 == 0.0 ? (int) radius : radius + ""));
    }

    @Override
    public void bind(Nifty nifty, Screen screen) {
        this.nifty = nifty;
        this.screen = screen;
    }

    @Override
    public void onStartScreen() {
        if (screen.getScreenId().equals("hud")) {
            screen.findElementByName("labelRadius").getRenderer(TextRenderer.class).setText("Radius: " + ((radius % 1 == 0.0) ? (int) radius : radius + ""));
            screen.findNiftyControl("sliderRadius", Slider.class).setValue(radius);
            screen.findNiftyControl("sliderRed", Slider.class).setValue(1 - backgroundColor.getRed());
            screen.findNiftyControl("sliderBlue", Slider.class).setValue(1 - backgroundColor.getBlue());
            screen.findNiftyControl("sliderGreen", Slider.class).setValue(1 - backgroundColor.getGreen());
            screen.findElementByName("colorPanel").getRenderer(PanelRenderer.class).setBackgroundColor(backgroundColor);
        } else if (screen.getScreenId().equals("saveGame")) {
            screen.findNiftyControl("name", TextField.class).setText(editorState.getMap().getLevel().getSaveName());
            if (editorState.getMap().getLevel().getLevelClass() != null) {
                screen.findNiftyControl("saveLevel", TextField.class).setText(editorState.getMap().getLevel().getLevelClass());
            }
        }
    }

    @Override
    public void onEndScreen() {
    }
}

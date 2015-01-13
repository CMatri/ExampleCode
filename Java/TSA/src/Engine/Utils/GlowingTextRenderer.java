package Engine.Utils;

import AppStates.GameState;
import AppStates.MainMenuState;
import Engine.Main;
import com.jme3.app.state.AbstractAppState;
import com.jme3.font.BitmapFont;
import com.jme3.font.BitmapText;
import com.jme3.font.LineWrapMode;
import com.jme3.font.Rectangle;

public class GlowingTextRenderer {

    Main main;
    String text;
    float coords;
    public BitmapText txt;
    boolean grow = false;
    float scale = 0.01f;
    public boolean kill = false;
    public boolean toKill = false;
    private float untilDeath = 0, toDeath = 0;

    public GlowingTextRenderer(Main main, String string, float x, float y) {
        this(main, main.getStateManager().getState(GameState.class), string, x, y);
    }

    public GlowingTextRenderer(Main main, MainMenuState gameState, String string, float x, float y) {
        this.main = main;
        txt = new BitmapText(main.getGameFont(), false);
        txt.setText(string);
        txt.setBox(new Rectangle(x - x / 1.2f, y, 800, 10));
        txt.setEllipsisChar('\n');
        txt.setLineWrapMode(LineWrapMode.Word);
        txt.setAlignment(BitmapFont.Align.Center);
        txt.setAlpha(0.1f);
        main.getGuiNode().attachChild(txt);
        gameState.addGlowingTextRenderer(this);
        scale = 0.01f;
    }

    public GlowingTextRenderer(Main main, GameState gameState, String string, float x, float y) {
        this.main = main;
        txt = new BitmapText(main.getGameFont(), false);
        txt.setText(string);
        txt.setBox(new Rectangle(x - x / 1.2f, y, 800, 10));
        txt.setEllipsisChar('\n');
        txt.setLineWrapMode(LineWrapMode.Word);
        txt.setAlignment(BitmapFont.Align.Center);
        txt.setAlpha(0.1f);
        main.getGuiNode().attachChild(txt);
        gameState.addGlowingTextRenderer(this);
        scale = 0.01f;
    }

    public BitmapText getTextBox() {
        return txt;
    }

    public void update(boolean playerDead) {
        if (scale > 0) {
            txt.setAlpha(txt.getAlpha() + (grow ? (scale) : (-scale)));
        } else if(scale <= 0 && txt.getAlpha() < 1.0f) {
            txt.setAlpha(txt.getAlpha() + 0.05f);
        }

        float s = txt.getAlpha();
        if (s >= 0.9f) {
            grow = false;
        } else if (s <= 0.3f) {
            grow = true;
        }
        if (toKill && toDeath >= untilDeath) {
            if (s <= 0.3f) {
                kill = true;
            }
        } else if (toKill && toDeath < untilDeath) {
            toDeath++;
        }

        if (playerDead) {
            txt.setAlpha(1.0f);
            kill();
        }
    }

    public void setScale(float scale) {
        this.scale = scale;
    }

    public void kill() {
        toKill = true;
        scale = 0.05f;
    }

    public void kill(float untilDeath) {
        toKill = true;
        this.untilDeath = untilDeath;
    }
}

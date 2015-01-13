package Engine.Gui;

import Engine.Main;
import Engine.Utils.GlowingTextRenderer;
import Engine.Utils.Util;

public class CutScene extends GlowingTextRenderer {

    private boolean finished = false;
    private int level;
    private float moveSpeed;

    public CutScene(Main main, int level, String text) {
        this(main, level, 50f, text);
    }

    public CutScene(Main main, int level, float moveSpeed, String text) {
        super(main, text, main.getViewPort().getCamera().getWidth(), 0);
        setScale(0.0f);
        this.moveSpeed = moveSpeed;
        this.level = level;
    }

    public void update(boolean playerDead) {
        super.update(playerDead);
        getTextBox().move(0, moveSpeed * Util.getMain().getTimer().getTimePerFrame(), 0);
        if (getTextBox().getLocalTranslation().getY() > Util.getMain().getViewPort().getCamera().getHeight() + getTextBox().getHeight()) {
            finished = true;
        }
    }

    public boolean isFinished() {
        return finished;
    }

    public int getLevel() {
        return level;
    }
}

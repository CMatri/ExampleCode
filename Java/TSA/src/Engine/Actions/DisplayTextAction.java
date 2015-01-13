package Engine.Actions;

import Engine.Main;
import Engine.Utils.GlowingTextRenderer;
import Engine.Utils.Util;

public class DisplayTextAction extends Action {

    public boolean outOfRangeCall = false;
    private boolean created = false;
    private GlowingTextRenderer gtr;

    public DisplayTextAction(String toDisplay, float x, float y, float z, float textX, float textY) {
        super(ID.NEAR);
        addUserData(toDisplay);
        addUserData(x);
        addUserData(y);
        addUserData(z);
        addUserData(textX);
        addUserData(textY);
    }

    public void actionPerformed(Main main) {
        if (!created) {
            String toDisplay = (String) getUserData(0);
            float x = (float) getUserData(4);
            float y = (float) getUserData(5);
            gtr = new GlowingTextRenderer(main, toDisplay, x, y);
            created = true;
        }
        outOfRangeCall = true;
    }

    public void onceOutOfRange() {
        gtr.kill();
        gtr = null;
        created = false;
        outOfRangeCall = false;
    }
}

package Engine.Actions;

import AppStates.GameState;
import Engine.Blocks.BlockFactory;
import Engine.Levels.Level;
import Engine.Main;
import com.jme3.math.Vector3f;

public class MakeBlockAction extends Action {

    boolean first = true;
    Level l;

    public MakeBlockAction() {
        super(Action.ID.WALK_ON);
    }

    public MakeBlockAction(float x, float y, float z, BlockFactory block, Level l) {
        super(Action.ID.WALK_ON);
        addUserData(x);
        addUserData(y);
        addUserData(z);
        this.l = l;
    }

    public void actionPerformed(Main main) {
        if (first) {
            Vector3f pos = new Vector3f((float) getUserData(0), (float) getUserData(1), (float) getUserData(2));
            main.getStateManager().getState(GameState.class).getCurrMap().getLevel().makeBlock(pos, Level.wallBlockFactory);
            first = false;
        }
    }
}

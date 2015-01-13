package Engine.Actions;

import AppStates.GameState;
import Engine.Blocks.BlockFactory;
import Engine.Levels.Level;
import Engine.Main;
import Engine.Utils.Util;
import com.jme3.math.Vector3f;

public class MakeWallAction extends Action {

    boolean first = true;
    Level l;

    public MakeWallAction(Vector3f startPos, Vector3f endPos, BlockFactory block, Level l) {
        super(Action.ID.WALK_ON);
        addUserData(startPos);
        addUserData(endPos);
        addUserData(block);
        this.l = l;
    }

    public void actionPerformed(Main main) {
        if (first) {
            main.getStateManager().getState(GameState.class).getCurrMap().getLevel().makeWall((Vector3f) getUserData(0), (Vector3f) getUserData(1), (BlockFactory) getUserData(2));
            first = false;
        }
    }
}

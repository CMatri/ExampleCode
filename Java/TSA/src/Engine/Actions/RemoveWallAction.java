package Engine.Actions;

import AppStates.GameState;
import Engine.Main;
import Engine.Utils.Util;
import com.jme3.math.Vector3f;

public class RemoveWallAction extends Action {

    public RemoveWallAction(Vector3f startPos, Vector3f endPos) {
        super(Action.ID.WALK_ON);
        addUserData(startPos);
        addUserData(endPos);
    }

    public void actionPerformed(Main main) {
        Vector3f startPos = (Vector3f) getUserData(0);
        Vector3f endPos = (Vector3f) getUserData(1);
        main.getStateManager().getState(GameState.class).getCurrMap().getLevel().removeWall(startPos, endPos);
    }
}

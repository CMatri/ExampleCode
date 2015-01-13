package Engine.Actions;

import AppStates.GameState;
import Engine.Blocks.BlockSuper;
import Engine.Main;
import Engine.Utils.Util;

public class BreakBlockAction extends Action {

    BlockSuper toOpen;

    public BreakBlockAction(BlockSuper toOpen) {
        super(ID.WALK_ON);
        this.toOpen = toOpen;
        addUserData(toOpen);
    }

    public void actionPerformed(Main main, ID id) {
        if (id == ID.WALK_ON) {
            toOpen = (BlockSuper) getUserData(0);
            main.getStateManager().getState(GameState.class).getCurrMap().getLevel().removeBlock(toOpen.getPosition());
        }
    }
}

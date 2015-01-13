package Engine.Levels;

import Engine.Actions.RemoveWallAction;
import Engine.Utils.Util;
import com.jme3.math.Vector3f;

public class Level2 {

    private Level l;

    public Level2(Level l) {
        this.l = l;

        l.getCube(12, 1, 3).addAction(new RemoveWallAction(new Vector3f(16, 3, 8), new Vector3f(16, 4, 9)));
        l.getCube(12, 1, 4).addAction(new RemoveWallAction(new Vector3f(16, 3, 8), new Vector3f(16, 4, 9)));

        l.getCube(18, 1, 14).addAction(new RemoveWallAction(new Vector3f(21, 3, 8), new Vector3f(21, 4, 9)));
        l.getCube(19, 1, 14).addAction(new RemoveWallAction(new Vector3f(21, 3, 8), new Vector3f(21, 4, 9)));
    }
}

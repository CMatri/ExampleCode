package Engine.Blocks;

import Engine.Balls.PlayerBall;
import Engine.Levels.Level;
import Engine.Main;
import Engine.Utils.Util;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;

public class Block_CheckPoint extends BlockSolid {

    private boolean setBefore = false;

    public Block_CheckPoint(Main main, int x, int y, int z, Level level, boolean creating) {
        super(main, new Vector3f(x, y, z), level, ColorRGBA.Green, new Vector3f(main.getBlockSize() / 1.2f, main.getBlockSize() / 15, main.getBlockSize() / 1.2f), "Checkpoint", creating, Level.checkPointBlockFactory);
    }

    public void delete() {
    }

    public void update() {
        super.update();
    }

    public void collideWithBall(PlayerBall ball) {
        if (!setBefore) {
            super.collideWithBall(ball);
            ball.setRestartPos(getPosition().add(0, 5, 0));
            setBefore = true;
        }
    }

    public boolean getSetBefore() {
        return this.setBefore;
    }
}

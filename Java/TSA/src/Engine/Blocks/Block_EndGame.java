package Engine.Blocks;

import AppStates.GameState;
import Engine.Balls.PlayerBall;
import Engine.Levels.Level;
import Engine.Main;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;

public class Block_EndGame extends BlockSolid {

    Main main;

    public Block_EndGame(Main main, int x, int y, int z, Level level, boolean creating) {
        super(main, new Vector3f(x, y, z), level, ColorRGBA.Blue, new Vector3f(main.getBlockSize() / 1.2f, main.getBlockSize() / 20, main.getBlockSize() / 1.2f), "End Game", creating, Level.endGameBlockFactory);
        setBlockColor(ColorRGBA.Blue);
        this.main = main;
    }

    public void setUp() {
    }

    public void delete() {
    }

    public void update() {
        super.update();
    }

    public void collideWithBall(PlayerBall ball) {
        super.collideWithBall(ball);
        if (!main.getStateManager().getState(GameState.class).getCurrMap().getLevel().isRemovingAll()) {
            main.getStateManager().getState(GameState.class).getCurrMap().getLevel().removeAll();
            main.getStateManager().getState(GameState.class).getCurrMap().getLevel().completed();
            main.getStateManager().getState(GameState.class).getPlayer().setCollisionEnabled(false);
            main.getStateManager().getState(GameState.class).getPlayer().setAngularVelocity(new Vector3f(0, 0, 0));
        }
    }
}

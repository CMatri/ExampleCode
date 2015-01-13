package Engine.Blocks;

import AppStates.GameState;
import Engine.Balls.PlayerBall;
import Engine.Levels.Level;
import Engine.Main;
import Engine.Utils.GlowingTextRenderer;
import Engine.Utils.Util;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import java.util.ArrayList;

public class Block_PressurePlate extends BlockSolid {

    public Block_PressurePlate(Main main, int x, int y, int z, Level level, boolean creating) {
        super(main, new Vector3f(x, y, z), level, ColorRGBA.Red, new Vector3f(main.getBlockSize() / 1.2f, main.getBlockSize() / 20, main.getBlockSize() / 1.2f), "Pressure Plate", creating, Level.pressurePlateBlockFactory);
    }

    public void update() {
        super.update();
    }

    public void collideWithBall(PlayerBall ball) {
        super.collideWithBall(ball);
    }
}

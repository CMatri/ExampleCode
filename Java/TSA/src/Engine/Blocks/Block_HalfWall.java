package Engine.Blocks;

import Engine.Balls.PlayerBall;
import Engine.Levels.Level;
import Engine.Main;
import Engine.Utils.Util;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;

public class Block_HalfWall extends BlockSolid {

    public Block_HalfWall(Main main, int x, int y, int z, Level level, boolean creating) {
        super(main, new Vector3f(x, y, z), level, ColorRGBA.White, new Vector3f(0.5f, 0.25f, 0.5f), "Half Wall", creating, Level.halfWallBlockFactory);
        setBlockColor(ColorRGBA.White);
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
    }
}

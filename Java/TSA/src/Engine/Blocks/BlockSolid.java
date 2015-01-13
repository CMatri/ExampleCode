package Engine.Blocks;

import Engine.Levels.Level;
import Engine.Main;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;

public abstract class BlockSolid extends BlockSuper {

    public BlockSolid(Main main, Vector3f position, Level level, String id, boolean creating, BlockFactory factory) {
        super(main, position, level, ColorRGBA.White, id, creating, true, factory);
    }

    public BlockSolid(Main main, Vector3f position, Level level, ColorRGBA color, String id, boolean creating, BlockFactory factory) {
        super(main, position, level, color, new Vector3f(main.getBlockSize(), main.getBlockSize(), main.getBlockSize()), id, creating, true, factory);
    }

    public BlockSolid(Main main, Vector3f position, Level level, ColorRGBA color, Vector3f dimensions, String id, boolean creating, BlockFactory factory) {
        super(main, position, level, color, dimensions, id, creating, true, factory);
    }
}

package Engine.Levels;

import Engine.Actions.DisplayTextAction;
import Engine.Actions.MakeWallAction;
import Engine.Actions.RemoveWallAction;
import Engine.Blocks.Block_Shooter;
import Engine.Blocks.Block_Trampoline;
import static Engine.Levels.Level.pressurePlateBlockFactory;
import static Engine.Levels.Level.trampolineBlockFactory;
import static Engine.Levels.Level.wallBlockFactory;
import com.jme3.math.Vector3f;

public class Level1 extends Level {

    public Level1() {
        super();
    }

    public void setUpLevel(boolean loaded) {
        if (loaded) {
            setLevelSize(90, 256, 90);

            //Piece 1
            makeWall(new Vector3f(1, 0, 0), new Vector3f(2, 0, 14), wallBlockFactory);
            makeBlock(1, 1, 6, trampolineBlockFactory);
            makeBlock(2, 1, 6, trampolineBlockFactory);
            Block_Trampoline b1 = (Block_Trampoline) getCube(1, 1, 6);
            b1.setStrength(4);
            Block_Trampoline b2 = (Block_Trampoline) getCube(2, 1, 6);
            b2.setStrength(4);

            //Piece 2
            makeBlock(7, 1, 13, pressurePlateBlockFactory);
            makeBlock(7, 1, 14, pressurePlateBlockFactory);

            makeWall(new Vector3f(12, 1, 13), new Vector3f(12, 1, 14), wallBlockFactory);
            makeWall(new Vector3f(13, 2, 13), new Vector3f(13, 2, 14), wallBlockFactory);
            makeWall(new Vector3f(14, 3, 13), new Vector3f(14, 3, 14), wallBlockFactory);
            makeWall(new Vector3f(15, 4, 13), new Vector3f(15, 4, 14), wallBlockFactory);
            makeWall(new Vector3f(16, 5, 13), new Vector3f(16, 5, 14), wallBlockFactory);

            //Piece 4
            makeWall(new Vector3f(12, 29, 6), new Vector3f(12, 35, 9), wallBlockFactory);
            makeWall(new Vector3f(7, 29, 6), new Vector3f(12, 35, 6), wallBlockFactory);
            makeWall(new Vector3f(7, 29, 9), new Vector3f(12, 35, 9), wallBlockFactory);
            makeWall(new Vector3f(6, 29, 6), new Vector3f(6, 35, 9), wallBlockFactory);
            makeBlock(7, 30, 7, pressurePlateBlockFactory);
            makeBlock(7, 30, 8, pressurePlateBlockFactory);
            getCube(7, 30, 7).addAction(new RemoveWallAction(new Vector3f(7, 29, 7), new Vector3f(7, 30, 8)));
            getCube(7, 30, 8).addAction(new RemoveWallAction(new Vector3f(7, 29, 7), new Vector3f(7, 30, 8)));

            //Piece 5
            makeWall(new Vector3f(3, 0, 6), new Vector3f(10, 4, 6), wallBlockFactory);
            makeWall(new Vector3f(3, 1, 6), new Vector3f(10, 1, 12), endGameBlockFactory);
            addPhysics();
        } else {
            super.setUpLevel(loaded);

            makeWall(new Vector3f(3, 0, 13), new Vector3f(11, 0, 14), wallBlockFactory);
            makeWall(new Vector3f(8, 0, 13), new Vector3f(11, 0, 14), wallBlockFactory);

            getCube(7, 1, 14).addAction(new DisplayTextAction("Those red blocks are pressure plate blocks. They can remove or create blocks. Step on these ones to create a bridge.", 2.5f, 2.5f, 2.5f, main.getViewPort().getCamera().getWidth(), 550));
            getCube(7, 1, 13).addAction(new MakeWallAction(new Vector3f(8, 0, 13), new Vector3f(11, 0, 14), wallBlockFactory, this));
            getCube(7, 1, 14).addAction(new MakeWallAction(new Vector3f(8, 0, 13), new Vector3f(11, 0, 14), wallBlockFactory, this));

            getCube(7, 30, 7).addAction(new RemoveWallAction(new Vector3f(7, 29, 7), new Vector3f(7, 30, 8)));
            getCube(7, 30, 8).addAction(new RemoveWallAction(new Vector3f(7, 29, 7), new Vector3f(7, 30, 8)));

            removeWall(new Vector3f(1, 0, 7), new Vector3f(2, 0, 9));
            removeWall(new Vector3f(8, 0, 13), new Vector3f(11, 0, 14));

            ((Block_Shooter) (getCube(21, 1, 15))).setData(Block_Shooter.Orient.ZNeg, 250, 0);
            ((Block_Shooter) (getCube(22, 1, 12))).setData(Block_Shooter.Orient.ZPos, 250, 47);
            ((Block_Shooter) (getCube(23, 1, 15))).setData(Block_Shooter.Orient.ZNeg, 250, 94);
            ((Block_Shooter) (getCube(24, 1, 12))).setData(Block_Shooter.Orient.ZPos, 250, 141);
            ((Block_Shooter) (getCube(25, 1, 15))).setData(Block_Shooter.Orient.ZNeg, 250, 188);
            ((Block_Shooter) (getCube(26, 1, 12))).setData(Block_Shooter.Orient.ZPos, 250, 235);

            getCube(17, 5, 13).addAction(new DisplayTextAction("Look out for orange shooter blocks. They'll knock you out on contact!", 3, 3, 3, main.getViewPort().getCamera().getWidth(), 550));
        }
    }

    public void update() {
        super.update();
    }
}

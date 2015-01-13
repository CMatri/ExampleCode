package Engine.Levels;

import Engine.Actions.DisplayTextAction;
import Engine.Actions.MakeWallAction;
import Engine.Blocks.Block_PressurePlate;
import Engine.Blocks.Block_Shooter;
import Engine.Blocks.Block_Trampoline;
import Engine.Utils.Util;
import com.jme3.math.Vector3f;

public class Level3 {

    private Level l;

    public Level3(Level l) {
        this.l = l;
        
        l.getCube(6, 6, 3).addAction(new DisplayTextAction("Sometimes you have to have faith in what's below...", 2, 2, 2, Util.getMain().getViewPort().getCamera().getWidth(), 550));
        l.getCube(7, 6, 24).addAction(new DisplayTextAction("Good luck!", 2, 2, 2, Util.getMain().getViewPort().getCamera().getWidth(), 550));
        l.getCube(22, 0, 23).addAction(new DisplayTextAction("Think. There is another way around this, even if it seems like cheating.", 2, 2, 2, Util.getMain().getViewPort().getCamera().getWidth(), 550));
//        l.makeBlock(6, 7, 3, Level.checkPointBlockFactory);

        ((Block_Trampoline) (l.getCube(6, 1, 7))).setStrength(10);
        ((Block_Trampoline) (l.getCube(7, 1, 7))).setStrength(10);

        ((Block_Shooter) (l.getCube(6, 5, 12))).setData(Block_Shooter.Orient.YPos, 200, 0);
        ((Block_Shooter) (l.getCube(7, 5, 12))).setData(Block_Shooter.Orient.YPos, 200, 0);

        ((Block_Shooter) (l.getCube(6, 5, 15))).setData(Block_Shooter.Orient.YPos, 200, 0);
        ((Block_Shooter) (l.getCube(7, 5, 15))).setData(Block_Shooter.Orient.YPos, 200, 0);

        ((Block_Shooter) (l.getCube(6, 5, 18))).setData(Block_Shooter.Orient.YPos, 200, 0);
        ((Block_Shooter) (l.getCube(7, 5, 18))).setData(Block_Shooter.Orient.YPos, 200, 0);

        ((Block_Shooter) (l.getCube(6, 5, 21))).setData(Block_Shooter.Orient.YPos, 200, 0);
        ((Block_Shooter) (l.getCube(7, 5, 21))).setData(Block_Shooter.Orient.YPos, 200, 0);


        ((Block_Shooter) (l.getCube(9, 4, 26))).setData(Block_Shooter.Orient.XNeg, 250, 30);
        ((Block_Shooter) (l.getCube(9, 3, 28))).setData(Block_Shooter.Orient.XNeg, 250, 60);
        ((Block_Shooter) (l.getCube(9, 2, 25))).setData(Block_Shooter.Orient.XNeg, 250, 90);

        ((Block_Shooter) (l.getCube(6, 5, 24))).setData(Block_Shooter.Orient.ZPos, 250, 15);

        ((Block_Shooter) (l.getCube(4, 3, 26))).setData(Block_Shooter.Orient.XPos, 250, 45);
        ((Block_Shooter) (l.getCube(4, 2, 28))).setData(Block_Shooter.Orient.XPos, 250, 75);

        ((Block_Shooter) (l.getCube(4, 5, 27))).setData(Block_Shooter.Orient.XPos, 250, 0);

        ((Block_Shooter) (l.getCube(5, 5, 29))).setData(Block_Shooter.Orient.ZNeg, 250, 60);

        ((Block_Shooter) (l.getCube(21, 1, 25))).setData(Block_Shooter.Orient.XPos, 100, 0);
        ((Block_Shooter) (l.getCube(21, 1, 27))).setData(Block_Shooter.Orient.XPos, 100, 0);
        ((Block_Shooter) (l.getCube(21, 1, 29))).setData(Block_Shooter.Orient.XPos, 100, 0);

        ((Block_Shooter) (l.getCube(24, 1, 30))).setData(Block_Shooter.Orient.XNeg, 100, 50);
        ((Block_Shooter) (l.getCube(24, 1, 28))).setData(Block_Shooter.Orient.XNeg, 100, 50);
        ((Block_Shooter) (l.getCube(24, 1, 26))).setData(Block_Shooter.Orient.XNeg, 100, 50);

        ((Block_PressurePlate) (l.getCube(15, 5, 14))).addAction(new MakeWallAction(new Vector3f(22, 1, 17), new Vector3f(23, 1, 17), Level.trampolineBlockFactory, l));
        ((Block_PressurePlate) (l.getCube(16, 5, 14))).addAction(new MakeWallAction(new Vector3f(22, 1, 17), new Vector3f(23, 1, 17), Level.trampolineBlockFactory, l));

        ((Block_PressurePlate) (l.getCube(23, 1, 32))).addAction(new MakeWallAction(new Vector3f(15, 4, 15), new Vector3f(16, 4, 19), Level.wallBlockFactory, l));
        ((Block_PressurePlate) (l.getCube(22, 1, 32))).addAction(new MakeWallAction(new Vector3f(15, 4, 15), new Vector3f(16, 4, 19), Level.wallBlockFactory, l));
    }
}

package Engine.Levels;

import Engine.Actions.DisplayTextAction;
import Engine.Actions.MakeWallAction;
import Engine.Actions.RemoveWallAction;
import Engine.Blocks.Block_Shooter;
import Engine.Utils.Util;
import com.jme3.math.Vector3f;

public class Level4 {

    private Level l;

    public Level4(Level l) {
        this.l = l;
        
        l.setSpawnPosition(new Vector3f(0, 20, 0));

        ((Block_Shooter) (l.getCube(0, 2, 1))).setData(Block_Shooter.Orient.ZPos, 400, 0);
        ((Block_Shooter) (l.getCube(1, 2, 1))).setData(Block_Shooter.Orient.ZPos, 400, 0);
        ((Block_Shooter) (l.getCube(0, 1, 1))).setData(Block_Shooter.Orient.ZPos, 400, 0);
        ((Block_Shooter) (l.getCube(1, 1, 1))).setData(Block_Shooter.Orient.ZPos, 400, 0);

        ((Block_Shooter) (l.getCube(8, 1, 7))).setData(Block_Shooter.Orient.ZNeg, 400, 0);
        ((Block_Shooter) (l.getCube(8, 2, 7))).setData(Block_Shooter.Orient.ZNeg, 400, 0);
        ((Block_Shooter) (l.getCube(7, 1, 7))).setData(Block_Shooter.Orient.ZNeg, 400, 0);
        ((Block_Shooter) (l.getCube(7, 2, 7))).setData(Block_Shooter.Orient.ZNeg, 400, 0);

        l.getCube(4, 4, 7).addAction(new MakeWallAction(new Vector3f(9, 4, 0), new Vector3f(9, 4, 1), Level.trampolineBlockFactory, l));
        l.getCube(4, 4, 8).addAction(new MakeWallAction(new Vector3f(9, 4, 0), new Vector3f(9, 4, 1), Level.trampolineBlockFactory, l));

        l.getCube(18, 1, 4).addAction(new MakeWallAction(new Vector3f(19, 0, 4), new Vector3f(30, 0, 5), Level.wallBlockFactory, l));
        l.getCube(18, 1, 5).addAction(new MakeWallAction(new Vector3f(19, 0, 4), new Vector3f(30, 0, 5), Level.wallBlockFactory, l));

        l.getCube(31, 0, 4).addAction(new DisplayTextAction("Sometimes you need to fail once in order to ultimately succeed...", 2, 2, 2, Util.getMain().getViewPort().getCamera().getWidth(), 550));

        l.getCube(31, 1, 4).addAction(new MakeWallAction(new Vector3f(2, 3, 9), new Vector3f(3, 3, 13), Level.wallBlockFactory, l));
        l.getCube(31, 1, 5).addAction(new MakeWallAction(new Vector3f(2, 3, 9), new Vector3f(3, 3, 13), Level.wallBlockFactory, l));
        l.getCube(31, 1, 4).addAction(new RemoveWallAction(new Vector3f(19, 0, 4), new Vector3f(28, 0, 5)));
        l.getCube(31, 1, 5).addAction(new RemoveWallAction(new Vector3f(19, 0, 4), new Vector3f(28, 0, 5)));

        l.getCube(4, 4, 13).addAction(new MakeWallAction(new Vector3f(15, 3, 6), new Vector3f(16, 3, 12), Level.wallBlockFactory, l));

        ((Block_Shooter) (l.getCube(16, 8, 22))).setData(Block_Shooter.Orient.ZNeg, 200, 0);
        ((Block_Shooter) (l.getCube(15, 8, 22))).setData(Block_Shooter.Orient.ZNeg, 200, 0);
    }
}

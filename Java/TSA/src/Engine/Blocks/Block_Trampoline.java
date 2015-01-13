package Engine.Blocks;

import Engine.Balls.PlayerBall;
import Engine.Levels.Level;
import Engine.Main;
import Engine.Utils.Util;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;

public class Block_Trampoline extends BlockSolid {

    PlayerBall ball;
    Vector3f bounce = new Vector3f(0, 5, 0);

    public Block_Trampoline(Main main, int x, int y, int z, Level level, boolean creating) {
        super(main, new Vector3f(x, y, z), level, ColorRGBA.Magenta, new Vector3f(main.getBlockSize() / 1.2f, main.getBlockSize() / 15, main.getBlockSize() / 1.2f), "Trampoline", creating, Level.trampolineBlockFactory);
    }

    public void delete() {
    }

    public void update() {
        super.update();
    }

    public void collideWithBall(PlayerBall ball) {
        super.collideWithBall(ball);
        ball.clearForces();
        ball.setLinearVelocity(ball.getLinearVelocity().clone().setY(0));
        ball.applyImpulse(bounce, Vector3f.ZERO);
        ball.setIsTrampolining(true);
    }
    
    public void setStrength(float strength){
        bounce = new Vector3f(0, strength, 0);
    }
}

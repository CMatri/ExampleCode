package Engine.Blocks;

import AppStates.EditorState;
import AppStates.GameState;
import Engine.Balls.PlayerBall;
import Engine.Balls.ProjectileBall;
import Engine.Levels.Level;
import Engine.Main;
import Engine.Utils.Util;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;

public class Block_Shooter extends BlockSolid {

    public enum Orient {

        ZPos, ZNeg, XPos, XNeg, YPos, YNeg;
    }
    private Orient orientation;
    private float timeBetweenShots = 500, timeStart = 499;
    private Vector3f direction = Vector3f.UNIT_Y, origDirection = direction;
    private float ballMass = .1f, ballLife = 100;
    private ColorRGBA ballColor = ColorRGBA.White;
    private boolean canStart = false;

    public Block_Shooter(Main main, int x, int y, int z, Level level, boolean creating) {
        super(main, new Vector3f(x, y, z), level, "BlockShooter", creating, Level.shooterBlockFactory);
        setBlockColor(ColorRGBA.Orange);
    }

    public void setData(Orient e, float timeBetweenShots, float startOffset, ColorRGBA color) {
        this.orientation = e;
        this.timeBetweenShots = timeBetweenShots;
        timeStart = timeBetweenShots - 1 - startOffset;
//        setBlockColor(color);

        if (orientation == Orient.ZPos) {
            direction = Vector3f.UNIT_Z;
            origDirection = direction.add(0, 0.5f, 0);
        } else if (orientation == Orient.ZNeg) {
            direction = Vector3f.UNIT_Z.negate();
            origDirection = direction.add(0, 0.5f, 0);
        } else if (orientation == Orient.XPos) {
            direction = Vector3f.UNIT_X;
            origDirection = direction.add(0, 0.5f, 0);
        } else if (orientation == Orient.XNeg) {
            direction = Vector3f.UNIT_X.negate();
            origDirection = direction.add(0, 0.5f, 0);
        } else if (orientation == Orient.YPos) {
            direction = Vector3f.UNIT_Y;
            origDirection = direction.add(0, 0.5f, 0);
        } else if (orientation == Orient.YNeg) {
            direction = Vector3f.UNIT_Y.negate();
            origDirection = direction.add(0, -0.5f, 0);
        }
        
        canStart = true;
    }

    public void setData(Orient e, ColorRGBA color, float offset) {
        setData(e, 500, offset, color);
    }

    public void setData(Orient e, float timeBetweenShots, float offset) {
        setData(e, timeBetweenShots, offset, ColorRGBA.Orange);
    }

    public void setProjectileData(float speed, float mass, float life, ColorRGBA color) {
        this.ballMass = mass;
        this.ballLife = life;
        this.ballColor = color;

        if (orientation == Orient.ZPos) {
            direction = Vector3f.UNIT_Z.add(0, 0, speed);
        } else if (orientation == Orient.ZNeg) {
            direction = Vector3f.UNIT_Z.negate().subtract(0, 0, speed);
        } else if (orientation == Orient.XPos) {
            direction = Vector3f.UNIT_X.add(speed, 0, 0);
        } else if (orientation == Orient.XNeg) {
            direction = Vector3f.UNIT_X.negate().subtract(speed, 0, 0);
        } else if (orientation == Orient.YPos) {
            direction = Vector3f.UNIT_Y.add(0, speed, 0);
        } else if (orientation == Orient.YNeg) {
            direction = Vector3f.UNIT_Y.negate().subtract(0, speed, 0);
        }
    }

    public void shoot() {
        new ProjectileBall(Util.getMain(), getCenter().add(origDirection), direction, ballMass, ballLife, ballColor);
    }

    public void update() {
        super.update();
        if (canStart) {
            if (Util.getMain().isEditor()) {
                if (timeStart >= timeBetweenShots) {
                    shoot();
                    timeStart = 0;
                } else {
                    timeStart++;
                }
            } else {
                if (timeStart >= timeBetweenShots && !Util.getMain().getStateManager().getState(GameState.class).getGameGuiController().isPaused()) {
                    shoot();
                    timeStart = 0;
                } else {
                    timeStart++;
                }
            }
        }
    }

    public void collideWithBall(PlayerBall ball) {
        super.collideWithBall(ball);
    }
}

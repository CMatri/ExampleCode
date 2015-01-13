package Engine.Balls;

import Engine.Main;
import com.jme3.math.Vector3f;

public class MainMenuBall extends PlayerBall {

    public MainMenuBall(Main main, Vector3f startPos) {
        super(main, startPos);
        setMass(.4f);
        setSize(.4f);
        setSpeed(2f);
        setFireInvuln(false);
        setCanFloat(false);
        setName("RegBall");
        setBounce(.5f);
        create();
    }

    public void collide() {
    }

    public void kill(boolean deathByFalling){
        super.kill(deathByFalling);
    }

    public void update(float tpf) {
        super.update(tpf);
    }
}

package Engine.Balls;

import Engine.Main;
import com.jme3.math.Vector3f;

public class RegularBall extends PlayerBall{
    public RegularBall(Main main, Vector3f startPos){
        super(main, startPos);
        setMass(.4f);
        setSize(.4f);
        setSpeed(2f);
        setFireInvuln(false);
        setCanFloat(false);
        setName("RegBall");
        create();
    }
    
    public void collide(){
        
    }
    
    public void kill(boolean deathByFalling){
        super.kill(deathByFalling);
    }
}

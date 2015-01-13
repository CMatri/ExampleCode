package Engine.Balls;

import Engine.Main;
import com.jme3.math.Vector3f;

public class OPBall extends PlayerBall{
    public OPBall(Main main, Vector3f startPos){
        super(main, startPos);
        setMass(.4f);
        setSize(.4f);
        setSpeed(10f);
        setFireInvuln(true);
        setCanFloat(true);
        setName("OPBall");
        create();
    }
    
    public void collide(){
        
    }
    
    public void kill(boolean deathByFalling){
        super.kill(deathByFalling);
    }
    
    public void update(float tpf){
        super.update(tpf);
    }
}

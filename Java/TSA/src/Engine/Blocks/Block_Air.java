/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Engine.Blocks;

import Engine.Balls.PlayerBall;
import Engine.Levels.Level;
import Engine.Main;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;

public class Block_Air extends BlockSuper {

    public Block_Air(Main main, int x, int y, int z, Level level) {
        super(main, new Vector3f(x, y, z), level, "Air", true, Level.airBlockFactory);
    }
    
    public void setUp(){
        
    }

    public void delete() {
    }
    
    public void update(){
        
    }
    
    public void collideWithBall(PlayerBall ball){
    }
}
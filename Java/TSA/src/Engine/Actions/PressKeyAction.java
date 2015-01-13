package Engine.Actions;

import Engine.Blocks.BlockFactory;
import Engine.Levels.Level;
import Engine.Main;
import com.jme3.input.KeyInput;
import com.jme3.math.Vector3f;

public class PressKeyAction extends Action {

    boolean first = true;
    int key;
    Action todo;

    public PressKeyAction(){
        super(Action.ID.PRESS_KEY);
    }
    
    public PressKeyAction(Vector3f startPos, Vector3f endPos, BlockFactory block, int key, Action todo) {
        super(Action.ID.PRESS_KEY);
        addUserData(key);
        this.todo = todo;
        this.key = key;
    }

    public void actionPerformed(Main main) {
        if (first) {
            if (getUserData(1) == key) {
                todo.actionPerformed(main);
                first = false;
            }
        }
    }
}

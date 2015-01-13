package AppStates;

import com.jme3.input.InputManager;
import com.jme3.input.MouseInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.AnalogListener;
import com.jme3.input.controls.MouseAxisTrigger;
import com.jme3.math.Vector3f;

public class MainMenuInputs {

    private MainMenuState mainMenuState;
    private InputManager inputManager;
    private boolean enabled = false;

    public MainMenuInputs(MainMenuState mainMenuState) {
        this.mainMenuState = mainMenuState;
        this.inputManager = mainMenuState.getInputManager();
        init();
    }

    public void update(float tpf) {
    }

    private void init() {
    }
    private AnalogListener analogListener = new AnalogListener() {
        public void onAnalog(String name, float value, float tpf) {
        }
    };
    private ActionListener actionListener = new ActionListener() {
        public void onAction(String name, boolean isPressed, float tpf) {
            if (enabled) {
            }
        }
    };

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}

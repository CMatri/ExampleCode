package AppStates;

import Engine.Balls.PlayerBall;
import Engine.Utils.GlowingTextRenderer;
import Engine.Utils.Util;
import com.jme3.input.InputManager;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.AnalogListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.math.Vector3f;
import java.util.Random;

public class GameInputs {

    private GameState gameState;
    private InputManager inputManager;
    private PlayerBall playerBall;

    public GameInputs(GameState gameState) {
        this.gameState = gameState;
        this.inputManager = gameState.getInputManager();
        this.playerBall = gameState.getPlayer();
        init();
    }

    private void init() {
        int[] allKeyCodes = new int[223];
        for (int i = 0; i < allKeyCodes.length; i++) {
            allKeyCodes[i] = i + 1;
        }

        inputManager.addMapping("Left", new KeyTrigger(KeyInput.KEY_A));
        inputManager.addMapping("Right", new KeyTrigger(KeyInput.KEY_D));
        inputManager.addMapping("Forward", new KeyTrigger(KeyInput.KEY_W));
        inputManager.addMapping("Back", new KeyTrigger(KeyInput.KEY_S));
        inputManager.addMapping("Kill", new KeyTrigger(KeyInput.KEY_K));
        inputManager.addMapping("Brake", new KeyTrigger(KeyInput.KEY_LSHIFT));

        inputManager.addMapping("Pause", new KeyTrigger(KeyInput.KEY_ESCAPE));
        inputManager.addMapping("Jump", new KeyTrigger(KeyInput.KEY_SPACE));
        inputManager.addMapping("Quit", new KeyTrigger(KeyInput.KEY_P));

        inputManager.addListener(analogListener, "Left");
        inputManager.addListener(analogListener, "Right");
        inputManager.addListener(analogListener, "Forward");
        inputManager.addListener(analogListener, "Back");
        inputManager.addListener(analogListener, "Kill");
        inputManager.addListener(analogListener, "Brake");

        inputManager.addListener(actionListener, "Pause");
        inputManager.addListener(actionListener, "Jump");
        inputManager.addListener(actionListener, "Quit");
    }

    public void update(float tpf) {
        this.playerBall = gameState.getPlayer();
    }
    private Random r = new Random();
    private AnalogListener analogListener = new AnalogListener() {
        public void onAnalog(String name, float value, float tpf) {
            if (!gameState.onDialogue()) {
                if (name.equals("Forward")) {
                    playerBall.getPhysicsBody().applyCentralForce(new Vector3f(playerBall.getSpeed(), 0, 0));
                }
                if (name.equals("Back")) {
                    playerBall.getPhysicsBody().applyCentralForce(new Vector3f(-playerBall.getSpeed(), 0, 0));
                }
                if (name.equals("Left")) {
                    playerBall.getPhysicsBody().applyCentralForce(new Vector3f(0, 0, -playerBall.getSpeed()));
                }
                if (name.equals("Right")) {
                    playerBall.getPhysicsBody().applyCentralForce(new Vector3f(0, 0, playerBall.getSpeed()));
                }
                if (name.equals("Brake")) {
//                new ProjectileBall(Util.getMain(), playerBall.getPosition().add(new Vector3f(0, 3, 0)), new Vector3f(0, r.nextInt(2), 0), 0.1f, 350, ColorRGBA.Green);
                    playerBall.getPhysicsBody().setAngularDamping(999999999);
                    playerBall.getPhysicsBody().setFriction(2);
                } else if (!name.equals("Brake")) {
                    playerBall.getPhysicsBody().setAngularDamping(playerBall.getOrigDamping());
                }
                if (name.equals("Kill")) {
                    //playerBall.kill();
                }
            }
        }
    };
    long time = System.currentTimeMillis();
    private ActionListener actionListener = new ActionListener() {
        public void onAction(String name, boolean isPressed, float tpf) {
            if (!gameState.onDialogue()) {
                if (name.equals("Jump") && isPressed && (System.currentTimeMillis() - time > 900)) {
                    playerBall.applyImpulse(new Vector3f(0, 5, 0), new Vector3f(0, 0, 0));
                    playerBall.setJumping(true);
                    time = System.currentTimeMillis();
                }
                if (System.currentTimeMillis() - time > 900) {
                    playerBall.setJumping(false);
                }
            }
            if (name.equals("Pause") && isPressed) {
                gameState.togglePause();
//                gameState.getCurrMap().getLevel().completed();
            }
            if (name.equals("Quit")) {
                gameState.getGameGuiController().quit();
            }
        }
    };
}

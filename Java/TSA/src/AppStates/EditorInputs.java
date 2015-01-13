package AppStates;

import Engine.Blocks.Block_Light;
import Engine.Balls.PlayerBall;
import Engine.Levels.Level;
import Engine.Main;
import Engine.Utils.ComboMove;
import Engine.Utils.ComboMoveExecution;
import Engine.Utils.Util;
import com.jme3.app.Application;
import com.jme3.asset.AssetManager;
import com.jme3.collision.CollisionResults;
import com.jme3.input.InputManager;
import com.jme3.input.KeyInput;
import com.jme3.input.MouseInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.input.controls.MouseAxisTrigger;
import com.jme3.input.controls.MouseButtonTrigger;
import com.jme3.material.Material;
import com.jme3.material.RenderState;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Ray;
import com.jme3.math.Vector3f;
import com.jme3.renderer.queue.RenderQueue;
import com.jme3.scene.Geometry;
import com.jme3.scene.shape.Box;
import de.lessvoid.nifty.tools.Color;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class EditorInputs {

    private EditorState editorState;
    private InputManager inputManager;
    private Main app;
    private AssetManager assetManager;
    private PlayerBall playerBall;
    private Geometry pickBlockGeom, areaMark;
    private Box pickBlock, areaBlock;
    private HashSet<String> pressedMappings = new HashSet<String>();
    private ComboMove currentMove = null;
    private ComboMove placeWall, removeWall, editBlock;
    private Vector3f placePos;
    private ComboMoveExecution placeWallExec, removeWallExec, editBlockExec;
    private boolean placedFirst = false, placedSecond = false, place = false, canPlace = true;
    private Vector3f pos1 = new Vector3f(0, 0, 0), pos2 = new Vector3f(0, 0, 0);

    public EditorInputs(EditorState editorState, Application app) {
        this.editorState = editorState;
        this.inputManager = editorState.getInputManager();
        this.assetManager = editorState.getAssetManager();
        this.app = (Main) app;
        init();
    }

    private void init() {
        inputManager.addMapping("SSAO", new KeyTrigger(KeyInput.KEY_F1));
        inputManager.addMapping("DOF", new KeyTrigger(KeyInput.KEY_F2));
        inputManager.addMapping("FPS", new KeyTrigger(KeyInput.KEY_F3));
        inputManager.addMapping("Place", new MouseButtonTrigger(MouseInput.BUTTON_RIGHT));
        inputManager.addMapping("Break", new MouseButtonTrigger(MouseInput.BUTTON_LEFT));
        inputManager.addMapping("ScrollUp", new MouseAxisTrigger(MouseInput.AXIS_WHEEL, true));
        inputManager.addMapping("ScrollDown", new MouseAxisTrigger(MouseInput.AXIS_WHEEL, false));
        inputManager.addMapping("Enter", new KeyTrigger(KeyInput.KEY_RETURN));
        inputManager.addMapping("Ctrl", new KeyTrigger(KeyInput.KEY_LCONTROL));
        inputManager.addMapping("PlaceStart", new KeyTrigger(KeyInput.KEY_F));
        inputManager.addMapping("RunDown", new KeyTrigger(KeyInput.KEY_R));
        inputManager.addMapping("Shift", new KeyTrigger(KeyInput.KEY_LSHIFT));

        inputManager.addMapping("Exit", new KeyTrigger(KeyInput.KEY_ESCAPE));

        inputManager.addListener(actionListener, "SSAO");
        inputManager.addListener(actionListener, "DOF");
        inputManager.addListener(actionListener, "FPS");
        inputManager.addListener(actionListener, "Place");
        inputManager.addListener(actionListener, "Break");
        inputManager.addListener(actionListener, "ScrollUp");
        inputManager.addListener(actionListener, "ScrollDown");
        inputManager.addListener(actionListener, "Enter");
        inputManager.addListener(actionListener, "Ctrl");
        inputManager.addListener(actionListener, "PlaceStart");
        inputManager.addListener(actionListener, "RunDown");
        inputManager.addListener(actionListener, "Shift");

        inputManager.addListener(actionListener, "Exit");

        inputManager.addMapping("Light", new KeyTrigger(KeyInput.KEY_2));
        inputManager.addMapping("Wall_Block", new KeyTrigger(KeyInput.KEY_1));

        inputManager.addListener(actionListener, "Light");
        inputManager.addListener(actionListener, "Wall_Block");
        pickBlock = new Box(app.getBlockSize(), app.getBlockSize(), app.getBlockSize());
//            pickBlock.setMode(Mesh.Mode.Lines);
        pickBlockGeom = new Geometry("Pick Block", pickBlock);
        pickBlockGeom.scale(1.05f);
        Material pickBlockMaterial = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        pickBlockMaterial.setColor("Color", new ColorRGBA(1, 1, 1, 0.2f));
        pickBlockMaterial.getAdditionalRenderState().setBlendMode(RenderState.BlendMode.Alpha);
        pickBlockGeom.setQueueBucket(RenderQueue.Bucket.Transparent);
        pickBlockGeom.setMaterial(pickBlockMaterial);
        pickBlockGeom.setLocalTranslation(10, 100, 10);
        editorState.getRootNode().attachChild(pickBlockGeom);

        areaBlock = new Box(app.getBlockSize(), app.getBlockSize(), app.getBlockSize());
        areaMark = new Geometry("Area Marker", areaBlock);
        areaMark.scale(1.05f);
        Material areaMaterial = pickBlockMaterial.clone();
        areaMaterial.setColor("Color", new ColorRGBA(1, 0, 0, 0.1f));
        areaMark.setQueueBucket(RenderQueue.Bucket.Transparent);
        areaMark.setMaterial(areaMaterial);
        areaMark.setLocalTranslation(10, 100, 10);
        editorState.getRootNode().attachChild(areaMark);

        placeWall = new ComboMove("Place Wall");
        placeWall.press("Ctrl", "Place").done();
        placeWall.setCastTime(0);
        placeWall.setUseFinalState(true);

        removeWall = new ComboMove("Remove Wall");
        removeWall.press("Ctrl", "Break").done();
        removeWall.setCastTime(0);
        removeWall.setUseFinalState(true);

        editBlock = new ComboMove("Edit Block");
        editBlock.press("Shift", "Break").done();
        editBlock.setCastTime(0);
        editBlock.setUseFinalState(true);

        placeWallExec = new ComboMoveExecution(placeWall);
        removeWallExec = new ComboMoveExecution(removeWall);
        editBlockExec = new ComboMoveExecution(editBlock);
    }
    Ray ray;

    public void resetMarker() {
        areaMark.setMesh(areaBlock);
        areaMark.setLocalTranslation(0, -100, 0);
    }

    public void update(float tpf) {
        CollisionResults results = new CollisionResults();
        ray = new Ray(app.getCamera().getLocation(), app.getCamera().getDirection());
        editorState.getMap().getLevel().getNode().collideWith(ray, results);
        if (results.size() > 0) {
            Vector3f targetPosition = new Vector3f(results.getClosestCollision().getGeometry().getWorldBound().getCenter().add(1, 0, 1));//.add(results.getClosestCollision().getContactNormal()));
            pickBlockGeom.setLocalTranslation((int) targetPosition.x - 0.015f, (int) targetPosition.y + 0.5f - 0.015f, (int) targetPosition.z - 0.015f);
            editorState.setLookAtCoords(targetPosition);
            placePos = new Vector3f(results.getClosestCollision().getGeometry().getLocalTranslation());
            moveTime += tpf;
            removeWallExec.updateExpiration(moveTime);
            placeWallExec.updateExpiration(moveTime);
            editBlockExec.updateExpiration(moveTime);
            if (currentMove != null) {
                currentMoveCastTime -= tpf;
                if (currentMoveCastTime <= 0) {
                    Util.log("Combo: " + currentMove.getMoveName());
                    if (currentMove.getMoveName().equals(removeWall.getMoveName())) {
                        if (!placedFirst) {
                            placedFirst = true;
                            pos1 = placePos.add(1, 0, 1);
                            areaMark.setLocalTranslation(pos1.add(-1, 0, -1));
                        } else {
                            placedSecond = true;
                            pos2 = placePos.add(1, 0, 1);
                            place = false;
                            areaMark.setMesh(new Box((pos2.x - pos1.x) / 2 + 0.5f, (pos2.y - pos1.y) / 2 + 0.02f, (pos2.z - pos1.z) / 2 + 0.5f));
                            areaMark.setLocalTranslation((pos2.x + pos1.x) / 2 - 1f, ((pos2.y + pos1.y) / 2) + 0.5f, (pos2.z + pos1.z) / 2 - 1f);
                        }
                    } else if (currentMove.getMoveName().equals(placeWall.getMoveName())) {
                        if (!placedFirst) {
                            placedFirst = true;
                            pos1 = placePos;
                            areaMark.setLocalTranslation(pos1);
                        } else {
                            placedSecond = true;
                            pos2 = placePos.add(1, 0, 1);
                            place = true;
                            areaMark.setMesh(new Box((pos2.x - pos1.x) / 2 + 0.5f, (pos2.y - pos1.y) / 2 + 0.02f, (pos2.z - pos1.z) / 2 + 0.5f));
                            areaMark.setLocalTranslation((pos2.x + pos1.x) / 2 - 1f, ((pos2.y + pos1.y) / 2) + 0.5f, (pos2.z + pos1.z) / 2 - 1f);
                        }
                    } else if (currentMove.getMoveName().equals(editBlock.getMoveName())) {
                        if (editorState.getMap().getLevel().getCube((int) targetPosition.x, (int) targetPosition.y, (int) targetPosition.z).getId().equals("Light")) {
                            Block_Light light = (Block_Light) editorState.getMap().getLevel().getCube((int) targetPosition.x, (int) targetPosition.y, (int) targetPosition.z);
                            areaMark.setLocalTranslation(placePos);
                            editorState.getGui().toggleLights(new Color(light.getLightColor().getRed(), light.getLightColor().getGreen(), light.getLightColor().getBlue(), 1), light.getRadius());
                        } else {
                        }
                    }

                    currentMoveCastTime = 0;
                    currentMove = null;
                }
            }
            if (editorState.getGui().onLights()) {
                Block_Light light = (Block_Light) editorState.getMap().getLevel().getCube((int) targetPosition.x, (int) targetPosition.y, (int) targetPosition.z);
                ColorRGBA color = new ColorRGBA(editorState.getGui().getLightColor().getRed(), editorState.getGui().getLightColor().getGreen(), editorState.getGui().getLightColor().getBlue(), 255);
                light.setLightColor(color);
                light.setRadius(editorState.getGui().getRadius());
            }
        }
    }
    private float currentMoveCastTime = 0, moveTime = 0;
    private boolean displayingFPS = false;
    private ActionListener actionListener = new ActionListener() {
        public void onAction(String name, boolean isPressed, float tpf) {
            if (name.equals("SSAO") && isPressed) {
                app.getLightManager().setSSAO(!app.getLightManager().ssaoEnabled());
            }

            if (name.equals("DOF") && isPressed) {
                app.getLightManager().setDOF(!app.getLightManager().dofEnabled());
            }

            if (name.equals("FPS") && isPressed) {
                Util.log(displayingFPS);
                app.setDisplayFps(!displayingFPS);
                displayingFPS = !displayingFPS;
            }

            if (name.equals("Ctrl") && isPressed) {
                canPlace = false;
            } else if (name.equals("Shift") && isPressed) {
                canPlace = false;
            } else if (name.equals("Shift") && !isPressed) {
                canPlace = true;
            } else if (name.equals("RunDown")) {
                editorState.getMap().getLevel().doRundown();
            } else if (name.equals("Ctrl") && !isPressed) {
                canPlace = true;
            } else if (canPlace && !editorState.getGui().onLights()) {
                if (name.equals("Place") && isPressed) {
                    CollisionResults results = new CollisionResults();
                    ray = new Ray(app.getCamera().getLocation(), app.getCamera().getDirection());
                    editorState.getMap().getLevel().getNode().collideWith(ray, results);
                    if (results.size() > 0) {
                        Vector3f targetPosition = new Vector3f(results.getClosestCollision().getGeometry().getWorldBound().getCenter().add(1, 0, 1).add(results.getClosestCollision().getContactNormal()));
                        editorState.getMap().getLevel().makeBlock(targetPosition, editorState.getToPlace());
                        editorState.getMap().getLevel().makeBlock(new Vector3f(0, 50, 0), Level.wallBlockFactory);
                        editorState.getMap().getLevel().removeBlock(new Vector3f(0, 50, 0));
                        editorState.getMap().getLevel().getNode().batch();
                    }
                } else if (name.equals("Break") && isPressed) {
                    CollisionResults results = new CollisionResults();
                    ray = new Ray(app.getCamera().getLocation(), app.getCamera().getDirection());
                    editorState.getMap().getLevel().getNode().collideWith(ray, results);
                    if (results.size() > 0) {
                        Vector3f targetPosition = new Vector3f(results.getClosestCollision().getGeometry().getWorldBound().getCenter().add(1, 0, 1));
                        editorState.getMap().getLevel().removeBlock(targetPosition);
                        editorState.getMap().getLevel().getNode().batch();
                    }
                }
            }
            if (name.equals("ScrollUp")) {
                editorState.scroll(1);
            } else if (name.equals("ScrollDown")) {
                editorState.scroll(-1);
            }

            if (isPressed) {
                pressedMappings.add(name);
            } else {
                pressedMappings.remove(name);
            }

            List<ComboMove> invokedMoves = new ArrayList<ComboMove>();
            if (placeWallExec.updateState(pressedMappings, moveTime)) {
                invokedMoves.add(placeWall);
            }
            if (removeWallExec.updateState(pressedMappings, moveTime)) {
                invokedMoves.add(removeWall);
            }
            if (editBlockExec.updateState(pressedMappings, moveTime)) {
                invokedMoves.add(editBlock);
            }

            if (invokedMoves.size() > 0) {
                ComboMove toExec = null;
                for (ComboMove move : invokedMoves) {
                    toExec = move;
                }
                if (currentMove != null) {
                    return;
                }
                currentMove = toExec;
                currentMoveCastTime = currentMove.getCastTime();
            }

            if (name.equals("Enter")) {
                if (placedSecond) {
                    Util.log("Large Removal/Place Initiated");
                    if (place) {
                        editorState.getMap().getLevel().makeWall(pos1, pos2, editorState.getToPlace());
                    } else {
                        editorState.getMap().getLevel().removeWall(pos1, pos2);
                    }
                    placedSecond = false;
                    placedFirst = false;
                    pos1 = new Vector3f(0, 0, 0);
                    pos2 = new Vector3f(0, 0, 0);
                    areaMark.setLocalTranslation(0, 100, 0);
                    areaMark.setMesh(new Box(app.getBlockSize(), app.getBlockSize(), app.getBlockSize()));
                }
            }
            if (name.equals("Exit")) {
                editorState.getGui().goToExit();
            }
            if (name.equals("PlaceStart")) {
                editorState.getMap().getLevel().setSpawnPosition(placePos);
                Util.log("Spawn Set: " + placePos);
            }
        }
    };
}

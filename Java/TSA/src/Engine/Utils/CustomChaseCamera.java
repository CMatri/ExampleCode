package Engine.Utils;

import Engine.Main;
import com.jme3.input.ChaseCamera;
import static com.jme3.input.ChaseCamera.ChaseCamDown;
import static com.jme3.input.ChaseCamera.ChaseCamUp;
import static com.jme3.input.ChaseCamera.ChaseCamZoomIn;
import static com.jme3.input.ChaseCamera.ChaseCamZoomOut;
import com.jme3.input.InputManager;
import com.jme3.renderer.Camera;
import com.jme3.scene.Spatial;

public class CustomChaseCamera extends ChaseCamera {

    private Main main;
    
    public CustomChaseCamera(Main main, Camera cam, Spatial target, InputManager inputManager) {
        super(cam, target, inputManager);
    }

    public void onAnalog(String name, float value, float tpf) {
        if (enabled) {
            inputManager.setCursorVisible(false);
        }
        if (name.equals(ChaseCamUp)) {
            vRotateCamera(value);
        } else if (name.equals(ChaseCamDown)) {
            vRotateCamera(-value);
        } else if (name.equals(ChaseCamZoomIn)) {
            zoomCamera(-value);
            if (zoomin == false) {
                distanceLerpFactor = 0;
            }
            zoomin = true;
        } else if (name.equals(ChaseCamZoomOut)) {
            zoomCamera(+value);
            if (zoomin == true) {
                distanceLerpFactor = 0;
            }
            zoomin = false;
        }
    }
    
    public void zoomOutComplete(){
        zoomCamera(maxDistance);
    }

    public void setMaxZoom(float max) {
        maxDistance = max;
    }

//    public void update(float tpf) {
//        main.getRootNode().updateGeometricState();
//        super.update(tpf);
//    }

    public void setMinZoom(float min) {
        minDistance = min;
    }
}

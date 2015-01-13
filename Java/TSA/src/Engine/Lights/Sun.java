package Engine.Lights;

import Engine.Main;
import com.jme3.light.DirectionalLight;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;

public class Sun {

    Node lights;
    DirectionalLight sun;
    
    public Sun(Vector3f dir, Main main, Node lights) {
        this.lights = lights;
        sun = new DirectionalLight();
        sun.setDirection(dir);
        sun.setColor(ColorRGBA.White);
        lights.addLight(sun);
        main.getRootNode().addLight(sun);
    }

    public void setDirection(Vector3f dir) {
        sun.setDirection(dir);
    }
    
    public void setColor(ColorRGBA color){
        sun.setColor(color);
    }
    
    public DirectionalLight getSun(){
        return sun;
    }
}

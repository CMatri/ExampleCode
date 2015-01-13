package Engine;

import com.jme3.input.FlyByCamera;
import com.jme3.system.AppSettings;

public class SettingsManager {

    public void initSettings(Main main, FlyByCamera flyCam, float flySpeed) {
        AppSettings newSetting = new AppSettings(true);
//        newSetting.setFrameRate(50);
        newSetting.setResolution(1280, 720);
//        newSetting.setSamples(0);
//        newSetting.setVSync(false);
//        newSetting.setFrequency(0);
        newSetting.setTitle("Escaping Vox");
        main.setSettings(newSetting);
        main.restart();

        main.getCamera().setFrustumFar(100);
        
        flyCam.setMoveSpeed(flySpeed);
        flyCam.setDragToRotate(false);
    }
}

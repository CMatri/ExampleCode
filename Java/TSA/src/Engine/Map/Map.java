package Engine.Map;

import Engine.Levels.Level;
import Engine.Main;
import com.jme3.asset.AssetManager;
import com.jme3.material.Material;
import com.jme3.scene.Node;

public class Map {

    Main main;
    Node rootNode;
    Level l;
    AssetManager assetManager;
    Material boxMat;

    public Map(Main main) {
        this.main = main;
        this.rootNode = main.getRootNode();
        this.assetManager = main.getAssetManager();

    }

    public void setMap(Level l, boolean loaded) {
        this.l = l;
        l.setUpLevel(loaded);
    }

    public void delete() {
//        l.removeAll();
//        rootNode.detachChild(l.getNode());
//        l = null;
    }

    public Level getLevel() {
        return l;
    }
}

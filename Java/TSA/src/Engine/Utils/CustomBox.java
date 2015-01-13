package Engine.Utils;

import Engine.Blocks.BlockSuper;
import com.jme3.scene.Geometry;
import com.jme3.scene.Mesh;

public class CustomBox extends Geometry {

    BlockSuper father;

    public CustomBox() {
    }

    public void setData(String s, Mesh m) {
        super.setMesh(m);
        super.setName(s);
    }

    public void setFather(BlockSuper block) {
        this.father = block;
    }

    public BlockSuper getFather() {
        return father;
    }
}

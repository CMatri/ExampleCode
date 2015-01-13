package Engine.Actions;

import Engine.Main;
import com.jme3.export.InputCapsule;
import com.jme3.export.JmeExporter;
import com.jme3.export.JmeImporter;
import com.jme3.export.OutputCapsule;
import com.jme3.export.Savable;
import java.io.IOException;
import java.util.ArrayList;

public class Action implements Savable {

    private ArrayList<Object> userData = new ArrayList<Object>();

    public static enum ID {

        WALK_ON, WALK_OFF, NEAR, PRESS_KEY, ARBITRARY;
    }
    ID id;

    public Action(ID id) {
        this.id = id;
    }
    
    public void setUserData(ArrayList<Object> o) {
        userData = o;
    }

    public void addUserData(Object o) {
        userData.add(o);
    }

    public Object getUserData(int index) {
        return userData.get(index);
    }

    public void actionPerformed(Main main) {
    }

    public ID getTag() {
        return id;
    }
    
    public void read(JmeImporter im) throws IOException {
        InputCapsule capsule = im.getCapsule(this);
        userData = capsule.readSavableArrayList("userData", new ArrayList<>());
        id = capsule.readEnum("id", ID.class, ID.ARBITRARY);
    }

    public void write(JmeExporter ex) throws IOException {
        OutputCapsule capsule = ex.getCapsule(this);
        capsule.writeSavableArrayList(userData, "userData", new ArrayList<>());
        capsule.write(id, "id", ID.ARBITRARY);
    }
}

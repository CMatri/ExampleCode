
package Engine.Utils;

import com.jme3.export.JmeExporter;
import com.jme3.export.JmeImporter;
import com.jme3.export.Savable;
import java.io.IOException;

public class Savable3DArray implements Savable {

    private int[][][] array;
    
    public Savable3DArray(int xSize, int ySize, int zSize){
        array = new int[xSize][ySize][zSize];
    }

    @Override
    public void write(JmeExporter ex) throws IOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void read(JmeImporter im) throws IOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    
}

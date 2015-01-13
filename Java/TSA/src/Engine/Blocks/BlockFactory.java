package Engine.Blocks;

import Engine.Levels.Level;
import Engine.Main;
import Engine.Utils.Util;
import com.jme3.export.JmeExporter;
import com.jme3.export.JmeImporter;
import com.jme3.export.Savable;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import java.io.IOException;

public abstract class BlockFactory<T extends BlockSuper> implements Savable {

    private String ID;

    public BlockFactory(String ID) {
        this.ID = ID;
    }

    public abstract T create(Main main, Vector3f pos, Level level, boolean creating);

    public String getID() {
        return ID;
    }

    public static class TrampolineFactory extends BlockFactory<Block_Trampoline> {

        public TrampolineFactory() {
            super("Trampoline");
        }

        public Block_Trampoline create(Main main, Vector3f pos, Level level, boolean creating) {
            return new Block_Trampoline(main, (int) pos.x, (int) pos.y, (int) pos.z, level, creating);
        }

        public void write(JmeExporter ex) throws IOException {
        }

        public void read(JmeImporter im) throws IOException {
        }
    }

    public static class CheckPointFactory extends BlockFactory<Block_CheckPoint> {

        public CheckPointFactory() {
            super("Checkpoint");
        }

        public Block_CheckPoint create(Main main, Vector3f pos, Level level, boolean creating) {
            return new Block_CheckPoint(main, (int) pos.x, (int) pos.y, (int) pos.z, level, creating);
        }

        public void write(JmeExporter ex) throws IOException {
        }

        public void read(JmeImporter im) throws IOException {
        }
    }

    public static class ShooterFactory extends BlockFactory<Block_Shooter> {

        public ShooterFactory() {
            super("Shooter");
        }

        public Block_Shooter create(Main main, Vector3f pos, Level level, boolean creating) {
            return new Block_Shooter(main, (int) pos.x, (int) pos.y, (int) pos.z, level, creating);
        }

        public void write(JmeExporter ex) throws IOException {
        }

        public void read(JmeImporter im) throws IOException {
        }
    }

    public static class LightFactory extends BlockFactory<Block_Light> {

        public LightFactory() {
            super("Light");
        }

        public Block_Light create(Main main, Vector3f pos, Level level, boolean creating) {
            return new Block_Light(main, (int) pos.x, (int) pos.y, (int) pos.z, level, creating, ColorRGBA.White, 10);
        }

        public void write(JmeExporter ex) throws IOException {
        }

        public void read(JmeImporter im) throws IOException {
        }
    }

    public static class HalfWallFactory extends BlockFactory<Block_HalfWall> {

        public HalfWallFactory() {
            super("Half Wall");
        }

        public Block_HalfWall create(Main main, Vector3f pos, Level level, boolean creating) {
            return new Block_HalfWall(main, (int) pos.x, (int) pos.y, (int) pos.z, level, creating);
        }

        public void write(JmeExporter ex) throws IOException {
        }

        public void read(JmeImporter im) throws IOException {
        }
    }

    public static class PressurePlateFactory extends BlockFactory<Block_PressurePlate> {

        public PressurePlateFactory() {
            super("Pressure Plate");
        }

        public Block_PressurePlate create(Main main, Vector3f pos, Level level, boolean creating) {
            return new Block_PressurePlate(main, (int) pos.x, (int) pos.y, (int) pos.z, level, creating);
        }

        public void write(JmeExporter ex) throws IOException {
        }

        public void read(JmeImporter im) throws IOException {
        }
    }

    public static class EndGameFactory extends BlockFactory<Block_EndGame> {

        public EndGameFactory() {
            super("End Game");
        }

        public Block_EndGame create(Main main, Vector3f pos, Level level, boolean creating) {
            return new Block_EndGame(main, (int) pos.x, (int) pos.y, (int) pos.z, level, creating);
        }

        public void write(JmeExporter ex) throws IOException {
        }

        public void read(JmeImporter im) throws IOException {
        }
    }

    public static class AirFactory extends BlockFactory<Block_Air> {

        public AirFactory() {
            super("Air");
        }

        public Block_Air create(Main main, Vector3f pos, Level level, boolean creating) {
            return new Block_Air(main, (int) pos.x, (int) pos.y, (int) pos.z, level);
        }

        public void write(JmeExporter ex) throws IOException {
        }

        public void read(JmeImporter im) throws IOException {
        }
    }

    public static class WallFactory extends BlockFactory<Block_Wall> {

        public WallFactory() {
            super("Wall");
        }

        public Block_Wall create(Main main, Vector3f pos, Level level, boolean creating) {
            return new Block_Wall(main, (int) pos.x, (int) pos.y, (int) pos.z, level, creating);
        }

        public void write(JmeExporter ex) throws IOException {
        }

        public void read(JmeImporter im) throws IOException {
        }
    }
}

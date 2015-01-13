package Engine.Levels;

import Engine.Actions.DisplayTextAction;
import Engine.Blocks.Block_Shooter;
import Engine.Blocks.Block_Trampoline;
import static Engine.Levels.Level.endGameBlockFactory;
import static Engine.Levels.Level.trampolineBlockFactory;
import static Engine.Levels.Level.wallBlockFactory;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;

public class LevelTut1 extends Level {

    public LevelTut1() {
        super();
    }

    public void setUpLevel(boolean loaded) {
        if (loaded) {
            ColorRGBA doorColor = ColorRGBA.Green;
            setLevelSize(90, 256, 90);
            makeFloor(20, 30, wallBlockFactory);

            makeWall(new Vector3f(0, 1, 0), new Vector3f(5, 4, 0), wallBlockFactory);
            makeWall(new Vector3f(0, 1, 30), new Vector3f(5, 4, 30), wallBlockFactory);
            makeWall(new Vector3f(0, 0, 0), new Vector3f(0, 1, 30), wallBlockFactory);
            makeWall(new Vector3f(7, 1, 0), new Vector3f(12, 4, 0), wallBlockFactory);
            makeWall(new Vector3f(6, 1, 30), new Vector3f(12, 4, 30), wallBlockFactory);

            for (int z = 10; z < 30; z += 10) {
                makeWall(new Vector3f(0, 1, z), new Vector3f(1, 4, z), wallBlockFactory);
                makeWall(new Vector3f(4, 1, z), new Vector3f(5, 4, z), wallBlockFactory);
                makeWall(new Vector3f(1, 3, z), new Vector3f(4, 4, z), wallBlockFactory);
                addLight(2, 1, z, doorColor, 3);
                addLight(3, 1, z, doorColor, 3);
            }

            for (int x = 0; x < 30; x += 10) {
                makeWall(new Vector3f(6, 1, x), new Vector3f(6, 4, x + 9), wallBlockFactory);
            }


            for (int x = 0; x < 30; x += 10) {
                makeWall(new Vector3f(12, 1, x), new Vector3f(12, 4, x + 10), wallBlockFactory);
            }

            //room 1
            String display1 = "Welcome to Vox|Escape! This is a tutorial level to show you the basics. To start off, you can use the keys WASD to move around. When you are ready, move to the room to the right!";
            getCube(3, 0, 5).addAction(new DisplayTextAction(display1, 2, 4, 4, main.getViewPort().getCamera().getWidth(), 550));

            //room 2
            String display2 = "Good job! Sometimes you need to get over blocks to solve a puzzle. To jump, press SPACEBAR. Also, you can move the mouse UP and DOWN to change your view as well as scroll your mouse to zoom.";
            makeBlock(3, 1, 15, wallBlockFactory);
            getCube(3, 1, 15).addAction(new DisplayTextAction(display2, 2.5f, 5, 5, main.getViewPort().getCamera().getWidth(), 550));

            //room 3
            String display3 = "What's that purple thing? It's a trampoline. Jump on one of these and you'll go flying! Use this to jump on that block lit with red there and get to the next room.";
            makeBlock(3, 1, 25, trampolineBlockFactory);
            Block_Trampoline b1 = (Block_Trampoline) getCube(3, 1, 25);
            b1.setStrength(7);
            makeBlock(5, 2, 25, wallBlockFactory);
            makeBlock(5, 1, 25, wallBlockFactory);
            removeBlock(new Vector3f(6, 3, 25));
            removeBlock(new Vector3f(6, 4, 25));
            addLight(3, 2, 25, ColorRGBA.White, 4);
            addLight(6, 4, 25, ColorRGBA.Red, 5);
            getCube(3, 1, 25).addAction(new DisplayTextAction(display3, 2, 4, 4, main.getViewPort().getCamera().getWidth(), 550));

            //room 4
            String display4 = "To slow down when going too fast, press LEFT-SHIFT. This will act as a brake. Let's practice! Get some speed down this hallway and slow down with shift.";
            String display5 = "Keep Going!";
            String display6 = "Hit SHIFT!!";
            String display7 = "If you didn't already discover this, you can jump on a trampoline and then hit SPACEBAR to double-jump! Use this to get to the next room.";
            makeBlock(7, 1, 25, wallBlockFactory);
            makeBlock(7, 2, 25, wallBlockFactory);
            makeBlock(9, 1, 4, trampolineBlockFactory);
            Block_Trampoline b = (Block_Trampoline) getCube(9, 1, 4);
            b.setStrength(5);
            removeBlock(12, 4, 3);
            removeBlock(12, 4, 4);
            removeBlock(12, 4, 5);
            makeBlock(12, 5, 6, wallBlockFactory);
            makeBlock(12, 5, 2, wallBlockFactory);
            makeWall(new Vector3f(12, 6, 2), new Vector3f(12, 6, 6), wallBlockFactory);
            addLight(9, 3, 25, ColorRGBA.White, 5);
            addLight(9, 3, 18, ColorRGBA.White, 5);
            addLight(9, 3, 10, ColorRGBA.White, 5);
            addLight(9, 3, 4, ColorRGBA.White, 5);
            getCube(9, 0, 25).addAction(new DisplayTextAction(display4, 2.5f, 3, 3, main.getViewPort().getCamera().getWidth(), 550));
            getCube(9, 0, 17).addAction(new DisplayTextAction(display5, 2.5f, 3, 3, main.getViewPort().getCamera().getWidth(), 550));
            getCube(9, 0, 9).addAction(new DisplayTextAction(display6, 2.5f, 3, 2, main.getViewPort().getCamera().getWidth(), 550));
            getCube(9, 1, 4).addAction(new DisplayTextAction(display7, 2.5f, 3, 2, main.getViewPort().getCamera().getWidth(), 550));

            //room 5
            String display8 = "Congratulations! You've completed the tutorial level. Since you now know the basics, let's move to the first real level. Jump onto that blue block.";
            makeWall(new Vector3f(13, 1, 0), new Vector3f(19, 4, 0), wallBlockFactory);
            makeWall(new Vector3f(19, 1, 0), new Vector3f(19, 4, 8), wallBlockFactory);
            makeWall(new Vector3f(13, 1, 8), new Vector3f(19, 4, 8), wallBlockFactory);
            addLight(15, 2, 4, ColorRGBA.White, 5);
            getCube(15, 0, 4).addAction(new DisplayTextAction(display8, 3, 3, 3, main.getViewPort().getCamera().getWidth(), 550));
            removeWall(new Vector3f(13, 0, 9), new Vector3f(20, 1, 30));
            makeBlock(18, 1, 4, endGameBlockFactory);
            addPhysics();
        } else {
            super.setUpLevel(loaded);

            //room 1
            String display1 = "Welcome to Vox|Escape! This is a tutorial level to show you the basics. To start off, you can use the keys WASD to move around. When you are ready, move to the room to the right!";
            getCube(3, 0, 5).addAction(new DisplayTextAction(display1, 2, 4, 4, main.getViewPort().getCamera().getWidth(), 550));

            //room 2
            String display2 = "Good job! Sometimes you need to get over blocks to solve a puzzle. To jump, press SPACEBAR. Also, you can move the mouse UP and DOWN to change your view as well as scroll your mouse to zoom.";
            getCube(3, 1, 15).addAction(new DisplayTextAction(display2, 2.5f, 5, 5, main.getViewPort().getCamera().getWidth(), 550));

            //room 3
            String display3 = "What's that purple thing? It's a trampoline. Jump on one of these and you'll go flying! Use this to jump on that block lit with red there and get to the next room.";
            Block_Trampoline b1 = (Block_Trampoline) getCube(3, 1, 25);
            b1.setStrength(7);
            getCube(3, 1, 25).addAction(new DisplayTextAction(display3, 2, 4, 4, main.getViewPort().getCamera().getWidth(), 550));

            //room 4
            String display4 = "To slow down when going too fast, press LEFT-SHIFT. This will act as a brake. Let's practice! Get some speed down this hallway and slow down with shift.";
            String display5 = "Keep Going!";
            String display6 = "Hit SHIFT!!";
            String display7 = "If you didn't already discover this, you can jump on a trampoline and then hit SPACEBAR to double-jump! Use this to get to the next room.";
            Block_Trampoline b = (Block_Trampoline) getCube(9, 1, 4);
            b.setStrength(5);
            removeBlock(12, 4, 3);
            removeBlock(12, 4, 4);
            removeBlock(12, 4, 5);
            getCube(9, 0, 25).addAction(new DisplayTextAction(display4, 2.5f, 3, 3, main.getViewPort().getCamera().getWidth(), 550));
            getCube(9, 0, 17).addAction(new DisplayTextAction(display5, 2.5f, 3, 3, main.getViewPort().getCamera().getWidth(), 550));
            getCube(9, 0, 9).addAction(new DisplayTextAction(display6, 2.5f, 3, 2, main.getViewPort().getCamera().getWidth(), 550));
            getCube(9, 1, 4).addAction(new DisplayTextAction(display7, 2.5f, 3, 2, main.getViewPort().getCamera().getWidth(), 550));

            //room 5
            String display8 = "Congratulations! You've completed the tutorial level. Since you now know the basics, let's move to the first real level. Jump onto that blue block.";
            getCube(15, 0, 4).addAction(new DisplayTextAction(display8, 3, 3, 3, main.getViewPort().getCamera().getWidth(), 550));
        }

        setSpawnPosition(new Vector3f(3, 4, 3));
    }

    public void update() {
        super.update();
    }
}

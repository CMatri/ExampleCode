package Engine.Utils;

import Engine.Main;
import com.jme3.audio.AudioNode;
import com.jme3.audio.AudioSource.Status;
import java.util.Random;

public class SoundManager {

    public static String IN_GAME_SONG_ONE = "Sounds/song1.ogg";
    public static String IN_GAME_SONG_TWO = "Sounds/song2.ogg";
    private Main main;
    private boolean playMusic = false;
    private AudioNode song1, song2;
    private AudioNode playing;

    public SoundManager(Main main) {
        this.main = main;

        song1 = new AudioNode(main.getAssetManager(), IN_GAME_SONG_ONE, true);
        song1.setDirectional(false);
        song1.setPositional(false);
        song1.setReverbEnabled(false);
        song1.setVolume(0.1f);

        song2 = new AudioNode(main.getAssetManager(), IN_GAME_SONG_TWO, true);
        song2.setDirectional(false);
        song2.setPositional(false);
        song2.setReverbEnabled(false);
        song2.setVolume(0.1f);
    }

    public void setPlayMusic(boolean playMusic) {
        this.playMusic = playMusic;
    }

    public void startPlayingMusic() {
        if (playing == null) {
            playing = ((new Random().nextBoolean()) ? song1 : song2);
            playing.play();
        } else {
            if (playing.equals(song1)) {
                song2 = new AudioNode(main.getAssetManager(), IN_GAME_SONG_TWO, true);
                song2.setDirectional(false);
                song2.setPositional(false);
                song2.setReverbEnabled(false);
                song2.setVolume(0.1f);
                playing = song2;
                playing.play();
            } else {
                song1 = new AudioNode(main.getAssetManager(), IN_GAME_SONG_ONE, true);
                song1.setDirectional(false);
                song1.setPositional(false);
                song1.setReverbEnabled(false);
                song1.setVolume(0.1f);
                playing = song1;
                playing.play();
            }
        }
    }

    public void stopPlayingMusic() {
        playing.stop();
        playing = null;
    }
    float endWait = System.currentTimeMillis() + 20000;

    public void update(float tpf) {
        if (playing.getStatus() == Status.Stopped && System.currentTimeMillis() >= endWait) {
            endWait = System.currentTimeMillis() + 20000;
            startPlayingMusic();
        }
    }

    public void playSound(String sound) {
    }
}

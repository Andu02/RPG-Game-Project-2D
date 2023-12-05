package main;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.net.URL;

public class Sound {

    Clip clip;
    URL soundURL[] = new URL[30];

    public Sound() {

        soundURL[0] = getClass().getResource("/sound/theme_song.wav");
        soundURL[1] = getClass().getResource("/sound/door_sound.wav");
        soundURL[2] = getClass().getResource("/sound/pick_up_key_sound.wav");
        soundURL[3] = getClass().getResource("/sound/power_up_sound.wav");
        soundURL[4] = getClass().getResource("/sound/coin_sound.wav");
        soundURL[5] = getClass().getResource("/sound/win_sound.wav");

    }

    public void setFile(int i) {
        try {
            AudioInputStream ais = AudioSystem.getAudioInputStream(soundURL[i]);
            clip = AudioSystem.getClip();
            clip.open(ais);
        } catch (Exception e) {
            e.getStackTrace();
        }
    }
    public void play() {
        clip.start();
    }

    public void loop() {
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }

    public void stop() {
        if(clip != null) {
            clip.stop();
        }
    }

}

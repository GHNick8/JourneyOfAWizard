package main;

import java.io.IOException;
import java.net.URL;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class Sound {
    // Create your own sounds @BeepBox 
    Clip clip;
    URL soundURL[] = new URL[30]; // Add sound 
    public Sound() {
        soundURL[0] = getClass().getResource("/resources/sound/BlueBoyAdventure.wav"); // Change later on.. 
        soundURL[1] = getClass().getResource("/resources/sound/coin.wav");
        soundURL[2] = getClass().getResource("/resources/sound/powerup.wav");
        soundURL[3] = getClass().getResource("/resources/sound/unlock.wav");
        soundURL[4] = getClass().getResource("/resources/sound/blocked.wav");
        soundURL[5] = getClass().getResource("/resources/sound/fanfare.wav");
    }
    public void setFile(int i) {
        try {
            AudioInputStream audio = AudioSystem.getAudioInputStream(soundURL[i]);
            clip = AudioSystem.getClip();
            clip.open(audio);
        } catch (IOException | LineUnavailableException | UnsupportedAudioFileException e) {
            e.printStackTrace();
        }
    }
    public void play() {
        clip.start();
    }
    public void loop() {
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }
    public void stop() {
        clip.stop();
    }
}

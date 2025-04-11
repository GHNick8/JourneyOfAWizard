package game;

import java.io.IOException;
import java.net.URL;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class Sound {
    private Clip clip;

    public Sound(String filePath) {
        try {
            URL soundURL = getClass().getResource("/sounds/" + filePath);
            if (soundURL == null) {
                System.err.println("Warning: sound file not found: " + filePath);
                return;
            }

            AudioInputStream audioInput = AudioSystem.getAudioInputStream(soundURL);
            clip = AudioSystem.getClip();
            clip.open(audioInput);

        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
        }
    }

    public void play() {
        if (clip == null) return;
        if (clip.isRunning()) clip.stop();
        clip.setFramePosition(0);
        clip.start();
    }

    public void loop() {
        if (clip == null) return;
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }

    public void stop() {
        if (clip == null) return;
        clip.stop();
    }

    public boolean isPlaying() {
        return clip != null && clip.isRunning();
    }
}
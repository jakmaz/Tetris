package tetris;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

abstract class SoundPlayer {

    public static void playSound(String soundFileName) {
        try {
            // Open an audio input stream.
            File soundFile = new File(soundFileName);
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(soundFile);

            // Get a sound clip resource.
            Clip clip = AudioSystem.getClip();

            // Open the audio clip and load samples from the audio input stream.
            clip.open(audioIn);
            clip.start();
        } catch (UnsupportedAudioFileException e) {
            System.err.println("Audio file format not supported: " + e.getMessage());
        } catch (IOException e) {
            System.err.println("Error reading the audio file: " + e.getMessage());
        } catch (LineUnavailableException e) {
            System.err.println("Audio line for playing back is unavailable: " + e.getMessage());
        }
    }
}

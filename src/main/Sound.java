package main;

import java.io.File;
import java.io.IOException;
import java.util.TreeMap;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;


public class Sound {

	Clip clip;
	TreeMap<String, String> url = new TreeMap<>();

	public Sound() {
		url.put("background", "../res/Sound/BlueBoyAdventure.wav");
		url.put("Key", "../res/Sound/coin.wav");
		url.put("Boots", "../res/Sound/powerup.wav");
		url.put("Door", "../res/Sound/unlock.wav");
		url.put("Chest", "../res/Sound/fanfare.wav");
	}

	public void setFile(String type) {
		try {
			AudioInputStream aus = AudioSystem.getAudioInputStream(new File(url.get(type)));
			clip = AudioSystem.getClip();
			clip.open(aus);
		} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
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

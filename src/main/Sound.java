package main;

import java.io.File;
import java.io.IOException;
import java.util.TreeMap;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class Sound {

	Clip clip;
	TreeMap<String, String> url = new TreeMap<>();
	FloatControl floatControl;

	public Sound() {
		url.put("Click", "../res/Sound/click1.wav");
		url.put("Background", "../res/Sound/background.wav");
		url.put("Key", "../res/Sound/coin.wav");
		url.put("Boots", "../res/Sound/powerup.wav");
		url.put("Door", "../res/Sound/unlock.wav");
		url.put("Chest", "../res/Sound/fanfare.wav");
		url.put("Explode", "../res/Sound/explode.wav");
		url.put("TakeDamage", "../res/Sound/takeDamage.wav");
		url.put("BombLevelUp", "../res/Sound/bombLevelUp.wav");
		url.put("Winning1", "../res/Sound/winning1.wav");
		url.put("Winning2", "../res/Sound/winning2.wav");
		url.put("Winning3", "../res/Sound/winning3.wav");
	}

	public void setFile(String type) {
		try {
			AudioInputStream aus = AudioSystem.getAudioInputStream(new File(url.get(type)));
			clip = AudioSystem.getClip();
			clip.open(aus);
			floatControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
		} catch (UnsupportedAudioFileException | IOException | LineUnavailableException | NullPointerException e) {
			e.printStackTrace();
		}

	}

	public void setVolume(int volume) {
		floatControl.setValue(volume);
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

	public boolean isRunning() {
		return clip.isRunning();
	}

}

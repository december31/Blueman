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
		url.put("Click", "../res/sound/click1.wav");
		url.put("Background", "../res/sound/background.wav");
		url.put("Key", "../res/sound/coin.wav");
		url.put("Boots", "../res/sound/powerup.wav");
		url.put("Door", "../res/sound/unlock.wav");
		url.put("Chest", "../res/sound/fanfare.wav");
		url.put("Explode", "../res/sound/explode.wav");
		url.put("TakeDamage", "../res/sound/takeDamage.wav");
		url.put("BombLevelUp", "../res/sound/bombLevelUp.wav");
		url.put("Winning1", "../res/sound/winning1.wav");
		url.put("Winning2", "../res/sound/winning2.wav");
		url.put("Winning3", "../res/sound/winning3.wav");
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
		if(volume == 0) {
			floatControl.setValue(-80);
		}
		else if(volume == 50) {
			floatControl.setValue(0);
		}
		else if(volume < 50) {
			floatControl.setValue(volume - 50);
		}
		else {
			floatControl.setValue((float)((6.0 / 50) * (volume - 50)));
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

	public boolean isRunning() {
		return clip.isRunning();
	}

}

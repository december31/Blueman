package main;
import java.awt.*;
import javax.swing.*;

import entity.Player;
import object.SuperObject;
import tile.TileManager;

public class GamePanel extends JPanel implements Runnable{

	// screen setting
	public final int originalTileSize = 16;	// 16x16 pixel
	public final int scale = 3;

	public final int tileSize = originalTileSize * scale;	// 48 pixels
	public final int maxScreenCol = 16;
	public final int maxScreenRow = 12;
	public final int screenWidth = tileSize * maxScreenCol;
	public final int screenHeight = tileSize * maxScreenRow;

	// map setting
	public final int maxWorldCol = 50;
	public final int maxWorldRow = 50;
	public final int maxWorldWidth = tileSize * maxWorldCol;
	public final int maxWorldHeight = tileSize * maxWorldRow;

	
	public Thread gameThread;
	final int FPS = 60;

	KeyHandler keyHandler = new KeyHandler(this);
	public TileManager tileManager = new TileManager(this);
	public Sound music = new Sound();
	public Sound soundEffect = new Sound();
	public AssetSetter assetSetter = new AssetSetter(this);
	public SuperObject[] objects = new SuperObject[10];
	public CollisionChecker collisionChecker = new CollisionChecker(this);
	public Player player = new Player(this, keyHandler);
	public UI ui = new UI(this);

	public int gameState;
	public int titleState;
	public int playState = 1;
	public int pauseState = 2;
	public int tutorialState = 3;

	public GamePanel() {
		this.setPreferredSize(new Dimension(screenWidth, screenHeight));
		this.setBackground(Color.BLACK);
		this.setDoubleBuffered(true);	// improve game's rendering performance
		this.addKeyListener(keyHandler);
		this.setFocusable(true);
	}

	public void setupGame() {
		assetSetter.setObject();
		gameState = titleState;
		// playMusic();
	}
	
	public void startGameThread() {
		gameThread = new Thread(this);
		gameThread.start();
	}

	@Override
	public void run() {
		double lastTime = System.currentTimeMillis();
		double drawInterval = 1000 / FPS;
		double delta = 0;
		double currentTime;

		while(gameThread != null) {
			currentTime = System.currentTimeMillis();
			delta += (currentTime - lastTime) / drawInterval;
			lastTime = currentTime;

			if(delta >= 1) {
				update();
				repaint();
				delta--;
			}
		}
	}

	private void update() {
		if(gameState == playState) {
			player.update();
		} else if(gameState == pauseState) {
			// do nothing
		}
	}

	@Override
	protected void paintComponent(Graphics g) {
		Graphics2D g2D = (Graphics2D)g;
		super.paintComponent(g2D);
		if(gameState == titleState || gameState == tutorialState) {
			ui.draw(g2D);
		} else {
			// TILES
			tileManager.draw(g2D);
	
			// OBJECT
			for(int i = 0; i < 10; i++) {
				if(objects[i] != null) {
					objects[i].draw(g2D, this);
				}
			}
			
			// PLAYER
			player.draw(g2D);
	
			// UI
			ui.draw(g2D);
		}
		
		
		g2D.dispose();
	}

	public void playMusic() {
		music.setFile("background");
		music.play();
		music.loop();
	}

	public void stopMusic() {
		soundEffect.stop();
	}

	public void playSoundEffect(String type) {
		soundEffect.setFile(type);
		soundEffect.play();
	}
}

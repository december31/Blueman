package main;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

import javax.swing.JPanel;

import entity.Entity;
import entity.Player;
import object.OBJ_Bomb;
import object.OBJ_Boots;
import object.OBJ_ExtraBomb;
import object.OBJ_HealingPotion;
import object.OBJ_Key;
import object.OBJ_LevelUpBomb;
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
	public final int maxWorldCol = 31;
	public final int maxWorldRow = 83;
	public final int maxWorldWidth = tileSize * maxWorldCol;
	public final int maxWorldHeight = tileSize * maxWorldRow;

	public Thread gameThread;
	final int FPS = 60;
	public int fps;

	public KeyHandler keyHandler = new KeyHandler(this);
	public MouseHandler mouseHandler = new MouseHandler(this);
	public TileManager tileManager = new TileManager(this);
	public Sound music = new Sound();
	public Sound soundEffect = new Sound();
	public AssetSetter assetSetter = new AssetSetter(this);
	public CollisionChecker collisionChecker = new CollisionChecker(this);

	public Player player = new Player(this);
	public int characterIndex = 0;
	public int character0 = 0;
	public int character1 = 1;
	public Entity[] objects = new Entity[160];
	public Entity[] items = new Entity[10];
	public Entity[] monsters = new Entity[20];
	public OBJ_Bomb[] bombs = new OBJ_Bomb[20];
	public List<Entity> entityList = new ArrayList<>();

	public UI ui = new UI(this);

	public int gameState;
	public int titleState;
	public int playState = 1;
	public int pauseState = 2;
	public int tutorialState = 3;
	public int settingState = 4;
	public int finishState = 5;
	public int exitConfirmState = 6;
	public int chooseCharacterState = 7;
	public int[] previousState = new int[8];
	public boolean checkKeyHandlerFinishState = false;

	public int level;

	// last monster killed coordinate
	int monsterWorldX = 0;
	int monsterWorldY = 0;

	public boolean continuable;
	public Random random = new Random();

	public GamePanel() {
		this.setPreferredSize(new Dimension(screenWidth, screenHeight));
		this.setBackground(Color.BLACK);
		this.setDoubleBuffered(true);	// improve game's rendering performance
		this.addKeyListener(keyHandler);
		this.addMouseListener(mouseHandler.clickListener);
		this.addMouseMotionListener(mouseHandler.dragListener);
		this.setFocusable(true);
		playMusic();
	}

	public void setupGame() {
		level = 1;
		objects = new Entity[160];
		assetSetter.setObject();
		assetSetter.setMonster();

		continuable = false;
		
		gameState = titleState;
		
		player.loadPlayerImage();
		player.setValue();
	}

	public void newGame() {
		player.setDefaultValues();
		tileManager.loadMap("../res/map/worldV4.txt");
		setupGame();
		gameState = playState;
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

		double timer = 0;
		int drawCount = 0;

		while(gameThread != null) {
			currentTime = System.currentTimeMillis();
			delta += (currentTime - lastTime) / drawInterval;
			timer += (currentTime - lastTime);
			lastTime = currentTime;

			if(delta >= 1) {
				update();
				repaint();
				drawCount++;
				delta--;
			}

			if(timer > 1000) {
				fps = drawCount;
				drawCount = 0;
				timer = 0;
			}
		}
	}

	private void update() {
		if(gameState == playState) {
			player.update();

			// MONSTER
			int monsterCounter = monsters.length;

			for(int i = 0; i < monsters.length; i++) {
				if(monsters[i] != null && monsters[i].dying == false) {
					monsters[i].update();
					if(monsters[i].alive == false) {
						monsterWorldX = monsters[i].worldX;
						monsterWorldY = monsters[i].worldY;
						monsters[i] = null;
					}
				} else if(monsters[i] == null){
					monsterCounter--;
				}
			}
			
			// win if all monster has been killed (demo)
			if(monsterCounter == 0) {
				if(level == 5) {
					music.stop();
				}
				if(level < 5) {
					level++;
					assetSetter.setMonster();
					playSoundEffect("Winning2");
					objects[assetSetter.objectIndex] = new OBJ_Key(this);
					objects[assetSetter.objectIndex].worldX = monsterWorldX;
					objects[assetSetter.objectIndex].worldY = monsterWorldY;
				}
			}

			// BOMBS
			for(int i = 0; i < bombs.length; i++) {
				if(!bombs[i].isExploded) {
					bombs[i].update();
				}
			}
		}
		else if (gameState == pauseState) {
			// do nothing
		}
		else if (gameState == finishState) {
			if(	keyHandler.upPressed == false && keyHandler.downPressed == false) {
				checkKeyHandlerFinishState = true;
			}
		}
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		Graphics2D g2D = (Graphics2D)g;
		if(gameState == playState || gameState == pauseState || gameState == finishState) {
			// TILES
			tileManager.draw(g2D);
			
			for(int i = 0; i < bombs.length; i++) {
				if(!bombs[i].isExploded) {
					bombs[i].draw(g2D);
				}
			}
			
			// add entities to array list
			entityList.add(player);
			for(int i = 0; i < objects.length; i++) {
				if(objects[i] != null) {
					if(objects[i].alive == true) {
						entityList.add(objects[i]);
					} else {
						int worldX = objects[i].worldX;
						int worldY = objects[i].worldY;
						objects[i] = randomItem();
						if(objects[i] != null) {
							objects[i].worldX = worldX;
							objects[i].worldY = worldY;
							objects[i].alive = true;
						}
					}
				}
			}
			for(int i = 0; i < monsters.length; i++) {
				if(monsters[i] != null) {
					entityList.add(monsters[i]);
				}
			}
	
			// sort ascending by world y coordinate
			Collections.sort(entityList, new Comparator<Entity>() {
				@Override
				public int compare(Entity e1, Entity e2) {
					return Integer.compare(e1.worldY, e2.worldY);
				}
			});
			
			// draw one by one
			entityList.forEach(entity -> {
				entity.draw(g2D);
			});
			entityList.clear();
	
			// UI
			ui.draw(g2D);
		}
		else {
			ui.draw(g2D);
		}
		
		g2D.dispose();
	}

	public void playMusic() {
		music.setFile("Background");
		music.setVolume(ui.musicVolume);
		music.play();
		music.loop();
	}

	public void stopMusic() {
		soundEffect.stop();
	}

	public void playSoundEffect(String type) {
		soundEffect.setFile(type);
		soundEffect.setVolume(ui.effectVolume);
		soundEffect.play();
	}

	public Entity randomItem() {
		int rand = random.nextInt(100);
		if(rand < 5) return new OBJ_HealingPotion(this);
		else if(rand < 20) return new OBJ_ExtraBomb(this);
		else if(rand < 30) return new OBJ_LevelUpBomb(this);
		else if(rand < 35) return new OBJ_Boots(this);
		return null;
	}
}

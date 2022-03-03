package entity;

import java.awt.*;
import java.awt.image.*;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;
import main.KeyHandler;
import main.UtilityTool;
import object.Bomb;

public class Player extends Entity{

	KeyHandler keyHandler;
	GamePanel gamePanel;

	public final int screenX;
	public final int screenY;
	public int hasKey = 0;
	public int hasBomb;
	boolean isPlaceBomb = false;

	Bomb[] bombs;
	int bombIndex = 0;

	public Player(GamePanel gamePanel, KeyHandler keyHandler) {
		this.gamePanel = gamePanel;
		this.keyHandler = keyHandler;

		screenX = (gamePanel.screenWidth - gamePanel.tileSize) / 2;
		screenY = (gamePanel.screenHeight - gamePanel.tileSize) / 2;

		solidArea = new Rectangle(8,16,32,32);
		solidAreaDefaultX = solidArea.x;
		solidAreaDefaultY = solidArea.y;

		setDefaultValues();
		loadPlayerImage();
	}
	
	public void setDefaultValues() {
		hasBomb = 5;
		bombs = new Bomb[hasBomb];
		for(int i = 0; i < bombs.length; i++) bombs[i] = new Bomb(gamePanel);
		worldX = gamePanel.tileSize * 23;
		worldY = gamePanel.tileSize * 21;
		speed = 3;
		direction = "up";
	}

	public void loadPlayerImage() {
		up1 = setup("boy_up_1");
		up2 = setup("boy_up_2");
		down1 = setup("boy_down_1");
		down2 = setup("boy_down_2");
		left1 = setup("boy_left_1");
		left2 = setup("boy_left_2");
		right1 = setup("boy_right_1");
		right2 = setup("boy_right_2");
	}

	public BufferedImage setup(String imageName) {
		UtilityTool uTool = new UtilityTool();
		BufferedImage image = null;
		try {
			image = ImageIO.read(new File("../res/Player/Walking sprites/"+imageName+".png"));
			image = uTool.scaleImage(image, gamePanel.tileSize, gamePanel.tileSize);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return image;
	}

	public void setupBomb() {
		if(bombIndex >= bombs.length && bombs[0].isExploded) {
			bombIndex = 0;
		}

		int bombCol = (this.worldX + (gamePanel.tileSize / 2)) / gamePanel.tileSize;
		int bombRow = (this.worldY + (gamePanel.tileSize / 2)) / gamePanel.tileSize;
		bombs[bombIndex].worldX = bombCol * gamePanel.tileSize;
		bombs[bombIndex].worldY = bombRow * gamePanel.tileSize;

		// if this place already has a bomb
		for(int i = 0; i < bombs.length; i++) {
			if(i == bombIndex) continue;
			if(!bombs[i].isExploded &&
				bombs[bombIndex].worldX == bombs[i].worldX &&
				bombs[bombIndex].worldY == bombs[i].worldY
			) {
				isPlaceBomb = false;
				return;
			}
		}

		hasBomb--;
		bombs[bombIndex].timer = 5;
		bombs[bombIndex].isExploded = false;
		bombIndex++;
	}
	
	public void update() {
		if(keyHandler.placeBombPressed == true) {
			isPlaceBomb = true;
			keyHandler.placeBombPressed = false;
		}
		if(keyHandler.upPressed == true) {direction = "up";}
		else if(keyHandler.downPressed == true) {direction = "down";}
		else if(keyHandler.leftPressed == true) {direction = "left";}
		else if(keyHandler.rightPressed == true) {direction = "right";}
		else return;
		
		// CHECK TILES COLLISION
		collisionOn = false;
		gamePanel.collisionChecker.checkTile(this);
		
		// CHECK OBJECTS COLLISION
		int index = gamePanel.collisionChecker.checkEntity(gamePanel.objects, this);
		gamePanel.collisionChecker.checkEntity(bombs, this);
		pickUpKey(index);

		switch (direction) {
			case "up":if(collisionOn == false) {worldY -= speed;}break;
			case "down":if(collisionOn == false) {worldY += speed;}break;
			case "left":if(collisionOn == false) {worldX -= speed;}break;
			case "right":if(collisionOn == false) {worldX += speed;}break;
			default: break;
		}

		spritesCounter++;
		if(spritesCounter == 12) {
			if(spritesNum == 1) spritesNum = 2;
			else spritesNum = 1;
			spritesCounter = 0;
		}
	}

	public void pickUpKey(int i) {
		if(i != -1) {
			switch (gamePanel.objects[i].name) {
				case "Key":
					gamePanel.playSoundEffect("Key");
					gamePanel.objects[i] = null;
					hasKey++;
					gamePanel.ui.showMessage("you got a key!");
					break;
				case "Door":
					if(hasKey > 0) {
						gamePanel.playSoundEffect("Door");
						gamePanel.objects[i] = null;
						hasKey--;
						gamePanel.ui.showMessage("door opened!");
					} else {
						gamePanel.ui.showMessage("this door is locked! You might need a key");
					}
					break;
				case "Boots":
					gamePanel.playSoundEffect("Boots");
					gamePanel.ui.showMessage("Speed up!");
					gamePanel.objects[i] = null;
					speed += 1;
					break;
				case "Chest":
					// gamePanel.music.stop();
					gamePanel.playSoundEffect("Chest");
					gamePanel.ui.gameFinished = true;
					break;
				default:
					break;
			}
		}
	}

	public void draw(Graphics2D g2D) {
		
		BufferedImage image = null;
		switch (direction) {
			case "up":
				if(spritesNum == 1) image = up1;
				else image = up2;
				break;
			case "down":
				if(spritesNum == 1)	image = down1;
				else image = down2;
				break;
			case "left":
				if(spritesNum == 1) image = left1;
				else image = left2;
				break;
			case "right":
				if(spritesNum == 1) image = right1;
				else image = right2;
				break;
			default:
				break;
		}

		if(isPlaceBomb == true && hasBomb > 0) {
			setupBomb();
		} 
		isPlaceBomb = false;

		// BOMB
		for(int i = 0; i < bombs.length; i++) {
			Bomb tmp = bombs[i];
			if(!tmp.isExploded) {
				tmp.draw(g2D, gamePanel);

				// if player go away from bomb then set the bomb's collision = true
				// get player solid area position
				this.solidArea.x = this.worldX + this.solidArea.x;
				this.solidArea.y = this.worldY + this.solidArea.y;

				// Get object's solid area position
				tmp.solidArea.x = tmp.worldX + tmp.solidArea.x;
				tmp.solidArea.y = tmp.worldY + tmp.solidArea.y;

				if(tmp.collision == false && !tmp.solidArea.intersects(this.solidArea)) {
					tmp.collision = true;
				}

				this.solidArea.x = this.solidAreaDefaultX;
				this.solidArea.y = this.solidAreaDefaultY;
				tmp.solidArea.x = tmp.solidAreaDefaultX;
				tmp.solidArea.y = tmp.solidAreaDefaultY;


				// bomb clock count down
				tmp.frameCounter++;
				if(tmp.frameCounter >= 60) {
					tmp.timer--;
					tmp.frameCounter = 0;
					if(tmp.timer == 0) {
						tmp.explode();
						hasBomb++;
					}
				}
			}
		}
		g2D.drawImage(image, screenX, screenY, null);
	}
}

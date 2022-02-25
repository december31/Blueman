package entity;

import java.awt.*;
import java.awt.image.*;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;
import main.KeyHandler;
import main.UtilityTool;

public class Player extends Entity{

	KeyHandler keyHandler;
	GamePanel gamePanel;

	public final int screenX;
	public final int screenY;
	public int hasKey = 0;

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
		worldX = gamePanel.tileSize * 23;
		worldY = gamePanel.tileSize * 21;
		speed = 3;
		direction = "up";
	}

	public void loadPlayerImage() {
		try {
			up1 = ImageIO.read(new File("../res/Player/Walking sprites/boy_up_1.png"));
			up2 = ImageIO.read(new File("../res/Player/Walking sprites/boy_up_2.png"));
			down1 = ImageIO.read(new File("../res/Player/Walking sprites/boy_down_1.png"));
			down2 = ImageIO.read(new File("../res/Player/Walking sprites/boy_down_2.png"));
			left1 = ImageIO.read(new File("../res/Player/Walking sprites/boy_left_1.png"));
			left2 = ImageIO.read(new File("../res/Player/Walking sprites/boy_left_2.png"));
			right1 = ImageIO.read(new File("../res/Player/Walking sprites/boy_right_1.png"));
			right2 = ImageIO.read(new File("../res/Player/Walking sprites/boy_right_2.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
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

	public void update() {
		if(keyHandler.upPressed == true) {
			direction = "up";
		}
		else if(keyHandler.downPressed == true) {
			direction = "down";
		}
		else if(keyHandler.leftPressed == true) {
			direction = "left";
		}
		else if(keyHandler.rightPressed == true) {
			direction = "right";
		}
		else return;

		// CHECK TILES COLLISION
		collisionOn = false;
		gamePanel.collisionChecker.checkTile(this);

		// CHECK OBJECTS COLLISION
		int index = gamePanel.collisionChecker.checkEntity(this);
		pickUpKey(index);

		switch (direction) {
			case "up":
				if(collisionOn == false) {
					worldY -= speed;
				}
				break;
			case "down":
				if(collisionOn == false) {
					worldY += speed;
				}
				break;
			case "left":
				if(collisionOn == false) {
					worldX -= speed;
				}
				break;
			case "right":
				if(collisionOn == false) {
					worldX += speed;
				}
				break;
			default:
				break;
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
					gamePanel.music.stop();
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
		g2D.drawImage(image, screenX, screenY, null);
	}
}

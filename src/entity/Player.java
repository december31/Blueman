package entity;

import java.awt.*;

import main.GamePanel;
import object.OBJ_Bomb;

public class Player extends Entity{
	public final int screenX;
	public final int screenY;
	public int hasKey;
	public int hasBomb;
	public int maxBomb;
	public int point;
	boolean isPlaceBomb = false;

	// BOMB
	int bombIndex = 0;
	int check = 0;

	public Player(GamePanel gamePanel) {
		super(gamePanel);

		screenX = (gamePanel.screenWidth - gamePanel.tileSize) / 2;
		screenY = (gamePanel.screenHeight - gamePanel.tileSize) / 2;

		solidArea = new Rectangle(8,16,32,32);
		solidAreaDefaultX = solidArea.x;
		solidAreaDefaultY = solidArea.y;

		setDefaultValues();
		loadPlayerImage();
	}
	
	public void setDefaultValues() {
		worldX = gamePanel.tileSize * 15;
		worldY = gamePanel.tileSize * 73;

		speed = 3;
		direction = "up";
		invincibleTime = 2;
		hasBomb = 1;
		maxBomb = hasBomb;
		hasKey = 0;
		point = 0;

		maxLife = 6;
		alive = true;
		life = maxLife;
		dyingCounter = 0;
	}

	public void loadPlayerImage() {
		up1 = setup("../res/Player/Walking sprites/Masked/boy_up_1.png");
		up2 = setup("../res/Player/Walking sprites/Masked/boy_up_2.png");
		down1 = setup("../res/Player/Walking sprites/Masked/boy_down_1.png");
		down2 = setup("../res/Player/Walking sprites/Masked/boy_down_2.png");
		left1 = setup("../res/Player/Walking sprites/Masked/boy_left_1.png");
		left2 = setup("../res/Player/Walking sprites/Masked/boy_left_2.png");
		right1 = setup("../res/Player/Walking sprites/Masked/boy_right_1.png");
		right2 = setup("../res/Player/Walking sprites/Masked/boy_right_2.png");
	}

	public void setupBomb() {
		if(bombIndex >= gamePanel.bombs.length && gamePanel.bombs[0].isExploded) {
			bombIndex = 0;
		}
		// get bomb position
		int bombCol = (this.worldX + (gamePanel.tileSize / 2)) / gamePanel.tileSize;
		int bombRow = (this.worldY + (gamePanel.tileSize / 2)) / gamePanel.tileSize;
		int bombWorldX = bombCol * gamePanel.tileSize;
		int bombWorldY = bombRow * gamePanel.tileSize;
		// set up bomb
		if(gamePanel.bombs[bombIndex].setUpBomb(bombWorldX, bombWorldY) == true) {
			hasBomb--;
			bombIndex++;
		}

	}
	
	@Override
	public void update() {

		if(!alive) gamePanel.gameState = gamePanel.finishState;

		if(invincible) {
			invincibleCounter++;
			if(invincibleCounter >= invincibleTime * 60) {
				invincible = false;
				invincibleCounter = 0;
			}
		}

		// PLACE gamePanel.bombs
		// if player continue press place bomb key then bomb will be placed continuously 
		// so in this case only if player release place bomb key then bomb is setup and placed
		if(gamePanel.keyHandler.placeBombPressed == true) {
			if(check == 0) {
				isPlaceBomb = true;
				check = 1;
			}
		}
		if(gamePanel.keyHandler.placeBombPressed == false) {check = 0;}
		if(isPlaceBomb == true && hasBomb > 0) {setupBomb();} 
		isPlaceBomb = false;
		

		if(gamePanel.keyHandler.upPressed == true) {direction = "up";}
		else if(gamePanel.keyHandler.downPressed == true) {direction = "down";}
		else if(gamePanel.keyHandler.leftPressed == true) {direction = "left";}
		else if(gamePanel.keyHandler.rightPressed == true) {direction = "right";}
		else return;
		
		// CHECK TILES COLLISION
		collisionOn = false;
		gamePanel.collisionChecker.checkTile(this);
		
		// CHECK OBJECTS COLLISION
		int index = gamePanel.collisionChecker.checkEntity(gamePanel.objects, this);
		int monsterIndex = gamePanel.collisionChecker.checkEntity(gamePanel.monsters, this);
		gamePanel.collisionChecker.checkEntity(gamePanel.bombs, this);

		interactObject(index);
		takeDamage(monsterIndex);

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

	public void interactObject(int i) {
		if(i != -1) {
			switch (gamePanel.objects[i].name) {
				case "Key":
					gamePanel.playSoundEffect("Key");
					gamePanel.objects[i] = null;
					hasKey++;
					gamePanel.ui.showMessage("you got a key!");
					point += 20;
					break;
				case "Door":
					if(hasKey > 0 && gamePanel.objects[i].collision == true) {
						gamePanel.playSoundEffect("Door");
						gamePanel.objects[i].direction = "up";
						gamePanel.objects[i].worldY -= 4;
						gamePanel.objects[i].collision = false;
						hasKey--;
						gamePanel.ui.showMessage("door opened!");
					} else if (gamePanel.objects[i].collision == true){
						gamePanel.ui.showMessage("this door is locked! You might need a key");
					}
					break;
				case "Boots":
					gamePanel.objects[i] = null;
					gamePanel.playSoundEffect("Boots");
					if(speed < maxSpeed) {
						speed += 1;
						gamePanel.ui.showMessage("Speed up!");
						point += 30;
					}
					break;
				case "ExtraBomb":
					if(maxBomb < gamePanel.bombs.length) {
						hasBomb++;
						maxBomb++;
					}
					point += 30;
					gamePanel.objects[i] = null;
					gamePanel.playSoundEffect("BombLevelUp");
					break;
				case "LevelUpBomb":
					OBJ_Bomb.power++;
					gamePanel.objects[i] = null;
					gamePanel.playSoundEffect("BombLevelUp");
					point += 30;
					break;
				case "HealingPotion":
					if(life < maxLife) life++;
					gamePanel.objects[i] = null;
					point += 30;
					break;
				case "Chest":
					gamePanel.music.stop();
					gamePanel.soundEffect.stop();
					gamePanel.playSoundEffect("Winning3");
					gamePanel.gameState = gamePanel.finishState;
					break;
				default:
					break;
			}
		}
	}
	
	@Override
	public void takeDamage(int index) {
		if(index != -1) {
			if(!invincible) {
				gamePanel.playSoundEffect("TakeDamage");
				life--;
				if(life == 0) {
					dying = true;
					return;
				}
				invincible = true;
			}
		}
	}
}

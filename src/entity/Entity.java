package entity;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;
import main.UtilityTool;

public class Entity {
	public GamePanel gamePanel;

	public BufferedImage image, image1, image2;
	public String name = "";
	public boolean collision = false;
	public boolean breakable = false;
	public UtilityTool uTool = new UtilityTool();

	public int worldX, worldY;
	public int speed;
	public final int maxSpeed = 5;
	public BufferedImage up1, up2, down1, down2, left1, left2, right1, right2;
	public String direction = "down";
	public int spritesCounter = 0;
	public int spritesNum = 1;

	public Rectangle solidArea = new Rectangle(0,0,48,48);
	public int solidAreaDefaultX, solidAreaDefaultY;
	public boolean collisionOn;

	public int actionLockCounter = 0;
	public int maxLife;
	public int life;

	public boolean invincible = false;
	public int invincibleTime;
	public int invincibleCounter = 0;

	public boolean alive = true;
	public boolean dying = false;
	public int dyingCounter = 0;

	public int type;	// 0: player, 1: monster, 2: npc

	public Entity(GamePanel gamePanel) {
		this.gamePanel = gamePanel;
	}

	public BufferedImage setup(String imagePath) {
		UtilityTool uTool = new UtilityTool();
		BufferedImage image = null;
		try {
			image = ImageIO.read(new File(imagePath));
			image = uTool.scaleImage(image, gamePanel.tileSize, gamePanel.tileSize);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return image;
	}

	public BufferedImage setup(String imagePath, int width, int height) {
		UtilityTool uTool = new UtilityTool();
		BufferedImage image = null;
		try {
			image = ImageIO.read(new File(imagePath));
			image = uTool.scaleImage(image, width, height);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return image;
	}

	public void setAction(){}
	public void takeDamage(int index){}
	
	public void update() {

		// CHECK TILES COLLISION
		collisionOn = false;
		gamePanel.collisionChecker.checkTile(this);
		
		// CHECK OBJECTS COLLISION
		gamePanel.collisionChecker.checkEntity(gamePanel.objects, this);
		gamePanel.collisionChecker.checkEntity(gamePanel.monsters, this);
		gamePanel.collisionChecker.checkEntity(gamePanel.bombs, this);
		int contactPlayer = gamePanel.collisionChecker.checkPlayer(this);
		
		if(type == 2) {
			gamePanel.player.takeDamage(contactPlayer);
		}

		switch (direction) {
			case "up":if(collisionOn == false) {worldY -= speed;}break;
			case "down":if(collisionOn == false) {worldY += speed;}break;
			case "left":if(collisionOn == false) {worldX -= speed;}break;
			case "right":if(collisionOn == false) {worldX += speed;}break;
			default: break;
		}
		setAction();
		
		spritesCounter++;
		if(spritesCounter == 12) {
			if(spritesNum == 1) spritesNum = 2;
			else spritesNum = 1;
			spritesCounter = 0;
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

		int screenX = worldX - gamePanel.player.worldX + gamePanel.player.screenX;
		int screenY = worldY - gamePanel.player.worldY + gamePanel.player.screenY;
		
		if(dying == true) {
			dyingAnimation(g2D);
		}
		else if(invincible == true) {
			invincibleAnimation(g2D);
		}

		// MONSTER HEALTH BAR
		if(type == 2 || type == 3) {
			double oneScale = (double)down1.getWidth() / maxLife;
			double hpBarValue = oneScale * life;
			g2D.setColor(new Color(0xF2EFC2));
			
			if(type == 2) {
				g2D.setFont(g2D.getFont().deriveFont(Font.BOLD, 12));
			} else {
				g2D.setFont(g2D.getFont().deriveFont(Font.BOLD, 14));
			}
			int length = (int)g2D.getFontMetrics().getStringBounds(name, g2D).getWidth();
			int x = screenX + down1.getWidth()/2 - length/2;
			g2D.drawString(name, x, screenY - 20);
			
			g2D.setColor(new Color(0,0,0));
			g2D.fillRect(screenX - 1, screenY - 16, down1.getWidth() + 2, 5);
			g2D.setColor(new Color(0xF50201));
			g2D.fillRect(screenX, screenY - 15, (int)hpBarValue, 3);
		}

		g2D.drawImage(image, screenX, screenY, null);
		// reset alpha
		g2D.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
	}

	public void invincibleAnimation(Graphics2D g2D) {
		invincibleCounter++;
		// set alpha (make player fade)
		if(invincibleCounter % 15 > 7) {
			g2D.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.3f));
		}
		if(invincibleCounter >= invincibleTime * 60) {
			invincible = false;
			invincibleCounter = 0;
		}
	}

	public void dyingAnimation(Graphics2D g2D) {
		dyingCounter++;
		if(dyingCounter % 10 >= 5) {
			g2D.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0f));
		}
		if(dyingCounter >= 40) {
			dying = false;
			alive = false;
		}
	}
}

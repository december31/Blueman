package object;

import java.io.File;
import java.io.IOException;
import java.awt.Graphics2D;
import java.awt.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

import main.GamePanel;

public class Bomb extends SuperObject{
	public int timer;		// (second) counter hit 0 then explode
	public int frameCounter;
	public boolean isExploded;
	public int power;
	BufferedImage explode;
	BufferedImage explode1;
	BufferedImage explode2;


	public Bomb(GamePanel gamePanel) {
		try {
			image = ImageIO.read(new File("../res/Object/bomb.png"));
			image = uTool.scaleImage(image, gamePanel.tileSize, gamePanel.tileSize);

			explode = ImageIO.read(new File("../res/Object/explode.png"));
			explode = uTool.scaleImage(explode, gamePanel.tileSize, gamePanel.tileSize);
			
			explode1 = ImageIO.read(new File("../res/Object/explode1.png"));
			explode1 = uTool.scaleImage(explode1, gamePanel.tileSize, gamePanel.tileSize);
			
			explode2 = ImageIO.read(new File("../res/Object/explode2.png"));
			explode2 = uTool.scaleImage(explode2, gamePanel.tileSize, gamePanel.tileSize);
		} catch (IOException e) {
			e.printStackTrace();
		}
		setDefaultValues();
	}
	
	public void setDefaultValues() {
		name = "Bomb";
		timer = 5;
		frameCounter = 0;
		isExploded = true;
		power = 3;
		collision = false;
	}

	public void explode() {
		isExploded = true;
		collision = false;
	}

	@Override
	public void draw(Graphics2D g2D, GamePanel gamePanel) {
		int screenX = worldX - gamePanel.player.worldX + gamePanel.player.screenX;
		int screenY = worldY - gamePanel.player.worldY + gamePanel.player.screenY;
		
		if(timer > 1) {
			g2D.drawImage(image, screenX, screenY, null);
		} else {
			g2D.drawImage(explode, screenX, screenY, null);
			
			int worldCol = worldX / gamePanel.tileSize;
			int worldRow = worldY / gamePanel.tileSize;
			int tileNum;

			boolean checkUp, checkDown, checkLeft, checkRight;
			checkUp = checkDown = checkLeft = checkRight = true;

			// bomb blow up
			for(int i = 1; i <= power; i++) {
				// top
				tileNum = gamePanel.tileManager.mapTilesNum[worldRow - i][worldCol];	
				if(gamePanel.tileManager.tiles[tileNum].breakable == true) {
					gamePanel.tileManager.mapTilesNum[worldRow - i][worldCol] = 0;
				} else if (gamePanel.tileManager.tiles[tileNum].collision == true){
					checkUp = false;
				}
				if(checkUp == true) {
					g2D.drawImage(explode1, screenX, screenY - gamePanel.tileSize * i, null);
				}
				// bottom
				tileNum = gamePanel.tileManager.mapTilesNum[worldRow + i][worldCol];
				if(gamePanel.tileManager.tiles[tileNum].breakable == true) {
					gamePanel.tileManager.mapTilesNum[worldRow + i][worldCol] = 0;
				} else if(gamePanel.tileManager.tiles[tileNum].collision == true) {
					checkDown = false;
				}
				if(checkDown == true) {
					g2D.drawImage(explode1, screenX, screenY + gamePanel.tileSize * i, null);
				}
				
				// left
				tileNum = gamePanel.tileManager.mapTilesNum[worldRow][worldCol - i];
				if(gamePanel.tileManager.tiles[tileNum].breakable == true) {
					gamePanel.tileManager.mapTilesNum[worldRow][worldCol - i] = 0;
				} else if(gamePanel.tileManager.tiles[tileNum].collision == true) {
					checkLeft = false;
				}
				if(checkLeft == true) {
					g2D.drawImage(explode2, screenX - gamePanel.tileSize * i, screenY, null);
				}
				
				// right
				tileNum = gamePanel.tileManager.mapTilesNum[worldRow][worldCol + i];
				if(gamePanel.tileManager.tiles[tileNum].breakable == true) {
					gamePanel.tileManager.mapTilesNum[worldRow][worldCol + i] = 0;
				} else if(gamePanel.tileManager.tiles[tileNum].collision == true) {
					checkRight = false;
				}
				if(checkRight == true) {
					g2D.drawImage(explode2, screenX + gamePanel.tileSize * i, screenY, null);
				}
			}
		}
	}
}
package main;

import java.awt.Graphics2D;
import java.awt.*;
import java.awt.image.BufferedImage;

import object.OBJ_Key;

public class UI {
	
	GamePanel gamePanel;
	Font arial_40, arial_80B;
	BufferedImage keyImage;
	public boolean messageOn = false;
	public String message = "";
	public int messageCounter = 0;
	public boolean gameFinished = false;

	double playTime = 0;

	public UI(GamePanel gamePanel) {
		this.gamePanel = gamePanel;
		arial_40 = new Font("Arial", Font.PLAIN, 40);
		arial_80B = new Font("Arial", Font.BOLD, 80);
		OBJ_Key key = new OBJ_Key(gamePanel);
		keyImage = key.image;
	}

	public void showMessage(String text) {
		message = text;
		messageOn = true;
	}

	public void draw(Graphics2D g2D) {
		if(gameFinished == true) {
			String text;
			int textLength;
			int x, y;
			
			text = "You found the treasure!";
			g2D.setFont(arial_40);
			g2D.setColor(Color.white);
			textLength = (int)g2D.getFontMetrics().getStringBounds(text, g2D).getWidth();	// return the length of text
			x = gamePanel.screenWidth/2 - textLength/2;
			y = gamePanel.screenHeight/2 - gamePanel.tileSize * 3;
			g2D.drawString(text,x,y);
			
			text = "Congratulation";
			g2D.setFont(arial_80B);
			g2D.setColor(Color.YELLOW);
			textLength = (int)g2D.getFontMetrics().getStringBounds(text, g2D).getWidth();	// return the length of text
			x = gamePanel.screenWidth/2 - textLength/2;
			y = gamePanel.screenHeight/2 + gamePanel.tileSize * 2;
			g2D.drawString(text,x,y);
			
			text = String.format("Your time is: %.2fs!", playTime);
			g2D.setFont(arial_40);
			g2D.setColor(Color.WHITE);
			textLength = (int)g2D.getFontMetrics().getStringBounds(text, g2D).getWidth();	// return the length of text
			x = gamePanel.screenWidth/2 - textLength/2;
			y = gamePanel.screenHeight/2 + gamePanel.tileSize * 4;
			g2D.drawString(text,x,y);


			gamePanel.gameThread = null;

		} else {
			int keyCount = gamePanel.player.hasKey;
	
			g2D.setFont(arial_40);
			g2D.setColor(Color.white);
			g2D.drawImage(keyImage, gamePanel.tileSize/2, gamePanel.tileSize/2, gamePanel.tileSize, gamePanel.tileSize, null);
			g2D.drawString(" x " + keyCount, 74, 65);
	
			// display time
			playTime += (double)1/60;
			g2D.drawString(String.format("Time: %.2f", playTime), gamePanel.tileSize*11, 65);

			// draw message
			if(messageOn == true) {
				g2D.drawString(message, gamePanel.tileSize / 2, gamePanel.screenHeight / 2 - g2D.getFont().getSize());
				messageCounter++;
	
				if(messageCounter > 120) {
					messageCounter = 0;
					messageOn = false;
				}
			}
		}
	}
	
}

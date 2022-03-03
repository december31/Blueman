package main;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import object.Bomb;
import object.OBJ_Key;

public class UI {
	
	GamePanel gamePanel;
	Font arial_40, arial_80B, NewellsHand_40, pixel;
	BufferedImage keyImage;
	BufferedImage bombImage;
	public boolean messageOn = false;
	public String message = "";
	public int messageCounter = 0;
	public boolean gameFinished = false;
	double playTime = 0;
	public int commandNum = 0;

	// tutorial image
	BufferedImage background;
	BufferedImage escape;
	BufferedImage control;
	BufferedImage placeBomb;
	BufferedImage pressedW;
	BufferedImage pressedA;
	BufferedImage pressedD;
	BufferedImage pressedS;
	BufferedImage pressedB;


	public UI(GamePanel gamePanel) {
		this.gamePanel = gamePanel;
		arial_40 = new Font("Arial", Font.PLAIN, 40);
		arial_80B = new Font("Arial", Font.BOLD, 80);
		NewellsHand_40 = new Font("NewellsHand", Font.BOLD, 40);
		try {
			pixel = Font.createFont(Font.TRUETYPE_FONT, new File("../res/Font/pixels.ttf"));
		} catch (FontFormatException | IOException e) {
			e.printStackTrace();
		}

		// import tutorial image
		background = loadImage("../res/Title/title.png", gamePanel.screenWidth, gamePanel.screenHeight);
		escape = loadImage("../res/Title/escape.png", gamePanel.tileSize / 2, gamePanel.tileSize / 2);
		control = loadImage("../res/Title/control.png", 3);
		placeBomb = loadImage("../res/Title/placeBomb.png", 3);
		pressedW = loadImage("../res/Title/pressedW.png", 3);
		pressedS = loadImage("../res/Title/pressedS.png", 3);
		pressedA = loadImage("../res/Title/pressedA.png", 3);
		pressedD = loadImage("../res/Title/pressedD.png", 3);
		pressedB = loadImage("../res/Title/pressedB.png", 3);


		OBJ_Key key = new OBJ_Key(gamePanel);
		Bomb bomb = new Bomb(gamePanel);
		keyImage = key.image;
		bombImage = bomb.image;
	}

	public void showMessage(String text) {
		message = text;
		messageOn = true;
	}

	public BufferedImage loadImage(String filePath, int width, int height) {
		UtilityTool uTool = new UtilityTool();
		try {
			BufferedImage image = ImageIO.read(new File(filePath));
			image = uTool.scaleImage(image, width, height);
			return image;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public BufferedImage loadImage(String filePath, int scale) {
		UtilityTool uTool = new UtilityTool();
		try {
			BufferedImage image = ImageIO.read(new File(filePath));
			image = uTool.scaleImage(image, image.getWidth() * scale, image.getHeight() * scale);
			return image;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public void draw(Graphics2D g2D) {
		g2D.setFont(pixel);
		if(gamePanel.gameState == gamePanel.pauseState) {
			drawPauseScreen(g2D);
		}
		else if(gamePanel.gameState == gamePanel.titleState) {
			drawTitleScreen(g2D);
		}
		else if(gamePanel.gameState == gamePanel.tutorialState) {
			drawTutorialScreen(g2D);
		}
		else {
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
	
				// draw key
				int keyCount = gamePanel.player.hasKey;	
				g2D.setFont(arial_40);
				g2D.setColor(Color.white);
				g2D.drawImage(keyImage, gamePanel.tileSize/2, gamePanel.tileSize/2, null);
				g2D.drawString(" x " + keyCount, 74, 65);
	
				// draw bomb
				int bombCount = gamePanel.player.hasBomb;
				g2D.setFont(arial_40);
				g2D.setColor(Color.white);
				g2D.drawImage(bombImage, gamePanel.tileSize/2, gamePanel.tileSize/2 + gamePanel.tileSize, null);
				g2D.drawString(" x " + bombCount, 74, 65 + gamePanel.tileSize);
		
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

	public void drawTutorialScreen(Graphics2D g2D) {
		BufferedImage image;		
		// BACKGROUND
		image = background;
		g2D.drawImage(image, 0, 0, null);
		g2D.setColor(new Color(0,0,0,150));
		g2D.fillRect(0, 0, gamePanel.screenWidth, gamePanel.screenHeight);

		// ESCAPE
		image = escape;
		int x = gamePanel.tileSize / 2;
		int y = gamePanel.tileSize / 2;
		g2D.setFont(g2D.getFont().deriveFont(Font.BOLD, 20));
		g2D.drawImage(image, x, y, null);
		g2D.setColor(Color.white);
		g2D.drawString("go back (esc)", x + 30, y + 18);
		
		// CONTROL KEYS
		image = control;
		x = gamePanel.tileSize;
		y = gamePanel.tileSize * 3;
		g2D.drawImage(image, x, y, null);
		
		x += gamePanel.tileSize * 4;
		y += gamePanel.tileSize + 20;
		g2D.setFont(g2D.getFont().deriveFont(Font.BOLD, 60));
		g2D.setColor(Color.white);
		g2D.drawString("   -    M o v e", x, y);


		// PLACE BOMB KEY
		image = placeBomb;
		x = gamePanel.tileSize * 2;
		y = gamePanel.tileSize * 8;
		g2D.drawImage(image, x, y, null);
		
		x += gamePanel.tileSize * 3;
		y += gamePanel.tileSize - 10;
		g2D.setFont(g2D.getFont().deriveFont(Font.BOLD, 60));
		g2D.drawString("   -    P l a c e  b o m b", x, y);


		// KEY HANDLER: draw effect if player press keys
		if(gamePanel.keyHandler.upPressed == true) {
			g2D.drawImage(pressedW, gamePanel.tileSize * 2 + 6, gamePanel.tileSize * 3, null);;
		}
		if(gamePanel.keyHandler.downPressed == true) {
			g2D.drawImage(pressedS, gamePanel.tileSize * 2 + 6, gamePanel.tileSize * 4 + 6, null);;
		}
		if(gamePanel.keyHandler.leftPressed == true) {
			g2D.drawImage(pressedA, gamePanel.tileSize, gamePanel.tileSize * 4 + 6, null);;
		}
		if(gamePanel.keyHandler.rightPressed == true) {
			g2D.drawImage(pressedD, gamePanel.tileSize * 3 + 12, gamePanel.tileSize * 4 + 6, null);;
		}
		if(gamePanel.keyHandler.placeBombPressed == true) {
			g2D.drawImage(pressedB, gamePanel.tileSize * 2, gamePanel.tileSize * 8, null);;
		}

	}

	public void drawTitleScreen(Graphics2D g2D) {
		try {
			BufferedImage titleImage = ImageIO.read(new File("../res/Title/title.png"));
			g2D.drawImage(titleImage, 0, 0, gamePanel.screenWidth, gamePanel.screenHeight, null);
			g2D.setColor(new Color(0,0,0,150));
			g2D.fillRect(0, 0, gamePanel.screenWidth, gamePanel.screenHeight);
		} catch (IOException e) {
			e.printStackTrace();
		}

		g2D.setFont(g2D.getFont().deriveFont(Font.BOLD, 120));
		String text = "Bomberman 2";
		int x = getXForCenteredText(g2D, text);
		int y = gamePanel.tileSize * 3;

		// shadow
		g2D.setColor(Color.gray);
		g2D.drawString(text, x + 5, y + 5);
		g2D.drawString(text, x + 4, y + 4);
		g2D.drawString(text, x + 3, y + 3);
		g2D.drawString(text, x + 2, y + 2);
		g2D.drawString(text, x + 1, y + 1);
		// title
		g2D.setColor(Color.white);
		g2D.drawString(text, x, y);


		// character
		int size = gamePanel.tileSize * 2;
		x = gamePanel.screenWidth / 2 - size / 2;
		y = gamePanel.tileSize * 5;
		g2D.drawImage(gamePanel.player.down1, x, y, size, size ,null);

		// menu
		g2D.setFont(g2D.getFont().deriveFont(Font.BOLD, 36));
		text = "new game".toUpperCase();
		x = getXForCenteredText(g2D, text);
		y = gamePanel.tileSize * 9;
		if(commandNum == 0) {
			g2D.setColor(Color.yellow);
			g2D.drawString(">", x - gamePanel.tileSize, y - 2);
		} else {
			g2D.setColor(Color.white);
		}
		g2D.drawString(text, x, y);

		text = "tutorial".toUpperCase();
		x = getXForCenteredText(g2D, text);
		y = gamePanel.tileSize * 10;
		if(commandNum == 1) {
			g2D.setColor(Color.yellow);
			g2D.drawString(">", x - gamePanel.tileSize, y - 2);
		} else {
			g2D.setColor(Color.white);
		}
		g2D.drawString(text, x, y);
		
		text = "quit game".toUpperCase();
		x = getXForCenteredText(g2D, text);
		y = gamePanel.tileSize * 11;
		if(commandNum == 2) {
			g2D.setColor(Color.yellow);
			g2D.drawString(">", x - gamePanel.tileSize, y - 2);
		} else {
			g2D.setColor(Color.white);
		}
		g2D.drawString(text, x, y);
	}

	public void drawPauseScreen(Graphics2D g2D) {
		g2D.setFont(g2D.getFont().deriveFont(Font.BOLD, 40));
		g2D.setColor(new Color(0, 0, 0, 200));
		g2D.fillRect(0, 0, gamePanel.screenWidth, gamePanel.screenHeight);
		g2D.setColor(Color.white);

		String text = "Continue";
		int x = getXForCenteredText(g2D, text);
		int y = gamePanel.screenHeight / 2 - gamePanel.tileSize;
		if(commandNum == 0) {
			g2D.setColor(Color.yellow);
			g2D.drawString(">", x - gamePanel.tileSize, y - 2);
		} else {
			g2D.setColor(Color.white);
		}
		g2D.drawString(text, x, y);
		
		text = "Main menu";
		x = getXForCenteredText(g2D, text);
		y = gamePanel.screenHeight / 2;
		if(commandNum == 1) {
			g2D.setColor(Color.yellow);
			g2D.drawString(">", x - gamePanel.tileSize, y - 2);
		} else {
			g2D.setColor(Color.white);
		}
		g2D.drawString(text, x, y);

	}

	public int getXForCenteredText(Graphics2D g2D, String text) {
		int length = (int)g2D.getFontMetrics().getStringBounds(text, g2D).getWidth();
		return gamePanel.screenWidth / 2 - length / 2;
	}
}

package main;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import object.OBJ_Bomb;
import object.OBJ_Heart;
import object.OBJ_LevelUpBomb;

public class UI {
	
	GamePanel gamePanel;
	Font arial_40, arial_80B, NewellsHand_40, pixel;
	BufferedImage bombImage, bombPowerImage;
	public boolean messageOn = false;
	public String message = "";
	public int messageCounter = 0;
	public int commandNum = 0;
	public int exitConfirmCommandNum = 0;

	public int chooseEffectCounter = 0;
	public int check = 0;

	// volume control
	public final int maxVolumeWidth = 400;
	public final int oneScale = maxVolumeWidth / 100;
	public int musicVolume = 50;
	public int effectVolume = 50;
	public int musicVolumeWidth = musicVolume * oneScale;
	public int effectVolumeWidth = effectVolume * oneScale;

	// tutorial image
	BufferedImage background;
	BufferedImage escape;
	BufferedImage control;
	BufferedImage placeBomb;
	BufferedImage pause;
	BufferedImage pressedW;
	BufferedImage pressedA;
	BufferedImage pressedD;
	BufferedImage pressedS;
	BufferedImage pressedB;
	BufferedImage pressedSpace;

	// heart
	BufferedImage heart_blank, heart_half, heart_full;

	public UI(GamePanel gamePanel) {
		this.gamePanel = gamePanel;
		arial_40 = new Font("Arial", Font.PLAIN, 40);
		arial_80B = new Font("Arial", Font.BOLD, 80);
		NewellsHand_40 = new Font("NewellsHand", Font.BOLD, 40);
		try {
			pixel = Font.createFont(Font.TRUETYPE_FONT, new File("../res/font/pixels.ttf"));
		} catch (FontFormatException | IOException e) {
			e.printStackTrace();
		}

		// import tutorial image
		background = loadImage("../res/title/title1.png", gamePanel.screenWidth, gamePanel.screenHeight);
		escape = loadImage("../res/title/escape.png", gamePanel.tileSize / 2, gamePanel.tileSize / 2);
		control = loadImage("../res/title/control.png", 3);
		placeBomb = loadImage("../res/title/placeBomb.png", 3);
		pause = loadImage("../res/title/space.png", 3);
		pressedW = loadImage("../res/title/pressedW.png", 3);
		pressedS = loadImage("../res/title/pressedS.png", 3);
		pressedA = loadImage("../res/title/pressedA.png", 3);
		pressedD = loadImage("../res/title/pressedD.png", 3);
		pressedB = loadImage("../res/title/pressedB.png", 3);
		pressedSpace = loadImage("../res/title/pressedSpace.png", 3);

		OBJ_Bomb bomb = new OBJ_Bomb(gamePanel);
		bombImage = bomb.image;
		OBJ_LevelUpBomb levelUpBomb = new OBJ_LevelUpBomb(gamePanel);
		bombPowerImage = levelUpBomb.down1;

		OBJ_Heart heart = new OBJ_Heart(gamePanel);
		heart_blank = heart.image;
		heart_half = heart.image1;
		heart_full = heart.image2;
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
		if(gamePanel.gameState == gamePanel.titleState || gamePanel.gameState == gamePanel.exitConfirmState) {
			drawTitleScreen(g2D);
		}
		else if(gamePanel.gameState == gamePanel.pauseState) {
			drawPauseScreen(g2D);
		}
		else if(gamePanel.gameState == gamePanel.tutorialState) {
			drawTutorialScreen(g2D);
		}
		else if(gamePanel.gameState == gamePanel.finishState) {
			drawFinishScreen(g2D);
		}
		else if(gamePanel.gameState == gamePanel.settingState) {
			drawSettingScreen(g2D);
		}
		else if(gamePanel.gameState == gamePanel.chooseCharacterState){
			drawChooseCharacter(g2D);
		}
		else {
			drawPlayScreen(g2D);
		}
	}


	private void drawChooseCharacter(Graphics2D g2D) {
		BufferedImage image = background;
		g2D.drawImage(image, 0, 0, gamePanel.screenWidth, gamePanel.screenHeight, null);
		g2D.setColor(new Color(0,0,0,170));
		g2D.fillRect(0, 0, gamePanel.screenWidth, gamePanel.screenHeight);

		// ESCAPE
		image = escape;
		int x = gamePanel.tileSize / 2;
		int y = gamePanel.tileSize / 2;
		g2D.setFont(g2D.getFont().deriveFont(Font.BOLD, 20));
		g2D.drawImage(image, x, y, null);
		g2D.setColor(Color.white);
		g2D.drawString("go back (esc)", x + 30, y + 18);

		// enter
		x = gamePanel.tileSize * 5 + 15;
		y = gamePanel.tileSize * 10 + gamePanel.tileSize / 2;
		try {
			image = ImageIO.read(new File("../res/title/enter0.png"));
			g2D.drawImage(image, x, y, gamePanel.tileSize * 2, gamePanel.tileSize / 2, null);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		g2D.setFont(g2D.getFont().deriveFont(Font.BOLD, 30));
		g2D.drawString("  >>>  play game", x + gamePanel.tileSize * 2, y + 20);

		// heading
		g2D.setFont(g2D.getFont().deriveFont(Font.BOLD, 80));
		String text = "choose your character";
		x = getXForCenteredText(g2D, text);
		y = gamePanel.tileSize * 2 + gamePanel.tileSize / 2;

		// shadow
		g2D.setColor(Color.gray);
		for(int i = 1; i <= 5; i++) {
			g2D.drawString(text, x + i, y + i);
		}
		// title
		g2D.setColor(Color.white);
		g2D.drawString(text, x, y);


		// character
		try {
			int size = gamePanel.tileSize * 3;
			int lineSpacing = 32;
			y = gamePanel.screenHeight / 2 - gamePanel.tileSize;
			BufferedImage arrowUp = ImageIO.read(new File("../res/title/arrowUp.png"));
			// boy
			image = ImageIO.read(new File("../res/player/Walking sprites/Masked/boy_down_1.png"));
			x = gamePanel.screenWidth / 2 - (gamePanel.tileSize * 6);
			if(commandNum == 0) {
				g2D.setColor(new Color(47, 49, 89, 150));
				g2D.fillRoundRect(x + gamePanel.tileSize * 3, y, 160, gamePanel.tileSize * 3, 30, 30);
				g2D.setFont(g2D.getFont().deriveFont(Font.PLAIN, 30));
				g2D.setColor(Color.white);
				text = "hp: 5";
				g2D.drawString(text, x + size + 30, y + lineSpacing);
				text = "speed: 2";
				g2D.drawString(text, x + size + 30, y + lineSpacing * 2);
				text = "bombs: 2";
				g2D.drawString(text, x + size + 30, y + lineSpacing * 3);
				text = "power: 1";
				g2D.drawString(text, x + size + 30, y + lineSpacing * 4);
				int arrowX = x + gamePanel.tileSize;
				int arrowY = y + gamePanel.tileSize * 3 + gamePanel.tileSize / 2;
				g2D.drawImage(arrowUp, arrowX, arrowY, gamePanel.tileSize, gamePanel.tileSize, null);
				text = "Romeo";
				g2D.setColor(Color.yellow);
				g2D.setFont(g2D.getFont().deriveFont(Font.BOLD, 50));
				g2D.drawString(text, x + 15, y - 20);
			} else {
				text = "Romeo";
				g2D.setFont(g2D.getFont().deriveFont(Font.BOLD, 40));
				g2D.setColor(Color.white);
				g2D.drawString(text, x + 25, y - 20);
			}
			g2D.drawImage(image, x, y, size, size ,null);

			// girl
			image = ImageIO.read(new File("../res/player/Walking sprites/Masked/girl_down_1.png"));
			x = gamePanel.screenWidth / 2 + (gamePanel.tileSize * 3);
			if(commandNum == 1) {
				g2D.setColor(new Color(242, 61, 61, 150));
				g2D.fillRoundRect(x - gamePanel.tileSize * 3 - 15, y, 160, gamePanel.tileSize * 3, 30, 30);
				g2D.setFont(g2D.getFont().deriveFont(Font.PLAIN, 30));
				g2D.setColor(Color.white);
				text = "hp: 3";
				g2D.drawString(text, x - gamePanel.tileSize * 3 + 15, y + lineSpacing);
				text = "speed: 3";
				g2D.drawString(text, x - gamePanel.tileSize * 3 + 15, y + lineSpacing * 2);
				text = "bombs: 1";
				g2D.drawString(text, x - gamePanel.tileSize * 3 + 15, y + lineSpacing * 3);
				text = "power: 2";
				g2D.drawString(text, x - gamePanel.tileSize * 3 + 15, y + lineSpacing * 4);
				int arrowX = x + gamePanel.tileSize;
				int arrowY = y + gamePanel.tileSize * 3 + gamePanel.tileSize / 2;
				g2D.drawImage(arrowUp, arrowX, arrowY, gamePanel.tileSize, gamePanel.tileSize, null);
				text = "Juliet";
				g2D.setColor(Color.yellow);
				g2D.setFont(g2D.getFont().deriveFont(Font.BOLD, 50));
				g2D.drawString(text, x + 24, y - 20);
			} else {
				text = "Juliet";
				g2D.setFont(g2D.getFont().deriveFont(Font.BOLD, 40));
				g2D.setColor(Color.white);
				g2D.drawString(text, x + 30, y - 20);
			}
			g2D.drawImage(image, x, y, size, size ,null);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void drawPlayScreen(Graphics2D g2D) {
		// draw max heart possible
		int x = gamePanel.tileSize / 2;
		int y = gamePanel.tileSize / 2;
		for(int i = 0; i < gamePanel.player.maxLife / 2; i++) {
			g2D.drawImage(heart_blank, x, y, null);
			x += gamePanel.tileSize;
		}
		
		x = gamePanel.tileSize / 2;
		y = gamePanel.tileSize / 2;
		int i = 0;
		while(i < gamePanel.player.life) {
			g2D.drawImage(heart_half, x, y, null);
			i++;
			if(i < gamePanel.player.life) {
				g2D.drawImage(heart_full, x, y, null);
			}
			i++;
			x += gamePanel.tileSize;
		}


		// draw bomb
		int bombCount = gamePanel.player.hasBomb;
		g2D.setFont(arial_40);
		g2D.setColor(Color.white);
		g2D.drawImage(bombImage, gamePanel.tileSize/2, gamePanel.tileSize/2 + gamePanel.tileSize, null);
		g2D.drawString(" x " + bombCount, 74, 65 + gamePanel.tileSize);

		int bombPower = OBJ_Bomb.power;
		g2D.setFont(arial_40);
		g2D.setColor(Color.white);
		g2D.drawImage(bombPowerImage, gamePanel.tileSize/2, gamePanel.tileSize/2 + gamePanel.tileSize * 2 + 10, null);
		g2D.drawString(" x " + bombPower, 74, 65 + gamePanel.tileSize * 2 + 10);
		
		// draw point
		int point = gamePanel.player.point;
		g2D.setFont(pixel.deriveFont(Font.PLAIN, 60));
		g2D.setColor(Color.white);
		g2D.drawString(String.format("%04d", point), 650, 60);
		
		// draw fps
		int fps = gamePanel.fps;
		g2D.setFont(arial_40.deriveFont(Font.PLAIN, 20));
		g2D.setColor(Color.white);
		g2D.drawString(String.format("fps: %d", fps), 700, 550);
		
		// draw message
		if(messageOn == true) {

			int messageLength = (int)g2D.getFontMetrics().getStringBounds(message, g2D).getWidth();

			int messageBoxWidth = messageLength + 100;
			int messageBoxHeight = 80;
			x = gamePanel.screenWidth / 2 - messageBoxWidth / 2;
			y = 130;

			g2D.setColor(new Color(255,255,255,20));
			for(int j = 6; j > 0; j--) {
				g2D.fillRoundRect(x - j, y - j, messageBoxWidth + j * 2, messageBoxHeight + j * 2, 30, 30);
			}
			// g2D.setColor(new Color(255,255,255,70));
			// g2D.fillRoundRect(x - 4, y - 4, messageBoxWidth + 8, messageBoxHeight + 8, 30, 30);
			// g2D.setColor(new Color(255,255,255,90));
			// g2D.fillRoundRect(x - 3, y - 3, messageBoxWidth + 6, messageBoxHeight + 6, 30, 30);
			// g2D.setColor(new Color(255,255,255,110));
			// g2D.fillRoundRect(x - 2, y - 2, messageBoxWidth + 4, messageBoxHeight + 4, 30, 30);
			// g2D.setColor(new Color(255,255,255,130));
			// g2D.fillRoundRect(x - 1, y - 1, messageBoxWidth + 2, messageBoxHeight + 2, 30, 30);

			g2D.setColor(Color.black);
			int[] triangleX = {	x + messageBoxWidth / 2 + 50,
								x + messageBoxWidth / 2 + 24,
								x + messageBoxWidth / 2 + 80};

			int[] triangleY = {y + messageBoxHeight, y + messageBoxHeight + gamePanel.tileSize, y + messageBoxHeight};
			g2D.fillPolygon(new Polygon(triangleX, triangleY, 3));

			g2D.setColor(new Color(0,0,0,210));
			g2D.fillRoundRect(x, y, messageBoxWidth, messageBoxHeight, 30, 30);

			g2D.setColor(Color.white);
			g2D.setFont(pixel.deriveFont(Font.BOLD, 30));
			x = getXForCenteredText(g2D, message);
			y = 177;
			g2D.drawString(message, x, y);
			messageCounter++;
	
			if(messageCounter > 120) {
				messageCounter = 0;
				messageOn = false;
			}
		}
	}

	public void drawFinishScreen(Graphics2D g2D) {
		g2D.setColor(new Color(0, 0, 0, 200));
		g2D.fillRect(0, 0, gamePanel.screenWidth, gamePanel.screenHeight);
		
		g2D.setFont(g2D.getFont().deriveFont(Font.BOLD, 140));
		String text;
		int x, y;
		if(gamePanel.player.alive == true) {
			text = "You win";
		} else {
			text = "You loose";
		}
		x = getXForCenteredText(g2D, text);
		y = gamePanel.tileSize * 4;
		g2D.setColor(Color.gray);
		g2D.drawString(text, x + 5, y + 5);
		g2D.drawString(text, x + 4, y + 4);
		g2D.drawString(text, x + 3, y + 3);
		g2D.drawString(text, x + 2, y + 2);
		g2D.drawString(text, x + 1, y + 1);
		g2D.setColor(Color.white);
		g2D.drawString(text, x, y);
		
		
		g2D.setFont(g2D.getFont().deriveFont(Font.BOLD, 40));
		text = "Play again";
		x = getXForCenteredText(g2D, text);
		y = gamePanel.tileSize * 7;
		if(commandNum == 0) {
			g2D.setColor(Color.yellow);
			g2D.drawString(">", x - gamePanel.tileSize, y - 2);
		} else {
			g2D.setColor(Color.white);
		}
		g2D.drawString(text, x, y);

		text = "Main menu";
		x = getXForCenteredText(g2D, text);
		y = gamePanel.tileSize * 8;
		if(commandNum == 1) {
			g2D.setColor(Color.yellow);
			g2D.drawString(">", x - gamePanel.tileSize, y - 2);
		} else {
			g2D.setColor(Color.white);
		}
		g2D.drawString(text, x, y);
	}

	public void drawTutorialScreen(Graphics2D g2D) {
		BufferedImage image;		
		// BACKGROUND
		image = background;
		g2D.drawImage(image, 0, 0, null);
		g2D.setColor(new Color(0,0,0,190));
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
		y = gamePanel.tileSize * 2;
		g2D.drawImage(image, x, y, null);
		
		x += gamePanel.tileSize * 4;
		y += gamePanel.tileSize + 20;
		g2D.setFont(g2D.getFont().deriveFont(Font.BOLD, 60));
		g2D.setColor(Color.white);
		g2D.drawString("   -    M o v e", x, y);


		// PLACE BOMB KEY
		image = placeBomb;
		x = gamePanel.tileSize * 2;
		y = gamePanel.tileSize * 6;
		g2D.drawImage(image, x, y, null);
		
		x += gamePanel.tileSize * 3;
		y += gamePanel.tileSize - 10;
		g2D.setFont(g2D.getFont().deriveFont(Font.BOLD, 60));
		g2D.drawString("   -    P l a c e  b o m b", x, y);
		
		
		// PAUSE KEY
		image = pause;
		x = gamePanel.tileSize;
		y = gamePanel.tileSize * 9;
		g2D.drawImage(image, x, y, null);
		
		x += gamePanel.tileSize * 4;
		y += gamePanel.tileSize - 10;
		g2D.setFont(g2D.getFont().deriveFont(Font.BOLD, 60));
		g2D.drawString("   -    P a u s e", x, y);


		// KEY HANDLER: draw effect if player press keys
		if(gamePanel.keyHandler.upPressed == true) {
			g2D.drawImage(pressedW, gamePanel.tileSize * 2 + 6, gamePanel.tileSize * 2, null);;
		}
		if(gamePanel.keyHandler.downPressed == true) {
			g2D.drawImage(pressedS, gamePanel.tileSize * 2 + 6, gamePanel.tileSize * 3 + 6, null);;
		}
		if(gamePanel.keyHandler.leftPressed == true) {
			g2D.drawImage(pressedA, gamePanel.tileSize, gamePanel.tileSize * 3 + 6, null);;
		}
		if(gamePanel.keyHandler.rightPressed == true) {
			g2D.drawImage(pressedD, gamePanel.tileSize * 3 + 12, gamePanel.tileSize * 3 + 6, null);;
		}
		if(gamePanel.keyHandler.placeBombPressed == true) {
			g2D.drawImage(pressedB, gamePanel.tileSize * 2, gamePanel.tileSize * 6, null);;
		}
		if(gamePanel.keyHandler.spacePressed) {
			g2D.drawImage(pressedSpace, gamePanel.tileSize, gamePanel.tileSize * 9, null);;
		}

	}

	public void drawTitleScreen(Graphics2D g2D) {
		BufferedImage titleImage = background;
		g2D.drawImage(titleImage, 0, 0, gamePanel.screenWidth, gamePanel.screenHeight, null);
		g2D.setColor(new Color(0,0,0,170));
		g2D.fillRect(0, 0, gamePanel.screenWidth, gamePanel.screenHeight);

		// GAME'S NAME
		g2D.setFont(g2D.getFont().deriveFont(Font.BOLD, 50));
		String text = "BOMBERMAN 2";
		int x = getXForCenteredText(g2D, text);
		int y = gamePanel.tileSize + 10;

		// shadow
		g2D.setColor(new Color(0xBF6B04));
		for(int i = 1; i <= 5; i++) {
			g2D.drawString(text, x + i, y + i);
		}
		// title
		g2D.setColor(new Color(0xF2DC6B));
		g2D.drawString(text, x, y);
		
		
		// SERIES'S NAME
		g2D.setFont(g2D.getFont().deriveFont(Font.BOLD, 115));
		text = "Covid Destroyer";
		x = getXForCenteredText(g2D, text);
		y = gamePanel.tileSize * 3;

		// shadow
		g2D.setColor(new Color(0x8b5e34));
		for(int i = 1; i <= 5; i++) {
			g2D.drawString(text, x + i, y + i);
		}
		// title
		g2D.setColor(new Color(0xf3d5b5));
		g2D.drawString(text, x, y);


		// character
		int size = gamePanel.tileSize * 2;
		x = gamePanel.screenWidth / 2 - size / 2;
		// if game can be continued
		if(gamePanel.continuable == true) {
			y = gamePanel.tileSize * 3 + gamePanel.tileSize / 2;
		} else {
			y = gamePanel.tileSize * 5 - gamePanel.tileSize / 2;
		}
		g2D.drawImage(gamePanel.player.down1, x, y, size, size ,null);

		// menu
		if(gamePanel.continuable) {
			g2D.setFont(g2D.getFont().deriveFont(Font.BOLD, 40));
			text = "continue".toUpperCase();
			x = getXForCenteredText(g2D, text);
			y = gamePanel.tileSize * 7;
			if(commandNum == -1) {
				g2D.setColor(Color.yellow);
				g2D.drawString(">", x - gamePanel.tileSize, y - 2);
			} else {
				g2D.setColor(Color.white);
			}
			g2D.drawString(text, x, y);
		}

		g2D.setFont(g2D.getFont().deriveFont(Font.BOLD, 40));
		text = "new game".toUpperCase();
		x = getXForCenteredText(g2D, text);
		y = gamePanel.tileSize * 8;
		if(commandNum == 0) {
			g2D.setColor(Color.yellow);
			g2D.drawString(">", x - gamePanel.tileSize, y - 2);
		} else {
			g2D.setColor(Color.white);
		}
		g2D.drawString(text, x, y);

		text = "tutorial".toUpperCase();
		x = getXForCenteredText(g2D, text);
		y = gamePanel.tileSize * 9;
		if(commandNum == 1) {
			g2D.setColor(Color.yellow);
			g2D.drawString(">", x - gamePanel.tileSize, y - 2);
		} else {
			g2D.setColor(Color.white);
		}
		g2D.drawString(text, x, y);
		
		text = "setting".toUpperCase();
		x = getXForCenteredText(g2D, text);
		y = gamePanel.tileSize * 10;
		if(commandNum == 2) {
			g2D.setColor(Color.yellow);
			g2D.drawString(">", x - gamePanel.tileSize, y - 2);
		} else {
			g2D.setColor(Color.white);
		}
		g2D.drawString(text, x, y);		
		
		text = "quit game".toUpperCase();
		x = getXForCenteredText(g2D, text);
		y = gamePanel.tileSize * 11;
		if(commandNum == 3) {
			g2D.setColor(Color.yellow);
			g2D.drawString(">", x - gamePanel.tileSize, y - 2);
		} else {
			g2D.setColor(Color.white);
		}
		g2D.drawString(text, x, y);	

		if(gamePanel.gameState == gamePanel.exitConfirmState) {
			g2D.setColor(new Color(0,0,0,150));
			g2D.fillRect(0, 0, gamePanel.screenWidth, gamePanel.screenHeight);

			g2D.setColor(new Color(0x733702));
			g2D.fillRoundRect(100, 200, gamePanel.screenWidth - 200, gamePanel.screenHeight - 400, 30, 30);
			g2D.setColor(Color.white);
			g2D.fillRoundRect(110, 210, gamePanel.screenWidth - 220, gamePanel.screenHeight - 420, 30, 30);
			g2D.setColor(new Color(0x733702));
			g2D.fillRoundRect(120, 220, gamePanel.screenWidth - 240, gamePanel.screenHeight - 440, 30, 30);

			text = "Are you sure you want to exit?";
			x = getXForCenteredText(g2D, text);
			y = 260;
			g2D.setColor(Color.white);
			g2D.drawString(text, x, y);
			
			text = "Yes";
			x = 250;
			y = 330;
			if(exitConfirmCommandNum == 0) {
				g2D.setColor(Color.yellow);
				g2D.drawString(">", x - gamePanel.tileSize / 2, y - 2);
			} else {
				g2D.setColor(Color.white);
			}
			g2D.drawString(text, x, y);
			
			text = "No";
			x = 470;
			y = 330;
			if(exitConfirmCommandNum == 1) {
				g2D.setColor(Color.yellow);
				g2D.drawString(">", x - gamePanel.tileSize / 2, y - 2);
			} else {
				g2D.setColor(Color.white);
			}
			g2D.drawString(text, x, y);
		}
		
	}
	
	public void drawPauseScreen(Graphics2D g2D) {
				
		g2D.setColor(new Color(0, 0, 0, 200));
		g2D.fillRect(0, 0, gamePanel.screenWidth, gamePanel.screenHeight);
		g2D.setColor(Color.white);
		
		g2D.setFont(g2D.getFont().deriveFont(Font.BOLD, 115));
		String text = "Covid Destroyer";
		int x = getXForCenteredText(g2D, text);
		int y = gamePanel.tileSize * 3;

		// shadow
		g2D.setColor(Color.gray);
		for(int i = 1; i <= 5; i++) {
			g2D.drawString(text, x + i, y + i);
		}
		// title
		g2D.setColor(Color.white);
		g2D.drawString(text, x, y);

		
		g2D.setFont(g2D.getFont().deriveFont(Font.BOLD, 40));
		text = "Continue";
		x = getXForCenteredText(g2D, text);
		y = gamePanel.screenHeight / 2;
		if(commandNum == 0) {
			g2D.setColor(Color.yellow);
			g2D.drawString(">", x - gamePanel.tileSize, y - 2);
		} else {
			g2D.setColor(Color.white);
		}
		g2D.drawString(text, x, y);

		text = "Tutorial";
		x = getXForCenteredText(g2D, text);
		y = gamePanel.screenHeight / 2 + gamePanel.tileSize;
		if(commandNum == 1) {
			g2D.setColor(Color.yellow);
			g2D.drawString(">", x - gamePanel.tileSize, y - 2);
		} else {
			g2D.setColor(Color.white);
		}
		g2D.drawString(text, x, y);
		
		text = "Setting";
		x = getXForCenteredText(g2D, text);
		y = gamePanel.screenHeight / 2 + gamePanel.tileSize * 2;
		if(commandNum == 2) {
			g2D.setColor(Color.yellow);
			g2D.drawString(">", x - gamePanel.tileSize, y - 2);
		} else {
			g2D.setColor(Color.white);
		}
		g2D.drawString(text, x, y);
		
		text = "Main menu";
		x = getXForCenteredText(g2D, text);
		y = gamePanel.screenHeight / 2 + gamePanel.tileSize * 3;
		if(commandNum == 3) {
			g2D.setColor(Color.yellow);
			g2D.drawString(">", x - gamePanel.tileSize, y - 2);
		} else {
			g2D.setColor(Color.white);
		}
		g2D.drawString(text, x, y);
	}

	public void drawSettingScreen(Graphics2D g2D) {
		BufferedImage image = background;
		g2D.drawImage(image, 0, 0, null);
		g2D.setColor(new Color(0,0,0,180));
		g2D.fillRect(0, 0, gamePanel.screenWidth, gamePanel.screenHeight);

		g2D.setColor(Color.white);
		g2D.setFont(g2D.getFont().deriveFont(Font.BOLD, 80));
		String text = "SETTING";
		int x = getXForCenteredText(g2D, text);
		int y = gamePanel.tileSize * 2;
		g2D.drawString(text, x, y);
		
		// ESCAPE
		image = escape;
		x = gamePanel.tileSize / 2;
		y = gamePanel.tileSize / 2;
		g2D.setFont(g2D.getFont().deriveFont(Font.BOLD, 20));
		g2D.drawImage(image, x, y, null);
		g2D.setColor(Color.white);
		g2D.drawString("go back (esc)", x + 30, y + 18);
		
		
		// MUSIC VOLUME
		g2D.setFont(g2D.getFont().deriveFont(Font.BOLD, 50));
		text = "Music";
		x = gamePanel.tileSize * 2;
		y = gamePanel.tileSize * 5 + 16;
		g2D.drawString(text, x, y);
		x = gamePanel.tileSize * 5;
		y = gamePanel.tileSize * 5;

		g2D.setColor(Color.black);
		g2D.fillRect(x - 1, y - 1, maxVolumeWidth + 2, 12);
		g2D.setColor(Color.white);
		g2D.fillRect(x, y, maxVolumeWidth, 10);
		
		g2D.setColor(Color.gray);
		g2D.fillRect(x, y, musicVolumeWidth, 10);
		
		g2D.setColor(Color.black);
		g2D.fillRect(x + musicVolumeWidth - 7 - 1, y - 2 - 1, 16, 16);
		g2D.setColor(Color.yellow);
		g2D.fillRect(x + musicVolumeWidth - 7, y - 2, 14, 14);
		
		// display musicVolume
		g2D.setFont(g2D.getFont().deriveFont(Font.PLAIN, 30));
		text = "";
		if(musicVolume < 10) {
			text += "0";
		}
		text += musicVolume + "";
		g2D.drawString(text, x + musicVolumeWidth - 7, y - gamePanel.tileSize / 2);

		
		// EFFECT VOLUME
		g2D.setColor(Color.white);
		g2D.setFont(g2D.getFont().deriveFont(Font.BOLD, 50));
		text = "Effect";
		x = gamePanel.tileSize * 2 - 14;
		y = gamePanel.tileSize * 7 + 16;
		g2D.drawString(text, x, y);

		x = gamePanel.tileSize * 5;
		y = gamePanel.tileSize * 7;
		
		effectVolume = (int)(effectVolumeWidth / oneScale);

		g2D.setColor(Color.black);
		g2D.fillRect(x - 1, y - 1, maxVolumeWidth + 2, 12);
		g2D.setColor(Color.white);
		g2D.fillRect(x, y, maxVolumeWidth, 10);
		
		g2D.setColor(Color.gray);
		g2D.fillRect(x, y, effectVolumeWidth, 10);
		
		g2D.setColor(Color.black);
		g2D.fillRect(x + effectVolumeWidth - 7 - 1, y - 2 - 1, 16, 16);
		g2D.setColor(Color.yellow);
		g2D.fillRect(x + effectVolumeWidth - 7, y - 2, 14, 14);

		// display effect volume
		g2D.setFont(g2D.getFont().deriveFont(Font.PLAIN, 30));
		text = "";
		if(effectVolume < 10) {
			text += "0";
		}
		text += effectVolume + "";
		g2D.drawString(text, x + effectVolumeWidth - 7, y - gamePanel.tileSize / 2);

		if(gamePanel.mouseHandler.mousePressed == true) {
			int mouseX = gamePanel.mouseHandler.clickListener.getX();
			int mouseY = gamePanel.mouseHandler.clickListener.getY();
			if(x - 15 <= mouseX && mouseX < maxVolumeWidth + x + 15) {
				if((y - gamePanel.tileSize * 2) - 15 <= mouseY && mouseY < (y - gamePanel.tileSize * 2) + 15) {
					if(gamePanel.mouseHandler.mouseDragged == false) {
						gamePanel.mouseHandler.dragListener.setX(mouseX);
					}
					if(gamePanel.mouseHandler.dragListener.getX() - x < 0) {
						musicVolumeWidth = 0;
					} else if (gamePanel.mouseHandler.dragListener.getX() - x > maxVolumeWidth) {
						musicVolumeWidth = maxVolumeWidth;
					} else {
						musicVolumeWidth = gamePanel.mouseHandler.dragListener.getX() - x;
					}
				}
				else if (y - 15 < mouseY && mouseY < y + 15) {
					if(gamePanel.mouseHandler.mouseDragged == false) {
						gamePanel.mouseHandler.dragListener.setX(mouseX);
					}
					if(gamePanel.mouseHandler.dragListener.getX() - x < 0) {
						effectVolumeWidth = 0;
					} else if (gamePanel.mouseHandler.dragListener.getX() - x > maxVolumeWidth) {
						effectVolumeWidth = maxVolumeWidth;
					} else {
						effectVolumeWidth = gamePanel.mouseHandler.dragListener.getX() - x;
					}
				}
				musicVolume = musicVolumeWidth / oneScale;
				effectVolume = effectVolumeWidth / oneScale;
				gamePanel.music.setVolume(musicVolume);
				gamePanel.soundEffect.setVolume(effectVolume);
			}
		}
	}

	public int getXForCenteredText(Graphics2D g2D, String text) {
		int length = (int)g2D.getFontMetrics().getStringBounds(text, g2D).getWidth();
		return gamePanel.screenWidth / 2 - length / 2;
	}
}
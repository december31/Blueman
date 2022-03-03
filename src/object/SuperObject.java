package object;

import java.awt.image.BufferedImage;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import main.GamePanel;
import main.UtilityTool;

public class SuperObject {

	public BufferedImage image;
	public String name;
	public int worldX;
	public int worldY;
	public boolean collision;
	public Rectangle solidArea = new Rectangle(0,0,48,48);
	public int solidAreaDefaultX = 0;
	public int solidAreaDefaultY = 0;
	public UtilityTool uTool = new UtilityTool();

	public void draw(Graphics2D g2D, GamePanel gamePanel) {
		int screenX = worldX - gamePanel.player.worldX + gamePanel.player.screenX;
		int screenY = worldY - gamePanel.player.worldY + gamePanel.player.screenY;

		if(
			worldX + gamePanel.tileSize > gamePanel.player.worldX - gamePanel.player.screenX &&
			worldX - gamePanel.tileSize < gamePanel.player.worldX + gamePanel.player.screenX && 
			worldY + gamePanel.tileSize > gamePanel.player.worldY - gamePanel.player.screenY &&
			worldY - gamePanel.tileSize < gamePanel.player.worldY + gamePanel.player.screenY
		) {
			g2D.drawImage(image, screenX, screenY, null);
		}
	}

}

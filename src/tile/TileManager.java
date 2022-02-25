package tile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

import javax.imageio.ImageIO;

import main.GamePanel;
import main.UtilityTool;

import java.awt.*;

public class TileManager {
	
	public GamePanel gamePanel;
	public Tile[] tiles;
	public int[][] mapTilesNum;
	
	public TileManager(GamePanel gamePanel) {

		this.gamePanel = gamePanel;
		tiles = new Tile[6];
		mapTilesNum = new int[gamePanel.maxWorldRow][gamePanel.maxWorldCol];

		loadMap("../res/Map/world01.txt");
		getTileImage();
	}

	public void loadMap(String mapDir) {
		try {
			Scanner scanner = new Scanner(new File(mapDir));
			for(int i = 0; i < gamePanel.maxWorldRow; i++) {
				for(int j = 0; j < gamePanel.maxWorldCol; j++) {
					mapTilesNum[i][j] = scanner.nextInt();
				}
			}
		} catch (FileNotFoundException e) {	}
	}

	public void getTileImage() {

		setup(0, "grass", false);
		setup(1, "wall", true);
		setup(2, "water", true);
		setup(3, "earth", false);
		setup(4, "tree", true);
		setup(5, "sand", false);

	}

	public void setup(int index, String imageName, boolean collision) {
		UtilityTool uTool = new UtilityTool();

		try {
			tiles[index] = new Tile();
			tiles[index].image = ImageIO.read(new File("../res/Tiles/Old version/"+ imageName +".png"));
			tiles[index].image = uTool.scaleImage(tiles[index].image, gamePanel.tileSize, gamePanel.tileSize);
			tiles[index].collision = collision;
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void draw(Graphics2D g2D) {

		for(int worldRow = 0; worldRow < gamePanel.maxWorldRow; worldRow++) {
			for(int worldCol = 0; worldCol < gamePanel.maxWorldCol; worldCol++) {
				
				int worldX = worldCol * gamePanel.tileSize;
				int worldY = worldRow * gamePanel.tileSize;
				int screenX = worldX - gamePanel.player.worldX + gamePanel.player.screenX;
				int screenY = worldY - gamePanel.player.worldY + gamePanel.player.screenY;
				
				if(
					worldX + gamePanel.tileSize > gamePanel.player.worldX - gamePanel.player.screenX &&
					worldX - gamePanel.tileSize < gamePanel.player.worldX + gamePanel.player.screenX && 
					worldY + gamePanel.tileSize > gamePanel.player.worldY - gamePanel.player.screenY &&
					worldY - gamePanel.tileSize < gamePanel.player.worldY + gamePanel.player.screenY
				) {
					g2D.drawImage(tiles[mapTilesNum[worldRow][worldCol]].image, screenX, screenY, null);
				}
			}
		}
	}
}

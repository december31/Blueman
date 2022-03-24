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
		tiles = new Tile[60];
		mapTilesNum = new int[gamePanel.maxWorldRow][gamePanel.maxWorldCol];

		loadMap("../res/map/worldV4.txt");
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
		for(int i = 0; i < 10; i++) {
			setup(i, "grass00", false);
		}

		setup(10, "water00", true);
		setup(11, "water01", true);
		setup(12, "water02", true);
		setup(13, "water01R", true);
		setup(14, "water01L", true);
		setup(15, "water01U", true);
		setup(16, "water01C2R", true);
		setup(17, "water01C2L", true);
		setup(18, "water01C1R", true);
		setup(19, "water01C1L", true);

		setup(20, "wall00", true);
		setup(21, "wall01", true);
		setup(22, "wall02", true);
		setup(23, "wall03", true);
		setup(24, "wall04", true);
		setup(25, "wall05", true);
		setup(26, "wall06", true);
		setup(27, "wall07", true);
		setup(28, "wall08", true);
		setup(29, "wall09", true);
		
		setup(30, "grass00", false);
		setup(31, "grass01", false);
		setup(32, "grass02", false);
		setup(33, "grass03", false);
		setup(34, "grass04", false);
		setup(35, "grass05", false);
		setup(36, "grass06", false);

		setup(37, "grass00L", false);
		setup(38, "grass01L", false);
		setup(39, "grass02L", false);
		setup(40, "grass04L", false);

		setup(41, "grass00R", false);
		setup(42, "grass01R", false);
		setup(43, "grass02R", false);
		setup(44, "grass04R", false);
		
		setup(45, "grass00U", false);
		setup(46, "grass01U", false);
		setup(47, "grass02U", false);
		setup(48, "grass04U", false);

		setup(49, "grass00UL", false);
		setup(50, "grass01UL", false);
		setup(51, "grass02UL", false);
		setup(52, "grass04UL", false);
		
		setup(53, "grass00UR", false);
		setup(54, "grass01UR", false);
		setup(55, "grass02UR", false);
		setup(56, "grass04UR", false);

		// setup(0, "grass01", false);
		// setup(1, "wall", true);
		// setup(2, "water01", true);
		// setup(3, "earth", false);
		// setup(4, "tree", true, true);
		// setup(5, "road00", false);

	}

	public void setup(int index, String imageName, boolean collision) {
		UtilityTool uTool = new UtilityTool();

		try {
			tiles[index] = new Tile();
			tiles[index].image = ImageIO.read(new File("../res/tiles/My version/"+ imageName +".png"));
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

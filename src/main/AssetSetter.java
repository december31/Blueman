package main;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import monster.BOSS_Alpha;
import monster.BOSS_Delta;
import monster.BOSS_Omicron;
import monster.MON_Alpha;
import monster.MON_Delta;
import monster.MON_Omicron;

import object.OBJ_Bomb;
import object.OBJ_Bush;
import object.OBJ_Chest;
import object.OBJ_Crate;
import object.OBJ_Door;
import object.OBJ_Key;
import object.OBJ_Rock;

public class AssetSetter {
	GamePanel gamePanel;
	Scanner scanner;
	int objectIndex;

	public AssetSetter(GamePanel gamePanel) {
		this.gamePanel = gamePanel;
	}

	public void setObject() {
		objectIndex = 0;
		for(int i = 0; i < gamePanel.bombs.length; i++) {
			gamePanel.bombs[i] = new OBJ_Bomb(gamePanel);
		}

		String[] filePath = {
			"../res/map/objectMap1.txt",
			"../res/map/objectMap2.txt",
			"../res/map/objectMap3.txt",
			"../res/map/objectMap4.txt"
		};
		int[] originalYCoordinate = {56, 41, 26, 11};
		int objectNum = 0;
		
		for (int i = 0; i < filePath.length; i++) {
			try {
				scanner = new Scanner(new File(filePath[i]));
				// System.out.println(filePath[i]);
				for(int row = 0; row < 13; row++) {
					for(int col = 0; col < 15; col++) {
						objectNum = scanner.nextInt();
						if(objectNum == 0) continue;
						if(objectNum == 1) {
							gamePanel.objects[objectIndex] = new OBJ_Bush(gamePanel);
						} else if (objectNum == 2){
							gamePanel.objects[objectIndex] = new OBJ_Rock(gamePanel);
						} else if (objectNum == 3){
							gamePanel.objects[objectIndex] = new OBJ_Crate(gamePanel);
						}
						gamePanel.objects[objectIndex].worldX = (col + 8) * gamePanel.tileSize;
						gamePanel.objects[objectIndex].worldY = (row + originalYCoordinate[i]) * gamePanel.tileSize;
						objectIndex++;
					}
				}
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} finally {
				scanner.close();
			}
		}
		

		gamePanel.objects[objectIndex] = new OBJ_Crate(gamePanel);
		gamePanel.objects[objectIndex].worldX = 18 * gamePanel.tileSize;
		gamePanel.objects[objectIndex].worldY = 71 * gamePanel.tileSize;
		objectIndex++;
		
		gamePanel.objects[objectIndex] = new OBJ_Crate(gamePanel);
		gamePanel.objects[objectIndex].worldX = 12 * gamePanel.tileSize;
		gamePanel.objects[objectIndex].worldY = 75 * gamePanel.tileSize;
		objectIndex++;

		gamePanel.objects[objectIndex] = new OBJ_Bush(gamePanel);
		gamePanel.objects[objectIndex].worldX = 12 * gamePanel.tileSize;
		gamePanel.objects[objectIndex].worldY = 71 * gamePanel.tileSize;
		objectIndex++;

		gamePanel.objects[objectIndex] = new OBJ_Bush(gamePanel);
		gamePanel.objects[objectIndex].worldX = 12 * gamePanel.tileSize;
		gamePanel.objects[objectIndex].worldY = 74 * gamePanel.tileSize;
		objectIndex++;

		gamePanel.objects[objectIndex] = new OBJ_Bush(gamePanel);
		gamePanel.objects[objectIndex].worldX = 14 * gamePanel.tileSize;
		gamePanel.objects[objectIndex].worldY = 75 * gamePanel.tileSize;
		objectIndex++;

		gamePanel.objects[objectIndex] = new OBJ_Bush(gamePanel);
		gamePanel.objects[objectIndex].worldX = 18 * gamePanel.tileSize;
		gamePanel.objects[objectIndex].worldY = 75 * gamePanel.tileSize;
		objectIndex++;

		gamePanel.objects[objectIndex] = new OBJ_Bush(gamePanel);
		gamePanel.objects[objectIndex].worldX = 18 * gamePanel.tileSize;
		gamePanel.objects[objectIndex].worldY = 72 * gamePanel.tileSize;
		objectIndex++;

		gamePanel.objects[objectIndex] = new OBJ_Rock(gamePanel);
		gamePanel.objects[objectIndex].worldX = 17 * gamePanel.tileSize;
		gamePanel.objects[objectIndex].worldY = 72 * gamePanel.tileSize;
		objectIndex++;

		gamePanel.objects[objectIndex] = new OBJ_Rock(gamePanel);
		gamePanel.objects[objectIndex].worldX = 12 * gamePanel.tileSize;
		gamePanel.objects[objectIndex].worldY = 73 * gamePanel.tileSize;
		objectIndex++;

		gamePanel.objects[objectIndex] = new OBJ_Rock(gamePanel);
		gamePanel.objects[objectIndex].worldX = 13 * gamePanel.tileSize;
		gamePanel.objects[objectIndex].worldY = 75 * gamePanel.tileSize;
		objectIndex++;




		gamePanel.objects[objectIndex] = new OBJ_Door(gamePanel);
		gamePanel.objects[objectIndex].worldX = 15 * gamePanel.tileSize;
		gamePanel.objects[objectIndex].worldY = 70 * gamePanel.tileSize + 4;
		objectIndex++;

		gamePanel.objects[objectIndex] = new OBJ_Door(gamePanel);
		gamePanel.objects[objectIndex].worldX = 15 * gamePanel.tileSize;
		gamePanel.objects[objectIndex].worldY = 55 * gamePanel.tileSize + 4;
		objectIndex++;
		
		gamePanel.objects[objectIndex] = new OBJ_Door(gamePanel);
		gamePanel.objects[objectIndex].worldX = 15 * gamePanel.tileSize;
		gamePanel.objects[objectIndex].worldY = 40 * gamePanel.tileSize + 4;
		objectIndex++;

		gamePanel.objects[objectIndex] = new OBJ_Door(gamePanel);
		gamePanel.objects[objectIndex].worldX = 15 * gamePanel.tileSize;
		gamePanel.objects[objectIndex].worldY = 25 * gamePanel.tileSize + 4;
		objectIndex++;

		gamePanel.objects[objectIndex] = new OBJ_Door(gamePanel);
		gamePanel.objects[objectIndex].worldX = 15 * gamePanel.tileSize;
		gamePanel.objects[objectIndex].worldY = 10 * gamePanel.tileSize + 4;
		objectIndex++;
		
		gamePanel.objects[objectIndex] = new OBJ_Key(gamePanel);
		gamePanel.objects[objectIndex].worldX = 17 * gamePanel.tileSize;
		gamePanel.objects[objectIndex].worldY = 71 * gamePanel.tileSize;
		objectIndex++;

		gamePanel.objects[objectIndex] = new OBJ_Chest(gamePanel);
		gamePanel.objects[objectIndex].worldX = 15 * gamePanel.tileSize;
		gamePanel.objects[objectIndex].worldY = 5 * gamePanel.tileSize;
		objectIndex++;
	}

	public void setMonster() {
		if(gamePanel.level == 1) {
			gamePanel.monsters[0] = new MON_Delta(gamePanel);
			gamePanel.monsters[0].worldX = gamePanel.tileSize * 12;
			gamePanel.monsters[0].worldY = gamePanel.tileSize * 58;
	
			gamePanel.monsters[1] = new MON_Delta(gamePanel);
			gamePanel.monsters[1].worldX = gamePanel.tileSize * 13;
			gamePanel.monsters[1].worldY = gamePanel.tileSize * 58;
	
			gamePanel.monsters[2] = new MON_Delta(gamePanel);
			gamePanel.monsters[2].worldX = gamePanel.tileSize * 15;
			gamePanel.monsters[2].worldY = gamePanel.tileSize * 58;
	
			gamePanel.monsters[3] = new MON_Delta(gamePanel);
			gamePanel.monsters[3].worldX = gamePanel.tileSize * 16;
			gamePanel.monsters[3].worldY = gamePanel.tileSize * 60;
	
			gamePanel.monsters[4] = new BOSS_Delta(gamePanel);
			gamePanel.monsters[4].worldX = gamePanel.tileSize * 14;
			gamePanel.monsters[4].worldY = gamePanel.tileSize * 56;
		}
		else if(gamePanel.level == 2) {
			gamePanel.monsters[0] = new MON_Alpha(gamePanel);
			gamePanel.monsters[0].worldX = gamePanel.tileSize * 12;
			gamePanel.monsters[0].worldY = gamePanel.tileSize * (57 - 15);
	
			gamePanel.monsters[1] = new MON_Alpha(gamePanel);
			gamePanel.monsters[1].worldX = gamePanel.tileSize * 13;
			gamePanel.monsters[1].worldY = gamePanel.tileSize * (58 - 15);
	
			gamePanel.monsters[2] = new MON_Alpha(gamePanel);
			gamePanel.monsters[2].worldX = gamePanel.tileSize * 15;
			gamePanel.monsters[2].worldY = gamePanel.tileSize * (57 - 15);
	
			gamePanel.monsters[3] = new MON_Alpha(gamePanel);
			gamePanel.monsters[3].worldX = gamePanel.tileSize * 16;
			gamePanel.monsters[3].worldY = gamePanel.tileSize * (60 - 15);
	
			gamePanel.monsters[4] = new BOSS_Alpha(gamePanel);
			gamePanel.monsters[4].worldX = gamePanel.tileSize * 10;
			gamePanel.monsters[4].worldY = gamePanel.tileSize * (59 - 15);
		}
		else if(gamePanel.level == 3) {
			gamePanel.monsters[0] = new MON_Omicron(gamePanel);
			gamePanel.monsters[0].worldX = gamePanel.tileSize * 12;
			gamePanel.monsters[0].worldY = gamePanel.tileSize * (57 - 30);
	
			gamePanel.monsters[1] = new MON_Omicron(gamePanel);
			gamePanel.monsters[1].worldX = gamePanel.tileSize * 13;
			gamePanel.monsters[1].worldY = gamePanel.tileSize * (58 - 30);
	
			gamePanel.monsters[2] = new MON_Omicron(gamePanel);
			gamePanel.monsters[2].worldX = gamePanel.tileSize * 15;
			gamePanel.monsters[2].worldY = gamePanel.tileSize * (57 - 30);
	
			gamePanel.monsters[3] = new MON_Omicron(gamePanel);
			gamePanel.monsters[3].worldX = gamePanel.tileSize * 16;
			gamePanel.monsters[3].worldY = gamePanel.tileSize * (60 - 30);
	
			gamePanel.monsters[4] = new BOSS_Omicron(gamePanel);
			gamePanel.monsters[4].worldX = gamePanel.tileSize * 10;
			gamePanel.monsters[4].worldY = gamePanel.tileSize * (58 - 30);
		} else if(gamePanel.level == 4) {
			gamePanel.monsters[0] = new BOSS_Alpha(gamePanel);
			gamePanel.monsters[0].worldX = gamePanel.tileSize * 10;
			gamePanel.monsters[0].worldY = gamePanel.tileSize * (58 - 45);
			
			gamePanel.monsters[1] = new BOSS_Delta(gamePanel);
			gamePanel.monsters[1].worldX = gamePanel.tileSize * 14;
			gamePanel.monsters[1].worldY = gamePanel.tileSize * (58 - 45);

			gamePanel.monsters[2] = new BOSS_Omicron(gamePanel);
			gamePanel.monsters[2].worldX = gamePanel.tileSize * 18;
			gamePanel.monsters[2].worldY = gamePanel.tileSize * (58 - 45);
		}
	}
}

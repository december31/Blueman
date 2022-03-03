package main;

import entity.Entity;
import entity.Player;
import object.SuperObject;

public class CollisionChecker {

	GamePanel gamePanel;

	public CollisionChecker(GamePanel gamePanel) {
		this.gamePanel = gamePanel;
	}

	public void checkTile(Entity entity) {
		int entityLeftWorldX = entity.worldX + entity.solidArea.x;
		int entityRightWorldX = entity.worldX + entity.solidArea.x + entity.solidArea.width;
		int entityTopWorldY = entity.worldY + entity.solidArea.y;
		int entityBottomWorldY = entity.worldY + entity.solidArea.height + entity.solidArea.y;

		int entityLeftCol = entityLeftWorldX / gamePanel.tileSize;
		int entityRightCol = entityRightWorldX / gamePanel.tileSize;
		int entityTopRow = entityTopWorldY / gamePanel.tileSize;
		int entityBottomRow = entityBottomWorldY / gamePanel.tileSize;

		int tileNum1, tileNum2;

		switch (entity.direction) {
			case "up":
				entityTopRow = (entityTopWorldY - entity.speed) / gamePanel.tileSize;
				tileNum1 = gamePanel.tileManager.mapTilesNum[entityTopRow][entityLeftCol];
				tileNum2 = gamePanel.tileManager.mapTilesNum[entityTopRow][entityRightCol];
				if(gamePanel.tileManager.tiles[tileNum1].collision == true
				|| gamePanel.tileManager.tiles[tileNum2].collision == true) {
					entity.collisionOn = true;
				}
				break;
			case "down":
				entityBottomRow = (entityBottomWorldY + entity.speed) / gamePanel.tileSize;
				tileNum1 = gamePanel.tileManager.mapTilesNum[entityBottomRow][entityLeftCol];
				tileNum2 = gamePanel.tileManager.mapTilesNum[entityBottomRow][entityRightCol];
				if(gamePanel.tileManager.tiles[tileNum1].collision == true
				|| gamePanel.tileManager.tiles[tileNum2].collision == true) {
					entity.collisionOn = true;
				}
				break;
			case "left":
				entityLeftCol = (entityLeftWorldX - entity.speed) / gamePanel.tileSize;
				tileNum1 = gamePanel.tileManager.mapTilesNum[entityTopRow][entityLeftCol];
				tileNum2 = gamePanel.tileManager.mapTilesNum[entityBottomRow][entityLeftCol];
				if(gamePanel.tileManager.tiles[tileNum1].collision == true
				|| gamePanel.tileManager.tiles[tileNum2].collision == true) {
					entity.collisionOn = true;
				}
				break;
			case "right":
				entityRightCol = (entityRightWorldX + entity.speed) / gamePanel.tileSize;
				tileNum1 = gamePanel.tileManager.mapTilesNum[entityTopRow][entityRightCol];
				tileNum2 = gamePanel.tileManager.mapTilesNum[entityBottomRow][entityRightCol];
				if(gamePanel.tileManager.tiles[tileNum1].collision == true
				|| gamePanel.tileManager.tiles[tileNum2].collision == true) {
					entity.collisionOn = true;
				}
				break;
			default:
				break;
		}
	}

	public int checkEntity(SuperObject[] objects, Entity entity) {
		int index = -1;

		for(int i = 0; i < objects.length; i++) {
			if(objects[i] != null) {
				// Get entity's solid area position
				entity.solidArea.x = entity.worldX + entity.solidArea.x;
				entity.solidArea.y = entity.worldY + entity.solidArea.y;

				// Get object's solid area position
				objects[i].solidArea.x = objects[i].worldX + objects[i].solidArea.x;
				objects[i].solidArea.y = objects[i].worldY + objects[i].solidArea.y;

				switch (entity.direction) {
					case "up":
						entity.solidArea.x -= entity.speed;
						if(objects[i].solidArea.intersects(entity.solidArea)) {
							if(objects[i].collision == true) entity.collisionOn = true;
							if(entity instanceof Player) index = i;
						}
						break;
					case "down":
						entity.solidArea.y += entity.speed;
						if(objects[i].solidArea.intersects(entity.solidArea)) {
							if(objects[i].collision == true) entity.collisionOn = true;
							if(entity instanceof Player) index = i;
						}
						break;
					case "left":
						entity.solidArea.x -= entity.speed;
						if(objects[i].solidArea.intersects(entity.solidArea)) {
							if(objects[i].collision == true) entity.collisionOn = true;
							if(entity instanceof Player) index = i;
						}
						break;
					case "right":	
						entity.solidArea.x += entity.speed;
						if(objects[i].solidArea.intersects(entity.solidArea)) {
							if(objects[i].collision == true) entity.collisionOn = true;
							if(entity instanceof Player) index = i;
						}
						break;
					default:
						break;
				}
				entity.solidArea.x = entity.solidAreaDefaultX;
				entity.solidArea.y = entity.solidAreaDefaultY;

				objects[i].solidArea.x = objects[i].solidAreaDefaultX;
				objects[i].solidArea.y = objects[i].solidAreaDefaultY;
			}
		}
		return index;
	}
}

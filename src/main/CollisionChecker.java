package main;

import entity.Entity;
import entity.Player;
import object.OBJ_Door;

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

		int tileNum1 = 0, tileNum2 = 0;

		switch (entity.direction) {
			case "up":
				entityTopRow = (entityTopWorldY - entity.speed) / gamePanel.tileSize;
				tileNum1 = gamePanel.tileManager.mapTilesNum[entityTopRow][entityLeftCol];
				tileNum2 = gamePanel.tileManager.mapTilesNum[entityTopRow][entityRightCol];
				break;
			case "down":
				entityBottomRow = (entityBottomWorldY + entity.speed) / gamePanel.tileSize;
				tileNum1 = gamePanel.tileManager.mapTilesNum[entityBottomRow][entityLeftCol];
				tileNum2 = gamePanel.tileManager.mapTilesNum[entityBottomRow][entityRightCol];
				break;
			case "left":
				entityLeftCol = (entityLeftWorldX - entity.speed) / gamePanel.tileSize;
				tileNum1 = gamePanel.tileManager.mapTilesNum[entityTopRow][entityLeftCol];
				tileNum2 = gamePanel.tileManager.mapTilesNum[entityBottomRow][entityLeftCol];
				break;
			case "right":
				entityRightCol = (entityRightWorldX + entity.speed) / gamePanel.tileSize;
				tileNum1 = gamePanel.tileManager.mapTilesNum[entityTopRow][entityRightCol];
				tileNum2 = gamePanel.tileManager.mapTilesNum[entityBottomRow][entityRightCol];
				break;
			default:
				break;
		}
		if(gamePanel.tileManager.tiles[tileNum1].collision == true
		|| gamePanel.tileManager.tiles[tileNum2].collision == true) {
			entity.collisionOn = true;
		}
	}

	public int checkEntity(Entity[] targets, Entity entity) {
		int index = -1;

		for(int i = 0; i < targets.length; i++) {
			if(targets[i] != null) {

				// if player went through door then door close itself
				if(targets[i] instanceof OBJ_Door) {
					((OBJ_Door)targets[i]).autoClose();
				}
				
				// Get entity's solid area position
				entity.solidArea.x = entity.worldX + entity.solidArea.x;
				entity.solidArea.y = entity.worldY + entity.solidArea.y;

				// Get object's solid area position
				targets[i].solidArea.x = targets[i].worldX + targets[i].solidArea.x;
				targets[i].solidArea.y = targets[i].worldY + targets[i].solidArea.y;

				switch (entity.direction) {
					case "up":entity.solidArea.y -= entity.speed;break;
					case "down":entity.solidArea.y += entity.speed;break;
					case "left":entity.solidArea.x -= entity.speed;break;
					case "right":entity.solidArea.x += entity.speed;break;
					default:break;
				}
				if(targets[i].solidArea.intersects(entity.solidArea)) {
					if(targets[i].type == 2 && entity instanceof Player) {
						targets[i].actionLockCounter = 120;
					}

					if(targets[i].collision == true ) {
						if(targets[i] != entity) {
							entity.collisionOn = true;
						}
					}
					index = i;
				}
				entity.solidArea.x = entity.solidAreaDefaultX;
				entity.solidArea.y = entity.solidAreaDefaultY;

				targets[i].solidArea.x = targets[i].solidAreaDefaultX;
				targets[i].solidArea.y = targets[i].solidAreaDefaultY;
			}
		}
		return index;
	}

	public int checkPlayer(Entity entity) {
		int index = -1;
		// Get entity's solid area position
		entity.solidArea.x = entity.worldX + entity.solidArea.x;
		entity.solidArea.y = entity.worldY + entity.solidArea.y;

		// Get object's solid area position
		gamePanel.player.solidArea.x = gamePanel.player.worldX + gamePanel.player.solidArea.x;
		gamePanel.player.solidArea.y = gamePanel.player.worldY + gamePanel.player.solidArea.y;

		switch (entity.direction) {
			case "up":entity.solidArea.y -= entity.speed;break;
			case "down":entity.solidArea.y += entity.speed;break;
			case "left":entity.solidArea.x -= entity.speed;break;
			case "right":entity.solidArea.x += entity.speed;break;
			default:break;
		}
		if(gamePanel.player.solidArea.intersects(entity.solidArea)) {
			entity.collisionOn = true;
			index = 1;
		}
		entity.solidArea.x = entity.solidAreaDefaultX;
		entity.solidArea.y = entity.solidAreaDefaultY;

		gamePanel.player.solidArea.x = gamePanel.player.solidAreaDefaultX;
		gamePanel.player.solidArea.y = gamePanel.player.solidAreaDefaultY;
		return index;
	}

}

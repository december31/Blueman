package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Door extends Entity{

	public OBJ_Door(GamePanel gamePanel) {
		super(gamePanel);
		name = "Door";
		down1 = setup("../res/Object/door00.png");
		up1 = setup("../res/Object/door01.png");
		collision = true;
		breakable = false;
	}

	public boolean autoClose() {
		if(collision == false && worldY > gamePanel.player.worldY + gamePanel.tileSize * 2) {
			worldY += 4;
			gamePanel.playSoundEffect("Door");
			collision = true;
			direction = "down";
			return true;
		}
		return false;
	}

}

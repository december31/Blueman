package monster;

import java.util.Random;

import entity.Entity;
import main.GamePanel;

public class BOSS_Delta extends Entity{

	public BOSS_Delta(GamePanel gamePanel) {
		super(gamePanel);
		speed = 1;
		maxLife = 4;
		life = maxLife;
		name = "Super Delta";
		solidArea.x = 18;
		solidArea.y = 18;
		solidArea.width = 60;
		solidArea.height = 60;
		solidAreaDefaultX = solidArea.x;
		solidAreaDefaultY = solidArea.y;
		collision = true;
		type = 3;
		invincibleTime = 2;
		loadImage();
	}

	public void loadImage() {
		up1 = setup("../res/Monster/delta0.png", gamePanel.tileSize * 2, gamePanel.tileSize * 2);
		up2 = setup("../res/Monster/delta1.png", gamePanel.tileSize * 2, gamePanel.tileSize * 2);
		down1 = setup("../res/Monster/delta0.png", gamePanel.tileSize * 2, gamePanel.tileSize * 2);
		down2 = setup("../res/Monster/delta1.png", gamePanel.tileSize * 2, gamePanel.tileSize * 2);
		left1 = setup("../res/Monster/delta0.png", gamePanel.tileSize * 2, gamePanel.tileSize * 2);
		left2 = setup("../res/Monster/delta1.png", gamePanel.tileSize * 2, gamePanel.tileSize * 2);
		right1 = setup("../res/Monster/delta0.png", gamePanel.tileSize * 2, gamePanel.tileSize * 2);
		right2 = setup("../res/Monster/delta1.png", gamePanel.tileSize * 2, gamePanel.tileSize * 2);
	}

	@Override
	public void takeDamage(int index) {
		if(index != -1) {
			if(!invincible) {
				life -= 1;
				if(life <= 0) dying = true;
				invincible = true;
			}
		}
	}

	@Override
	public void setAction() {
		actionLockCounter++;
		if(actionLockCounter >= 120) {
			int rand = new Random().nextInt(100) + 1;
			if(rand <= 25) {
				direction = "up";
			}
			else if(rand <= 50) {
				direction = "down";
			}
			else if(rand <= 75) {
				direction = "left";
			}
			else if(rand <= 100) {
				direction = "right";
			}
			actionLockCounter = 0;
		}
	}
}

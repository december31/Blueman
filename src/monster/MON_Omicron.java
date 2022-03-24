package monster;

import java.util.Random;

import entity.Entity;
import main.GamePanel;

public class MON_Omicron extends Entity{
	public MON_Omicron(GamePanel gamePanel) {
		super(gamePanel);
		name = "Omicron";
		speed = 2;
		maxLife = 2;
		life = maxLife;

		solidArea.x = 9;
		solidArea.y = 9;
		solidArea.width = 30;
		solidArea.height = 30;
		solidAreaDefaultX = solidArea.x;
		solidAreaDefaultY = solidArea.y;
		collision = true;
		type = 2;
		invincibleTime = 2;
		loadImage();
	}
	
	public void loadImage() {
		up1 = setup("../res/monster/omicron0.png");
		up2 = setup("../res/monster/omicron1.png");
		down1 = setup("../res/monster/omicron0.png");
		down2 = setup("../res/monster/omicron1.png");
		left1 = setup("../res/monster/omicron0.png");
		left2 = setup("../res/monster/omicron1.png");
		right1 = setup("../res/monster/omicron0.png");
		right2 = setup("../res/monster/omicron1.png");
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

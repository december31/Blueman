package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_LevelUpBomb extends Entity{

	public OBJ_LevelUpBomb(GamePanel gamePanel) {
		super(gamePanel);
		name = "LevelUpBomb";
		down1 = setup("../res/Object/levelUpBomb1.png");
		down2 = setup("../res/Object/levelUpBomb2.png");
	}
}

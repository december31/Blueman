package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_LevelUpBomb extends Entity{

	public OBJ_LevelUpBomb(GamePanel gamePanel) {
		super(gamePanel);
		name = "LevelUpBomb";
		image = setup("../res/object/levelUpBomb1.png");
	}
}

package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_HealingPotion extends Entity{

	public OBJ_HealingPotion(GamePanel gamePanel) {
		super(gamePanel);
		name = "HealingPotion";
		down1 = setup("../res/Object/potion_red.png");
	}
}

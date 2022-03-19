package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_ExtraBomb extends Entity{

	public OBJ_ExtraBomb(GamePanel gamePanel) {
		super(gamePanel);
		down1 = setup("../res/Object/extraBomb.png");
		name = "ExtraBomb";
	}
	
}

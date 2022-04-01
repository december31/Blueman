package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_ExtraBomb extends Entity{

	public OBJ_ExtraBomb(GamePanel gamePanel) {
		super(gamePanel);
		down1 = setup("../res/object/extraBomb.png");
		name = "ExtraBomb";
	}
}

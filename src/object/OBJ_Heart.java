package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Heart extends Entity{

	public OBJ_Heart(GamePanel gamePanel) {
		super(gamePanel);
		name = "Heart";
		image = setup("../res/object/heart_blank.png");
		image1 = setup("../res/object/heart_half.png");
		image2 = setup("../res/object/heart_full.png");
	}
}

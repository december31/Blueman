package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Rock extends Entity{

	public OBJ_Rock(GamePanel gamePanel) {
		super(gamePanel);
		name = "Rock";
		down1 = setup("../res/object/rock.png");
		collision = true;
		breakable = false;
	}
}

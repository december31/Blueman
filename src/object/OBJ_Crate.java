package object;

import java.awt.Graphics2D;

import entity.Entity;
import main.GamePanel;

public class OBJ_Crate extends Entity{

	public OBJ_Crate(GamePanel gamePanel) {
		super(gamePanel);
		name = "Crate";
		down1 = setup("../res/Object/crate00.png");
		image1 = setup("../res/Object/crate01.png");
		image2 = setup("../res/Object/crate02.png");
		collision = true;
		breakable = true;
	}

	@Override
	public void takeDamage(int index) {
		if(index != -1) {
			dying = true;
		}
	}

	@Override
	public void dyingAnimation(Graphics2D g2D) {
		dyingCounter++;
		if(dyingCounter >= 20) {
			down1 = image2;
		} else {
			down1 = image1;
		}
		if(dyingCounter == 40) {
			dying = false;
			alive = false;
		}
	}

}

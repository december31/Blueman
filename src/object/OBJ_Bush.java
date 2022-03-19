package object;

import java.awt.Graphics2D;

import entity.Entity;
import main.GamePanel;

public class OBJ_Bush extends Entity{

	public OBJ_Bush(GamePanel gamePanel) {
		super(gamePanel);
		name = "Bush";
		down1 = setup("../res/Object/bush00.png");
		image1 = setup("../res/Object/bush01.png");
		image2 = setup("../res/Object/bush02.png");
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
	public void dyingAnimation(Graphics2D g2d) {
		dyingCounter++;
		if(dyingCounter >= 20) {
			down1 = image2;
		} else {
			down1 = image1;
		}
		if(dyingCounter >= 40) {
			alive = false;
			dying = false;
		}
	}

}

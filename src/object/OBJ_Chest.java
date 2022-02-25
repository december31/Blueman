package object;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;

public class OBJ_Chest extends SuperObject{

	public OBJ_Chest(GamePanel gamePanel) {
		name = "Chest";
		try {
			image = ImageIO.read(new File("../res/Object/chest.png"));
			image = uTool.scaleImage(image, gamePanel.tileSize, gamePanel.tileSize);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}

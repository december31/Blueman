package object;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;

public class OBJ_Door extends SuperObject{

	public OBJ_Door(GamePanel gamePanel) {
		name = "Door";
		try {
			image = ImageIO.read(new File("../res/Object/door.png"));
			image = uTool.scaleImage(image, gamePanel.tileSize, gamePanel.tileSize);
		} catch (IOException e) {
			e.printStackTrace();
		}
		collision = true;
	}

}

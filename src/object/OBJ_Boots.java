package object;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;


public class OBJ_Boots extends SuperObject{

	public OBJ_Boots(GamePanel gamePanel) {
		name = "Boots";
		try {
			image = ImageIO.read(new File("../res/Object/boots.png"));
			image = uTool.scaleImage(image, gamePanel.tileSize, gamePanel.tileSize);
		} catch (IOException e) {
			e.printStackTrace();
		}
		collision = false;
	}
	
}

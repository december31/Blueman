package main;

import java.awt.image.BufferedImage;
import java.awt.Graphics2D;

public class UtilityTool {

	// scaled input image and return scaled one
	// scale image before draw to game panel for better performance
	public BufferedImage scaleImage(BufferedImage original, int width, int height) {

		BufferedImage scaledImage = new BufferedImage(width, height, original.getType());
		Graphics2D g2D = scaledImage.createGraphics();
		g2D.drawImage(original, 0, 0, width, height, null);
		g2D.dispose();
		return scaledImage;
	}
}

package main;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

public class Game extends JFrame{
	public Game() {

		ImageIcon icon = new ImageIcon("../res/monster/delta0.png");
		this.setIconImage(icon.getImage());
		this.setTitle("Bomberman 2: Covid Destroyer");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		GamePanel gamePanel = new GamePanel();
		this.add(gamePanel);
		
		this.pack();
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setVisible(true);

		gamePanel.startGameThread();
	}
}

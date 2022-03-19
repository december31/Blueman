package main;

import javax.swing.JFrame;

public class Game extends JFrame{
	public Game() {
		this.setTitle("Covid Destroyer");
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

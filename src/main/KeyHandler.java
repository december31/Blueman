package main;
import java.awt.event.*;

public class KeyHandler implements KeyListener{

	public boolean upPressed, downPressed, leftPressed, rightPressed, placeBombPressed;
	GamePanel gamePanel;

	public KeyHandler(GamePanel gamePanel) {
		this.gamePanel = gamePanel;
		upPressed = downPressed = leftPressed = rightPressed = false;
	}

	@Override
	public void keyTyped(KeyEvent e) {}

	@Override
	public void keyPressed(KeyEvent e) {
		int code = e.getKeyCode();

		// game how to play state
		// if player press esc key then go back to title screen
		if(gamePanel.gameState == gamePanel.tutorialState) {
			if(code == KeyEvent.VK_ESCAPE) {
				gamePanel.gameState = gamePanel.titleState;
			}
		}

		// game pause state
		if(code == KeyEvent.VK_P) {
			if(gamePanel.gameState == gamePanel.playState) {
				gamePanel.gameState = gamePanel.pauseState;
			}
		}
		if(gamePanel.gameState == gamePanel.pauseState) {
			if(code == KeyEvent.VK_W) {
				gamePanel.ui.commandNum--;
				if(gamePanel.ui.commandNum < 0) {
					gamePanel.ui.commandNum = 2;
				}
			}
			if(code == KeyEvent.VK_S) {
				gamePanel.ui.commandNum++;
				if(gamePanel.ui.commandNum > 1) {
					gamePanel.ui.commandNum = 0;
				}
			}
		}

		// title state
		if(gamePanel.gameState == gamePanel.titleState) {
			if(code == KeyEvent.VK_W) {
				gamePanel.ui.commandNum--;
				if(gamePanel.ui.commandNum < 0) {
					gamePanel.ui.commandNum = 2;
				}
			}
			if(code == KeyEvent.VK_S) {
				gamePanel.ui.commandNum++;
				if(gamePanel.ui.commandNum > 2) {
					gamePanel.ui.commandNum = 0;
				}
			}
		}
		
		// for title state and pause state
		if(code == KeyEvent.VK_ENTER) {
			// title
			if(gamePanel.gameState == gamePanel.titleState) {
				if(gamePanel.ui.commandNum == 0) {
					gamePanel.gameState = gamePanel.playState;
				}
				else if (gamePanel.ui.commandNum == 1) {
					gamePanel.gameState = gamePanel.tutorialState;
				}
				else if(gamePanel.ui.commandNum == 2) {
					System.exit(0);
				}
			}

			// pause
			if(gamePanel.gameState == gamePanel.pauseState) {
				if(gamePanel.ui.commandNum == 0) {
					gamePanel.gameState = gamePanel.playState;
				}
				else if (gamePanel.ui.commandNum == 1) {
					gamePanel.gameState = gamePanel.titleState;
					gamePanel.ui.commandNum = 0;
				}
			}
		}

		if(code == KeyEvent.VK_W) {
			upPressed = true;
		}
		if(code == KeyEvent.VK_S) {
			downPressed = true;
		}
		if(code == KeyEvent.VK_A) {
			leftPressed = true;
		}
		if(code == KeyEvent.VK_D) {
			rightPressed = true;
		}

		// place bomb
		if(code == KeyEvent.VK_B) {
			placeBombPressed = true;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		int code = e.getKeyCode();
		if(code == KeyEvent.VK_W) {
			upPressed = false;
		}
		if(code == KeyEvent.VK_S) {
			downPressed = false;
		}
		if(code == KeyEvent.VK_A) {
			leftPressed = false;
		}
		if(code == KeyEvent.VK_D) {
			rightPressed = false;
		}
		if(code == KeyEvent.VK_B) {
			placeBombPressed = false;
		}
	}
	
}

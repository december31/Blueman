package main;
import java.awt.event.*;

public class KeyHandler implements KeyListener{

	public boolean upPressed, downPressed, leftPressed, rightPressed, placeBombPressed,
	spacePressed, enterPressed;
	
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

		if(gamePanel.gameState == gamePanel.tutorialState) {
			gamePanel.playSoundEffect("Click");
		}
		
		// game how to play state
		// if player press esc key then go back to previous screen
		if(code == KeyEvent.VK_ESCAPE) {
			// tutorial state
			if(gamePanel.gameState == gamePanel.tutorialState) {
				gamePanel.playSoundEffect("Click");
					gamePanel.gameState = gamePanel.previousState[gamePanel.tutorialState];
			}

			// setting state
			if(gamePanel.gameState == gamePanel.settingState) {
					gamePanel.playSoundEffect("Click");
					gamePanel.gameState = gamePanel.previousState[gamePanel.settingState];
			}

			// choose character state
			if(gamePanel.gameState == gamePanel.chooseCharacterState) {
					gamePanel.playSoundEffect("Click");
					gamePanel.gameState = gamePanel.titleState;
			}
		}



		// game pause state
		if(code == KeyEvent.VK_SPACE) {
			spacePressed = true;
			if(gamePanel.gameState == gamePanel.playState) {
				gamePanel.playSoundEffect("Click");
				gamePanel.gameState = gamePanel.pauseState;
				gamePanel.ui.commandNum = 0;
			}
			else if(gamePanel.gameState == gamePanel.pauseState) {
				gamePanel.playSoundEffect("Click");
				gamePanel.gameState = gamePanel.playState;
			}
		}
		if(gamePanel.gameState == gamePanel.pauseState) {
			if(code == KeyEvent.VK_W || code == KeyEvent.VK_UP) {
				gamePanel.playSoundEffect("Click");
				gamePanel.ui.commandNum--;
				if(gamePanel.ui.commandNum < 0) {
					gamePanel.ui.commandNum = 3;
				}
			}
			if(code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN) {
				gamePanel.playSoundEffect("Click");
				gamePanel.ui.commandNum++;
				if(gamePanel.ui.commandNum > 3) {
					gamePanel.ui.commandNum = 0;
				}
			}
		}

		// game finish state
		if(gamePanel.gameState == gamePanel.finishState) {
			if(gamePanel.checkKeyHandlerFinishState == true) {
				if(code == KeyEvent.VK_W || code == KeyEvent.VK_UP) {
					gamePanel.playSoundEffect("Click");
					gamePanel.ui.commandNum--;
					if(gamePanel.ui.commandNum < 0) {
						gamePanel.ui.commandNum = 1;
					}
				}
				if(code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN) {
					gamePanel.playSoundEffect("Click");
					gamePanel.ui.commandNum++;
					if(gamePanel.ui.commandNum > 1) {
						gamePanel.ui.commandNum = 0;
					}
				}
			}
		}

		// title state
		if(gamePanel.gameState == gamePanel.titleState) {
			int limit = gamePanel.continuable?-1:0;
			if(code == KeyEvent.VK_W || code == KeyEvent.VK_UP) {
				gamePanel.playSoundEffect("Click");
				gamePanel.ui.commandNum--;
				if(gamePanel.ui.commandNum < limit) {
					gamePanel.ui.commandNum = 3;
				}
			}
			if(code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN) {
				gamePanel.playSoundEffect("Click");
				gamePanel.ui.commandNum++;
				if(gamePanel.ui.commandNum > 3) {
					gamePanel.ui.commandNum = limit;
				}
			}
		}

		// choose character state
		if(gamePanel.gameState == gamePanel.chooseCharacterState) {
			if(code == KeyEvent.VK_W || code == KeyEvent.VK_UP || code == KeyEvent.VK_LEFT || code == KeyEvent.VK_A) {
				gamePanel.playSoundEffect("Click");
				if(gamePanel.ui.commandNum == 0) {
					gamePanel.ui.commandNum = 1;
				} else {
					gamePanel.ui.commandNum = 0;
				}
			}
			if(code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN || code == KeyEvent.VK_RIGHT || code == KeyEvent.VK_D) {
				gamePanel.playSoundEffect("Click");
				if(gamePanel.ui.commandNum == 0) {
					gamePanel.ui.commandNum = 1;
				} else {
					gamePanel.ui.commandNum = 0;
				}
			}			
		}
		
		// exit confirm state
		if(gamePanel.gameState == gamePanel.exitConfirmState) {
			if(code == KeyEvent.VK_W || code == KeyEvent.VK_UP || code == KeyEvent.VK_LEFT || code == KeyEvent.VK_A) {
				gamePanel.playSoundEffect("Click");
				if(gamePanel.ui.exitConfirmCommandNum == 0) {
					gamePanel.ui.exitConfirmCommandNum = 1;
				} else {
					gamePanel.ui.exitConfirmCommandNum = 0;
				}
			}
			if(code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN || code == KeyEvent.VK_RIGHT || code == KeyEvent.VK_D) {
				gamePanel.playSoundEffect("Click");
				if(gamePanel.ui.exitConfirmCommandNum == 0) {
					gamePanel.ui.exitConfirmCommandNum = 1;
				} else {
					gamePanel.ui.exitConfirmCommandNum = 0;
				}
			}
		}
		
		// Enter key to choose command
		if(code == KeyEvent.VK_ENTER) {
			
			// title
			enterPressed = true;
			if(gamePanel.gameState == gamePanel.titleState) {
				gamePanel.playSoundEffect("Click");
				if(gamePanel.ui.commandNum == -1) {
					gamePanel.gameState = gamePanel.playState;
				}
				if(gamePanel.ui.commandNum == 0) {
					gamePanel.gameState = gamePanel.chooseCharacterState;
				}
				else if (gamePanel.ui.commandNum == 1) {
					gamePanel.gameState = gamePanel.tutorialState;
					gamePanel.previousState[gamePanel.tutorialState] = gamePanel.titleState;
				}
				else if(gamePanel.ui.commandNum == 2) {
					gamePanel.gameState = gamePanel.settingState;
					gamePanel.previousState[gamePanel.settingState] = gamePanel.titleState;
				}
				else if(gamePanel.ui.commandNum == 3) {
					gamePanel.gameState = gamePanel.exitConfirmState;
				}
			}

			// choose character
			else if(gamePanel.gameState == gamePanel.chooseCharacterState) {
				gamePanel.playSoundEffect("Click");
				if(gamePanel.ui.commandNum == 0) {
					gamePanel.characterIndex = 0;
				} else {
					gamePanel.characterIndex = 1;
				}
				gamePanel.newGame();
			}

			// exit confirmation
			else if(gamePanel.gameState == gamePanel.exitConfirmState) {
				gamePanel.playSoundEffect("Click");
				if(gamePanel.ui.exitConfirmCommandNum == 0) {
					System.exit(0);
				}
				else if(gamePanel.ui.exitConfirmCommandNum == 1) {
					gamePanel.gameState = gamePanel.titleState;
					gamePanel.ui.exitConfirmCommandNum = 0;
				}
			}
			
			// pause
			else if(gamePanel.gameState == gamePanel.pauseState) {
				gamePanel.playSoundEffect("Click");
				if(gamePanel.ui.commandNum == 0) {
					gamePanel.gameState = gamePanel.playState;
				}
				else if (gamePanel.ui.commandNum == 1) {
					gamePanel.gameState = gamePanel.tutorialState;
					gamePanel.previousState[gamePanel.tutorialState] = gamePanel.pauseState;
				}
				else if (gamePanel.ui.commandNum == 2) {
					gamePanel.gameState = gamePanel.settingState;
					gamePanel.previousState[gamePanel.settingState] = gamePanel.pauseState;
				}
				else if (gamePanel.ui.commandNum == 3) {
					gamePanel.gameState = gamePanel.titleState;
					gamePanel.continuable = true;
					gamePanel.ui.commandNum = 0;
				}
			}
		
			// finish
			else if(gamePanel.gameState == gamePanel.finishState) {
				gamePanel.music.stop();
				gamePanel.music.setFile("Background");
				gamePanel.music.play();
				gamePanel.music.loop();
				if(gamePanel.ui.commandNum == 0) {
					gamePanel.newGame();
				}
				if(gamePanel.ui.commandNum == 1) {
					gamePanel.gameState = gamePanel.titleState;
					gamePanel.continuable = false;
					gamePanel.ui.commandNum = 0;
				}
			}	
		}


		if(code == KeyEvent.VK_W) {upPressed = true;}
		if(code == KeyEvent.VK_S) {downPressed = true;}
		if(code == KeyEvent.VK_A) {leftPressed = true;}
		if(code == KeyEvent.VK_D) {rightPressed = true;}

		if(code == KeyEvent.VK_UP) {upPressed = true;}
		if(code == KeyEvent.VK_DOWN) {downPressed = true;}
		if(code == KeyEvent.VK_LEFT) {leftPressed = true;}
		if(code == KeyEvent.VK_RIGHT) {rightPressed = true;}

		// place bomb
		if(code == KeyEvent.VK_B) {placeBombPressed = true;}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		int code = e.getKeyCode();
		if(code == KeyEvent.VK_W) {upPressed = false;}
		if(code == KeyEvent.VK_S) {downPressed = false;}
		if(code == KeyEvent.VK_A) {leftPressed = false;}
		if(code == KeyEvent.VK_D) {rightPressed = false;}

		if(code == KeyEvent.VK_UP) {upPressed = false;}
		if(code == KeyEvent.VK_DOWN) {downPressed = false;}
		if(code == KeyEvent.VK_LEFT) {leftPressed = false;}
		if(code == KeyEvent.VK_RIGHT) {rightPressed = false;}

		if(code == KeyEvent.VK_B) {placeBombPressed = false;}
		if(code == KeyEvent.VK_SPACE) {spacePressed = false;}
		if(code == KeyEvent.VK_ENTER) {enterPressed = false;}
	}
	
}

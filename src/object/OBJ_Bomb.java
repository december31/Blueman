package object;

import java.io.File;
import java.io.IOException;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

import entity.Entity;
import main.GamePanel;

public class OBJ_Bomb extends Entity{
	public int timer;		// (second) counter hit 1 then explode
	public int frameCounter;
	public boolean isExploded;
	public static int power;
	
	BufferedImage[][] explode = new BufferedImage[5][3];
	
	BufferedImage explode0;
	BufferedImage explodeH;	// explode horizontal
	BufferedImage explodeV;	// explode vertical
	
	public Rectangle damageZoneHorizontal = new Rectangle();
	public Rectangle damageZoneVertical = new Rectangle();
	public int damageZoneHorizontalDefaultX;
	public int damageZoneHorizontalDefaultY;
	public int damageZoneVerticalDefaultX;
	public int damageZoneVerticalDefaultY;
	public int up, down, left, right;	// max tiles can bomb blowup in each direction

	public OBJ_Bomb(GamePanel gamePanel) {
		super(gamePanel);
		try {
			image = ImageIO.read(new File("../res/Object/bomb.png"));
			image = uTool.scaleImage(image, gamePanel.tileSize, gamePanel.tileSize);

			up1 = setup("../res/Object/bomb00.png");
			up2 = setup("../res/Object/bomb01.png");
			down1 = setup("../res/Object/bomb02.png");
			down2 = setup("../res/Object/bomb03.png");
			left1 = setup("../res/Object/bomb04.png");
			left2 = setup("../res/Object/bomb05.png");
			right1 = setup("../res/Object/bomb06.png");
			right2 = setup("../res/Object/bomb07.png");

			explode[0][0] = setup("../res/Object/bombExplode/explode0.png");
			explode[0][1] = setup("../res/Object/bombExplode/explodeH0.png");
			explode[0][2] = setup("../res/Object/bombExplode/explodeV0.png");

			explode[1][0] = setup("../res/Object/bombExplode/explode1.png");
			explode[1][1] = setup("../res/Object/bombExplode/explodeH1.png");
			explode[1][2] = setup("../res/Object/bombExplode/explodeV1.png");

			explode[2][0] = setup("../res/Object/bombExplode/explode2.png");
			explode[2][1] = setup("../res/Object/bombExplode/explodeH2.png");
			explode[2][2] = setup("../res/Object/bombExplode/explodeV2.png");

			explode[3][0] = setup("../res/Object/bombExplode/explode3.png");
			explode[3][1] = setup("../res/Object/bombExplode/explodeH3.png");
			explode[3][2] = setup("../res/Object/bombExplode/explodeV3.png");

			explode[4][0] = setup("../res/Object/bombExplode/explode4.png");
			explode[4][1] = setup("../res/Object/bombExplode/explodeH4.png");
			explode[4][2] = setup("../res/Object/bombExplode/explodeV4.png");
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		setDefaultValues();
	}

	public boolean setUpBomb(int worldX, int worldY) {
		this.worldX = worldX;
		this.worldY = worldY;
		// if this place already has a bomb
		for(int i = 0; i < gamePanel.bombs.length; i++) {
			if(gamePanel.bombs[i] == this) continue;
			if(!gamePanel.bombs[i].isExploded &&
				this.worldX == gamePanel.bombs[i].worldX &&
				this.worldY == gamePanel.bombs[i].worldY
			) {
				return false;
			}
		}
		timer = 5;
		frameCounter = 0;
		collision = false;
		isExploded = false;
		setExplosionZone();
		return true;
	}

	public void setDefaultValues() {
		name = "Bomb";
		timer = 5;
		power = 1;
		frameCounter = 0;
		isExploded = true;
		collision = false;
	}

	public void explode() {
		isExploded = true;
		collision = false;
	}

	public void giveDamage(Entity target) {

		// gets damage zone position
		damageZoneHorizontal.x = worldX - gamePanel.tileSize * left;
		damageZoneHorizontal.y = worldY + damageZoneHorizontal.y;
		damageZoneVertical.y = worldY - gamePanel.tileSize * up;
		damageZoneVertical.x = worldX + damageZoneVertical.x;
		
		// get entity's solid area position
		target.solidArea.x = target.worldX + target.solidArea.x;
		target.solidArea.y = target.worldY + target.solidArea.y;
		
		if(target.solidArea.intersects(damageZoneHorizontal) || target.solidArea.intersects(damageZoneVertical)) {
			target.takeDamage(1);
		}
		
		target.solidArea.x = target.solidAreaDefaultX;
		target.solidArea.y = target.solidAreaDefaultY;

		damageZoneHorizontal.x = damageZoneHorizontalDefaultX;
		damageZoneHorizontal.y = damageZoneHorizontalDefaultY;
		damageZoneVertical.y = damageZoneVerticalDefaultY;
		damageZoneVertical.x = damageZoneVerticalDefaultX;
	}

	@Override
	public void update() {
		if(!isExploded) {

			if(collision == false) {
				// if player go away from bomb then set the bomb's collision = true
				// get player solid area position
				gamePanel.player.solidArea.x = gamePanel.player.worldX + gamePanel.player.solidArea.x;
				gamePanel.player.solidArea.y = gamePanel.player.worldY + gamePanel.player.solidArea.y;
	
				// Get bomb's solid area position
				solidArea.x = worldX + solidArea.x;
				solidArea.y = worldY + solidArea.y;

				if(!gamePanel.player.solidArea.intersects(solidArea)) {
					collision = true;
				}

				gamePanel.player.solidArea.x = gamePanel.player.solidAreaDefaultX;
				gamePanel.player.solidArea.y = gamePanel.player.solidAreaDefaultY;
				solidArea.x = solidAreaDefaultX;
				solidArea.y = solidAreaDefaultY;
			}

			// bomb clock count down
			frameCounter++;

			if(frameCounter >= 60) {
				timer--;
				frameCounter = 0;
				if(timer == 1) {
					gamePanel.playSoundEffect("Explode");
				}
				if(timer == 0) {
					explode();
					gamePanel.player.hasBomb++;
				}
			}
			if(timer == 1) {
				for(int i = 0; i < gamePanel.monsters.length; i++) {
					if(gamePanel.monsters[i] != null) {
						giveDamage(gamePanel.monsters[i]);
					}
				}
				for(int i = 0; i < gamePanel.objects.length; i++) {
					if(gamePanel.objects[i] != null) {
						giveDamage(gamePanel.objects[i]);
					}
				}
				giveDamage(gamePanel.player);
			}
		}
	}

	@Override
	public void draw(Graphics2D g2D) {
		int screenX = worldX - gamePanel.player.worldX + gamePanel.player.screenX;
		int screenY = worldY - gamePanel.player.worldY + gamePanel.player.screenY;		
		
		if(timer > 1) {
			if(frameCounter % 10 > 5) {
				if(timer > 4) {image = up1;}
				else if(timer > 3) {image = down1;}
				else if(timer > 2) {image = left1;}
				else if(timer > 1) {image = right1;}
			} else {
				if(timer > 4) {image = up2;}
				else if(timer > 3) {image = down2;}
				else if(timer > 2) {image = left2;}
				else if(timer > 1) {image = right2;}
			}
			g2D.drawImage(image, screenX, screenY, null);
		} else {
			// explode animation
			if(frameCounter < 5) {
				explode0 = explode[0][0];
				explodeH = explode[0][1];
				explodeV = explode[0][2];
			} else if(frameCounter < 10) {
				explode0 = explode[1][0];
				explodeH = explode[1][1];
				explodeV = explode[1][2];
			} else if(frameCounter < 50) {
				explode0 = explode[2][0];
				explodeH = explode[2][1];
				explodeV = explode[2][2];
			} else if(frameCounter < 55) {
				explode0 = explode[3][0];
				explodeH = explode[3][1];
				explodeV = explode[3][2];
			} else if(frameCounter < 60) {
				explode0 = explode[4][0];
				explodeH = explode[4][1];
				explodeV = explode[4][2];
			}
			g2D.drawImage(explode0, screenX, screenY, null);
			// bomb blow up
			for(int i = 1; i <= power; i++) {

				if(i <= up)		g2D.drawImage(explodeV, screenX, screenY - gamePanel.tileSize * i, null);
				if(i <= down)	g2D.drawImage(explodeV, screenX, screenY + gamePanel.tileSize * i, null);
				if(i <= left)	g2D.drawImage(explodeH, screenX - gamePanel.tileSize * i, screenY, null);
				if(i <= right)	g2D.drawImage(explodeH, screenX + gamePanel.tileSize * i, screenY, null);
			}
		}
	}

	public void setExplosionZone() {
		int tileNum;
		int worldCol = worldX / gamePanel.tileSize;
		int worldRow = worldY / gamePanel.tileSize;
		boolean checkUp, checkDown, checkLeft, checkRight;
		checkUp = checkDown = checkLeft = checkRight = true;
		up = down = left = right = 0;
		
		for(int i = 1; i <= power; i++) {
			// TILES
			if(checkUp == true) {
				tileNum = gamePanel.tileManager.mapTilesNum[worldRow - i][worldCol];	
				if (gamePanel.tileManager.tiles[tileNum].collision == true) {
					checkUp = false;
				}
			}

			// bottom
			if(checkDown == true) {
				tileNum = gamePanel.tileManager.mapTilesNum[worldRow + i][worldCol];
				if(gamePanel.tileManager.tiles[tileNum].collision == true) {
					checkDown = false;
				}
			}
			
			// left
			if(checkLeft == true) {
				tileNum = gamePanel.tileManager.mapTilesNum[worldRow][worldCol - i];
				if(gamePanel.tileManager.tiles[tileNum].collision == true) {
					checkLeft = false;
				}
			}
			
			// right
			if(checkRight == true) {
				tileNum = gamePanel.tileManager.mapTilesNum[worldRow][worldCol + i];
				if(gamePanel.tileManager.tiles[tileNum].collision == true) {
					checkRight = false;
				}
			}

			// OBJECTS
			for(int objIdx = 0; objIdx < gamePanel.objects.length; objIdx++) {
				if(gamePanel.objects[objIdx] == null) continue;
				if(gamePanel.objects[objIdx].collision == false) continue;
				if(worldX == gamePanel.objects[objIdx].worldX) {		// bomb and object in a same column
					// UP
					if(gamePanel.objects[objIdx].name.equals("Door")) {
						gamePanel.objects[objIdx].worldY -= 4;
					}
					if(worldY - gamePanel.tileSize * i == gamePanel.objects[objIdx].worldY && checkUp == true) {
						if(gamePanel.objects[objIdx].breakable == true) {
							up++;
						}
						checkUp = false;
					}
					// DOWN
					if(worldY + gamePanel.tileSize * i == gamePanel.objects[objIdx].worldY && checkDown == true) {
						if(gamePanel.objects[objIdx].breakable == true) {
							down++;
						}
						checkDown = false;
					}
					if(gamePanel.objects[objIdx].name.equals("Door")) {
						gamePanel.objects[objIdx].worldY += 4;
					}
				} else if(worldY == gamePanel.objects[objIdx].worldY) {	// bomb and object in a same row
					if(gamePanel.objects[objIdx].name.equals("Door")) {
						gamePanel.objects[objIdx].worldY -= 4;
					}
					// LEFT
					if(worldX - gamePanel.tileSize * i == gamePanel.objects[objIdx].worldX && checkLeft == true) {
						if(gamePanel.objects[objIdx].breakable == true) {
							left++;
						}
						checkLeft = false;
					}
					// RIGHT
					if(worldX + gamePanel.tileSize * i == gamePanel.objects[objIdx].worldX && checkRight == true) {
						if(gamePanel.objects[objIdx].breakable == true) {
							right++;
						}
						checkRight = false;
					}
					if(gamePanel.objects[objIdx].name.equals("Door")) {
						gamePanel.objects[objIdx].worldY += 4;
					}
				}
			}
			if(checkUp == true) up++;
			if(checkDown == true) down++;
			if(checkLeft == true) left++;
			if(checkRight == true) right++;
		}
		
		// FOR DEBUG
		// System.out.println("up: " + up);
		// System.out.println("down: " + down);
		// System.out.println("left: " + left);
		// System.out.println("right: " + right);
		// System.out.println();
		
		int verticalLongSize = gamePanel.tileSize * (up + down) + gamePanel.tileSize;
		int horizontalLongSide = gamePanel.tileSize * (left + right) + gamePanel.tileSize;
		int shortSide = gamePanel.tileSize - 16;
		damageZoneHorizontal.setBounds(0, 8, horizontalLongSide, shortSide);
		damageZoneVertical.setBounds(8, 0, shortSide, verticalLongSize);

		damageZoneHorizontalDefaultX = damageZoneHorizontal.x;
		damageZoneHorizontalDefaultY = damageZoneHorizontal.y;
		damageZoneVerticalDefaultX = damageZoneVertical.x;
		damageZoneVerticalDefaultY = damageZoneVertical.y;
	}
}
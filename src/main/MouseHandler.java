package main;

import java.awt.event.*;

// for sound volume control
public class MouseHandler {

	public GamePanel gamePanel;
	public ClickListener clickListener;
	public DragListener dragListener;

	public boolean mousePressed;
	public boolean mouseDragged;

	public MouseHandler(GamePanel gamePanel) {
		this.gamePanel = gamePanel;
		clickListener = new ClickListener();
		dragListener = new DragListener();
	}

	public class ClickListener extends MouseAdapter {

		int x, y;

		@Override
		public void mousePressed(MouseEvent e) {
			x = e.getX();
			y = e.getY();
			mousePressed = true;
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			mousePressed = false;
			mouseDragged = false;
		}

		public int getX() {return x;}
		public int getY() {return y;}
	}

	public class DragListener extends MouseMotionAdapter {

		int x, y;
		@Override
		public void mouseDragged(MouseEvent e) {
			x = e.getX();
			y = e.getY();
			mouseDragged= true;
		}

		public int getX() {return x;}
		public int getY() {return y;}
		public void setX(int x) {this.x = x;}
		public void setY(int y) {this.y = y;}
	}
}

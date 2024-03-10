package misha.tictactoe.screen.sessionscreen;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;

import misha.tictactoe.TicTacToe3D;
import misha.tictactoe.screen.Screen;
import misha.tictactoe.screen.ScreenManager;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;

public class ConnectionFailedScreen extends Screen {
	
	private Rectangle backButton;
	
	public ConnectionFailedScreen(ScreenManager manager) {
		super(manager);
	}

	@Override
	public void setup() {
		int width = 2 * TicTacToe3D.width / 5;
		backButton = new Rectangle(TicTacToe3D.width / 2 - width / 2, 2 * TicTacToe3D.height / 3 - 40, width, 80);
	}

	@Override
	public void draw(Graphics g) {
		setup();
		
		g.setFont(new Font("monospaced", Font.PLAIN, 30));
		g.setColor(Color.WHITE);
		drawTextInBounds(g, 0, TicTacToe3D.height / 3 - 40, TicTacToe3D.width, 80, "Connection to server failed...");
		
		g.setFont(new Font("monospaced", Font.PLAIN, 30));
		drawButton(g, backButton, new Color(150, 150, 150), null, Color.BLACK, "Back", 10);
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
	}
	
	@Override
	public void mousePressed(MouseEvent e) {
		Point p = e.getPoint();
		
		if (backButton.contains(p.x, p.y)) {
			screenManager.switchScreen(ScreenManager.MENU_SCREEN);
		}
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		// Do nothing
	}
	
	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		// Do nothing
	}
	
	@Override
	public void mouseMoved(MouseEvent e) {
		// Do nothing
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// Do nothing
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// Do nothing
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// Do nothing
		
	}

	@Override
	public void onClientMessageRecieved(String msg) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onClientDisconnect() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onClientConnect() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	
}

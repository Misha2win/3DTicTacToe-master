package misha.tictactoe.screen.sessionscreen;

import java.awt.event.MouseEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.geom.Rectangle2D;
import java.awt.Color;
import java.awt.Font;
import misha.net.message.MessageType;
import misha.tictactoe.TicTacToe3D;
import misha.tictactoe.screen.ScreenManager;

import java.awt.Graphics;
import java.awt.Point;

public class SessionJoinScreen extends TwoFieldInputScreen {
	
	private static final int ROUNDESS = 10;
	
	private Rectangle2D backButton;
	private Rectangle2D joinButton;
	
	public SessionJoinScreen(ScreenManager manager) {
		super(manager);
	}
	
	public void focused() {
		super.focused();
		
		super.nameField.setEditable(false);
		passwordField.setText("");
	}
	
	@Override
	public void setup() {
		super.setup();
		
		int width = 3 * TicTacToe3D.width / 4;
		
		joinButton = new Rectangle2D.Double(TicTacToe3D.width / 2 - width / 2, TicTacToe3D.height - 280, width, 80);
		backButton = new Rectangle2D.Double(TicTacToe3D.width / 2 - width / 2, TicTacToe3D.height - 180, width, 80);
	}

	@Override
	public void draw(Graphics g) {
		setup();
		
		g.setFont(new Font("monospaced", Font.PLAIN, 30));
		
		drawButton(g, backButton, new Color(150, 150, 150), null, Color.BLACK, "Back", ROUNDESS);
		drawButton(g, joinButton, new Color(150, 150, 150), null, Color.BLACK, "Create", ROUNDESS);
	}
	
	public void submitRequest() {
		screenManager.switchScreen(ScreenManager.SESSION_JOIN_WAIT_SCREEN);
		screenManager.getClient().sendMessage(
				MessageType.QUERY_CONNECT_SESSION, 
				TicTacToe3D.GAME_NAME + " " + nameField.getText() + " " + passwordField.getText(), 
				null
		);
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		// Do nothing
	}
	
	@Override
	public void mousePressed(MouseEvent e) {
		Point p = e.getPoint();
		
		if (backButton.contains(p.x, p.y)) {
			screenManager.switchScreen(ScreenManager.SESSION_SELECT_SCREEN);
		}  else if (joinButton.contains(p.x, p.y)) {
			if (super.nameField.getText().length() > 0 && super.passwordField.getText().length() > 0)
				submitRequest();
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
	public void onClientMessageRecieved(String message) {
		// Do nothing since no message should be received
	}

	@Override
	public void onClientDisconnect() {
		// TODO Go to connection failed screen
	}

	@Override
	public void onClientConnect() {
		// Do nothing
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

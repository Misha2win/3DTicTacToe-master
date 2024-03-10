package misha.tictactoe.screen.sessionscreen;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.Color;
import java.awt.Font;
import misha.net.ClientListener;
import misha.net.message.Message;
import misha.net.message.MessageType;
import misha.tictactoe.TicTacToe3D;
import misha.tictactoe.screen.Screen;
import misha.tictactoe.screen.ScreenManager;
import misha.tictactoe.screen.gamescreen.GameScreen;

import java.awt.Graphics;

public class SessionJoinWaitScreen extends Screen implements ClientListener {
	
	public SessionJoinWaitScreen(ScreenManager manager) {
		super(manager);
	}

	@Override
	public void setup() {
	}

	@Override
	public void draw(Graphics g) {
		double anim = System.currentTimeMillis() / 1000;
		String str = null;
		if (anim % 4 == 0) {
			str = "Waiting to join session";
		} else if (anim % 4 == 1) {
			str = "Waiting to join session.";
		} else if (anim % 4 == 2) {
			str = "Waiting to join session..";
		} else if (anim % 4 == 3) {
			str = "Waiting to join session...";
		}
		
		if (str != null) {
			g.setFont(new Font("monospaced", Font.PLAIN, 25));
			g.setColor(Color.WHITE);
			drawTextInBounds(g, 0, 0, TicTacToe3D.width, TicTacToe3D.height, str);
		}
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
	}
	
	@Override
	public void mousePressed(MouseEvent e) {
	}

	@Override
	public void mouseDragged(MouseEvent e) {
	}
	
	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
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
		Message msg = Message.parse(message);
		
		if (msg.type == MessageType.SESSION_ERROR) {
			screenManager.switchScreen(ScreenManager.CONNECTION_FAILED_SCREEN);
		} else if (msg.type == MessageType.SESSION_ID_ASSIGN) {
			screenManager.switchScreen(ScreenManager.MULTIPLAYER_GAME_SCREEN);
			GameScreen gameScreen = (GameScreen) screenManager.getScreen(ScreenManager.MULTIPLAYER_GAME_SCREEN);
			gameScreen.setPlayingAs(false);
		}
	}

	@Override
	public void onClientDisconnect() {
	}

	@Override
	public void onClientConnect() {
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

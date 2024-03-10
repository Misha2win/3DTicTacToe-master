package misha.tictactoe.screen.sessionscreen;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.geom.Rectangle2D;
import java.awt.Color;
import java.awt.Font;
import misha.net.ClientListener;
import misha.tictactoe.TicTacToe3D;
import misha.tictactoe.screen.Screen;
import misha.tictactoe.screen.ScreenManager;

import java.awt.Graphics;

public class ServerJoinWaitScreen extends Screen implements ClientListener {
	
	@SuppressWarnings("unused")
	private Rectangle2D backButton;
	
	public ServerJoinWaitScreen(ScreenManager manager) {
		super(manager);
	}
	
	@Override
	public void focused() {
		if (!screenManager.getClient().getIsConnected()) {
			screenManager.getClient().connectToServer("w2ui.com", 25565);
		} else {
			screenManager.switchScreen(ScreenManager.SESSION_SELECT_SCREEN);
		}
	}
	
	@Override
	public void draw(Graphics g) {
		double anim = System.currentTimeMillis() / 1000;
		String str = null;
		if (anim % 4 == 0) {
			str = "Waiting to join server";
		} else if (anim % 4 == 1) {
			str = "Waiting to join server.";
		} else if (anim % 4 == 2) {
			str = "Waiting to join server..";
		} else if (anim % 4 == 3) {
			str = "Waiting to join server...";
		}
		
		if (str != null) {
			g.setFont(new Font("monospaced", Font.PLAIN, 25));
			g.setColor(Color.WHITE);
			drawTextInBounds(g, 0, 0, TicTacToe3D.width, TicTacToe3D.height, str);
		}
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseWheelMoved(MouseWheelEvent arg0) {
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

	@Override
	public void onClientMessageRecieved(String message) {
	}

	@Override
	public void onClientDisconnect() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onClientConnect() {
		screenManager.switchScreen(ScreenManager.SESSION_SELECT_SCREEN);
	}
	
	@Override
	public void onClientConnectFail() {
		screenManager.switchScreen(ScreenManager.CONNECTION_FAILED_SCREEN);
	}

	@Override
	public void setup() {
		// TODO Auto-generated method stub
		
	}
	
}

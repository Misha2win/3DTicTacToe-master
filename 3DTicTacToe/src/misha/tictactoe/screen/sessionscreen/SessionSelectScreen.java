package misha.tictactoe.screen.sessionscreen;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.geom.Rectangle2D;
import java.awt.Rectangle;
import java.awt.Color;
import java.awt.Font;
import misha.net.ClientListener;
import misha.net.message.Message;
import misha.net.message.MessageType;
import misha.tictactoe.TicTacToe3D;
import misha.tictactoe.screen.Screen;
import misha.tictactoe.screen.ScreenManager;

import java.awt.Graphics;
import java.awt.Point;

public class SessionSelectScreen extends Screen implements ClientListener {
	
	private static final int PADDING = 15;
	private static final int ROUNDESS = 10;
	
	private String[] sessionList;
	private String[] sessionDisplayNameList;
	private Rectangle2D[] sessionButtons;
	private int scroll;
	
	private Rectangle2D backButton;
	private Rectangle2D createButton;
	private Rectangle2D refreshButton;
	
	public SessionSelectScreen(ScreenManager manager) {
		super(manager);
		
		sessionList = new String[0];
	}
	
	@Override
	public void focused() {
		if (!screenManager.getClient().getIsConnected()) {
			screenManager.switchScreen(ScreenManager.CONNECTION_FAILED_SCREEN);
		} else {
			screenManager.getClient().sendMessage(MessageType.QUERY_JOINABLE_SESSIONS, TicTacToe3D.GAME_NAME, null);
		}
	}

	@Override
	public void setup() {
		int width = (TicTacToe3D.width - 2 * PADDING) / 3 - (3 * PADDING / 4);
		
		sessionButtons = new Rectangle2D[sessionList.length];
		int buttonHeight = 50;
		for (int i = 0; i < sessionList.length; i++) {
			int yOffset = scroll + i * PADDING + i * buttonHeight;
			sessionButtons[i] = new Rectangle2D.Double(2 * PADDING, yOffset + 2 * PADDING, TicTacToe3D.width - 4 * PADDING, buttonHeight);
		}
		
		backButton = new Rectangle2D.Double(PADDING, TicTacToe3D.height - 145 + 30, width, 60);
		createButton = new Rectangle2D.Double(1 * PADDING + 1 * width + PADDING, TicTacToe3D.height - 145 + 30, width, 60);
		refreshButton = new Rectangle2D.Double(2 * PADDING + 2 * width + PADDING, TicTacToe3D.height - 145 + 30, width, 60);
	}

	@Override
	public void draw(Graphics g) {
		setup();
		
		g.setFont(new Font("monspaced", Font.PLAIN, 20));
		
		g.setColor(new Color(35, 35, 35));
		g.fillRoundRect(PADDING, PADDING, TicTacToe3D.width - 2 * PADDING, TicTacToe3D.height - 160, ROUNDESS, ROUNDESS);
		
		if (sessionList.length > 0) {
			g.setClip(new Rectangle(PADDING, PADDING, TicTacToe3D.width - 2 * PADDING, TicTacToe3D.height - 160));
			for (int i = 0; i < sessionList.length; i++) {
				drawButton(g, sessionButtons[i], new Color(150, 150, 150), null, Color.BLACK, sessionDisplayNameList[i], ROUNDESS);
			}
			g.setClip(null);
		} else {
			g.setColor(new Color(150, 150, 150));
			super.drawTextInBounds(g, PADDING, PADDING, TicTacToe3D.width - 2 * PADDING, TicTacToe3D.height - 160, "No joinable sessions...");
		}
		
		drawButton(g, backButton, new Color(150, 150, 150), null, Color.BLACK, "Back", ROUNDESS);
		drawButton(g, createButton, new Color(150, 150, 150), null, Color.BLACK, "Create", ROUNDESS);
		drawButton(g, refreshButton, new Color(150, 150, 150), null, Color.BLACK, "Refresh", ROUNDESS);
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
	}
	
	@Override
	public void mousePressed(MouseEvent e) {
		Point p = e.getPoint();
		
		if (backButton.contains(p.x, p.y)) {
			screenManager.switchScreen(ScreenManager.MENU_SCREEN);
		}  else if (createButton.contains(p.x, p.y)) {
			screenManager.switchScreen(ScreenManager.SESSION_CREATE_SCREEN);
		}  else if (refreshButton.contains(p.x, p.y)) {
			screenManager.getClient().sendMessage(MessageType.QUERY_JOINABLE_SESSIONS, TicTacToe3D.GAME_NAME, null);
		} else {
			for (int i = 0; i < sessionButtons.length; i++) {
				if (sessionButtons[i].contains(p.x, p.y)) {
					((SessionJoinScreen) screenManager.getScreen(ScreenManager.SESSION_JOIN_SCREEN)).setSessionNameTextField(sessionList[i]);
					screenManager.switchScreen(ScreenManager.SESSION_JOIN_SCREEN);
				}
			}
		}
	}

	@Override
	public void mouseDragged(MouseEvent e) {
	}
	
	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		scroll += e.getUnitsToScroll() * 5;
		if (scroll > 0) {
			scroll = 0;
		}
		
		int maxScroll = ((sessionList.length - 1) * 50) + ((sessionList.length - 1) * 15);
		if (scroll < -maxScroll) {
			scroll = -maxScroll;
		}
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
		
		if (msg.type == MessageType.REPLY) {
			Message rply = Message.parse(msg.reply);
			
			if (rply.type == MessageType.QUERY_JOINABLE_SESSIONS) {
				String[] sessionInfo = msg.message.split(" ");
				if (sessionInfo.length > 0) {
					sessionList = new String[sessionInfo.length / 3];
					sessionDisplayNameList = new String[sessionInfo.length / 3];
					
					for (int i = 0, session = 0; i < sessionInfo.length && session < sessionList.length; i += 3, session++) {
						sessionList[session] = sessionInfo[i];
						sessionDisplayNameList[session] = sessionInfo[i] + " (" + sessionInfo[i + 1] + "/" + sessionInfo[i + 2] + ")";
					}
				} else if (sessionInfo.length == 0) {
					sessionList = new String[0];
					sessionDisplayNameList = new String[0];
				}
			}
		} else if (msg.type == MessageType.SESSION_UPDATE) {
			screenManager.getClient().sendMessage(MessageType.QUERY_JOINABLE_SESSIONS, TicTacToe3D.GAME_NAME, message);
		}
	}

	@Override
	public void onClientDisconnect() {
		// Cry
	}

	@Override
	public void onClientConnect() {
		screenManager.getClient().sendMessage(MessageType.QUERY_JOINABLE_SESSIONS, TicTacToe3D.GAME_NAME, null);
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

package misha.tictactoe.screen;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import javax.swing.JPanel;
import misha.net.Client;
import misha.net.ClientListener;
import misha.tictactoe.DrawingPanel;
import misha.tictactoe.screen.gamescreen.AIGameScreen;
import misha.tictactoe.screen.gamescreen.LocalGameScreen;
import misha.tictactoe.screen.gamescreen.MultiplayerGameScreen;
import misha.tictactoe.screen.gamescreen.PauseScreen;
import misha.tictactoe.screen.handler.KeyHandler;
import misha.tictactoe.screen.handler.MouseHandler;
import misha.tictactoe.screen.sessionscreen.ConnectionFailedScreen;
import misha.tictactoe.screen.sessionscreen.ServerJoinWaitScreen;
import misha.tictactoe.screen.sessionscreen.SessionCreateScreen;
import misha.tictactoe.screen.sessionscreen.SessionCreateWaitScreen;
import misha.tictactoe.screen.sessionscreen.SessionJoinScreen;
import misha.tictactoe.screen.sessionscreen.SessionJoinWaitScreen;
import misha.tictactoe.screen.sessionscreen.SessionSelectScreen;

public class ScreenManager implements MouseMotionListener, MouseListener, MouseWheelListener, KeyListener, ClientListener {
	
	/**
	 * The ID for MenuScreen
	 */
	public static final int MENU_SCREEN = 0;
	public static final int LOCAL_GAME_SCREEN = 1;
	public static final int SESSION_SELECT_SCREEN = 2;
	public static final int SESSION_CREATE_SCREEN = 3;
	public static final int SESSION_CREATE_WAIT_SCREEN = 4;
	public static final int SESSION_JOIN_WAIT_SCREEN = 5;
	public static final int CONNECTION_FAILED_SCREEN = 6;
	public static final int SESSION_JOIN_SCREEN = 7;
	public static final int PAUSE_SCREEN = 8;
	public static final int AI_GAME_SCREEN = 9;
	public static final int MULTIPLAYER_GAME_SCREEN = 10;
	public static final int SERVER_JOIN_WAIT_SCREEN = 11;
	
	private final JPanel panel;

	private ArrayList<Screen> screens;
	private int currentScreen;
	
	private final Client client;
	
	/**
	 * Constructs a new ScreenManager
	 * 
	 * @param surface the surface to give all of the Screens
	 */
	public ScreenManager(DrawingPanel panel) {
		this.panel = panel;
		
		this.client = new Client();
		client.addServerListener(this);
		
		MouseHandler mouseHandler = new MouseHandler(this);
		panel.addMouseMotionListener(mouseHandler);
		panel.addMouseListener(mouseHandler);
		panel.addMouseWheelListener(mouseHandler);
		panel.addMouseWheelListener(mouseHandler);
		
		KeyHandler keyHandler = new KeyHandler(this);
		panel.addKeyListener(keyHandler);
		
		currentScreen = ScreenManager.MENU_SCREEN;
		
		this.screens = new ArrayList<Screen>();
		screens.add(new MenuScreen(this));
		screens.add(new LocalGameScreen(this));
		screens.add(new SessionSelectScreen(this));
		screens.add(new SessionCreateScreen(this));
		screens.add(new SessionCreateWaitScreen(this));
		screens.add(new SessionJoinWaitScreen(this));
		screens.add(new ConnectionFailedScreen(this));
		screens.add(new SessionJoinScreen(this));
		screens.add(new PauseScreen(this));
		screens.add(new AIGameScreen(this));
		screens.add(new MultiplayerGameScreen(this));
		screens.add(new ServerJoinWaitScreen(this));
		
		screens.get(currentScreen).focused();
		
		setup();
	}
	
	/**
	 * Calls all of the Screen's setup methods
	 */
	public void setup() {
		for (Screen s : screens) {
			s.setup();
		}
	}
	
	public Client getClient() {
		return client;
	}
	
	public JPanel getPanel() {
		return panel;
	}
	
	/**
	 * Switches screens
	 * 
	 * @param i the ID of the Screen to switch to
	 */
	public void switchScreen(int i) {
		screens.get(currentScreen).unfocused();
		currentScreen = i;
		screens.get(currentScreen).focused();
	}
	
	/**
	 * @return the current active screen
	 */
	public Screen getCurrentScreen() {
		return screens.get(currentScreen);
	}
	
	/**
	 * @param screen the ID of the Screen to return
	 * @return the Screen with the ID of screen, if no such Screen exists then returns null
	 */
	public Screen getScreen(int screen) {
		if (screen >= 0 && screen < screens.size())
			return screens.get(screen);
		else
			return null;
	}

	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		getCurrentScreen().mouseWheelMoved(e);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		getCurrentScreen().mouseClicked(e);
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		getCurrentScreen().mouseEntered(e);
	}

	@Override
	public void mouseExited(MouseEvent e) {
		getCurrentScreen().mouseExited(e);
	}

	@Override
	public void mousePressed(MouseEvent e) {
		getCurrentScreen().mousePressed(e);
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		getCurrentScreen().mouseReleased(e);
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		getCurrentScreen().mouseDragged(e);
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		getCurrentScreen().mouseMoved(e);
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		getCurrentScreen().keyPressed(e);
	}

	@Override
	public void keyReleased(KeyEvent e) {
		getCurrentScreen().keyReleased(e);
	}

	@Override
	public void keyTyped(KeyEvent e) {
		getCurrentScreen().keyTyped(e);
	}

	@Override
	public void onClientMessageRecieved(String msg) {
		getCurrentScreen().onClientMessageRecieved(msg);
	}

	@Override
	public void onClientDisconnect() {
		getCurrentScreen().onClientDisconnect();
	}

	@Override
	public void onClientConnect() {
		getCurrentScreen().onClientConnect();
	}

	@Override
	public void onClientConnectFail() {
		getCurrentScreen().onClientConnectFail();
	}
	
}

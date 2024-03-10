package misha.tictactoe.screen.gamescreen;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;

import misha.net.message.MessageType;
import misha.tictactoe.TicTacToe3D;
import misha.tictactoe.screen.Screen;
import misha.tictactoe.screen.ScreenManager;

public class PauseScreen extends Screen {
	
	private BufferedImage background;
	private int screenToReturnTo;
	
	private Rectangle menuButton;
	private Rectangle backButton;
	
	public PauseScreen(ScreenManager manager) {
		super(manager);
	}
	
	@Override
	public void focused() {
		screenToReturnTo = -1;
	}

	@Override
	public void setup() {
		int width = 2 * TicTacToe3D.width / 5;
		menuButton = new Rectangle(TicTacToe3D.width / 2 - width / 2, 2 * TicTacToe3D.height / 3 - 85, width, 80);
		backButton = new Rectangle(TicTacToe3D.width / 2 - width / 2, 2 * TicTacToe3D.height / 3 + 5, width, 80);
	}

	@Override
	public void draw(Graphics g) {
		setup();
		
		if (background != null) {
			g.drawImage(background, 0, 0, TicTacToe3D.width, TicTacToe3D.height, null);
			g.setColor(new Color(0, 0, 0, 100));
			g.fillRect(0, 0, TicTacToe3D.width, TicTacToe3D.height);
		}
		
		g.setFont(new Font("monospaced", Font.PLAIN, 30));
		g.setColor(Color.WHITE);
		drawTextInBounds(g, 0, TicTacToe3D.height / 3 - 40, TicTacToe3D.width, 80, "Game paused...");
		
		g.setFont(new Font("monospaced", Font.PLAIN, 30));
		drawButton(g, menuButton, new Color(150, 150, 150), null, Color.BLACK, "Main menu", 10);
		
		g.setFont(new Font("monospaced", Font.PLAIN, 30));
		drawButton(g, backButton, new Color(150, 150, 150), null, Color.BLACK, "Back", 10);
	}
	
	public void setImage(BufferedImage img) {
		background = blur(img, 3);
		
		new Thread(() -> {
			for (int i = 0; i < 8; i++) {
				if (screenManager.getCurrentScreen() == this)
					background = blur(background, 3);
			}
		}).start();
	}
	
	private static BufferedImage blur(BufferedImage image, int size) {
		float weight = 1.0f / (size * size);
		float[] data = new float[size * size];
		
		for (int i = 0; i < data.length; i++) {
			data[i] = weight;
		}
		
		ConvolveOp op = new ConvolveOp(new Kernel(size, size, data));
		
		return op.filter(image, null);
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
	}
	
	@Override
	public void mousePressed(MouseEvent e) {
		Point p = e.getPoint();
		
		if (menuButton.contains(p.x, p.y)) {
			screenManager.switchScreen(ScreenManager.MENU_SCREEN);
			
			if (screenManager.getClient().getIsConnected())
				screenManager.getClient().sendMessage(MessageType.SESSION_LEAVE, "", null);
			
			GameScreen gameScreen = (GameScreen) screenManager.getScreen(screenToReturnTo);
			gameScreen.setup();
		} else if (backButton.contains(p.x, p.y)) {
			turnToGameScreen();
		}
	}
	
	public void setReturnTo(int screen) {
		screenToReturnTo = screen;
	}
	
	private void turnToGameScreen() {
		if (screenToReturnTo != -1) {
			screenManager.switchScreen(screenToReturnTo);
		} else 
			throw new IllegalStateException("No screen set to return to!");
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
		if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
			turnToGameScreen();
		}
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

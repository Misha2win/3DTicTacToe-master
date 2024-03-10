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

import misha.tictactoe.TicTacToe3D;
import misha.tictactoe.screen.Screen;
import misha.tictactoe.screen.ScreenManager;

public class GameOverScreen extends Screen {
	
	private BufferedImage background;
	private int screenToReturnTo;
	
	private Rectangle menuButton;
	private Rectangle playAgainButton;
	
	public GameOverScreen(ScreenManager manager) {
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
		playAgainButton = new Rectangle(TicTacToe3D.width / 2 - width / 2, 2 * TicTacToe3D.height / 3 + 5, width, 80);
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
		drawButton(g, playAgainButton, new Color(150, 150, 150), null, Color.BLACK, "Back", 10);
	}
	
	public void setImage(BufferedImage img) {
		background = blur(img, 1);
		
		new Thread(() -> {
			for (int i = 2; i < 4; i++) {
				if (screenManager.getCurrentScreen() == this)
					background = blur(background, i);
			}
		}).start();
	}
	
	private static BufferedImage blur(BufferedImage image, int radius) {
		int size = radius * 2 + 1;
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
		} else if (playAgainButton.contains(p.x, p.y)) {
			turnToGameScreen();
			GameScreen gameScreen = (GameScreen) screenManager.getCurrentScreen();
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

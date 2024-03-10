package misha.tictactoe.screen;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.geom.Rectangle2D;

import misha.graphics.Renderer3D;
import misha.graphics.shapes.Point3D;
import misha.tictactoe.TicTacToe3D;
import misha.tictactoe.game.Board;

import java.awt.Font;
import java.awt.Color;

public class MenuScreen extends Screen {
	
	private Renderer3D renderer;
	
	private Rectangle2D AIButton;
	private Rectangle2D localButton;
	private Rectangle2D onlineButton;
	private Rectangle2D helpButton;
	private Rectangle2D settingsButton;
	private Rectangle2D quitButton;
	
	private Rectangle2D targetedButton;
	
	private final double xRotate, yRotate, zRotate;
	private boolean doRaindow;
	
	public MenuScreen(ScreenManager manager) {
		super(manager);
		
		renderer = new Renderer3D();
		Board board = new Board(renderer.getEntityManager());
		
		xRotate = (Math.random() * (Math.random() * 0.7f) + 0.2f) * ((int) (Math.random() * 2) == 1 ? -1 : 1);
		yRotate = (Math.random() * (Math.random() * 0.7f) + 0.2f) * ((int) (Math.random() * 2) == 1 ? -1 : 1);
		zRotate = (Math.random() * (Math.random() * 0.7f) + 0.2f) * ((int) (Math.random() * 2) == 1 ? -1 : 1);
	}

	@Override
	public void setup() {
		int x = 0, y = 0, w = 0, h = 0;
		
		w = TicTacToe3D.width * 2 / 5 > 250 ? TicTacToe3D.width * 2 / 5 : 250;
		h = 50;
		x = TicTacToe3D.width / 2 - w / 2;
		y = TicTacToe3D.height * 3 / 5 - 200;
		AIButton = new Rectangle2D.Double(x, y, w, h);
		
		y += 20 + h;
		localButton = new Rectangle2D.Double(x, y, w, h);
		
		y += 20 + h;
		onlineButton = new Rectangle2D.Double(x, y, w, h);
		
		y += 20 + h;
		helpButton = new Rectangle2D.Double(x, y, w, h);
		
		y += 20 + h;
		settingsButton = new Rectangle2D.Double(x, y, w, h);
		
		y += 20 + h;
		quitButton = new Rectangle2D.Double(x, y, w, h);
		
		Point3D.scale = (TicTacToe3D.width < TicTacToe3D.height ? TicTacToe3D.width : TicTacToe3D.height) / 2000f;
	}

	@Override
	public void draw(Graphics g) {
		setup();
		
		if (doRaindow) {
			rainbowCycle(g);
			g.fillRect(0, 0, TicTacToe3D.width, TicTacToe3D.height);
			renderer.getEntityManager().rotateX(6f);
			renderer.getEntityManager().rotateY(15f);
			renderer.getEntityManager().rotateZ(3f);
		}
		
		renderer.render(g);
		renderer.getEntityManager().rotateX(xRotate);
		renderer.getEntityManager().rotateY(yRotate);
		renderer.getEntityManager().rotateZ(zRotate);
		
		g.setFont(new Font("monospaced", Font.PLAIN, 50));
		
		String str = "3D TicTacToe";
		int strWidth = g.getFontMetrics().stringWidth(str);
		g.setColor(Color.BLACK);
		g.drawString(str, TicTacToe3D.width / 2 - strWidth / 2 - 2, TicTacToe3D.height / 5 - 2);
		g.drawString(str, TicTacToe3D.width / 2 - strWidth / 2 + 2, TicTacToe3D.height / 5 + 2);
		g.setColor(Color.WHITE);
		g.drawString(str, TicTacToe3D.width / 2 - strWidth / 2, TicTacToe3D.height / 5);
		
		g.setFont(new Font("monospaced", Font.PLAIN, 20));
		
		int roundness = 50;
		
		drawButton(g, AIButton, "Play against AI", roundness);
		drawButton(g, localButton, "Local Multiplayer", roundness);
		drawButton(g, onlineButton, "Online Multiplayer", roundness);
		drawButton(g, helpButton, "Help", roundness);
		drawButton(g, settingsButton, "Settings", roundness);
		drawButton(g, quitButton, "Quit", roundness);
	}
	
	public void rainbowCycle(Graphics g) {
		long time = System.currentTimeMillis() / 2 % 600;
		
		if (time >= 0 && time <= 100) {
			g.setColor(new Color(255, 0, (int) (255 - (time * 2.55)))); // 255, 0, 0 <- red

		} else if (time > 100 && time <= 200) {
			g.setColor(new Color(255, (int) ((time - 100) * 1.65), 0)); // 255, 165, 0 <- orange

		} else if (time > 200 && time <= 300) {
			g.setColor(new Color(255, (int) (165 + ((time - 200) * 0.90)), 0)); // 255, 255, 0 <- yellow

		} else if (time > 300 && time <= 400) {
			g.setColor(new Color((int) (255 - ((time - 300) * 2.55)), 255, 0)); // 0, 255, 0 <- green

		} else if (time > 400 && time <= 500) {
			g.setColor(new Color(0, (int) (255 - ((time - 400) * 2.55)), (int) ((time - 400) * 2.55))); // 0, 0, 255 <- blue

		} else if (time > 500 && time <= 600) {
			g.setColor(new Color((int) ((time - 500) * 2.55), 0, 255)); // 255, 0, 255 <- purple

		}
	}
	
	private void drawButton(Graphics g, Rectangle2D rect, String str, int roundness) {
		int strWidth = g.getFontMetrics().stringWidth(str);
		
		g.setColor(Color.WHITE);
		g.fillRoundRect((int) rect.getX(), (int) rect.getY(), (int) rect.getWidth(), (int) rect.getHeight(), roundness, roundness);
		g.setColor(Color.BLACK);
		g.drawRoundRect((int) rect.getX(), (int) rect.getY(), (int) rect.getWidth(), (int) rect.getHeight(), roundness, roundness);
		g.drawString(str, (int) (rect.getX() + rect.getWidth() / 2 - strWidth / 2), (int) (rect.getY() + rect.getHeight() / 2 + 7));
	}
	
	private void processButtons(MouseEvent e) {
		Point p = e.getPoint();
		
		if (AIButton.contains(p.x, p.y)) {
			screenManager.switchScreen(ScreenManager.AI_GAME_SCREEN);
			
		} else if (localButton.contains(p.x, p.y)) {
			screenManager.switchScreen(ScreenManager.LOCAL_GAME_SCREEN);
			
		} else if (onlineButton.contains(p.x, p.y)) {
			screenManager.switchScreen(ScreenManager.SERVER_JOIN_WAIT_SCREEN);
			
		} else if (settingsButton.contains(p.x, p.y)) {
			doRaindow = !doRaindow;
			
		} else if (quitButton.contains(p.x, p.y)) {
			System.exit(0);
			
		}
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
	}
	
	@Override
	public void mousePressed(MouseEvent e) {
		Point p = e.getPoint();
		
		if (AIButton.contains(p.x, p.y)) {
			targetedButton = AIButton;
			
		} else if (localButton.contains(p.x, p.y)) {
			targetedButton = localButton;
			
		} else if (onlineButton.contains(p.x, p.y)) {
			targetedButton = onlineButton;
			
		} else if (helpButton.contains(p.x, p.y)) {
			targetedButton = helpButton;
			
		} else if (settingsButton.contains(p.x, p.y)) {
			targetedButton = settingsButton;
			
		} else if (quitButton.contains(p.x, p.y)) {
			targetedButton = quitButton;
		}
	}
	
	@Override
	public void mouseReleased(MouseEvent e) {
		Point p = e.getPoint();
		
		if (targetedButton != null) {
			if (targetedButton.contains(p.x, p.y)) {
				processButtons(e);
			}
			targetedButton = null;
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
	public void onClientMessageRecieved(String msg) {
		// Ignore
	}

	@Override
	public void onClientDisconnect() {
		// Ignore
	}

	@Override
	public void onClientConnect() {
		// Ignore
	}

	@Override
	public void keyPressed(KeyEvent e) {
	}

	@Override
	public void keyReleased(KeyEvent e) {
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}
	
}

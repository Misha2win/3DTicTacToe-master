package misha.tictactoe.screen.gamescreen;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.Color;
import java.awt.Font;
import misha.graphics.entity.Entity;
import misha.graphics.entity.EntityManager;
import misha.graphics.shapes.Point3D;
import misha.graphics.shapes.Shape3D;
import misha.graphics.Renderer3D;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import misha.tictactoe.TicTacToe3D;
import misha.tictactoe.game.AI;
import misha.tictactoe.game.Board;
import misha.tictactoe.screen.Screen;
import misha.tictactoe.screen.ScreenManager;

import java.awt.Point;

public abstract class GameScreen extends Screen {
	
	// Rendering variables
	protected Renderer3D renderer;
	private Point intialPoint;
	
	// General game variables
	protected Board board;
	protected boolean playingAsX;
	
	public GameScreen(ScreenManager manager) {
		super(manager);
		
		setup();
	}

	@Override
	public void setup() {
		renderer = new Renderer3D();
		board = new Board(renderer.getEntityManager());
	}

	@Override
	public void draw(Graphics g) {
		renderer.render(g);
		
		g.setColor(Color.WHITE);
		g.setFont(new Font("monospaced", Font.PLAIN, 20));
		if (board.isGameOver()) {
			if (board.hasWinner()) {
				g.drawString((AI.getEvaluation(board.getGridCopy()) > 0 ? "X" : "O") + " won the game!", 5, TicTacToe3D.height - 35);
			} else {
				g.drawString("The game is tied!", 5, TicTacToe3D.height - 35);
			}
		}
	}
	
	public void setPlayingAs(boolean playingAs) {
		this.playingAsX = playingAs;
	}
	
	public Entity getClickedCube(MouseEvent e) {
		EntityManager entityManager = renderer.getEntityManager();
		
		// Iterate through ArrayList backwards because of how it is sorted
		for (int i = entityManager.getPolygons().size() - 1; i >= 0; i--) {
			Entity entity = entityManager.getPolygons().get(i);
			
			// If the point where the mouse was clicked is inside of the 2d projection of the polygon
			if (entity instanceof Shape3D && ((Shape3D)entity).getPolygons()[0].get2DPolygon().contains(e.getPoint())) {
				
				// Find which cube this polygon belongs to
				for (Entity cube : entityManager.getEntities()) {
					if (!(cube instanceof Shape3D))
						continue;
					
					for (Entity face : ((Shape3D)cube).getPolygons()) {
						
						// If the polygon belongs to the cube
						if (face == entity) {
							return cube;
						}
					}
				}
				
				break;
			}
		}
		
		return null;
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		Entity cube = getClickedCube(e);
		
		if (cube != null) {
			if (playingAsX == board.getXToMove()) {
				board.makeMove(cube);
			}
		}
	}
	
	@Override
	public void mousePressed(MouseEvent e) {
		intialPoint = e.getPoint();
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		if (e.getButton() == MouseEvent.BUTTON1) {
			int sens = 1;
			
			int yRotate = (e.getX() - intialPoint.x) * sens;
			int xRotate = (e.getY() - intialPoint.y) * sens;
			
			renderer.getEntityManager().rotateY(yRotate);
			renderer.getEntityManager().rotateX(xRotate);
			
			intialPoint = e.getPoint();
		}
	}
	
	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		renderer.getEntityManager().redrawNextFrame();
		Point3D.scale += e.getUnitsToScroll() * 0.01;
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
	}

	@Override
	public void onClientMessageRecieved(String msg) {
	}

	@Override
	public void onClientDisconnect() {
	}

	@Override
	public void onClientConnect() {
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
			screenManager.switchScreen(ScreenManager.PAUSE_SCREEN);
			PauseScreen screen = (PauseScreen) screenManager.getCurrentScreen();
			
			BufferedImage img = new BufferedImage(TicTacToe3D.width, TicTacToe3D.height, BufferedImage.TYPE_INT_RGB);
			this.draw(img.getGraphics());
			screen.setImage(img);
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}
	
}

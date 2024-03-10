package misha.tictactoe.game;

import misha.graphics.entity.Entity;
import misha.graphics.entity.EntityManager;
import misha.graphics.shapes.Cube3D;
import misha.graphics.shapes.Line3D;
import misha.graphics.shapes.Point3D;
import misha.graphics.shapes.Polygon3D;
import java.awt.Color;
import java.util.LinkedList;

/**
 * The game logic behind creating a 3D game of tictactoe. Handles everything between making moves and calculating the winner.
 * 
 * @author Misha Malinouski
 * @version 1.0
 */
public class Board { // TODO Whenever I have more braincells, refactor the getEvaluation() methods to be shorter and use only 1-2 loops instead
	
	// Constant variables
	
	private static final int CUBE_SIZE = 50; // 100
	private static final int CUBE_PADDING = 200; // 200

	private static final int NO_COLOR_ALPHA = 50; // 50
	private static final int COLOR_APLHA = 255; // 255
	
	public static final Color NO_COLOR = new Color(255, 255, 255, NO_COLOR_ALPHA);
	public static final Color NO_COLOR_2 = new Color(150, 150, 150, NO_COLOR_ALPHA);
	
//	public static final Color ALT_NO_COLOR = new Color(100, 100, 100);
//	public static final Color ALT_NO_COLOR_2 = new Color(125, 125, 125);
	
	public static final Color X_COLOR = new Color(100, 100, 255, COLOR_APLHA);
	public static final Color O_COLOR = new Color(255, 100, 100, COLOR_APLHA);
	
	public static final boolean DRAW_LINES = false;
	public static final Color LINE_COLOR = new Color(200, 200, 200);

	public static final int GRID_SIZE = 4;
	
	// Instance variables
	
	private LinkedList<Integer> moveList;

	private final Cube3D[][][] cubes;
	
	private Boolean[][][] gameState;
	
	private Cube3D previousMove;
	
	private boolean gameActive;
	
	private boolean xToMove;

	/**
	 * Constructs a new board
	 * 
	 * @param entityManager the EntityManager to add all of the Cube3D's of this board to so that they're drawn by the 3D renderer
	 */
	public Board(EntityManager entityManager) {
		cubes = new Cube3D[GRID_SIZE][GRID_SIZE][GRID_SIZE];
		gameState = new Boolean[GRID_SIZE][GRID_SIZE][GRID_SIZE];

		int offset = CUBE_SIZE + CUBE_PADDING;
		double val = (GRID_SIZE - 1) * 0.5; // Offset to positions to center the cube grid

		// Create all of the cubes in the cube grid based off of gridSize
		for (int x = 0; x < GRID_SIZE; x++) {
			for (int y = 0; y < GRID_SIZE; y++) {
				for (int z = 0; z < GRID_SIZE; z++) {
					Point3D p = new Point3D((x - val) * offset, (y - val) * offset, (z - val) * offset); // Create point of center for each cube
					cubes[x][y][z] = new Cube3D(NO_COLOR, p, CUBE_SIZE, CUBE_SIZE, CUBE_SIZE); // Create cube

					// Make sure each cube's polygons are drawing outlines
					for (Polygon3D poly : cubes[x][y][z].getPolygons()) {
						poly.drawOutline = true;
					}
					
					if (DRAW_LINES) {
						for (int axis = 0; axis < 3; axis++) {
							double x1 = (axis == 0 && x != 3 ? (x - val) * offset + CUBE_SIZE / 2 : (x - val) * offset);
							double y1 = (axis == 1 && y != 3  ? (y - val) * offset + CUBE_SIZE / 2 : (y - val) * offset);
							double z1 = (axis == 2 && z != 3  ? (z - val) * offset + CUBE_SIZE / 2 : (z - val) * offset);
							
							double x2 = (axis == 0 && x != 3  ? ((x + 1) - val) * offset - CUBE_SIZE / 2 : (x - val) * offset);
							double y2 = (axis == 1 && y != 3  ? ((y + 1) - val) * offset - CUBE_SIZE / 2 : (y - val) * offset);
							double z2 = (axis == 2 && z != 3  ? ((z + 1) - val) * offset - CUBE_SIZE / 2 : (z - val) * offset);
							
							entityManager.addEntity(new Line3D(LINE_COLOR, new Point3D(x1, y1, z1), new Point3D(x2, y2, z2)));
						}
					}
				}
			}
		}

		// Add every cube in the cubes 3D array into the entity manager
		for (Cube3D cube : getCubes()) {
			entityManager.addEntity(cube);
		}

		resetGame();
	}
	
	// Public methods
	
	/**
	 * Returns boolean which represents if there is a winner or not
	 * 
	 * @return true if either X or O won, else false
	 */
	public boolean hasWinner() {
		return AI.getEvaluation(gameState) != 0;
	}
	
	/**
	 * Calculates if the game is over
	 * 
	 * @return true if the game is over either by tie or someone won, else false
	 */
	public boolean isGameOver() {
		return (hasWinner() || !AI.hasMoves(gameState));
	}

	/**
	 * Makes a move onto the grid then evaluates the current position to see who won or if the game is still in progress
	 * 
	 * @param clickedCube the clicked cube
	 * @return true if the move was made on the grid without error
	 */
	public boolean makeMove(Entity clickedCube) {
		// Find where the clicked cube is in the cubes 3D Array
		for (Cube3D cube : getCubes()) {
			if (clickedCube == cube) {
				// If game isn't over yet and the cube is not taken
				if (gameActive && getGameStateWithCube(cube) == null) {
					
					updateGameState(cube);

					gameActive = !isGameOver();
					
					System.out.println("The game is currently " + (gameActive ? "ongoing" : "over") + "!");
					
					// Stuff to do once game is over
					if (!gameActive) {
						String winner = (AI.getEvaluation(gameState) == 0 ? "No one" : (AI.getEvaluation(gameState) > 0 ? "X" : "O"));
						System.out.println(winner + " won!");
					}

					xToMove = !xToMove;

					return true;
				}

				break;
			}
		}

		return false;
	}
	
	@SuppressWarnings("unchecked")
	public LinkedList<Integer> getMoveListCopy() {
		return (LinkedList<Integer>) moveList.clone();
	}
	
	private void updateGameState(Cube3D cube) {
		cube.setColor((xToMove ? X_COLOR.brighter() : O_COLOR.brighter())); // Make cube a little lighter to show this was the move made
		
		if (previousMove != null)
			previousMove.setColor((!xToMove ? X_COLOR : O_COLOR)); // Make cube color normal color
		previousMove = cube;
		
		for (int x = 0; x < GRID_SIZE; x++) {
			for (int y = 0; y < GRID_SIZE; y++) {
				for (int z = 0; z < GRID_SIZE; z++) {
					if (cubes[x][y][z] == cube) {
						gameState[x][y][z] = xToMove;
						moveList.add(AI.get1DIndex(x, y, z));
						return;
					}
				}
			}
		}
	}
	
	/**
	 * Generates and returns a copy of the cubes array
	 * 
	 * @return A Boolean[][][] copy of the internally stored gameState 3D array
	 */
	public Boolean[][][] getGridCopy() {
		Boolean[][][] copy = new Boolean[GRID_SIZE][GRID_SIZE][GRID_SIZE];
		
		for (int x = 0; x < GRID_SIZE; x++) {
			for (int y = 0; y < GRID_SIZE; y++) {
				for (int z = 0; z < GRID_SIZE; z++) {
					if (gameState[x][y][z] != null) {
						copy[x][y][z] = gameState[x][y][z];
						//copy[x][y][z] = new Boolean(gameState[x][y][z]); // Why the frick is this the thing that message it up??
					}
				}
			}
		}
		
		return copy;
	}
	
	/**
	 * Getter for gameActive
	 * 
	 * @return the value of gameActive
	 */
	public boolean getGameActive() {
		return gameActive;
	}
	
	/**
	 * Getter for xToMove
	 * 
	 * @return the value of xToMove
	 */
	public boolean getXToMove() {
		return xToMove;
	}
	
	/**
	 * Assembles and returns a 1D array with gridSize * gridSize * gridSize indexes
	 * containing all of the cubes making up this board.
	 * 
	 * @return A 1D array containing all of the cubes making up this board.
	 */
	public Cube3D[] getCubes() {
		Cube3D[] cubes = new Cube3D[GRID_SIZE * GRID_SIZE * GRID_SIZE];

		int index = 0;

		for (Cube3D[][] cubesArray2D : this.cubes) {
			for (Cube3D[] cubesArray1D : cubesArray2D) {
				for (Cube3D cube : cubesArray1D) {
					cubes[index] = cube;
					index++;
				}
			}
		}

		return cubes;
	}
	
	// Private methods
	
	/**
	 * Resets all instance variables to restart the game
	 */
	public void resetGame() {
		moveList = new LinkedList<>();
		gameState = new Boolean[GRID_SIZE][GRID_SIZE][GRID_SIZE];
		
		for (int x = 0; x < GRID_SIZE; x++) {
			for (int y = 0; y < GRID_SIZE; y++) {
				for (int z = 0; z < GRID_SIZE; z++) {
					Color color = ((y + z) % 2 != (x % 2 == 0 ? 0 : 1) ? NO_COLOR : NO_COLOR_2);
					cubes[x][y][z].setColor(color);
					
					// Only for creating a start state!
					if (gameState[x][y][z] != null && gameState[x][y][z]) {
						cubes[x][y][z].setColor(X_COLOR);
					} else if (gameState[x][y][z] != null && !gameState[x][y][z]) {
						cubes[x][y][z].setColor(O_COLOR);
					}
				}
			}
		}

		gameActive = true;
		xToMove = true;
	}
	
	private Boolean getGameStateWithCube(Cube3D cube) {
		for (int x = 0; x < GRID_SIZE; x++) {
			for (int y = 0; y < GRID_SIZE; y++) {
				for (int z = 0; z < GRID_SIZE; z++) {
					if (cubes[x][y][z] == cube) {
						return gameState[x][y][z];
					}
				}
			}
		}
		
		throw new IllegalArgumentException("Cube does not exist!");
	}
	
	public String toString() {
		String str = "{\n\n";
		
		for (Boolean[][] barr2d : gameState) {
			String str2 = "";
			
			for (Boolean[] barr : barr2d) {
				String str3 = "\s{ ";
				
				for (Boolean b : barr) {
					str3 += (b == null ? "-" : (b ? 0 : 1)) + " ";
				}
				
				str3 += "}\n";
				str2 += str3;
			}
			
			str += str2 + "\n";
		}
		
		str += "}";
		
		return str;
	}

}

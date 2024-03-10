//package tictactoe.game;
//
//import java.util.Arrays;
//import java.util.Collections;
//import java.util.LinkedList;
//import java.util.List;
//
//public class AI {
//	
//	private static final int DEPTH_TO_SEARCH = 4;
//	
//	private static int iterationCounter = 0;
//	
//	/**
//	 * Will evaluate every possible move using a MiniMax algorithm. This method does have side effect such as altering the board 3D array.
//	 * 
//	 * @param board the board to evaluate
//	 * @param playingAs the player to play as
//	 * @return an int representing the cube that this AI wishes to move in. Will return -1 if no moves can be done.
//	 */
//	public static int getBestMove(Boolean[][][] board, boolean playingAs) {
//		Move bestMove = null;
//		
//		iterationCounter = 0;
//		
//		if (!hasMoves(board) || AI.DEPTH_TO_SEARCH <= 0) {
//			return -1; // There are no cubes to play a move in or DEPTH_TO_SEARCH is 0
//		}
//		
//		Move[] moves = AI.getMoves(board);
//		
//		System.out.println(Arrays.toString(moves));
//		
//		for (Move m : moves) {
//			int[] boardIndex = get3DIndexes(m.move);
//			board[boardIndex[0]][boardIndex[1]][boardIndex[2]] = playingAs;
//			int eval = miniMax(board, !playingAs, DEPTH_TO_SEARCH - 1, Integer.MIN_VALUE, Integer.MAX_VALUE);
//			board[boardIndex[0]][boardIndex[1]][boardIndex[2]] = null;
//			
//			m.eval = eval;
//			
//			if (m.eval == (playingAs ? Integer.MAX_VALUE : Integer.MIN_VALUE)) {
//				break;
//			}
//			
//			System.out.println("The current number of iterations: " + iterationCounter);
//		}
//		
//		if (moves.length > 0) {
//			List<Move> movesList = Arrays.asList(moves);
//			Collections.shuffle(movesList);
//			movesList.toArray(moves);
//			
//			bestMove = moves[0];
//			
//			for (int i = 1; i < moves.length; i++) {
//				if ((playingAs ? moves[i].eval > bestMove.eval : moves[i].eval < bestMove.eval)) {
//					bestMove = moves[i];
//				}
//			}
//		}
//		
//		System.out.println("Finished findind move with " + iterationCounter + " iterations!");
//		
//	
//		return (bestMove != null ? bestMove.move : -1);
//	}
//	
//	private static int miniMax(Boolean[][][] board, boolean isMaximizer, int depth, int alpha, int beta) {
//		iterationCounter++;
//		
//		if (!hasMoves(board)) {
//			return 0;
//		} else if (depth <= 0 || getEvaluation(board) != 0) {
//			return getWeightedEval(board);
//		}
//		
//		int bestScore = 0;
//		
//		if (isMaximizer) {
//			bestScore = Integer.MIN_VALUE;
//			
//			for (Move m : AI.getMoves(board)) {
//				int[] boardIndex = get3DIndexes(m.move);
//				board[boardIndex[0]][boardIndex[1]][boardIndex[2]] = isMaximizer;
//				int score = miniMax(board, !isMaximizer, depth - 1, alpha, beta);
//				board[boardIndex[0]][boardIndex[1]][boardIndex[2]] = null;
//				
//				bestScore = Math.max(score, bestScore);
//				
//				alpha = Math.max(bestScore, alpha);
//				if (alpha >= beta) {
//					break;
//				}
//			}
//		} else {
//			bestScore = Integer.MAX_VALUE;
//			
//			for (Move m : AI.getMoves(board)) {
//				int[] boardIndex = get3DIndexes(m.move);
//				board[boardIndex[0]][boardIndex[1]][boardIndex[2]] = isMaximizer;
//				int score = miniMax(board, !isMaximizer, depth - 1, alpha, beta);
//				board[boardIndex[0]][boardIndex[1]][boardIndex[2]] = null;
//				
//				bestScore = Math.min(score, bestScore);
//				
//				beta = Math.min(bestScore, beta);
//				if (alpha >= beta) {
//					break;
//				}
//			}
//		}
//		
//		return bestScore;
//	}
//	
//	/**
//	 * Determines if there are any more playable moves
//	 * 
//	 * @return true if there are still unoccupied cubes
//	 */
//	public static boolean hasMoves(Boolean[][][] gameState) {
//		for (Boolean[][] arr2d : gameState) {
//			for (Boolean[] arr1d : arr2d) {
//				for (Boolean bool : arr1d) {
//					if (bool == null) {
//						return true;
//					}
//				}
//			}
//		}
//
//		return false;
//	}
//	
//	/**
//	 * Evaluates this Board's cubes 3D array to find a winner (if any).
//	 * Highlights whatever cubes caused a player's win.
//	 * 
//	 * @return -1 if O won, 1 if X won, and 0 if there is either a tie or the game is still in progress
//	 */
//	public static int getEvaluation(Boolean[][][] gameState) {
//		int gridSize = gameState.length;
//		
//		for (Boolean[][][] dimension : getPlaneSlices(gameState)) {
//			for (Boolean[][] plane : dimension) {
//				int eval = getEvaluation(plane);
//				if (eval != 0)
//					return eval;
//			}
//		}
//
//		// Evaluate the opposite corner to corner diagonals (EX: front-top-left corner to back-bottom-right)
//		for (int check = 0; check < 4; check++) {
//			checkDiagonals:
//			for (int i = 0; i < gridSize; i++) {
//				Boolean state = gameState[(check == 1 ? gridSize - 1 : 0)][(check == 2 ? gridSize - 1 : 0)][(check == 3 ? gridSize - 1 : 0)];
//				if (state!= null) {
//					for (int j = 1; j < gridSize; j++) {
//						if (state != gameState[(check == 1 ? (gridSize - 1) - j : j)][(check == 2 ? (gridSize - 1) - j : j)][(check == 3 ? (gridSize - 1) - j : j)]) {
//							continue checkDiagonals;
//						}
//					}
//					
//					return (state ? 1 : -1); 
//				}
//			}
//		}
//
//		return 0;
//	}
//	
//	/**
//	 * Evaluates a 2D tictactoe grid and returns an integer representing who won (in any).
//	 * Also highlights which cubes are responsible for that players win.
//	 * 
//	 * @param grid the grid to analize
//	 * @return returns -1 if O wins, 1 if X wins, 0 if there is a tie or the game is still in process
//	 */
//	public static int getEvaluation(Boolean[][] grid) {
//		int gridSize = grid.length;
//		
//		for (int check = 0; check < 2; check++) {
//			checkLines:
//			for (int i = 0; i < gridSize; i++) {
//				Boolean value = grid[(check == 0 ? i : 0)][(check == 0 ? 0 : i)];
//				if (value != null) {
//					for (int j = 1; j < gridSize; j++) {
//						if (value != grid[(check == 0 ? i : j)][(check == 0 ? j : i)]) {
//							continue checkLines;
//						}
//					}
//					
//					return (value ? 1 : -1); // Someone won!
//				}
//			}
//			
//			checkDiagonals:
//			for (int i = 0; i < gridSize; i++) {
//				Boolean value = grid[0][(check == 0 ? 0 : gridSize - 1)];
//				if (value != null) {
//					for (int j = 1; j < gridSize; j++) { // Checked j=0 to j=1
//						if (value != grid[j][check == 0 ? j : (gridSize - 1) - j]) {
//							continue checkDiagonals;
//						}
//					}
//					
//					return (value ? 1 : -1); // Someone won!
//				}
//			}
//		}
//
//		return 0; // No one won!
//	}
//	
//	private static Boolean[][][][] getPlaneSlices(Boolean[][][] gameState) {
//		int gridSize = gameState.length;
//		
//		Boolean[][][][] planeSlices = new Boolean[3][gridSize][gridSize][gridSize];
//		
//		for (int dim = 0; dim < 3; dim++) {
//			for (int i = 0; i < gridSize; i++) {
//				Boolean[][] grid = new Boolean[gridSize][gridSize];
//				
//				for (int j = 0; j < gridSize; j++) {
//					for (int k = 0; k < gridSize; k++) {
//						if (dim == 0) {
//							grid[j][k] = gameState[i][j][k];
//						} else if (dim == 1) {
//							grid[j][k] = gameState[j][i][k];
//						} else if (dim == 2) {
//							grid[j][k] = gameState[k][j][i];
//						}
//					}
//				}
//				
//				planeSlices[dim][i] = grid;
//			}
//		}
//		
//		return planeSlices;
//	}
//	
//	private static int getWeightedEval(Boolean[][][] gameState) {
//		int score = getEvaluation(gameState);
//		if (score != 0) {
//			return (score > 0 ? Integer.MAX_VALUE : Integer.MIN_VALUE);
//		} else if (!hasMoves(gameState)) {
//			return 0;
//		}
//		
//		int gridSize = gameState.length;
//		
//		final int notNull = 1; // Favors positions with more pieces you control.
//		final int notOnEdges = 5; // Favors pieces placed in the center of the 3D cube.
//		final int onCorner = 2; // Favors pieces in the corners
//		final int twoInLineMultiplier = 2; // Favors positions where a side has a lot of set up lines
//		
//		int blueEval = 0;
//		int redEval = 0;
//		
//		for (Boolean[][][] dimension : getPlaneSlices(gameState)) {
//			for (Boolean[][] plane : dimension) {
//				for (int i = 0; i < plane.length; i++) {
//					int blueCount = 0;
//					int redCount = 0;
//					
//					for (int j = 0; j < plane[i].length; j++) {
//						if (plane[i][j] != null) {
//							if (plane[i][j])
//								blueCount++;
//							else
//								redCount++;
//						}
//					}
//					
//					blueEval += notNull * blueCount;
//					redEval += notNull * redCount;
//				}
//			}
//		}
//		
//		return blueEval - redEval;
//	}
//	
//	private static Move[] getMoves(Boolean[][][] board) {
//		List<Move> movesList = new LinkedList<Move>();
//		
//		// Get all moves
//		for (int x = 0; x < board.length; x++) {
//			for (int y = 0; y < board[x].length; y++) {
//				for (int z = 0; z < board[x][y].length; z++) {
//					if (board[x][y][z] == null) {
//						int eval = 0;
//					
//						// Check cardinals
//						for (int check = 0; check < 3; check++) {
//							Boolean checkSameness = null;
//							for (int i = 0; i < board.length; i++) {
//								Boolean state = board[(check == 0 ? i : x)][(check == 1 ? i : y)][check == 2 ? i : z];
//								if (state != null) {
//									if (checkSameness == null)
//										checkSameness = state;
//									
//									if (state != checkSameness) {
//										eval--; // This line should be slightly less important if this line cannot have a 4 in a row
//									}
//									
//									eval++;
//								}
//							}
//						}
//						
//						// TODO do cube diagonals
//						
//						movesList.add(new Move(get1DIndex(x, y, z), eval));
//					}
//				}
//			}
//		}
//		
//		// Sort moves
//		Collections.sort(movesList, (move1, move2) -> {
//			return move2.eval - move1.eval;
//		});
//		
//		return movesList.toArray(new Move[movesList.size()]);
//	}
//	
//	public static int[] get3DIndexes(int n) {
//		return new int[] { n / 16, (n % 16) / 4, (n % 16) % 4 };
//	}
//	
//	public static int get1DIndex(int layer, int row, int col) {
//		return layer * Board.GRID_SIZE * Board.GRID_SIZE + row * Board.GRID_SIZE + col;
//	}
//	
//	private static class Move {
//		
//		public final int move;
//		public int eval;
//		
//		public Move(int move, int eval) {
//			this.move = move;
//			this.eval = eval;
//		}
//		
//		@Override
//		public String toString() {
//			return String.format("(square: %d. eval: %d)", move, eval);
//		}
//		
//	}
//	
//}

package misha.tictactoe.game;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class AI {
	
	private static final int DEPTH_TO_SEARCH = 4;
	
	private static int iterationCounter = 0;
	
	/**
	 * Will evaluate every possible move using a MiniMax algorithm. This method does have side effect such as altering the board 3D array.
	 * 
	 * @param board the board to evaluate
	 * @param playingAs the player to play as
	 * @return an int representing the cube that this AI wishes to move in. Will return -1 if no moves can be done.
	 */
	public static int getBestMove(Boolean[][][] board, boolean playingAs) {
		Move bestMove = null;
		
		iterationCounter = 0;
		
		if (!hasMoves(board) || AI.DEPTH_TO_SEARCH <= 0) {
			return -1; // There are no cubes to play a move in or DEPTH_TO_SEARCH is 0
		}
		
		Move[] moves = AI.getMoves(board);
		
		System.out.println(Arrays.toString(moves));
		
		for (Move m : moves) {
			int[] boardIndex = get3DIndexes(m.move);
			board[boardIndex[0]][boardIndex[1]][boardIndex[2]] = playingAs;
			int eval = miniMax(board, !playingAs, DEPTH_TO_SEARCH - 1, Integer.MIN_VALUE, Integer.MAX_VALUE);
			board[boardIndex[0]][boardIndex[1]][boardIndex[2]] = null;
			
			m.eval = eval;
			
			if (m.eval == (playingAs ? Integer.MAX_VALUE : Integer.MIN_VALUE)) {
				break;
			}
			
			System.out.println("The current number of iterations: " + iterationCounter);
		}
		
		if (moves.length > 0) {
			List<Move> movesList = Arrays.asList(moves);
			Collections.shuffle(movesList);
			movesList.toArray(moves);
			
			bestMove = moves[0];
			
			for (int i = 1; i < moves.length; i++) {
				if ((playingAs ? moves[i].eval > bestMove.eval : moves[i].eval < bestMove.eval)) {
					bestMove = moves[i];
				}
			}
		}
		
		System.out.println("Finished findind move with " + iterationCounter + " iterations!");
		System.out.println("Best move eval is: " + bestMove.eval);
		
	
		return (bestMove != null ? bestMove.move : -1);
	}
	
	private static int miniMax(Boolean[][][] board, boolean isMaximizer, int depth, int alpha, int beta) {
		iterationCounter++;
		
		if (!hasMoves(board)) {
			return 0;
		} else if (depth <= 0 || getEvaluation(board) != 0) {
			return getWeightedEval(board);
		}
		
		int bestScore = 0;
		
		if (isMaximizer) {
			bestScore = Integer.MIN_VALUE;
			
			for (Move m : AI.getMoves(board)) {
				int[] boardIndex = get3DIndexes(m.move);
				board[boardIndex[0]][boardIndex[1]][boardIndex[2]] = isMaximizer;
				int score = miniMax(board, !isMaximizer, depth - 1, alpha, beta);
				board[boardIndex[0]][boardIndex[1]][boardIndex[2]] = null;
				
				bestScore = Math.max(score, bestScore);
				
				alpha = Math.max(bestScore, alpha);
				if (alpha >= beta) {
					break;
				}
			}
		} else {
			bestScore = Integer.MAX_VALUE;
			
			for (Move m : AI.getMoves(board)) {
				int[] boardIndex = get3DIndexes(m.move);
				board[boardIndex[0]][boardIndex[1]][boardIndex[2]] = isMaximizer;
				int score = miniMax(board, !isMaximizer, depth - 1, alpha, beta);
				board[boardIndex[0]][boardIndex[1]][boardIndex[2]] = null;
				
				bestScore = Math.min(score, bestScore);
				
				beta = Math.min(bestScore, beta);
				if (alpha >= beta) {
					break;
				}
			}
		}
		
		return bestScore;
	}
	
	/**
	 * Determines if there are any more playable moves
	 * 
	 * @return true if there are still unoccupied cubes
	 */
	public static boolean hasMoves(Boolean[][][] gameState) {
		for (Boolean[][] arr2d : gameState) {
			for (Boolean[] arr1d : arr2d) {
				for (Boolean bool : arr1d) {
					if (bool == null) {
						return true;
					}
				}
			}
		}

		return false;
	}
	
	/**
	 * Evaluates this Board's cubes 3D array to find a winner (if any).
	 * Highlights whatever cubes caused a player's win.
	 * 
	 * @return -1 if O won, 1 if X won, and 0 if there is either a tie or the game is still in progress
	 */
	public static int getEvaluation(Boolean[][][] gameState) {
		int gridSize = gameState.length;
		
		for (Boolean[][][] dimension : getPlaneSlices(gameState)) {
			for (Boolean[][] plane : dimension) {
				int eval = getEvaluation(plane);
				if (eval != 0)
					return eval;
			}
		}

		// Evaluate the opposite corner to corner diagonals (EX: front-top-left corner to back-bottom-right)
		for (int check = 0; check < 4; check++) {
			checkDiagonals:
			for (int i = 0; i < gridSize; i++) {
				Boolean state = gameState[(check == 1 ? gridSize - 1 : 0)][(check == 2 ? gridSize - 1 : 0)][(check == 3 ? gridSize - 1 : 0)];
				if (state!= null) {
					for (int j = 1; j < gridSize; j++) {
						if (state != gameState[(check == 1 ? (gridSize - 1) - j : j)][(check == 2 ? (gridSize - 1) - j : j)][(check == 3 ? (gridSize - 1) - j : j)]) {
							continue checkDiagonals;
						}
					}
					
					return (state ? 1 : -1); 
				}
			}
		}

		return 0;
	}
	
	/**
	 * Evaluates a 2D tictactoe grid and returns an integer representing who won (in any).
	 * Also highlights which cubes are responsible for that players win.
	 * 
	 * @param grid the grid to analize
	 * @return returns -1 if O wins, 1 if X wins, 0 if there is a tie or the game is still in process
	 */
	public static int getEvaluation(Boolean[][] grid) {
		int gridSize = grid.length;
		
		for (int check = 0; check < 2; check++) {
			checkLines:
			for (int i = 0; i < gridSize; i++) {
				Boolean value = grid[(check == 0 ? i : 0)][(check == 0 ? 0 : i)];
				if (value != null) {
					for (int j = 1; j < gridSize; j++) {
						if (value != grid[(check == 0 ? i : j)][(check == 0 ? j : i)]) {
							continue checkLines;
						}
					}
					
					return (value ? 1 : -1); // Someone won!
				}
			}
			
			checkDiagonals:
			for (int i = 0; i < gridSize; i++) {
				Boolean value = grid[0][(check == 0 ? 0 : gridSize - 1)];
				if (value != null) {
					for (int j = 1; j < gridSize; j++) { // Checked j=0 to j=1
						if (value != grid[j][check == 0 ? j : (gridSize - 1) - j]) {
							continue checkDiagonals;
						}
					}
					
					return (value ? 1 : -1); // Someone won!
				}
			}
		}

		return 0; // No one won!
	}
	
	private static Boolean[][][][] getPlaneSlices(Boolean[][][] gameState) {
		int gridSize = gameState.length;
		
		Boolean[][][][] planeSlices = new Boolean[3][gridSize][gridSize][gridSize];
		
		for (int dim = 0; dim < 3; dim++) {
			for (int i = 0; i < gridSize; i++) {
				Boolean[][] grid = new Boolean[gridSize][gridSize];
				
				for (int j = 0; j < gridSize; j++) {
					for (int k = 0; k < gridSize; k++) {
						if (dim == 0) {
							grid[j][k] = gameState[i][j][k];
						} else if (dim == 1) {
							grid[j][k] = gameState[j][i][k];
						} else if (dim == 2) {
							grid[j][k] = gameState[k][j][i];
						}
					}
				}
				
				planeSlices[dim][i] = grid;
			}
		}
		
		return planeSlices;
	}
	
	private static int getWeightedEval(Boolean[][][] gameState) {
		int score = getEvaluation(gameState);
		if (score != 0) {
			return (score > 0 ? Integer.MAX_VALUE : Integer.MIN_VALUE);
		} else if (!hasMoves(gameState)) {
			return 0;
		}
		
		int gridSize = gameState.length;
		
		final int notNull = 1; // Favors positions with more pieces you control.
		final int notOnEdges = 25; // Favors pieces placed in the center of the 3D cube.
		final int twoInLine = 50; // Favors positions where a side has a lot of set up lines
		final int fourInPlane = 250;
		final int twoByTwoFourInPlane = 2 * fourInPlane;
		
		int blueEval = 0;
		int redEval = 0;
		
		for (Boolean[][][] dimension : getPlaneSlices(gameState)) {
			for (Boolean[][] plane : dimension) {
				int blueCount = 0;
				int redCount = 0;
				
				for (int i = 0; i < plane.length; i++) {
					int blueLineCount = 0;
					int redLineCount = 0;
					
					for (int j = 0; j < plane[i].length; j++) {
						if (plane[i][j] != null) {
							if (plane[i][j]) {
								blueLineCount++;
								blueCount++;
							} else {
								redLineCount++;
								redCount++;
							}
							
							if (i > 0 && i < plane.length - 1 && j > 0 && j < plane[i].length - 1) {
								if (plane[i][j]) {
									blueEval += notOnEdges;
								} else {
									redEval += notOnEdges;
								}
							}
						}
					}
					
					if (blueLineCount == 2 && redLineCount == 0) {
						blueEval += twoInLine ;
					} else if (redLineCount == 2 && blueLineCount == 0) {
						redEval += twoInLine;
					}
				}
				
				if (blueCount >= 3 && redCount <= 2) {
					blueEval += fourInPlane;
				} else if (redCount >= 3 && blueCount <= 2) {
					redEval += fourInPlane;
				}
				
				if (redCount <= 2 && blueCount >= 4) {
					blueEval += twoByTwoFourInPlane;
				} else if (blueCount <= 2 && redCount >= 4) {
					redEval += twoByTwoFourInPlane;
				}
				
				blueEval += notNull * blueCount;
				redEval += notNull * redCount;
			}
		}
		
		return blueEval - redEval;
	}
	
	private static Move[] getMoves(Boolean[][][] board) {
		List<Move> movesList = new LinkedList<Move>();
		
		// Get all moves
		for (int x = 0; x < board.length; x++) {
			for (int y = 0; y < board[x].length; y++) {
				for (int z = 0; z < board[x][y].length; z++) {
					if (board[x][y][z] == null) {
						int eval = 0;
					
						for (int check = 0; check < 3; check++) {
							Boolean checkSameness = null;
							
							for (int i = 0; i < board.length; i++) {
								// Check cardinals
								Boolean state = board[(check == 0 ? i : x)][(check == 1 ? i : y)][check == 2 ? i : z];
								if (state != null) {
									if (checkSameness == null)
										checkSameness = state;
									
									if (state != checkSameness) {
										eval--; // This line should be slightly less important if this line cannot have a 4 in a row
									}
									
									eval++;
								}
								
								// Check diagonals
								if (check == 0) { // Check x diagonals
									
								} else if (check == 1) { // Check y diagonals
									
								} else if (check == 2) { // Check z diagonals
									
								}
							}
						}
						
						// TODO do cube diagonals
						
						movesList.add(new Move(get1DIndex(x, y, z), eval));
					}
				}
			}
		}
		
		// Sort moves
		Collections.sort(movesList, (move1, move2) -> {
			return move2.eval - move1.eval;
		});
		
		return movesList.toArray(new Move[movesList.size()]);
	}
	
	public static int[] get3DIndexes(int n) {
		return new int[] { 
				n / (Board.GRID_SIZE * Board.GRID_SIZE), 
				(n % (Board.GRID_SIZE * Board.GRID_SIZE)) / Board.GRID_SIZE, 
				(n % (Board.GRID_SIZE * Board.GRID_SIZE)) % Board.GRID_SIZE 
		};
	}
	
	public static int get1DIndex(int layer, int row, int col) {
		return layer * Board.GRID_SIZE * Board.GRID_SIZE + row * Board.GRID_SIZE + col;
	}
	
	private static class Move {
		
		public final int move;
		public int eval;
		
		public Move(int move, int eval) {
			this.move = move;
			this.eval = eval;
		}
		
		@Override
		public String toString() {
			return String.format("(square: %d. eval: %d)", move, eval);
		}
		
	}
	
}
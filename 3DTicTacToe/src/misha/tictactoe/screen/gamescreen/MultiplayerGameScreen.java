package misha.tictactoe.screen.gamescreen;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.LinkedList;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.Font;

import misha.net.Client;
import misha.net.message.Message;
import misha.net.message.MessageType;
import misha.tictactoe.TicTacToe3D;
import misha.tictactoe.screen.ScreenManager;
import misha.graphics.entity.Entity;

public class MultiplayerGameScreen extends GameScreen {
	
	private boolean playAgain;
	private boolean opponentPlayAgain;

	public MultiplayerGameScreen(ScreenManager manager) {
		super(manager);
	}
	
	@Override
	public void draw(Graphics g) {
		super.draw(g);
		
		if (!board.isGameOver()) {
			g.setColor(Color.WHITE);
			g.setFont(new Font("monospaced", Font.PLAIN, 20));
			g.drawString("It is your" + (playingAsX == board.getXToMove() ? " " : " opponent's ") + "turn to move!", 5, TicTacToe3D.height - 35);
		} else {
			String prompt = (playAgain ? "Waiting for opponent" : "Press ENTER to play again");
			String str = prompt + " (" + (playAgain ? (opponentPlayAgain ? 2 : 1) : (opponentPlayAgain ? 1 : 0)) + "/2)";
			
			g.setColor(Color.WHITE);
			g.setFont(new Font("monospaced", Font.PLAIN, 20));
			g.drawString(str, TicTacToe3D.width - g.getFontMetrics().stringWidth(str) - 5, TicTacToe3D.height - 35);
		}
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		Entity cube = getClickedCube(e);
		
		if (playingAsX == board.getXToMove()) {
			for (int i = 0; i < board.getCubes().length; i++) {
				if (board.getCubes()[i] == cube) {
					Client c = screenManager.getClient();
					c.sendMessage(MessageType.MESSAGE, "move " + i, null);
					board.makeMove(cube);
					return;
				}
			}
		}
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		super.keyPressed(e);
		
		if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
			screenManager.switchScreen(ScreenManager.PAUSE_SCREEN);
			PauseScreen pauseScreen = (PauseScreen) screenManager.getCurrentScreen();
			pauseScreen.setReturnTo(ScreenManager.MULTIPLAYER_GAME_SCREEN);
		} else if (e.getKeyCode() == KeyEvent.VK_ENTER) {
			if (board.isGameOver()) {
				playAgain = true;
				screenManager.getClient().sendMessage(MessageType.MESSAGE, "again", null);
				if (playAgain && opponentPlayAgain) {
					playAgain = false;
					opponentPlayAgain = false;
					super.setPlayingAs(!super.playingAsX);
					super.setup();
				}
			}
		}
	}
	
	@Override
	public void onClientMessageRecieved(String msg) {
		Message message = Message.parse(msg);
		
		if (message.type == MessageType.MESSAGE) {
			String[] parsedMsg = message.message.split(" ");
			if (parsedMsg[0].equals("move")) {
				int move = Integer.parseInt(parsedMsg[1]);
				board.makeMove(board.getCubes()[move]);
			} else if (parsedMsg[0].equals("board")) {
				for (int i = 0; i < parsedMsg.length; i += 2) {
					int position = Integer.parseInt(parsedMsg[i]);
					board.makeMove(board.getCubes()[position]);
				}
			} else if (parsedMsg[0].equals("again")) {
				opponentPlayAgain = true;
				if (playAgain && opponentPlayAgain) {
					playAgain = false;
					opponentPlayAgain = false;
					super.setPlayingAs(!super.playingAsX);
					super.setup();
				}
			}
		} else if (message.type == MessageType.SESSION_ERROR) {
			screenManager.switchScreen(ScreenManager.CONNECTION_FAILED_SCREEN);
			super.setup();
		} else if (message.type == MessageType.SESSION_DISCONNECT) {
			screenManager.switchScreen(ScreenManager.CONNECTION_FAILED_SCREEN);
			screenManager.getClient().sendMessage(MessageType.SESSION_LEAVE, "", null);
			super.setup();
		} else if (message.type == MessageType.SESSION_CONNECT) {
			String boardString = "";
			
			LinkedList<Integer> moveList = board.getMoveListCopy();
			for (int i = 0; i < moveList.size(); i++) {
				boardString += (i == 0 ? "" : " ") + moveList.get(i);
			}
			
			Client c = screenManager.getClient();
			c.sendMessage(MessageType.MESSAGE, "board " + boardString, null);
		}
	}

	@Override
	public void onClientDisconnect() {
		screenManager.switchScreen(ScreenManager.CONNECTION_FAILED_SCREEN);
	}

	@Override
	public void onClientConnect() {
		
	}
	
}

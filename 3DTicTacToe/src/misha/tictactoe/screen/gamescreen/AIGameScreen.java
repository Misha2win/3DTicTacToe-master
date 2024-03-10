package misha.tictactoe.screen.gamescreen;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import misha.tictactoe.TicTacToe3D;
import misha.tictactoe.game.AI;
import misha.tictactoe.screen.ScreenManager;

import java.awt.Color;
import java.awt.Font;

public class AIGameScreen extends GameScreen {
	
	private boolean showBotThinkingText;

	public AIGameScreen(ScreenManager manager) {
		super(manager);
	}
	
	@Override
	public void draw(Graphics g) {
		super.draw(g);
		
		if (showBotThinkingText) {
			g.setColor(Color.WHITE);
			g.setFont(new Font("monospaced", Font.PLAIN, 20));
			
			double anim = System.currentTimeMillis() / 1000;
			String str = null;
			if (anim % 4 == 0) {
				str = "Bot is thinking";
			} else if (anim % 4 == 1) {
				str = "Bot is thinking.";
			} else if (anim % 4 == 2) {
				str = "Bot is thinking..";
			} else if (anim % 4 == 3) {
				str = "Bot is thinking...";
			}
			
			if (str != null)
				g.drawString(str, 5, TicTacToe3D.height - 35);
		}
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		super.mouseClicked(e);
		
		if (playingAsX == !board.getXToMove()) {
			showBotThinkingText = true;
			
			new Thread(() -> {
				System.out.println("Calculating bot move!");
				
				int move = AI.getBestMove(board.getGridCopy(), !playingAsX);
				
				for (int i = 0; i < board.getCubes().length; i++) {
					if (i == move) {
						board.makeMove(board.getCubes()[i]);
						break;
					}
				}
				
				showBotThinkingText = false;
			}).start();
		}
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		super.keyPressed(e);
		
		if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
			screenManager.switchScreen(ScreenManager.PAUSE_SCREEN);
			PauseScreen pauseScreen = (PauseScreen) screenManager.getCurrentScreen();
			pauseScreen.setReturnTo(ScreenManager.AI_GAME_SCREEN);
		}
	}
	
}

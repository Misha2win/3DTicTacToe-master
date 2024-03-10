package misha.tictactoe.screen.gamescreen;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import misha.tictactoe.screen.ScreenManager;

public class LocalGameScreen extends GameScreen {

	public LocalGameScreen(ScreenManager manager) {
		super(manager);
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		super.mouseClicked(e);
		
		playingAsX = !playingAsX;
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		super.keyPressed(e);
		
		if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
			screenManager.switchScreen(ScreenManager.PAUSE_SCREEN);
			PauseScreen pauseScreen = (PauseScreen) screenManager.getCurrentScreen();
			pauseScreen.setReturnTo(ScreenManager.LOCAL_GAME_SCREEN);
		}
	}
	
}

package misha.tictactoe.screen.handler;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import misha.tictactoe.screen.ScreenManager;

public class KeyHandler implements KeyListener {
	
	private ScreenManager screenManager;
	
	public KeyHandler(ScreenManager screenManager) {
		this.screenManager = screenManager;
	}

	@Override
	public void keyPressed(KeyEvent e) {
		screenManager.keyPressed(e);
	}

	@Override
	public void keyReleased(KeyEvent e) {
		screenManager.keyReleased(e);
	}

	@Override
	public void keyTyped(KeyEvent e) {
		screenManager.keyTyped(e);
	}
	
}

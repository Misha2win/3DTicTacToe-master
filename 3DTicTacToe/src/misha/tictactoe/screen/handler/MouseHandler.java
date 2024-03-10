package misha.tictactoe.screen.handler;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

import misha.tictactoe.screen.ScreenManager;

public class MouseHandler implements MouseMotionListener, MouseListener, MouseWheelListener {
	
	private ScreenManager screenManager;
	
	public MouseHandler(ScreenManager screenManager) {
		this.screenManager = screenManager;
	}

	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		screenManager.mouseWheelMoved(e);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		screenManager.mouseClicked(e);
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		screenManager.mouseEntered(e);
	}

	@Override
	public void mouseExited(MouseEvent e) {
		screenManager.mouseExited(e);
	}

	@Override
	public void mousePressed(MouseEvent e) {
		screenManager.mousePressed(e);
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		screenManager.mouseDragged(e);
	}
	
	@Override
	public void mouseReleased(MouseEvent e) {
		screenManager.mouseReleased(e);
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		screenManager.mouseMoved(e);
	}
	
}

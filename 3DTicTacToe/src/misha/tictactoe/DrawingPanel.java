package misha.tictactoe;

import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JPanel;

import misha.tictactoe.screen.ScreenManager;

import java.awt.Color;

public class DrawingPanel extends JPanel {
	
	private ScreenManager screenManager;
	
	public DrawingPanel() {
		setBackground(Color.BLACK);
		
		screenManager = new ScreenManager(this);
		
		super.setFocusable(true);
		super.requestFocus();
		
		startRefresh();
	}
	
	// Begins painting a new frame every 10 milliseconds
	public void startRefresh() {
		Thread b = new Thread(new Runnable() {
			@Override
			public void run() {
				while (true) {
					repaint();
					try { Thread.sleep(10); } catch (Exception ex) {}
				}
			}
		});
		
		b.start();
	}
	
	@Override
	public void paintComponent(Graphics gr) {
		super.paintComponent(gr);
		Graphics2D g = (Graphics2D) gr;
		
		screenManager.getCurrentScreen().draw(g);
	}
	
}

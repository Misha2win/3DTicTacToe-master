package misha.tictactoe;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import javax.swing.JFrame;

import misha.graphics.Graphics3DRenderer;

public class TicTacToe3D extends JFrame implements ComponentListener {
	
	public static final String GAME_NAME = "TicTacToe3D";

	private DrawingPanel renderer;
	
	public static int width = 700, height = 700;

	public TicTacToe3D() {
		super("3D TicTacToe");
		
		renderer = new DrawingPanel();

		Container c = getContentPane();
		c.add(renderer, BorderLayout.CENTER);
		c.setBackground(Color.BLACK);
		
		super.setSize(width, height);
		super.setLocationRelativeTo(null);
		super.setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		super.setAlwaysOnTop(true);
		super.setVisible(true);
		
		super.addComponentListener(this);
		
		Graphics3DRenderer.width = getWidth();
		Graphics3DRenderer.height = getHeight();
	}
	
	@Override
	public void componentResized(ComponentEvent e) {
		if (getWidth() != 0) {
			Graphics3DRenderer.width = getWidth();
			Graphics3DRenderer.height = getHeight();
			
			width = getWidth();
			height = getHeight();
		}
	}

	@Override
	public void componentHidden(ComponentEvent e) {
		// Do nothing
	}

	@Override
	public void componentMoved(ComponentEvent e) {
		// Do nothing
	}

	@Override
	public void componentShown(ComponentEvent e) {
		// Do nothing
	}
	
	public static void main(String[] args) {
		TicTacToe3D w = new TicTacToe3D();
	}
	
}

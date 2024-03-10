package misha.tictactoe.screen;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelListener;
import java.awt.event.KeyListener;
import java.awt.geom.Rectangle2D;
import misha.net.ClientListener;
import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;

public abstract class Screen implements MouseMotionListener, MouseListener, MouseWheelListener, KeyListener, ClientListener {
	
	/**
	 * The ScreenManager this Screen is contained in
	 */
	protected final ScreenManager screenManager;
	
	/**
	 * Constructs a new Screen
	 * 
	 * @param manager the ScrenManager this Screen is contained in
	 * @param surface the PApplet to draw onto
	 */
	public Screen(ScreenManager manager) {
		this.screenManager = manager;
	}
	
	/**
	 * Sets up the buttons of this Screen
	 */
	public abstract void setup();
	
	/**
	 * Draws this Screen
	 */
	public abstract void draw(Graphics g);
	
	/**
	 * Is called when this screen is switched to
	 */
	public void focused() {}
	
	/**
	 * Is called when this screen is switched from
	 */
	public void unfocused() {}
	
	// Utility methods

	protected void drawButton(Graphics g, Rectangle2D rect, Color backgroundColor, Color borderColor, Color textColor, String str, int roundness) {
		if (backgroundColor != null) {
			g.setColor(backgroundColor);
			g.fillRoundRect((int) rect.getX(), (int) rect.getY(), (int) rect.getWidth(), (int) rect.getHeight(), roundness, roundness);
		}
		
		if (borderColor != null) {
			g.setColor(borderColor);
			g.drawRoundRect((int) rect.getX(), (int) rect.getY(), (int) rect.getWidth(), (int) rect.getHeight(), roundness, roundness);
		}
		
		if (textColor != null && str != null) {
			g.setColor(textColor);
			drawTextInBounds(g, (int) rect.getX(), (int) rect.getY(), (int) rect.getWidth(), (int) rect.getHeight(), str);
		}
	}
	
	protected void drawTextInBounds(Graphics g, int x, int y, int w, int h, String text) {
		FontMetrics metrics = g.getFontMetrics();
		g.drawString(text, x + w / 2 - metrics.stringWidth(text) / 2, y + (h / 2 - metrics.getHeight() / 2) + metrics.getAscent());
	}
	
	public void onClientConnectFail() {
		// Ignore
	}
	
}

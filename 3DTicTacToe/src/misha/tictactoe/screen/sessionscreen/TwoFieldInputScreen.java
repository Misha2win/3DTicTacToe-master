package misha.tictactoe.screen.sessionscreen;

import java.awt.event.MouseEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseWheelEvent;
import javax.swing.JTextField;

import misha.tictactoe.TicTacToe3D;
import misha.tictactoe.screen.Screen;
import misha.tictactoe.screen.ScreenManager;

import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Graphics;

public abstract class TwoFieldInputScreen extends Screen {
	
	protected JTextField nameField;
	protected JTextField passwordField;
	
	public TwoFieldInputScreen(ScreenManager manager) {
		super(manager);
		
		nameField = new PlaceHolderJTextField("Session Name");
		passwordField = new PlaceHolderJTextField("Session Password");
		
		Font f = new Font("monospaced", Font.PLAIN, 40);
		nameField.setFont(f);
		passwordField.setFont(f);
	}
	
	public abstract void submitRequest();
	
	public void setSessionNameTextField(String str) {
		nameField.setText(str);
	}
	
	@Override
	public void focused() {
		screenManager.getPanel().add(nameField);
		screenManager.getPanel().add(passwordField);
	}
	
	@Override
	public void unfocused() {
		screenManager.getPanel().remove(nameField);
		screenManager.getPanel().remove(passwordField);
	}

	@Override
	public void setup() {
		int width = 3 * TicTacToe3D.width / 4;
		
		nameField.setBounds(TicTacToe3D.width / 2 - width / 2, (TicTacToe3D.height - 280) / 2 - 10 - 80, width, 80);
		passwordField.setBounds(TicTacToe3D.width / 2 - width / 2, (TicTacToe3D.height - 280) / 2 + 10, width, 80);
	}

	@Override
	public void draw(Graphics g) {
		setup();
		
		g.setFont(new Font("monospaced", Font.PLAIN, 30));
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		// Do nothing
	}
	
	@Override
	public void mousePressed(MouseEvent e) {
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		// Do nothing
	}
	
	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		// Do nothing
	}
	
	@Override
	public void mouseMoved(MouseEvent e) {
		// Do nothing
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// Do nothing
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// Do nothing
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// Do nothing
	}

	@Override
	public void onClientMessageRecieved(String message) {
		// Do nothing since no message should be received
	}

	@Override
	public void onClientDisconnect() {
		// TODO Go to connection failed screen
	}

	@Override
	public void onClientConnect() {
		// Do nothing
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	private class PlaceHolderJTextField extends JTextField implements KeyListener, ActionListener {
		
		private String placeHolder;
		
		public PlaceHolderJTextField(String placeHolder) {
			this.placeHolder = placeHolder;
			
			addActionListener(this);
			addKeyListener(this);
		}
		
		@Override
	    protected void paintComponent(Graphics gr) {
	        super.paintComponent(gr);

	        if (getText().length() > 0) {
	            return;
	        }

	        Graphics2D g = (Graphics2D) gr;
	        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
	        g.setColor(getDisabledTextColor());

	        FontMetrics metrics = g.getFontMetrics();
	        g.drawString(placeHolder, getInsets().left, (getHeight() / 2 - metrics.getHeight() / 2) + metrics.getAscent());
	    }
		
		@Override
		public void keyPressed(KeyEvent e) {
			char c = e.getKeyChar();
			
			if (!e.isActionKey() && c != KeyEvent.VK_BACK_SPACE && c != KeyEvent.VK_ENTER) {
				if (!Character.isDigit(c) && !Character.isAlphabetic(c)) {
					e.consume();
				}
			}
		}

		@Override
		public void keyReleased(KeyEvent e) {
			keyPressed(e);
		}

		@Override
		public void keyTyped(KeyEvent e) {
			keyPressed(e);
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == nameField) {
				if (nameField.getText().length() > 0)
					passwordField.grabFocus();
			} else if (e.getSource() == passwordField) {
				if (nameField.getText().length() > 0 && passwordField.getText().length() > 0)
					submitRequest();
			}
		}
		
		
	}
	
}

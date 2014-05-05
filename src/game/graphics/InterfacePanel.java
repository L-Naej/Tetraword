package game.graphics;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import game.GameBoard;

import javax.swing.JPanel;

public class InterfacePanel extends JPanel {

	public InterfacePanel(GameBoard board){
		this.board = board;
		setSize(new Dimension (300,300));
		setOpaque(false);
	}
	
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		Graphics2D g2d = (Graphics2D) g;
		g2d.setColor(Color.RED);
		g2d.fillRect(this.getX(), this.getY(), this.getWidth(), this.getHeight());
		
	}
	
	private GameBoard board;

}

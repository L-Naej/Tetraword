package game.graphics;

import java.awt.Color;
import java.awt.Graphics;
import game.GameBoard;

import javax.swing.JPanel;

public class InterfacePanel extends JPanel {

	private static final long serialVersionUID = -8239328550859681658L;
	private int score;
	private GameBoard board;
	
	public InterfacePanel(GameBoard board){
		//score
		this.board = board;
		score = (int)(board.getScore());
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.setColor(Color.WHITE);
        g.drawString(String.valueOf(score), 100, 100);
	}
	
}

package game.graphics;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import game.GameBoard;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class InterfacePanel extends JPanel {

	private static final long serialVersionUID = -8239328550859681658L;
	private int score;
	private GameBoard board;
	
	public InterfacePanel(GameBoard board){
		//score
		this.board = board;
		score= (int)(board.getScore());



	     /* String scoreFinal = String.valueOf(score);
	      JLabel labelScore = new JLabel(scoreFinal);	
	      labelScore.setLocation(50, 40);
	      //panel.add(labelScore);
	      labelScore.setLocation(50, 40);
	      labelScore.setForeground(java.awt.Color.white);
	      labelScore.setVisible(true);*/

	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.setColor(Color.WHITE);
        g.drawString(String.valueOf(score), 100, 100);

		
	}
	


}

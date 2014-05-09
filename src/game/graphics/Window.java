package game.graphics;

import game.GameBoard;


import java.awt.Graphics;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


import javax.swing.*;


public class Window extends JFrame{


		private static final long serialVersionUID = 1L;
		private JPanel panel; 
		private BoardPanel boardPanel;

		public Window(GameBoard board){
      
      setLayout(null);
      //new panel
      panel = new JPanel();
      panel.setOpaque(true);
      panel.setBounds(0,20,1024,700);
      
      boardPanel = new BoardPanel(board);
      boardPanel.setBounds(0,0,1024,700);
      boardPanel.setOpaque(false);
      this.add(boardPanel);
         
      //affichage score
      //int score = board.getScore();
      int score = 4000;
      String scoreFinal = String.valueOf(score);
      JLabel labelScore = new JLabel(scoreFinal);	
      labelScore.setLocation(50, 40);
      //panel.add(labelScore);
      labelScore.setLocation(50, 40);
      labelScore.setForeground(java.awt.Color.black);
      labelScore.setVisible(true);
      
       //window
      setTitle("TetraWord"); // title     
      setDefaultCloseOperation(EXIT_ON_CLOSE); // close application
      setLocationRelativeTo(null);
      setLocation(150,20); // place
      setSize(1024,700); // size
          setResizable(false);
          setVisible(true);
		
		}		
			
			@Override
			public void paint(Graphics g) {
        super.paint(g);
			}
			
	
		private static void createAndShowGUI(final GameBoard board, final Window window) {
      
      Timer timer = new Timer(25, new ActionListener() {
        public void actionPerformed(ActionEvent evt) {
            board.doTurn();
            window.repaint();
        }
      });
      timer.setRepeats(true);
      timer.setCoalesce(true);
      timer.start();
      board.insertNewBrick();
		}
		
		
		public static void main(String args[]) throws InterruptedException {
			final GameBoard board = new GameBoard();
			final Window window = new Window(board);
			
      SwingUtilities.invokeLater(new Runnable() {
          public void run() {
              createAndShowGUI(board, window);
          }
      });

		}
		
}


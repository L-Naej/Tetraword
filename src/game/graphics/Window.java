package game.graphics;

import game.GameBoard;
import game.inputs.InputsUserEvent;

import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;




import javax.swing.*;

/**
 * The window of the application.
 * @author L-Naej
 *
 */
public class Window extends JFrame{


		private static final long serialVersionUID = 1L;
		private BoardPanel boardPanel;

		public Window(GameBoard board){
      
		      setLayout(null);
		            
		      boardPanel = new BoardPanel(board);
		      boardPanel.setBounds(0,0,1024,700);
		      this.add(boardPanel);
		      
		      
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
		}


		public static void main(String args[]) throws InterruptedException {
			final GameBoard board = new GameBoard();
			final Window window = new Window(board);
			InputsUserEvent inputs = new InputsUserEvent(board);
			window.addKeyListener(inputs);

			SwingUtilities.invokeLater(new Runnable() {
				public void run() {
					createAndShowGUI(board, window);
				}
			});

		}

}

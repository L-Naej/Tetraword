package game.graphics;

import game.Brick;
import game.BrickType;
import game.GameBoard;
import game.inputs.InputsUserEvent;
import game.BrickFactory;

import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.nio.file.FileSystems;
import java.nio.file.Path;

import javax.swing.*;

public class Window extends JFrame{

		private static final long serialVersionUID = 1L;
		private JPanel panel; 
		private BoardPanel boardPanel;
		private InterfacePanel interfaced;

		public Window(GameBoard board){
			
			//tableau = board.getBoard();

			setLayout(null);
			
			//new panel
	        panel = new JPanel();
	        panel.setOpaque(true);
	        panel.setBounds(0,20,1024,700);

			//background
	        Path backgroundPath = FileSystems.getDefault().getPath("img", "background_test.png");
			//fond = new ImageIcon(backgroundPath.toString()).getImage();
	        //background = new JLabel(image);
	        //panel.add(background);
			
			//affichage score
	        //int score = board.getScore();
	        int score = 4000;
	        String scoreFinal = String.valueOf(score);
	        JLabel labelScore = new JLabel(scoreFinal);	
	        panel.add(labelScore);
	        labelScore.setBounds(0, 0, 223, 90);
	        labelScore.setForeground(java.awt.Color.red);
	        labelScore.setVisible(true);
	        	       
	        //Bricks Path
	        Path bluePath = FileSystems.getDefault().getPath("img/bricks", "blue.jpg");
	        Path cyanPath = FileSystems.getDefault().getPath("img/bricks", "cyan.jpg");
	        Path greenPath = FileSystems.getDefault().getPath("img/bricks", "green.jpg");
	        Path magentaPath = FileSystems.getDefault().getPath("img/bricks", "magenta.jpg");
	        Path orangePath = FileSystems.getDefault().getPath("img/bricks", "orange.jpg");
	        Path redPath = FileSystems.getDefault().getPath("img/bricks", "red.jpg");
	        Path yellowPath = FileSystems.getDefault().getPath("img/bricks", "yellow.jpg");
	        
	        //Briks
			//MagentaBrick = new ImageIcon(magentaPath.toString()).getImage();
			//BlueBrick    = new ImageIcon(bluePath.toString()).getImage();
			//CyanBrick    = new ImageIcon(cyanPath.toString()).getImage();
			//ellowBrick  = new ImageIcon(yellowPath.toString()).getImage();
			//OrangeBrick  = new ImageIcon(orangePath.toString()).getImage();
			//RedBrick     = new ImageIcon(redPath.toString()).getImage();
			//GreenBrick   = new ImageIcon(greenPath.toString()).getImage();
			
	        //window
	        setTitle("TetraWord"); // title     
	        setDefaultCloseOperation(EXIT_ON_CLOSE); // close application
	        setLocationRelativeTo(null);
	        setLocation(150,20); // place
	        setSize(1024,700); // size
	        setResizable(false);
	        setVisible(true);
			add(panel);
			
		}
		
		public void PrintNextBrick (Graphics g, BrickFactory brick) {
			BrickType nextBrickType = brick.getNextBrickType();
			switch (nextBrickType){
				case  I:
					//g.drawImage(CyanBrick, 800, 100, null);
					break;
				case  J:
					//g.drawImage(BlueBrick, 800, 100, null);
					break;
				case  L:  
					//g.drawImage(OrangeBrick, 800, 100, null);
					break;
				case  O:  
					//g.drawImage(YellowBrick, 800, 100, null);
					break;
				case  S:  
					//g.drawImage(GreenBrick, 800, 100, null);
					break;
				case  T:  
					//g.drawImage(MagentaBrick, 800, 100, null);
					break;
				case  Z:  
					//g.drawImage(RedBrick, 800, 100, null);
					break;
			}
		}
/*
		public void BricksPaint(Graphics g, int i, int j, Brick brick ){
			//int x = (int)( 363+30*WIDTHTAB/BRICKSIZE*i/10);
			//int y = (int)( -621+30*HEIGHTTAB/BRICKSIZE*j/20);
			BrickType type = brick.getType(); // on recupe le type de brick
			switch (type){
				case  I:  g.drawImage(CyanBrick, x, -y, null);
				break;
				case  J:  g.drawImage(BlueBrick, x, -y, null);
				break;
				case  L:  g.drawImage(OrangeBrick, x, -y, null);
				break;
				case  O:  g.drawImage(YellowBrick, x, -y, null);
				break;
				case  S:  g.drawImage(GreenBrick, x, -y, null);
				break;
				case  T:  g.drawImage(MagentaBrick, x, -y, null);
				break;
				case  Z:  g.drawImage(RedBrick, x, -y, null);
				break;
			}
			
		}
*/
		/*
		public void GridPaint(Graphics g){
			g.drawImage(fond, 0, 0, null);
			for(int i = 0; i < 10; i++) {
				for(int j = 0; j < 20; j++) {
					if (tableau[i][j]!=null){
						BricksPaint(g, i , j, tableau[i][j]);
						//PrintNextBrick(g, );
					}
									
				}
			}
		}
		*/	
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
			InputsUserEvent inputs = new InputsUserEvent(board);
			window.addKeyListener(inputs);
				
			SwingUtilities.invokeLater(new Runnable() {
				public void run() {
					createAndShowGUI(board, window);
				}
			});

		}
		
}


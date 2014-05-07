package game.graphics;

import game.GameBoard;
import game.inputs.InputsUserEvent;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.nio.file.FileSystems;
import java.nio.file.Path;

import javax.swing.*;


public class Window extends JFrame{
	
	    private static final long serialVersionUID = 1L;
		private JPanel panel; 
		private ImageIcon image;
		private JLabel background;

	
		public Window(GameBoard board){
		 	
			setLayout(null);
			//new panel
	        panel = new JPanel();
	        panel.setOpaque(true);
	        
	        //interfacePanel creations & options
	        interfacePanel = new InterfacePanel(board);
	        interfacePanel.setOpaque(false);
	        this.add(interfacePanel);
	        interfacePanel.setBorder(BorderFactory.createTitledBorder("SCORE" ));
	        
	        //Positionner les pannels
	        interfacePanel.setBounds(690,0,324,300);
	        
	        //boardPanel = new BoardPanel(board);
	        
			//background
	        Path backgroundPath = FileSystems.getDefault().getPath("img", "background.jpg");
			image = new ImageIcon(backgroundPath.toString());
	        background = new JLabel(image);
	        panel.add(background);
	       
	        /*
	        //background test interfacePanel
	        Path backgroundInterfacePanel = FileSystems.getDefault().getPath("img", "backgroundInterfacePanel.png");
			imageInterfacePanel = new ImageIcon(backgroundInterfacePanel.toString());
	        background = new JLabel(imageInterfacePanel);
	        interfacePanel.add(background);
	        */
	        
	        //affichage score
	        int score = board.getScore();
	        String scoreFinal = String.valueOf(score);
	        JLabel labelScore = new JLabel(scoreFinal);	        
	        interfacePanel.add(labelScore);
	        labelScore.setLocation(60, 100);
	        
	        //affichage prochaine brick
	        //getNextBrickType();
	        
	        //window
	        setTitle("TetraWord"); // title     
	        setDefaultCloseOperation(EXIT_ON_CLOSE); // close application
	        setLocationRelativeTo(null);
	        setLocation(150,20); // place
	        setSize(1024,700); // size
	        setResizable(false);
	        setVisible(true);
			add(panel);
			add(interfacePanel);
		}

		@Override
		public void paint(Graphics g){
			super.paint(g);
			Graphics2D g2d = (Graphics2D) g;
		    //caca
			Path BluePath = FileSystems.getDefault().getPath("bricks", "blue.jpg");
			Image img = getToolkit().getImage(BluePath.toString());
			g2d.drawImage(img, 100, 100, null);
		}
		

	
		//test
		public static void main(String args[]) throws InterruptedException {
			GameBoard board = new GameBoard();
			Window window = new Window(board);
			InputsUserEvent inputs = new InputsUserEvent(board);
			window.addKeyListener(inputs);
			do{
				window.repaint();
				Thread.sleep(3000);
			}
			
			while(true);
			// boucle pr parcourir la gameboard
			// rafraichir la fenetre souvent: bouclerepaint 30/s
		}
		
		private InterfacePanel interfacePanel;
		//private BoardPanel boardPanel;
		
}

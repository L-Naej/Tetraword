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
	        interfacePanel = new InterfacePanel(board);
	        this.add(interfacePanel);
	        //Positionner les pannels etc
	        //boardPanel = new BoardPanel(board);
	        
			//background
	    Path backgroundPath = FileSystems.getDefault().getPath("img", "background.jpg");
			image = new ImageIcon(backgroundPath.toString());
	        background = new JLabel(image);
	        panel.add(background);
	        
	        //window
	        setTitle("TetraWord"); // title     
	        setDefaultCloseOperation(EXIT_ON_CLOSE); // close application
	        setLocationRelativeTo(null);
	        setLocation(150,20); // place
	        setSize(1024,700); // size
	        setResizable(true);
	        setVisible(true);
			add(panel);
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

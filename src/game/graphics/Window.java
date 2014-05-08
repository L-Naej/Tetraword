package game.graphics;

import game.Brick;
import game.BrickType;
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
		
		private Image fond;
		private Image MagentaBrick;
		private Image BlueBrick;
		private Image CyanBrick;
		private Image YellowBrick;
		private Image OrangeBrick;
		private Image RedBrick;
		private Image GreenBrick;
		private Brick[][] tableau;


		public Window(GameBoard board){
			
			tableau = board.getBoard();

			setLayout(null);
			//new panel
	        panel = new JPanel();
	        panel.setOpaque(true);
	        panel.setBounds(0,-20,1024,700);


			//background
	        Path backgroundPath = FileSystems.getDefault().getPath("img", "background.jpg");
			fond = new ImageIcon(backgroundPath.toString()).getImage();
	        //background = new JLabel(image);
	        //panel.add(background);
	       
	        //Bricks Path
	        Path bluePath = FileSystems.getDefault().getPath("img/bricks", "blue.jpg");
	        Path cyanPath = FileSystems.getDefault().getPath("img/bricks", "cyan.jpg");
	        Path greenPath = FileSystems.getDefault().getPath("img/bricks", "green.jpg");
	        Path magentaPath = FileSystems.getDefault().getPath("img/bricks", "magenta.jpg");
	        Path orangePath = FileSystems.getDefault().getPath("img/bricks", "orange.jpg");
	        Path redPath = FileSystems.getDefault().getPath("img/bricks", "red.jpg");
	        Path yellowPath = FileSystems.getDefault().getPath("img/bricks", "yellow.jpg");
	        
	        //Briks
			MagentaBrick = new ImageIcon(magentaPath.toString()).getImage();
			BlueBrick    = new ImageIcon(bluePath.toString()).getImage();
			CyanBrick    = new ImageIcon(cyanPath.toString()).getImage();
			YellowBrick  = new ImageIcon(yellowPath.toString()).getImage();
			OrangeBrick  = new ImageIcon(orangePath.toString()).getImage();
			RedBrick     = new ImageIcon(redPath.toString()).getImage();
			GreenBrick   = new ImageIcon(greenPath.toString()).getImage();
	

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
		
		public void BricksPaint(Graphics g, int i, int j, Brick brick ){
			int x = 0;// placement en X
			int y = 0;// placement en Y
			BrickType type = brick.getType(); // on recupe le type de brick
			switch (type){
			case  I:  g.drawImage(CyanBrick, x, y, null);
			break;
			case  J:  g.drawImage(BlueBrick, x, y, null);
			break;
			case  L:  g.drawImage(OrangeBrick, x, y, null);
			break;
			case  O:  g.drawImage(YellowBrick, x, y, null);
			break;
			case  S:  g.drawImage(GreenBrick, x, y, null);
			break;
			case  T:  g.drawImage(MagentaBrick, x, y, null);
			break;
			case  Z:  g.drawImage(RedBrick, x, y, null);
			break;
			}
			
		}



			public void GridPaint(Graphics g){
				for(int i = 0; i < 10; i++) {
					for(int j = 0; j < 20; j++) {
						if (tableau[i][j].getType()!=null){
							BricksPaint(g, i , j, tableau[i][j] );
						}
							
				}
			}
				//test affichage bricks
				g.drawImage(fond, 0, 0, null);
				g.drawImage(MagentaBrick, 363, 111, null);
				g.drawImage(BlueBrick, 393, 111, null);
				g.drawImage(YellowBrick, 423, 111, null);
				g.drawImage(GreenBrick, 453, 111, null);
				g.drawImage(OrangeBrick, 483, 111, null);
				g.drawImage(CyanBrick, 513, 111, null);
				g.drawImage(RedBrick, 543, 111, null);
			}
			
			@Override
			public void paint(Graphics g) {
				super.paint(g);
				GridPaint(g);

			}
			


		//test
		public static void main(String args[]) throws InterruptedException {
			GameBoard board = new GameBoard();
			Window window = new Window(board);
			while(true){
				//window.setDoubleBuffered(true);   a voir pr le scintillement
				window.repaint();
				Thread.sleep(300);
			}
		}



}


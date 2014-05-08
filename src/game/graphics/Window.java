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
		private JLabel background;
		
		private Image fond;
		private Image MagentaBrick;
		private Image BlueBrick;
		private Image CyanBrick;
		private Image YellowBrick;
		private Image OrangeBrick;
		private Image RedBrick;
		private Image GreenBrick;



		public Window(GameBoard board){

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
		
			@Override
			//grid
			//test
			//il faut "paint" les briques ici
			public void paint(Graphics g) {
				super.paint(g);
				g.drawImage(fond, 0, 0, null);
				g.drawImage(MagentaBrick, 100, 100, null);
				g.drawImage(BlueBrick, 130, 100, null);
				g.drawImage(YellowBrick, 160, 100, null);
				g.drawImage(GreenBrick, 190, 100, null);
				g.drawImage(OrangeBrick, 220, 100, null);
				g.drawImage(CyanBrick, 250, 100, null);
				
				/*for(int x = 0; x < 20; x++) {
					for(int y = 0; y < 10; y++) {
					}
				}*/
				
			
			}
			


		//test
		public static void main(String args[]) throws InterruptedException {
			GameBoard board = new GameBoard();
			Window window = new Window(board);
			while(true){
				window.repaint();
				Thread.sleep(300);
			}
		}



}


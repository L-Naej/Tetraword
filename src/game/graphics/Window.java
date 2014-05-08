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
	        panel.setBounds(0,-20,1024,700);


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
	        setResizable(false);
	        setVisible(true);
			add(panel);
			}
		
			@Override
			//grid
			
			//il faut "paint" les briques ici
			public void paint(Graphics g) {
				super.paint(g);
				GameBoard board = new GameBoard();
				
				for(int x = 0; x < 20; x++) {
					for(int y = 0; y < 10; y++) {
					}
				}
			
			}
			
			

		

		


		//test
		public static void main(String args[]) throws InterruptedException {
			GameBoard board = new GameBoard();
			board.getBoard();
			Window window = new Window(board);
		}



}


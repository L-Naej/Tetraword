package game.graphics;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.ImageIcon;


import javax.swing.*;


public class Window extends JFrame{
	
	    private static final long serialVersionUID = 1L;
		private JPanel panel; 
		private ImageIcon image;
		private JLabel background;

	
		public Window(){
		 	

			//new panel
	        panel = new JPanel();
	        panel.setOpaque(true);
			
			//background
			image = new ImageIcon(getClass().getResource("../../img/background.jpg"));
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
		

	
		//test
		public static void main(String args[]) {
			new Window();

		}

}

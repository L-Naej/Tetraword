package game.inputs;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JPanel;

import game.graphics.BoardPanel;

/**
 * 
 * Allows to show the command image
 * when clicking on help button
 * 
 * @author Brice Berthelot
 *
 */

public class ImageMouseListener extends JPanel {

	//listener click
	public static boolean click = false;
	
	Image help = BoardPanel.getHelpImage();

	  // The MouseListener that handles the click
	  public static MouseListener listener = new MouseListener() {
	  	public void mouseClicked(MouseEvent e) {
	       //actions when help clicked
	  		if(e.getClickCount() == 1) {
	  			click = true;
	  	        System.out.println("click true \n Un clic a été détecté sur le bouton help !!!");
	  	    }
	  		if(e.getClickCount() == 2) {
	  			click = false;
	  	        System.out.println("click false \n Un double clic a été détecté sur le bouton continue !!!");
	  	    }
	    }

		@Override
		public void mousePressed(MouseEvent e) {
		}

		@Override
		public void mouseReleased(MouseEvent e) {
		}

		@Override
		public void mouseEntered(MouseEvent e) {
		}

		@Override
		public void mouseExited(MouseEvent e) {			
		}
	  };
	  
	  public static MouseListener getListener(){
	    	return listener;
	  }
	  
	  public static boolean getClick(){
		  return click;
	  }
}
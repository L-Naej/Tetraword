package game.inputs;

import game.UserEventsListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * @author Brice Berthelot & L-Naej
 * 
 * Inputs of the user.
 * Keys have particular actions associated
 *
 */

public class InputsUserEvent implements KeyListener {
	
	private UserEventsListener listener;
	private boolean paused;
	
	public InputsUserEvent(UserEventsListener listener) {
		this.listener = listener;
		paused = false;
	}
	
	/*
	 * Call actions from UserEventsListener acording to
	 * the user input
	 * 
	 * @see java.awt.event.KeyListener#keyPressed(java.awt.event.KeyEvent)
	 */
	
	public void keyPressed(KeyEvent event) {
	    switch (event.getKeyCode()){
	    	case KeyEvent.VK_DOWN:
	    		listener.moveBrickDown();
	    		break;
	    	case KeyEvent.VK_LEFT:
	    		listener.moveBrickLeft();
	    		break;
	    	case KeyEvent.VK_RIGHT:
	    		listener.moveBrickRight();
	    		break;
	    	case KeyEvent.VK_SPACE:
	    		listener.flipBrick();
	     		break;
	     	case KeyEvent.VK_P:
	     	  if (!paused) {
	     	    listener.pause();
	     	    paused = true;
	     	  }
	     	  else listener.play();
	     		break;
	    }
	}

	@Override
	public void keyReleased(KeyEvent e) {
		listener.stopMoveBrick();
	}
	 
	@Override
	public void keyTyped(KeyEvent e) {
	}
	
}

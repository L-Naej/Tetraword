package game.inputs;

import game.UserEventsListener;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * This class maps the actual inputs of the user into
 * game's command. The mapping occurs through an instance of the {@link UserEventsListener}
 * interface.
 * @see UserEventsListener
 * @author L-Naej
 *
 */
public class InputsUserEvent implements KeyListener {
	
  /**
   * Unique constructor of {@link InputsUserEvent}
   * @param listener the listener to notify when the user
   * press a key related to a command.
   */
	public InputsUserEvent(UserEventsListener listener) {
		this.listener = listener;
		paused = false;
	}
	
	/**
	 * Actual mapping occurs here
	 */
	@Override
	public void keyPressed(KeyEvent event) {
	    
	    switch (event.getKeyCode()) {
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
		// TODO Auto-generated method stub
		
	}

	
	private UserEventsListener listener;
	private boolean paused;

}

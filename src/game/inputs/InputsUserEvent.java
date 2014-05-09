package game.inputs;

import game.UserEventsListener;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class InputsUserEvent implements KeyListener {
	
	public InputsUserEvent(UserEventsListener listener) {
		this.listener = listener;
	}
	
	public void keyPressed(KeyEvent event) {
	    
	    switch (event.getKeyCode()) {
	    	case KeyEvent.VK_SPACE:
	    		System.out.println("Barre espace pressée : bouge la brick vers le bas");
	    		listener.moveBrickDown();
	    		break;
	    	case KeyEvent.VK_Q:
	    		listener.moveBrickLeft();
	    		break;
	    	case KeyEvent.VK_D:
	    		listener.moveBrickRight();
	    		break;
	    	
	    	case KeyEvent.VK_F:
	    		listener.flipBrick();
	     		break;
	     		
	     	case KeyEvent.VK_P:
	     		listener.pause();
	     		break;
	    
	    }
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	private UserEventsListener listener;

}

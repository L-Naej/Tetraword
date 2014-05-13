/**
 * 
 */
package game.physics;

import java.util.ArrayList;

/**
 * An IPhysicEventListener reacts to the events
 * of an IPhysicSolver.
 * 
 * @see IPhysicSolver
 * @author L-Naej
 *
 */
public interface IPhysicEventListener {
  
  /**
   * Called when a brick touches the board's ground.
   */
  public void onBrickTouchsGround();
  /**
   * Called when the board is full (a brick just touched the 'ceil')
   */
  public void onBoardFull();
  /**
   * Called when one or more lines have been completed
   * @param lineIndices the list of indices of the lines which have been completed.
   */
  public void onLineCompleted(ArrayList<Integer> lineIndices);
}

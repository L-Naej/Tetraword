/**
 * 
 */
package game.physics;

import game.Brick;
import game.utils.Direction;

/**
 * Interface for the classes which resolve the Tetris physics.
 * This interface allows to easily add new game modes with different
 * physics rules.
 * 
 * @see IPhysicEventListener
 * @author L-Naej
 *
 */
public interface IPhysicSolver {
  
  /**
   * Add a listener which will react to the events generated
   * by this {@link IPhysicSolver}
   * @param listener the class which will listen to the events
   * @see IPhysicEventListener
   */
  public void addPhysicEventListener(IPhysicEventListener listener);
  
  /**
   * The method wich actually resolve the physic.
   * You can call it "in real time" because it takes care
   * of time handling (brick's speed...).
   */
  public void resolvePhysic();
  
  /**
   * Try to flip the current brick on the board.
   * @return true if the flipping succeed.
   */
  public boolean tryToFlipBrick();
  
  /**
   * Try to move the current brick on the board.
   * @param direction the direction in which to move the brick
   */
  public void tryToMove(Direction direction);
  
  /**
   * Insert a new brick on the board.
   * @param newBrick the enw brick to insert
   */
  public void insertNewBrick(Brick newBrick);

  /**
   * Reset the solver.
   * Call it when you start a new game.
   */
  public void resetSolver();
}

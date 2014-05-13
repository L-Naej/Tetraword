/**
 * 
 */
package game;

/**
 * Interface listing all the user's
 * commands when on the game board.
 * A UserEventsListener will take care of handling
 * all the user events available in this interface.
 * @author L-Naej
 *
 */
public interface UserEventsListener {

  /**
   * Ask the listener to move the brick
   * left on the board.
   */
  public void moveBrickLeft();
  /**
   * Ask the listener to move the brick
   * right on the board.
   */
  public void moveBrickRight();
  /**
   * Ask the listener to move the brick
   * down (accelerate it) on the board.
   */
  public void moveBrickDown();
  /**
   * Ask the listener to flip the brick
   * on the board.
   */
  public void flipBrick();
  /**
   * Ask the listener to stop
   * the current move of the brick on the board.
   */
  public void stopMoveBrick();
  /**
   * Ask the listener to pause the
   * game.
   */
  public void pause();
  /**
   * Ask the listener to play
   * the game.
   */
  public void play();
  /**
   * Ask the listener to restart
   * the board.
   */
  public void restart();
}

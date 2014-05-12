/**
 * 
 */
package game;

/**
 * Interface listing all the user's
 * commands when on the game board.
 * @author L-Naej
 *
 */
public interface UserEventsListener {

  public void moveBrickLeft();
  public void moveBrickRight();
  public void moveBrickDown();
  public void flipBrick();
  public void stopMoveBrick();
  
  public void pause();
  public void play();
}

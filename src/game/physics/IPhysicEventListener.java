/**
 * 
 */
package game.physics;

/**
 * @author L-Naej
 *
 */
public interface IPhysicEventListener {
  public void onBrickTouchsGround();
  public void onBoardFull();
  public void onLineCompleted(int lineIndex);
}

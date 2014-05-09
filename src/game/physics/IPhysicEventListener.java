/**
 * 
 */
package game.physics;

import java.util.ArrayList;

/**
 * @author L-Naej
 *
 */
public interface IPhysicEventListener {
  public void onBrickTouchsGround();
  public void onBoardFull();
  public void onLineCompleted(ArrayList<Integer> lineIndices);
}

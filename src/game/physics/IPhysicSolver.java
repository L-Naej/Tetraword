/**
 * 
 */
package game.physics;

import game.Brick;
import game.utils.Direction;

/**
 * @author L-Naej
 *
 */
public interface IPhysicSolver {
  
  public void addPhysicEventListener(IPhysicEventListener listener);
  
  public void resolvePhysic();
  public boolean tryToFlipBrick();
  public void tryToMove(Direction direction);
  public void insertNewBrick(Brick newBrick);
}

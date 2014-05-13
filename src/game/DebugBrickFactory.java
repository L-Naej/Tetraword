/**
 * 
 */
package game;


/**
 * @author L-Naej
 *
 */
public class DebugBrickFactory implements IBrickFactory {

  public DebugBrickFactory() {
  }
  
  @Override
  public Brick createNextBrick() {
    if (id < 2)
      return new Brick(++id, BrickType.I, GameBoard.ENTER_COORDINATES());
    else return new Brick(++id, BrickType.O, GameBoard.ENTER_COORDINATES());
  }


  @Override
  public BrickType getNextBrickType() {
    return BrickType.O;
  }

  private int id = 0;
}

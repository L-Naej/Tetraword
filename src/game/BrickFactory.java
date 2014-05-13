package game;

import java.util.Random;

/**
 * Encapsulate the generation of the brick during the game.
 * @author L-Naej
 * @see IBrickFactory
 *
 */
public class BrickFactory implements IBrickFactory {
  
  /**
   * Build the {@link BrickFactory} and generate the next brick type.
   */
  public BrickFactory() {

    randomizer = new Random(System.currentTimeMillis());
    generateNextBrick();
    currentId = 0;
  }
  
  @Override
  public Brick createNextBrick() {
    Brick nextBrick = new Brick(++currentId, nextBrickType, GameBoard.ENTER_COORDINATES());
    generateNextBrick();
    return nextBrick;
  }
  
  @Override
  public BrickType getNextBrickType() {
    return nextBrickType;
  }
  
  private void generateNextBrick() {
    nextBrickType = types[Math.abs(randomizer.nextInt()) % BRICK_TYPE_COUNT];
  }
  
  ///PRIVATE FIELDS
  private static final BrickType[] types = BrickType.class.getEnumConstants();
  private Random randomizer;
  private BrickType nextBrickType;
  private int currentId;
}

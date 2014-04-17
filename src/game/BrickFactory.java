package game;

import java.util.Random;

public class BrickFactory implements IBrickFactory {

  public static final int BRICK_TYPE_COUNT = BrickType.class.getEnumConstants().length;
  
  public BrickFactory() {

    randomizer = new Random(System.currentTimeMillis());
    generateNextBrick();
    currentId = 0;
  }
  
  public Brick createNextBrick() {
    Brick nextBrick = new Brick(++currentId, nextBrickType, GameBoard.ENTER_COORDINATES());
    generateNextBrick();
    return nextBrick;
  }
  
  public BrickType getNextBrickType() {
    return nextBrickType;
  }
  
  private void generateNextBrick() {
    nextBrickType = types[Math.abs(randomizer.nextInt()) % BRICK_TYPE_COUNT];
  }
  
  ///FIELDS
  private static final BrickType[] types = BrickType.class.getEnumConstants();
  private Random randomizer;
  private BrickType nextBrickType;
  private int currentId;
}

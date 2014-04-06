package game;

import java.util.Random;

import game.utils.Coordinates;

public class BrickFactory {

  public static final int BRICK_TYPE_COUNT = BrickType.class.getEnumConstants().length;
  
  public BrickFactory() {

    randomizer = new Random(System.currentTimeMillis());
    generateNextBrick();
    currentId = 0;
  }
  
  public Brick createNextBrick() {
    Brick nextBrick = new Brick(++currentId, nextBrickType, new Coordinates(GameBoard.BOARD_WIDTH / 2, GameBoard.BOARD_HEIGHT - 1));
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

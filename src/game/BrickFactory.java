package game;

import game.utils.Coordinates;

public class BrickFactory {

  public BrickFactory() {
    nextBrickType = BrickType.I;
  }
  
  public Brick createNextBrick() {
    Brick nextBrick = new Brick(nextBrickType, new Coordinates(GameBoard.BOARD_WIDTH / 2, GameBoard.BOARD_HEIGHT - 1));
    generateNextBrick();
    return nextBrick;
  }
  
  public BrickType getNextBrickType() {
    return nextBrickType;
  }
  
  private void generateNextBrick() {
    nextBrickType = BrickType.I;
  }
  
  private BrickType nextBrickType;
}

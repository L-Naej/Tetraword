package game.utils;

/**
 * Simple class used to store
 * coordinates inside the game board.
 * @author L-Naej
 * @see GameBoard
 */
public class Coordinates {
  public int x;
  public int y;
  
  public Coordinates(Coordinates o) {
    x = o.x;
    y = o.y;
  }
  
  public Coordinates(int x, int y) {
    this.x = x;
    this.y = y;
  }
  
  /**
   * Helper method.
   * @return the coordinates at the right of itself
   */
  public Coordinates right() {
    return new Coordinates(x+1, y);
  }
  
  /**
   * Helper method.
   * @return the coordinates at the left of itself
   */
  public Coordinates left() {
    return new Coordinates(x-1, y);
  }
  
  /**
   * Helper method.
   * @return the coordinates at the top of itself
   */
  public Coordinates top() {
    return new Coordinates(x, y+1);
  }
  
  /**
   * Helper method.
   * @return the coordinates at the bottom of itself
   */
  public Coordinates bottom() {
    return new Coordinates(x, y-1);
  }
  
  @Override
  public Object clone() {
    return new Coordinates(x, y);
  }
}

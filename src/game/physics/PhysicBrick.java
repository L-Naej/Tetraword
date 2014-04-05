package game.physics;


import java.util.ArrayList;

import game.Brick;
import game.utils.Coordinates;

/**
 * Physical component of a Brick.
 * A PhysicalBrick is considered to be inside a block of
 * 4x4 cells. The coordinates of the PhysicalBricks are the
 * coordinates of the upper left cell.
 * 
 * Each subclass is responsible of setting its mask.
 * 
 * @author L-Naej
 *
 */
public abstract class PhysicBrick {
  
  /**
   * y=0 <=> bottom of the mask
   * x=0 <=> left of the mask
   */
  protected class Mask {
    public static final short MASK_WIDTH = 4;
    public static final short MASK_HEIGHT = 4;
    
    public boolean mask[][];
    
    public Mask() {
      mask = new boolean[MASK_WIDTH][MASK_HEIGHT];
      for (short i = 0; i < MASK_WIDTH; ++i)
        for (short j = 0; j < MASK_HEIGHT; ++j)
          mask[i][j] = false;
    }
  }
  
  public static final PhysicBrick createPhysicBrick(Brick brick) {
    PhysicBrick result = null;
    switch (brick.getType())
    {
    case I:
      result = new PhysicBrickI(brick);
      break;
      
    //TODO
      default: 
        assert false;
        break;
    }
    
    return result;
  }
  
  /**
   * Initialize a physical representation of the Brick 
   * brick parameter.
   * @param the brick to represent in the physical world.
   */
  public PhysicBrick(Brick brick){  
    coordinates = brick.getCoordinates();
    currentMaskIndex = 0;
    coordinatesForFallingTest = new ArrayList<>();
  }
  
  /**
   * 
   * @return the current mask of the Brick
   */
  final public Mask getCurrentMask() {
    return masks.get(currentMaskIndex);
  }
  
  /**
   * 
   * @return the mask corresponding to the next flip of the brick.
   * @see Mask
   * @see PhysicBrick
   */
  final public Mask getNextFlip() {
    if (currentMaskIndex == masks.size() - 1)
      return masks.get(0);
    return masks.get(currentMaskIndex + 1);
  }
  
  /**
   * Flip the brick on the board.
   * Warning: the brick does not check if it cans actually
   * flip in the game board. Checking if flipping is possible
   * is the responsibility of the caller.
   */
  final public void flip() {
    if (currentMaskIndex == masks.size() - 1)
      currentMaskIndex = 0;
    else ++currentMaskIndex;
    
    //Recompute physic
    computeCoordinatesForFallingTest();
  }
  
  /**
   * Indicate whether or not the Brick lies on
   * another brick or the ground. In other words, 
   * this method tells if this brick should fall next 
   * turn or not.
   * @param board the board where the brick is evolving
   * @return true if the brick is "in the air". 
   * false if the brick lies on another brick or the ground.
   * 
   */
  /*
  public boolean isFalling(Cell[][] board) {
    for (short i = 0; i < NUM_COMPONENTS; ++i) {
      //Check if there is a brick under this brick, and if it's not itself. OR check if the brick is on the ground
      if ( (board[coordinates[i].x][coordinates[i].y-1].brick != BrickType.NO_BRICK
          && board[coordinates[i].x][coordinates[i].y-1].id != board[coordinates[i].x][coordinates[i].y].id)
          || coordinates[i].y == 0)
        return false;
    }
    
    return true;
  }
  */
  
  /**
   * Coordinates for falling test are the cells of the brick which are
   * not above another cell of the brick itself.
   * In other words, they are the coordinates of the cells which
   * can collide with another brick when the brick is falling.
   * For instance, in a "I" brick in vertical mode, only the lowest
   * cell can collide when the brick falls.
   * @return coordinates in mask used in falling test.
   * @see computeCoordinatesForFallingTest()
   * @see getCoordinatesForLeftCollidingTest()
   * @see getCoordinatesForRightCollidingTest()
   */
  public ArrayList<Coordinates> getCoordinatesForFallingTest() {
    return coordinatesForFallingTest;
  }
  

  /**
   * @see getCoordinatesForFallingTest()
   */
  public final void computeCoordinatesForFallingTest() {
    boolean[][] mask = masks.get(currentMaskIndex).mask;
    coordinatesForFallingTest.clear();
    for (short i = 0; i < Mask.MASK_WIDTH; ++i) {
      short j = Mask.MASK_HEIGHT - 1;
      boolean coordFound = false;
      //Look for the first occupied cell in a column of the mask
      while (j >= 0 && !coordFound) {
        if (mask[i][j]) {
          coordFound = true;
          coordinatesForFallingTest.add(new Coordinates(i, j));
        }
        --j;
      }
    }
  }
  
  /**
   * Make the brick "fall", i.e make
   * its vertical coordinates move down from
   * one step. The Brick does not check if this move
   * will move it outside of the board, it's the responsibility
   * of the caller to make that check before calling fall().
   * @see PhysicSolver
   * @see liesOnSomething()
   */
  final public void fall() {
      --coordinates.y;
  }


  ///FIELDS
  protected Coordinates coordinates;
  protected ArrayList<Coordinates> coordinatesForFallingTest;
  protected ArrayList<Mask> masks;
  protected short currentMaskIndex;
}

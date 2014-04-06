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
    coordinatesForLeftCollidingTest = new ArrayList<>();
    coordinatesForRightCollidingTest = new ArrayList<>();
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
    computeCoordinatesForLeftCollidingTest();
    computeCoordinatesForRightCollidingTest();
  }
 
  
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
  
  public ArrayList<Coordinates> getCoordinatesForLeftCollidingTest() {
    return coordinatesForLeftCollidingTest;
  }
  
  public ArrayList<Coordinates> getCoordinatesForRightCollidingTest() {
    return coordinatesForRightCollidingTest;
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
      //Look for the first occupied cell in a line of the mask
      while (j >= 0 && !coordFound) {
        if (mask[i][j]) {
          coordFound = true;
          coordinatesForFallingTest.add(new Coordinates(i, j));
        }
        --j;
      }
    }
  }
  
  public final void computeCoordinatesForLeftCollidingTest() {
    boolean[][] mask = masks.get(currentMaskIndex).mask;
    coordinatesForLeftCollidingTest.clear();
    for (short i = 0; i < Mask.MASK_HEIGHT; ++i) {
      short j = 0;
      boolean coordFound = false;
      //Look for the first occupied cell in a column of the mask
      while (j < Mask.MASK_WIDTH && !coordFound) {
        if (mask[j][i]) {
          coordFound = true;
          coordinatesForLeftCollidingTest.add(new Coordinates(j, i));
        }
        ++j;
      }
    }
  }
  
  public final void computeCoordinatesForRightCollidingTest() {
    boolean[][] mask = masks.get(currentMaskIndex).mask;
    coordinatesForRightCollidingTest.clear();
    for (short i = 0; i < Mask.MASK_HEIGHT; ++i) {
      short j = Mask.MASK_WIDTH - 1;
      boolean coordFound = false;
      //Look for the first occupied cell in a column of the mask
      while (j >= 0 && !coordFound) {
        if (mask[j][i]) {
          coordFound = true;
          coordinatesForRightCollidingTest.add(new Coordinates(j, i));
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
   */
  final public void fall() {
      --coordinates.y;
  }


  public void moveLeft() {
    --coordinates.x;
  }
  
  public void moveRight() {
    ++coordinates.x;
  }
  
  ///FIELDS
  protected Coordinates coordinates;
  protected ArrayList<Coordinates> coordinatesForFallingTest;
  protected ArrayList<Coordinates> coordinatesForLeftCollidingTest;
  protected ArrayList<Coordinates> coordinatesForRightCollidingTest;
  protected ArrayList<Mask> masks;
  protected short currentMaskIndex;

}

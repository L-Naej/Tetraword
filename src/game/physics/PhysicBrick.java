package game.physics;


import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.StringTokenizer;

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
public class PhysicBrick {
  
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
    PhysicBrick result = new PhysicBrick(brick);

    return result;
  }
  
  /**
   * Initialize a physical representation of the Brick 
   * brick parameter.
   * @param the brick to represent in the physical world.
   */
  protected PhysicBrick(Brick brick){  
    coordinates = brick.getCoordinates();
    currentMaskIndex = 0;
    coordinatesForFallingTest = new ArrayList<>();
    coordinatesForLeftCollidingTest = new ArrayList<>();
    coordinatesForRightCollidingTest = new ArrayList<>();
    
    masks = new ArrayList<>();
    
    Path maskPath = FileSystems.getDefault().getPath("data", "Brick" + brick.getType().toString() +".mask");
    List<String> result = null;
    //TODO work on exception handling in Tetraword
    try {
      result = Files.readAllLines(maskPath, Charset.defaultCharset());
    } catch (IOException e) {
      assert false;
      e.printStackTrace();
    }
   
    ListIterator<String> iterator = result.listIterator();
    
    while (iterator.hasNext()) {
      Mask mask = new Mask();
      if ( !iterator.next().contains(";")) {
        iterator.previous();
      }
      
      for (short i = 0; i < Mask.MASK_HEIGHT; ++i) {
        String line = iterator.next();
        StringTokenizer tokenizer = new StringTokenizer(line, " ");
        for (short j = 0; j < Mask.MASK_WIDTH; ++j) {
          mask.mask[j][i] = tokenizer.nextToken().compareTo("1") == 0 ? true : false;
        }
      }
      masks.add(mask);
      
    }
    
    computeCollidingCoordinates();
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
    computeCollidingCoordinates();
  }
  
  /**
   * Delete (mark 'false') the mask flag corresponding to the
   * piece of the brick which is at the 'boardCoordinates' coordinates.
   * @param boardCoordinates coordinates of the board where the 
   * brick's cell to delete is. 
   * WARNING: Do not check if coordinates are valid.
   */
  public void modifyMask(Coordinates boardCoordinates) {
    Mask currentMask = masks.get(currentMaskIndex);
    
    int maskX = boardCoordinates.x - coordinates.x;
    int maskY = coordinates.y - boardCoordinates.y;
    
    currentMask.mask[maskX][maskY] = false;
    computeCollidingCoordinates();
  }
  
  /**
   * Indicates wether or not the brick has still a physical
   * existence. If all the brick's cells has been destroyed,
   * then the brick is "totally destroyed" and this method returns
   * true.
   * @return true if the brick has no more living cells.
   * else return false
   */
  public boolean isBrickTotallyDestroyed() {
    for (int i = 0; i < Mask.MASK_WIDTH; ++i) {
      for (int j = 0; j < Mask.MASK_HEIGHT; ++j) {
       if ( getCurrentMask().mask[i][j])
         return false;
      }
    }
    
    return true;
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
  
  
  private final void computeCollidingCoordinates() {
    computeCoordinatesForFallingTest();
    computeCoordinatesForLeftCollidingTest();
    computeCoordinatesForRightCollidingTest();
  }
  
  /**
   * @see getCoordinatesForFallingTest()
   */
  private final void computeCoordinatesForFallingTest() {
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
  
  private final void computeCoordinatesForLeftCollidingTest() {
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
  
  private final void computeCoordinatesForRightCollidingTest() {
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
  
  ///FIELDS
  private Coordinates coordinates;
  private ArrayList<Coordinates> coordinatesForFallingTest;
  private ArrayList<Coordinates> coordinatesForLeftCollidingTest;
  private ArrayList<Coordinates> coordinatesForRightCollidingTest;
  private ArrayList<Mask> masks;
  private short currentMaskIndex;

}

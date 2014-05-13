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
public class PhysicBrick implements Comparable<PhysicBrick>{
  
  /**
   * Inner class handling the "mask part" of the
   * physic brick
   * y=0 <=> top of the mask
   * x=0 <=> left of the mask
   */
  protected class Mask {
    public static final short MASK_WIDTH = 4;
    public static final short MASK_HEIGHT = 4;
    
    public boolean mask[][];
    
    public Mask() {
      mask = new boolean[MASK_WIDTH][MASK_HEIGHT];
      cleanMask();
    }
    
    /**
     * Reset the mask
     */
    private void cleanMask() {
      for (short i = 0; i < MASK_WIDTH; ++i)
        for (short j = 0; j < MASK_HEIGHT; ++j)
          mask[i][j] = false;
    }
    
    /**
     * Test if the brick is broken according to this mask.
     * If so, two new masks are created. Theses masks represent
     * the two new bricks which are the result of the split.
     * @param [out parameter] topMask the top part of the splitted mask.
     * @param [out paramter] bottomMask the bottom part of the splitted mask.
     * @return true if the brick is brocken, else return false. If returns false,
     * the out parameters are irrelevant.
     */
    private boolean isBrickBrocken(Mask topMask, Mask bottomMask) {
      boolean flag1 = false, flag2 = false, broken = false;
      int top = 0, bottom = 0;
      for (int i = 0; i < MASK_HEIGHT; ++i) {
        int j = 0;
        while (j < MASK_WIDTH && !mask[j][i]) 
          ++j;
        
        if (j < MASK_WIDTH && mask[j][i]) {
          if(!flag1) {
            flag1 = true;
            top = i;
          }
          else if (flag2) {
            broken = true;
            bottom = i;
          }
          else top = i;
        }
        else if (j >= MASK_WIDTH && flag1) {
          flag2 = true; 
        }
      }
      if (!broken)
        return false;
     
      System.out.println("broken");
      //Copy masks
      bottomMask.cleanMask();
      topMask.cleanMask();
      
      for (int i = 0; i < MASK_WIDTH; ++i) {
        for (int j = 0; j <= top; ++j) {
          topMask.mask[i][j] = mask[i][j];
        }
      }
      
      for (int i = 0; i < MASK_WIDTH; ++i) {
        for (int j = bottom; j < MASK_HEIGHT; ++j) {
          bottomMask.mask[i][j] = mask[i][j];
        }
      }
      
      System.out.println("New top mask");
      for (int i = 0; i < MASK_HEIGHT; ++i) {
        for (int j = 0; j < MASK_WIDTH; ++j) {
          System.out.print(topMask.mask[j][i] ? "1" : "0");
        }
        System.out.println();
      }
      
      
 
      
      System.out.println("New bottom mask");
      for (int i = 0; i < MASK_HEIGHT; ++i) {
        for (int j = 0; j < MASK_WIDTH; ++j) {
          System.out.print(bottomMask.mask[j][i] ? "1" : "0" );
        }
        System.out.println();
      }
      
      
      return true;
    }

  }
  
  /**
   * You can create a {@link PhysicBrick} from the outside only with this
   * static method.
   * @param brick the brick you want to generate a physic component to.
   * @return the PhysicBrick created
   */
  public static final PhysicBrick createPhysicBrick(Brick brick) {
    PhysicBrick result = new PhysicBrick(brick);

    return result;
  }
  
  /**
   * Test if the brick is broken. If so, returns two new bricks.
   * @param newBricks [out param] index0 : top brick, index 1: bottom brick
   * @return true if the brick is broken, else return false.
   */
  public boolean isBroken(ArrayList<PhysicBrick> newBricks) {
    Mask topMask = new Mask();
    Mask bottoMask = new Mask();
    if (getCurrentMask().isBrickBrocken(topMask, bottoMask)) {
      newBricks.add(new PhysicBrick(coordinates, topMask));
      newBricks.add(new PhysicBrick(coordinates, bottoMask));
      return true;
    }
    
    return false;
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
  
  protected PhysicBrick(Coordinates coordinates, Mask mask) {
    masks = new ArrayList<>();
    masks.add(mask);
    this.coordinates = coordinates;
    currentMaskIndex = 0;
    
    coordinatesForFallingTest = new ArrayList<>();
    coordinatesForLeftCollidingTest = new ArrayList<>();
    coordinatesForRightCollidingTest = new ArrayList<>();
    
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

  /**
   * Move the brick left.
   * Warning: does not test if this move is possible,
   * this is the responsibility of the caller to make sure it is.
   * @see tryToMove(Direction direction) for more info
   */
  public void moveLeft() {
    --coordinates.x;
  }
  
  /**
   * Move the brick right
   * Warning: does not test if this move is possible,
   * this is the responsibility of the caller to make sure it is.
   * @see tryToMove(Direction direction) for more info
   */
  public void moveRight() {
    ++coordinates.x;
  }
  
  @Override
  public int compareTo(PhysicBrick o) {
    assert coordinatesForFallingTest.size() > 0;
    assert o.coordinatesForFallingTest.size() > 0;
    return (this.coordinates.y - coordinatesForFallingTest.get(0).y) - (o.coordinates.y - o.coordinatesForFallingTest.get(0).y);
  }
  
  public void setCoordinates(Coordinates newCoordinates) {
    coordinates = newCoordinates;
  }
  
  //______________ PRIVATE METHODS
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

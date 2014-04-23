package game.physics;

import java.util.ArrayList;
import game.Brick;
import game.GameBoard;
import game.physics.PhysicBrick.Mask;
import game.utils.Coordinates;
import game.utils.Direction;

/**
 * PhysicalWorld is the class responsible
 * of the physical update of the board.
 * All processings related to bricks moves
 * and collisions are done in this class.
 * 
 * @author L-Naej
 *
 */
public class PhysicSolver implements IPhysicSolver{
  
  /**
   * The turn duration is the time needed to
   * a brick to fall.
   */
  public static final long TURN_DURATION_MS = 500;
  
  public PhysicSolver(Brick[][] board) {
    this.board = board;
    currentBrick = null;
    bricksList = new ArrayList<>();
    listeners = new ArrayList<>();
    directionAsked = Direction.DOWN;
    flipTheBrick = false;
    brickJustEnteredTheBoard = true;
    lastTime = System.currentTimeMillis();
  }
  
  @Override
  public void insertNewBrick(Brick brick) {
    Coordinates brickCoordinates = brick.getCoordinates();
    Mask mask = brick.getPhysic().getCurrentMask();
    
    //Check if there is enough room to insert a new brick
    for (int i = 0; i < Mask.MASK_HEIGHT && i < GameBoard.BOARD_HEIGHT; ++i) {
      for (int j = 0; j < Mask.MASK_WIDTH && j < GameBoard.BOARD_WIDTH; ++j) {
        if (mask.mask[j][i]
          && board[brickCoordinates.x + j][brickCoordinates.y - i] != null) {
          
          for (IPhysicEventListener listener : listeners)
            listener.onBoardFull();
          
          return;
        }
      }
    }
    currentBrick = brick;
    bricksList.add(brick);
    brickJustEnteredTheBoard = true;
  }
  
  @Override
  public void resolvePhysic() {
    long elapsedTime = System.currentTimeMillis() - lastTime;
    
    Mask mask = currentBrick.getPhysic().getCurrentMask();
    Coordinates brickCoordinates = currentBrick.getCoordinates();
    
    //Clean the last location of the brick
    cleanBrickLocation(mask, brickCoordinates);
    
    if (flipTheBrick) {
      currentBrick.getPhysic().flip();
      //Update mask
      mask = currentBrick.getPhysic().getCurrentMask();
      flipTheBrick = false;
      cleanBrickLocation(mask, brickCoordinates);
    }
    
    boolean brickTouchedGround = false;
    //See if the brick is falling
    if (brickJustEnteredTheBoard)
    {
      //If the brick just entered the board, we don't want it to fall this turn
      brickJustEnteredTheBoard = false;
    }
    else if (directionAsked == Direction.LEFT && tryToMoveLeft()) {
      
    }
    else if (directionAsked == Direction.RIGHT && tryToMoveRight()) {
      
    }
    else if (elapsedTime >= TURN_DURATION_MS && isBrickFalling(currentBrick)) {
      currentBrick.getPhysic().fall();
      lastTime = System.currentTimeMillis();
    }
    else if (elapsedTime >= TURN_DURATION_MS){
      brickTouchedGround = true;
    }
        

    updateBrickPositionOnBoard(currentBrick);
    
    if (brickTouchedGround) {
      ArrayList<Integer> linesCompleted = checkLinesCompleted(currentBrick);
      for (Integer completedLine : linesCompleted)
        destroyLine(completedLine);
      
      for (IPhysicEventListener listener : listeners) {
        for (Integer completedLine : linesCompleted)
          listener.onLineCompleted(completedLine);
      }
      for (IPhysicEventListener listener : listeners)
        listener.onBrickTouchsGround();
    }

  }
  
  @Override
  public void addPhysicEventListener(IPhysicEventListener listener) {
    listeners.add(listener);
  }
  
  @Override
  public void tryToMove(Direction direction) {
    directionAsked = direction;
  }

  @Override
  public boolean tryToFlipBrick() {
    //Test if the brick can actually flip
    Mask flippedBrick = currentBrick.getPhysic().getNextFlip();
    Coordinates brickCoordinates = currentBrick.getCoordinates();
    for (int i = 0; i < Mask.MASK_WIDTH; ++i) {
      for (int j = 0; j < Mask.MASK_HEIGHT; ++j) {
        Coordinates finalCoordinates = new Coordinates(brickCoordinates.x + i, brickCoordinates.y - j);
        if (! flippedBrick.mask[i][j]) {
          continue;
        }
        
        if ( !isCoordinateInsideBoard(finalCoordinates))
          return false;
        Brick cellToTest = board[brickCoordinates.x + i][brickCoordinates.y - j];
        if (cellToTest != null && cellToTest.getId() != currentBrick.getId()) {
          return false;
        }
      }
    }
    flipTheBrick = true;
    return true;
  }
  
  ///________________PRIVATE METHODS_____________________________
  ///
  private boolean isBrickFalling(Brick brick) {
    ArrayList<Coordinates> coordToTest = brick.getPhysic().getCoordinatesForFallingTest();
    Coordinates brickCoordinates = brick.getCoordinates();
    
    for (Coordinates coord : coordToTest) {
      int yUnderMe = brickCoordinates.y - coord.y - 1;
      Coordinates finalCoordinates = new Coordinates(brickCoordinates.x + coord.x, yUnderMe);
      if (isCoordinateInsideBoard(finalCoordinates) && yUnderMe >= 0 &&  board[finalCoordinates.x][yUnderMe] != null)
        return false;
      else if (yUnderMe < 0)
        return false;
    }
    return true;
  }
  
  private boolean tryToMoveLeft() {
    ArrayList<Coordinates> coordToTest = currentBrick.getPhysic().getCoordinatesForLeftCollidingTest();
    Coordinates brickCoordinates = currentBrick.getCoordinates();
    
    for (Coordinates coord : coordToTest) {
      int xLeftToMe = brickCoordinates.x + coord.x - 1;
      Coordinates finalCoordinates = new Coordinates(xLeftToMe, brickCoordinates.y - coord.y);
      if (isCoordinateInsideBoard(finalCoordinates) && xLeftToMe >= 0 && board[xLeftToMe][brickCoordinates.y - coord.y] != null)
        return false;
      else if (xLeftToMe < 0)
        return false;
    }
    
    currentBrick.getPhysic().moveLeft();
    return true;
  }
  
  private boolean tryToMoveRight() {
    ArrayList<Coordinates> coordToTest = currentBrick.getPhysic().getCoordinatesForRightCollidingTest();
    Coordinates brickCoordinates = currentBrick.getCoordinates();
    
    for (Coordinates coord : coordToTest) {
      int xRightToMe = brickCoordinates.x + coord.x + 1;
      if (xRightToMe < GameBoard.BOARD_WIDTH && board[xRightToMe][brickCoordinates.y - coord.y] != null)
        return false;
      else if (xRightToMe >= GameBoard.BOARD_WIDTH)
        return false;
    }
    
    currentBrick.getPhysic().moveRight();
    return true;
  }
  
  /**
   * Clean the last place of the brick on the board.
   * See it like a glClear in OpenGL.
   */
  private void cleanBrickLocation(Mask mask, Coordinates brickCoordinates) {
    for (int i = 0; i < Mask.MASK_HEIGHT; ++i) {
      for (int j = 0; j < Mask.MASK_WIDTH; ++j) {
        Coordinates finalCoordinates = new Coordinates(brickCoordinates.x + j, brickCoordinates.y - i);
        if (mask.mask[j][i] && isCoordinateInsideBoard(finalCoordinates)) {
          board[brickCoordinates.x + j][brickCoordinates.y - i] = null;
        }
      }
    }
  }
  
  private boolean isCoordinateInsideBoard(Coordinates coordToTest) {
    if ( coordToTest.y >= GameBoard.BOARD_HEIGHT 
    || coordToTest.x < 0 
    || coordToTest.x >= GameBoard.BOARD_WIDTH
    || coordToTest.y < 0)
      return false;
    
    return true;
  }
  
  /**
   * Check if there are lines in the game board which are completed.
   * @param lastBrick the last brick which touched the ground
   * @return an array containing the complete lines indices, may be
   * empty if no line is completed.
   */
  private ArrayList<Integer> checkLinesCompleted(Brick lastBrick) {
    Coordinates coord = lastBrick.getCoordinates();
    ArrayList<Integer> linesCompleted = new ArrayList<>();
    Mask brickMask = lastBrick.getPhysic().getCurrentMask();
    for (int i = 0; i < Mask.MASK_HEIGHT; ++i) {
      for (int j = 0; j < Mask.MASK_WIDTH; ++j) {
        int localX = coord.x + j, localY = coord.y - i;
        Coordinates finalCoordinates = new Coordinates(localX, localY);
        if ( !brickMask.mask[j][i] || ! isCoordinateInsideBoard(finalCoordinates)) 
          continue;
        
        //Check the current line
        int widthIndex = 0;
        while (widthIndex < GameBoard.BOARD_WIDTH && board[widthIndex][localY] != null) {
          ++widthIndex;
        }
        if (widthIndex == GameBoard.BOARD_WIDTH)
          linesCompleted.add(localY);
        break;
      }
    }
    
    return linesCompleted;
  }
  
  /**
   * Destroy on the board the line indicated by lineIndex.
   * @param lineIndex the line to destroy
   */
  private void destroyLine(int lineIndex) {
    
    for (int i = 0; i < GameBoard.BOARD_WIDTH; ++i) {
      Brick brickToModify = board[i][lineIndex];
      //Modify brick mask
      brickToModify.getPhysic().modifyMask(new Coordinates(i, lineIndex));
      
      if (brickToModify.getPhysic().isBrickTotallyDestroyed())
        bricksList.remove(brickToModify);
      
      //Update board
      board[i][lineIndex] = null;
    }
    
    //Do gravity
    for (Brick brick : bricksList) {
      if (isBrickFalling(brick)) {
        cleanBrickLocation(brick.getPhysic().getCurrentMask(), brick.getCoordinates());
        brick.getPhysic().fall();
        updateBrickPositionOnBoard(brick);
      }
    }
  }
  
  private void updateBrickPositionOnBoard(Brick brick) {
    Coordinates brickCoordinates = brick.getCoordinates();
    Mask mask = brick.getPhysic().getCurrentMask();
    for (int i = 0; i < Mask.MASK_HEIGHT; ++i) {
      for (int j = 0; j < Mask.MASK_WIDTH; ++j) {
        Coordinates finalCoordinates = new Coordinates(brickCoordinates.x + j, brickCoordinates.y - i);
        if (mask.mask[j][i] && isCoordinateInsideBoard(finalCoordinates)) {
          board[brickCoordinates.x + j][brickCoordinates.y - i] = currentBrick;
        }
      }
    }
  }
  
  ///FIELDS
  private Brick currentBrick;
  private ArrayList<Brick> bricksList;
  private Brick[][] board;
  private ArrayList<IPhysicEventListener> listeners;
  private Direction directionAsked;
  private boolean flipTheBrick;
  private boolean brickJustEnteredTheBoard;
  private long lastTime;

}

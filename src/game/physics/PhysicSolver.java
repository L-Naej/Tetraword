package game.physics;

import java.util.ArrayList;


import java.util.Collections;

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
 * @see IPhysicSolver
 * 
 * @author L-Naej
 *
 */
public class PhysicSolver implements IPhysicSolver{
 
  /**
   * Time needed to
   * a brick to fall.
   */
  public static final long NORMAL_BRICK_SPEED = 225;
  public static final long ACCELERATED_BRICK_SPEED = 50;
  
  public PhysicSolver(Brick[][] board) {
    this.board = board;
    bricksList = new ArrayList<>();
    listeners = new ArrayList<>();
    
    resetSolver(this.board);

  }
  
  @Override
  public void resetSolver(Brick[][] board) {
    this.board = board; 
    currentBrick = null;
    directionAsked = Direction.NO_DIRECTION;
    flipTheBrick = false;
    brickJustEnteredTheBoard = true;
    lastTime = System.currentTimeMillis();
    brickSpeed = NORMAL_BRICK_SPEED;
    bricksList.clear();
    brickSpeed = NORMAL_BRICK_SPEED;
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

    if (directionAsked == Direction.DOWN) {
      brickSpeed = ACCELERATED_BRICK_SPEED;
    }
    else {
      brickSpeed = NORMAL_BRICK_SPEED;
    }
    
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
    else if (elapsedTime >= brickSpeed && isBrickFalling(currentBrick)) {
      currentBrick.getPhysic().fall();
      lastTime = System.currentTimeMillis();
    }
    else if (elapsedTime >= brickSpeed){
      brickTouchedGround = true;
    }
        

    updateBrickPositionOnBoard(currentBrick);
    
    if (brickTouchedGround) {
      ArrayList<Integer> linesCompleted = checkLinesCompleted(currentBrick);
      Collections.sort(linesCompleted);
      for (Integer completedLine : linesCompleted)
        destroyLine(completedLine);
      //Now that the line is destroyed: update gravity!
      updateGravityOnBoard();
      
      for (IPhysicEventListener listener : listeners) {
          listener.onLineCompleted(linesCompleted);
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
    assert brick.getPhysic() != null;
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
  
  /**
   * Indicate wether or not the coordinates are in the game board
   * @param coordToTest the coordinates to test
   * @return true if the coordinates are in the game board.
   */
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
    System.out.println("------------------------------------------");
    System.out.println("Destroy line " + lineIndex);
    for (int i = 0; i < GameBoard.BOARD_WIDTH; ++i) {
      System.out.println("Destroy column " + i);
      Brick brickToModify = board[i][lineIndex];
      assert brickToModify != null;
      
      //Modify brick mask
      brickToModify.getPhysic().modifyMask(new Coordinates(i, lineIndex));
      
      //If brick is split in two
      ArrayList<PhysicBrick> result = new ArrayList<>();
      System.out.println(("Brick type : " + brickToModify.getType()));
      if (brickToModify.getPhysic().isBroken(result)) {
        Brick topBrick = new Brick(brickToModify.getId(), brickToModify.getType(), new Coordinates(brickToModify.getCoordinates()));
        topBrick.setPhysic(result.get(0));
        Brick bottomBrick = new Brick(brickToModify.getId(), brickToModify.getType(), new Coordinates(brickToModify.getCoordinates()));
        bottomBrick.setPhysic(result.get(1));

        //Replace old brick by new bottom brick
        for(int row = 0; row < lineIndex; ++row) {
          for (int col = 0; col < GameBoard.BOARD_WIDTH; ++col) {
            if (board[col][row]  == brickToModify) {
              board[col][row] = bottomBrick;
              System.out.print("New bottom brick " + bottomBrick + " at (" +col + ", "+ row +")" );
            }
          }
        }
                
        //Replace old brick by new top brick
        for(int row = GameBoard.BOARD_HEIGHT - 1; row > lineIndex; --row) {
          for (int col = 0; col < GameBoard.BOARD_WIDTH; ++col) {
            if (board[col][row] == brickToModify ) {
              board[col][row] = topBrick;
              System.out.print("New top brick " + topBrick + " at (" +col + ", "+ row +")" );
            }
          }
        }
        System.out.println();
        
        bricksList.add(topBrick);
        bricksList.add(bottomBrick);
        bricksList.remove(brickToModify);
      }
      
      else if (brickToModify.getPhysic().isBrickTotallyDestroyed())
        bricksList.remove(brickToModify);
      
      //Update board
      board[i][lineIndex] = null;
    }
  }
  
  /**
   * Update the position of the brick 'brick' on the board.
   * @param brick the brick to update
   */
  private void updateBrickPositionOnBoard(Brick brick) {
    Coordinates brickCoordinates = brick.getCoordinates();
    Mask mask = brick.getPhysic().getCurrentMask();
    for (int i = 0; i < Mask.MASK_HEIGHT; ++i) {
      for (int j = 0; j < Mask.MASK_WIDTH; ++j) {
        Coordinates finalCoordinates = new Coordinates(brickCoordinates.x + j, brickCoordinates.y - i);
        if (mask.mask[j][i] && isCoordinateInsideBoard(finalCoordinates)) {
          board[brickCoordinates.x + j][brickCoordinates.y - i] = brick;
        }
      }
    }
  }
  
  /**
   * After a line has been destroyed, update the gravity of all the bricks
   * on the board if needed.
   */
  private void updateGravityOnBoard() {
  //Do gravity, sort the brick list first by height (important!)
    Collections.sort(bricksList, new Brick.HeightComparator());
    for (Brick brick : bricksList) {
      assert brick != null;
      if (isBrickFalling(brick)) {
        cleanBrickLocation(brick.getPhysic().getCurrentMask(), brick.getCoordinates());
        System.out.println("Brick " + brick.getType() + " ( "+ brick + ") is falling");
        brick.getPhysic().fall();
        updateBrickPositionOnBoard(brick);
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
  /**
   * Time needed to
   * a brick to fall.
   */
  private long brickSpeed;

}

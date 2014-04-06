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
  
  public PhysicSolver(Brick[][] board) {
    this.board = board;
    currentBrick = null;
    listeners = new ArrayList<>();
    directionAsked = Direction.DOWN;
    brickJustEnteredTheBoard = true;
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
    brickJustEnteredTheBoard = true;
  }
  
  @Override
  public void resolvePhysic() {
    Mask mask = currentBrick.getPhysic().getCurrentMask();
    Coordinates brickCoordinates = currentBrick.getCoordinates();
    
    //Clean the last location of the brick
    for (int i = 0; i < Mask.MASK_HEIGHT; ++i) {
      for (int j = 0; j < Mask.MASK_WIDTH; ++j) {
        if (mask.mask[j][i] && brickCoordinates.y - i != GameBoard.BOARD_HEIGHT && brickCoordinates.x + j >= 0 && brickCoordinates.y - i >= 0) {
          board[brickCoordinates.x + j][brickCoordinates.y - i] = null;
        }
      }
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
    else if (isBrickFalling())
      currentBrick.getPhysic().fall();
    else {
      brickTouchedGround = true;
    }
        
    //Update position of the brick
    for (int i = 0; i < Mask.MASK_HEIGHT; ++i) {
      for (int j = 0; j < Mask.MASK_WIDTH; ++j) {
        if (mask.mask[j][i] && brickCoordinates.x + j >= 0 && brickCoordinates.y - i >= 0) {
          board[brickCoordinates.x + j][brickCoordinates.y - i] = currentBrick;
        }
      }
    }
    
    if (brickTouchedGround) {
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
    //TODO
    directionAsked = direction;
  }

  @Override
  public boolean tryToFlipBrick() {
    //Test if the brick can actually flip
    //TODO
    currentBrick.getPhysic().flip();
    return true;
  }
  
  ///PRIVATE METHODS
  private boolean isBrickFalling() {
    ArrayList<Coordinates> coordToTest = currentBrick.getPhysic().getCoordinatesForFallingTest();
    Coordinates brickCoordinates = currentBrick.getCoordinates();
    
    for (Coordinates coord : coordToTest) {
      int yUnderMe = brickCoordinates.y - coord.y - 1;
      if (yUnderMe >= 0 &&  board[brickCoordinates.x + coord.x][yUnderMe] != null)
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
      if (xLeftToMe >= 0 && board[xLeftToMe][brickCoordinates.y - coord.y] != null)
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
  
  ///FIELDS
  private Brick currentBrick;
  private Brick[][] board;
  ArrayList<IPhysicEventListener> listeners;
  Direction directionAsked;
  private boolean brickJustEnteredTheBoard;

}

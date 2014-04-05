package game.physics;

import java.util.ArrayList;


import game.Brick;
import game.GameBoard;
import game.physics.PhysicBrick.Mask;
import game.utils.Coordinates;

/**
 * PhysicalWorld is the class responsible
 * of the physical update of the board.
 * All processings related to bricks moves
 * and collisions are done in this class.
 * 
 * @author L-Naej
 *
 */
public class PhysicSolver {
  
  public PhysicSolver() {
    currentBrick = null;
    turnEnded = false;
  }
  
  public void setNewCurrentBrick(Brick brick) {
    currentBrick = brick;
    turnEnded = false;
  }
  
  public void flipBrick() {
    //Test if the brick can actually flip
    //TODO
    currentBrick.getPhysic().flip();
  }
  
  public void resolvePhysic(Brick[][] board) {
    Mask mask = currentBrick.getPhysic().getCurrentMask();
    Coordinates brickCoordinates = currentBrick.getCoordinates();
    
    //Clean the last location of the brick
    for (int i = 0; i < Mask.MASK_HEIGHT && i < GameBoard.BOARD_HEIGHT; ++i) {
      for (int j = 0; j < Mask.MASK_WIDTH && j < GameBoard.BOARD_WIDTH; ++j) {
        if (mask.mask[j][i] && brickCoordinates.y != GameBoard.BOARD_HEIGHT - 1) {
          board[brickCoordinates.x + j][brickCoordinates.y + 1 - i] = null;
        }
      }
    }
      
    for (int i = 0; i < Mask.MASK_HEIGHT && i < GameBoard.BOARD_HEIGHT; ++i) {
      for (int j = 0; j < Mask.MASK_WIDTH && j < GameBoard.BOARD_WIDTH; ++j) {
        if (mask.mask[j][i]) {
          board[brickCoordinates.x + j][brickCoordinates.y - i] = currentBrick;
        }
      }
    }
    
    if (isBrickFalling(board))
      currentBrick.getPhysic().fall();
    else
      turnEnded = true;
  }
  
  public boolean isTurnEnded() {
    return turnEnded;
  }
  
  ///PRIVATE METHODS
  private boolean isBrickFalling(Brick[][] board) {
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
  
  ///FIELDS
  private Brick currentBrick;
  private boolean turnEnded;
}

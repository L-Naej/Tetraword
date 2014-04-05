package game.physics;

import java.util.ArrayList;

import game.BrickType;
import game.GameBoard;
import game.GameBoard.Cell;
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
  
  public PhysicSolver(GameBoard board) {
    this.board = board;
    currentBrick = null;
    turnEnded = false;
  }
  
  public void setNewCurrentBrick(PhysicBrick brick) {
    currentBrick = brick;
    turnEnded = false;
  }
  
  public void flipBrick() {
    //Test if the brick can actually flip
    //TODO
    currentBrick.flip();
  }
  
  public void resolvePhysic(GameBoard board) {
    //TODO
    Cell[][] currentBoard = board.getBoard();
    Mask mask = currentBrick.getCurrentMask();
    //Clean the last location of the brick
    for (int i = 0; i < Mask.MASK_HEIGHT && i < GameBoard.BOARD_HEIGHT; ++i) {
      for (int j = 0; j < Mask.MASK_WIDTH && j < GameBoard.BOARD_WIDTH; ++j) {
        if (mask.mask[j][i] && currentBrick.coordinates.y != GameBoard.BOARD_HEIGHT - 1) {
          currentBoard[currentBrick.coordinates.x + j][currentBrick.coordinates.y + 1 - i].id = 0;
          currentBoard[currentBrick.coordinates.x + j][currentBrick.coordinates.y + 1 - i].brick = BrickType.NO_BRICK;
        }
      }
    }
      
    for (int i = 0; i < Mask.MASK_HEIGHT && i < GameBoard.BOARD_HEIGHT; ++i) {
      for (int j = 0; j < Mask.MASK_WIDTH && j < GameBoard.BOARD_WIDTH; ++j) {
        if (mask.mask[j][i]) {
          currentBoard[currentBrick.coordinates.x + j][currentBrick.coordinates.y - i].id = 1;
          currentBoard[currentBrick.coordinates.x + j][currentBrick.coordinates.y - i].brick = BrickType.I;
        }
      }
    }
    
    if (isBrickFalling(currentBoard))
      currentBrick.fall();
    else
      turnEnded = true;
  }
  
  public boolean isTurnEnded() {
    return turnEnded;
  }
  
  ///PRIVATE METHODS
  private boolean isBrickFalling(Cell[][] board) {
    ArrayList<Coordinates> coordToTest = currentBrick.getCoordinatesForFallingTest();
    
    for (Coordinates coord : coordToTest) {
      int yUnderMe = currentBrick.coordinates.y - coord.y - 1;
      if (yUnderMe >= 0 &&  board[currentBrick.coordinates.x + coord.x][yUnderMe].brick != BrickType.NO_BRICK)
        return false;
      else if (yUnderMe < 0)
        return false;
    }
    return true;
  }
  
  ///FIELDS
  private GameBoard board;
  private PhysicBrick currentBrick;
  private boolean turnEnded;
}

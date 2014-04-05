package game;

import game.physics.PhysicSolver;

/**
 * @author L-Naej
 * 
 * The generic representation of the board.
 * Contains informations used by physics, gameplay and graphics
 * modules of the game.
 * This is a purely data class and it does no computation.
 *
 */
public class GameBoard implements UserEventsListener {
  
  public static final short BOARD_WIDTH = 10;
  public static final short BOARD_HEIGHT = 22;
  
  public GameBoard() {
    boardRepresentation = new Brick[BOARD_WIDTH][BOARD_HEIGHT];
    physic = new PhysicSolver();
    brickFactory = new BrickFactory();
    currentBrick = null;

    for (short i = 0; i < BOARD_WIDTH; ++i)
      for (short j = 0; j < BOARD_HEIGHT; ++j) {
        boardRepresentation[i][j] = null;
      }
  }
  
  public void insertNewBrick() {
    currentBrick = null;
    currentBrick = brickFactory.createNextBrick();
    //TEST
    if (Math.random() < 0.5)
      currentBrick.flip();
    physic.setNewCurrentBrick(currentBrick);
  }
  
  public void doTurn() {
    
    physic.resolvePhysic(boardRepresentation);
    if (physic.isTurnEnded()) {
      insertNewBrick();
    }
  }
  
  public Brick[][] getBoard() {
    return boardRepresentation;
  }
  
  @Override
  public void moveBrickLeft() {
    // TODO Auto-generated method stub
    
  }

  @Override
  public void moveBrickRight() {
    // TODO Auto-generated method stub
    
  }

  @Override
  public void moveBrickDown() {
    // TODO Auto-generated method stub
    
  }

  @Override
  public void flipBrick() {
    // TODO Auto-generated method stub
    
  }

  @Override
  public void pause() {
    // TODO Auto-generated method stub
    
  }
  
  ///FIELDS
  private Brick[][] boardRepresentation;
  private PhysicSolver physic;
  private BrickFactory brickFactory;
  private Brick currentBrick;

}

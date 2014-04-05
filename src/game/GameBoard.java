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
  
  /**
   * Internal class representing a board's cell.
   * A cell contains an information about
   * what type of brick it contains (can be BrickType.NO_BRICK)
   * and its id. The id has no meaning if the BrickType is NO_BRICK.
   * 
   * @author L-Naej
   *
   */
  public class Cell {
    public BrickType brick;
    public int id;
  }

  public static final short BOARD_WIDTH = 10;
  public static final short BOARD_HEIGHT = 22;
  
  private Cell[][] boardRepresentation;
  private int lastId;
  
  public GameBoard() {
    boardRepresentation = new Cell[BOARD_WIDTH][BOARD_HEIGHT];
    physic = new PhysicSolver(this);
    brickFactory = new BrickFactory();
    currentBrick = null;
    
    lastId = 0;
    for (short i = 0; i < BOARD_WIDTH; ++i)
      for (short j = 0; j < BOARD_HEIGHT; ++j) {
        boardRepresentation[i][j] = new Cell();
        boardRepresentation[i][j].brick = BrickType.NO_BRICK;
        boardRepresentation[i][j].id = lastId;
      }
  }
  
  public void insertNewBrick() {
    currentBrick = null;
    currentBrick = brickFactory.createNextBrick();
    //TEST
    if (Math.random() < 0.5)
      currentBrick.flip();
    physic.setNewCurrentBrick(currentBrick.getPhysic());
  }
  
  public void doTurn() {
    
    physic.resolvePhysic(this);
    if (physic.isTurnEnded()) {
      insertNewBrick();
    }
  }
  
  public Cell[][] getBoard() {
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
  private PhysicSolver physic;
  private BrickFactory brickFactory;
  private Brick currentBrick;

}

package game;

import game.physics.IPhysicEventListener;
import game.physics.IPhysicSolver;
import game.physics.PhysicSolver;
import game.utils.Coordinates;
import game.utils.Direction;

/**
 * @author L-Naej
 * 
 * The generic representation of the board.
 * Contains informations used by physics, gameplay and graphics
 * modules of the game.
 *
 */
public class GameBoard implements UserEventsListener, IPhysicEventListener {
  
  public static final short BOARD_WIDTH = 10;
  public static final short BOARD_HEIGHT = 22;
  public static Coordinates ENTER_COORDINATES() {
    return new Coordinates(GameBoard.BOARD_WIDTH / 2 - 2, GameBoard.BOARD_HEIGHT - 1);
  }
  
  public GameBoard() {
    boardRepresentation = new Brick[BOARD_WIDTH][BOARD_HEIGHT];
    physic = new PhysicSolver(boardRepresentation);
    physic.addPhysicEventListener(this);
    
    //brickFactory = new BrickFactory();
    brickFactory = new DebugBrickFactory();
    currentBrick = null;

    for (short i = 0; i < BOARD_WIDTH; ++i)
      for (short j = 0; j < BOARD_HEIGHT; ++j) {
        boardRepresentation[i][j] = null;
      }
  }
  
  public void insertNewBrick() {
    currentBrick = null;
    currentBrick = brickFactory.createNextBrick();
    physic.insertNewBrick(currentBrick);
    
    //TEST
    physic.tryToFlipBrick();
    ++IA;
    switch (IA) {
    case 1: 
      physic.tryToMove(Direction.RIGHT);
      break;
    case 2:
      physic.tryToMove(Direction.LEFT);
      break;
    default:
        physic.tryToMove(Direction.DOWN);
        IA = 0;
        break;
    }
  }
  
  static int IA = 0;
  public void doTurn() {
    /*
    double rand = Math.random();
    if (rand < 0.2)
      physic.tryToMove(Direction.LEFT);
    else if (rand >= 0.2 && rand < 0.5)
      physic.tryToMove(Direction.RIGHT);
    else
      physic.tryToMove(Direction.DOWN);
     */
    
    physic.resolvePhysic();
  }
  
  public Brick[][] getBoard() {
    return boardRepresentation;
  }
  
  @Override
  public void onBrickTouchsGround() {
    insertNewBrick();
  }

  @Override
  public void onBoardFull() {
    System.out.println("GAME FINISHED");
    System.exit(0);
  }

  @Override
  public void onLineCompleted(int lineIndex) {
    System.out.println("Line " + lineIndex + " completed.");
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
  private IPhysicSolver physic;
  private IBrickFactory brickFactory;
  private Brick currentBrick;

}

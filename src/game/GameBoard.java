package game;

import java.util.ArrayList;

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
 * It's the entry point of the gameplay part of the game.
 * The main method of this class is doTurn() which takes care of
 * running the game. You just have to call it in real time in a loop.
 * 
 * @see UserEventsListener
 * @see IPhysicEventListener
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
    
    brickFactory = new BrickFactory();
    
    restart();

  }
  
  @Override
  public void restart() {
    score = null;
    score = new Score();
    paused = false;
    needNewBrick = true;
    boardFull = false;
    currentBrick = null;
    for (short i = 0; i < BOARD_WIDTH; ++i)
      for (short j = 0; j < BOARD_HEIGHT; ++j) {
        boardRepresentation[i][j] = null;
      }
    
    physic.resetSolver(this.boardRepresentation);
  }
  
  public IBrickFactory getBrickFactory() {
    return brickFactory;
  }
  
  public void insertNewBrick() {
    currentBrick = null;
    currentBrick = brickFactory.createNextBrick();
    physic.insertNewBrick(currentBrick);
  }
  
  public void doTurn() {
    if (paused || boardFull)
      return;
    
    if (needNewBrick) {
      insertNewBrick();
      needNewBrick = false;
    }
    
    physic.resolvePhysic();
  }
  
  public Brick[][] getBoard() {
    return boardRepresentation;
  }
  
  public int getScore() {
    return score.getScore();
  }
  
  public boolean isBoardFull() {
    return boardFull;
  }
  
  @Override
  public void onBrickTouchsGround() {
    needNewBrick = true;
  }

  @Override
  public void onBoardFull() {
    System.out.println("GAME FINISHED");
    System.out.println("Score: " + score.getScore());
    boardFull = true;
  }

  @Override
  public void onLineCompleted(ArrayList<Integer> lineIndices) {
    for (Integer line : lineIndices) {
      System.out.println("Line " + line + " completed.");
      score.lineCompleted();
    }
  }
  
  
  @Override
  public void moveBrickLeft() {
    physic.tryToMove(Direction.LEFT);
  }

  @Override
  public void moveBrickRight() {
    physic.tryToMove(Direction.RIGHT);
  }

  @Override
  public void moveBrickDown() {
    physic.tryToMove(Direction.DOWN);
  }
  
  @Override
  public void stopMoveBrick() {
    physic.tryToMove(Direction.NO_DIRECTION);
  }

  @Override
  public void flipBrick() {
    physic.tryToFlipBrick();
  }

  @Override
  public void pause() {
    paused = true; 
  }
  
  @Override
  public void play() {
	  paused = false;
  }
  
  ///PRIVATE FIELDS
  private Brick[][] boardRepresentation;
  private IPhysicSolver physic;
  private IBrickFactory brickFactory;
  private Brick currentBrick;
  private boolean paused;
  private Score score;
  private boolean needNewBrick;
  private boolean boardFull;

}

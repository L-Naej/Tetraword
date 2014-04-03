package game;

/**
 * @author L-Naej
 * 
 * The generic representation of the board.
 * Contains informations used by physics, gameplay and graphics
 * modules of the game.
 * This is a purely data class and it does no computation.
 *
 */
public class GameBoard {
  
  /**
   * Internal class representing a board's celle.
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
  
  private Cell boardRepresentation[][];
  private int lastId;
  
  public GameBoard() {
    boardRepresentation = new Cell[BOARD_HEIGHT][BOARD_WIDTH];
    lastId = 0;
    for (short i = 0; i < BOARD_HEIGHT; ++i)
      for (short j = 0; j < BOARD_WIDTH; ++j) {
        boardRepresentation[i][j].brick = BrickType.NO_BRICK;
        boardRepresentation[i][j].id = lastId;
      }
  }
  
  public Cell[][] getBoard() {
    return boardRepresentation;
  }
}

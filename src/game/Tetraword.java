package game;

/**
 * Main class of Tetraword game.
 * @author L-Naej
 *
 */
public class Tetraword {

  public static void main(String[] args) {
    Tetraword game = new Tetraword();
    game.generateNewBoard();
    
    game.startGame();

  }
  
  /// PUBLIC INTERFACE
  public Tetraword() {
    
  }
  
  public void generateNewBoard() {
    board = null;
    board = new GameBoard();
    board.insertNewBrick();
  }
  
  public void startGame() {
    System.out.println("Start new game");
    
    while (! gameFinished()) {
     doTurn();
     render();//DEBUG METHOD
     try {
       Thread.sleep(100);
     } catch (InterruptedException e) {
       // TODO Auto-generated catch block
       e.printStackTrace();
     }
    }
    
  }
  
  public void doTurn() {
  
    //analyseInputs();
    board.doTurn();
  }
  
  ///PRIVATE METHODS
  
  private boolean gameFinished() {
    return false;
  }
  
  private void render() {
    Brick currentBoard[][] = board.getBoard();
    for (short i = GameBoard.BOARD_HEIGHT - 1; i >= 0 ; --i) {
      for (short j = 0; j < GameBoard.BOARD_WIDTH; ++j) {
        if (currentBoard[j][i] == null)
          System.out.print("0");
        else {
          int displayNumber = currentBoard[j][i].getId() % 10;
          System.out.print(Integer.toString(displayNumber == 0 ? 1 : displayNumber));
        }
      }
      System.out.println();
    }
    
    System.out.println("----------------------------------------------");
    System.out.println("----------------------------------------------");
  }
  
  ///FIELDS
  private GameBoard board;

}

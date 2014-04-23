/**
 * 
 */
package game;

/**
 * Score class encapsulates all the process relative 
 * to the scoring. It allows to tune easily the scoring system.
 * @author L-Naej
 *
 */
public class Score {

  public Score() {
    score = 0;
    lineCompletedBonus = 10;
  }
  
  /**
   * Call this method whenever a line has been completed.
   * The score is updated consequently.
   */
  public void lineCompleted() {
    score += lineCompletedBonus;
  }
  
  public int getScore() {
    return score;
  }

  public int getLineCompletedBonus() {
    return lineCompletedBonus;
  }
  
  public void setLineCompletedBonus(int lineCompletedBonus) {
    this.lineCompletedBonus = lineCompletedBonus;
  }

  ///PRIVATE FIELDS
  private int score;
  private int lineCompletedBonus;
}

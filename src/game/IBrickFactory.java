/**
 * 
 */
package game;

/**
 * Interface for all the brick factories.
 * @author L-Naej
 *
 */
public interface IBrickFactory {
  /**
   * Number of brick's type.
   */
  public static final int BRICK_TYPE_COUNT = BrickType.class.getEnumConstants().length;
  
  /**
   * Create the actual next brick object based on the next brick type.
   * Actualize the next brick type with a new brick type.
   * @return the brick created.
   */
  public Brick createNextBrick();  
  
  /**
   * @return the next brick's type
   */
  public BrickType getNextBrickType();

}

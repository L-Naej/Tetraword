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
  public static final int BRICK_TYPE_COUNT = BrickType.class.getEnumConstants().length;
  
  public Brick createNextBrick();  
  public BrickType getNextBrickType();

}

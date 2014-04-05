package game;

import game.physics.PhysicBrick;
import game.utils.Coordinates;

/**
 * Game object storing all the components of a Brick
 * (Physic, Graphic, Gameplay).
 * A Brick does not exist before it enters the board.
 * That's why the constructor requires coordinates in addition to
 * the brick type.
 * @author L-Naej
 *
 */
public class Brick {
 
  public Brick(int id, BrickType type, Coordinates coordinates) {
    this.setType(type);
    this.setCoordinates(coordinates);
    this.id = id;
   
    physic = PhysicBrick.createPhysicBrick(this);
  }
  
  public PhysicBrick getPhysic() {
    return physic;
  }
  
  public int getId() {
    return id;
  }
  
  public BrickType getType() {
    return type;
  }

  public void setType(BrickType type) {
    this.type = type;
  }

  /**
   * 
   * @return the upper left coordinate of the brick.
   */
  public Coordinates getCoordinates() {
    return coordinates;
  }

  /**
   * Set the upper left coordinates of the brick.
   * @param coordinates
   */
  public void setCoordinates(Coordinates coordinates) {
    this.coordinates = coordinates;
  }

  
  public String toString() {
    return Integer.toString(id);
  }

  ///FIELDS
  /*
   * A Brick cannot be instanciated with default constructor.
   * See class documentation.
   */
  @SuppressWarnings("unused")
  private Brick() {}
  
  private int id;
  private BrickType type;
  private Coordinates coordinates;
  
  //Components
  private PhysicBrick physic;
  //private BrickRenderer graphics;
  
}

package game;

import java.util.Comparator;

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
public class Brick implements Comparable<Brick>{
 
  /**
   * Height Comparator
   */
  static public class HeightComparator implements Comparator<Brick> {

    @Override
    public int compare(Brick brick1, Brick brick2) {
      return brick1.compareTo(brick2);
    }
    
  }
  public Brick(int id, BrickType type, Coordinates coordinates) {
    this.setType(type);
    this.setCoordinates(coordinates);
    this.id = id;
   
    physic = PhysicBrick.createPhysicBrick(this);
  }
  
  public PhysicBrick getPhysic() {
    return physic;
  }
  
  public void setPhysic(PhysicBrick newPhysic) {
    physic = null;
    physic = newPhysic;
    newPhysic.setCoordinates(coordinates);
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

  /*
  public String toString() {
    return Integer.toString(id);
  }
  */
  
  @Override
  public int compareTo(Brick o) {
    return getPhysic().compareTo(o.getPhysic());
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

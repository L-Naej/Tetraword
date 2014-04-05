package game.physics;

import java.util.ArrayList;

import game.Brick;
import game.utils.Coordinates;

/**
 * Represents the Brick "I" in Tetris game.
 * Brick I Mask 1:
 * 0 I 0 0  
 * 0 I 0 0
 * 0 I 0 0
 * 0 I 0 0
 * 
 * Brick I Mask 2:
 * 0 0 0 0  
 * 0 0 0 0
 * I I I I
 * 0 0 0 0
 * 
 * @author L-Naej
 *
 */
public class PhysicBrickI extends PhysicBrick {
  
  public PhysicBrickI(Brick brick) {
    super(brick);
    masks = new ArrayList<>(2);
    
    Mask mask1 = new Mask();
    mask1.mask[1][0] = true;
    mask1.mask[1][1] = true;
    mask1.mask[1][2] = true;
    mask1.mask[1][3] = true;
    masks.add(mask1);
    
    Mask mask2 = new Mask();
    mask2.mask[3][1] = true;
    mask2.mask[2][1] = true;
    mask2.mask[1][1] = true;
    mask2.mask[0][1] = true;
    masks.add(mask2);
    
    computeCoordinatesForFallingTest();
  }

}

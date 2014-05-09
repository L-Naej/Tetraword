/**
 * 
 */
package game.graphics;

import game.Brick;
import game.BrickType;
import game.GameBoard;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.Font;
import java.nio.file.FileSystems;
import java.nio.file.Path;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

/**
 * @author Florent Fran�ois
 *
 */
public class BoardPanel extends JPanel {
  
  private static final long serialVersionUID = -6673567999209675854L;
  
  private Image fond;
  private Image MagentaBrick;
  private Image BlueBrick;
  private Image CyanBrick;
  private Image YellowBrick;
  private Image OrangeBrick;
  private Image RedBrick;
  private Image GreenBrick;
  private Image	IShape;
  private Image JShape;
  private Image LShape;
  private Image OShape;
  private Image SShape;
  private Image TShape;
  private Image ZShape;
  private GameBoard board;

  //We use BufferedImages to improve performances and avoid flickering
  private BufferedImage backgroundBuffer;
  private BufferedImage brickBuffer;
  
  public final int WIDTHTAB = 300;
  public final int HEIGHTTAB= 600;
  public final int BRICKSIZE= 30;
  
  public BoardPanel(GameBoard board) {
    this.board = board;
    
    //background
    Path backgroundPath = FileSystems.getDefault().getPath("img", "background.jpg");
    fond = new ImageIcon(backgroundPath.toString()).getImage();
    
    //Bricks and Forms Path
    Path bluePath = FileSystems.getDefault().getPath("img/bricks", "blue.jpg");
    Path cyanPath = FileSystems.getDefault().getPath("img/bricks", "cyan.jpg");
    Path greenPath = FileSystems.getDefault().getPath("img/bricks", "green.jpg");
    Path magentaPath = FileSystems.getDefault().getPath("img/bricks", "magenta.jpg");
    Path orangePath = FileSystems.getDefault().getPath("img/bricks", "orange.jpg");
    Path redPath = FileSystems.getDefault().getPath("img/bricks", "red.jpg");
    Path yellowPath = FileSystems.getDefault().getPath("img/bricks", "yellow.jpg");
    Path IPath = FileSystems.getDefault().getPath("img/formes", "I.jpg");
    Path JPath = FileSystems.getDefault().getPath("img/formes", "J.png");
    Path LPath = FileSystems.getDefault().getPath("img/formes", "L.png");
    Path OPath = FileSystems.getDefault().getPath("img/formes", "O.jpg");
    Path SPath = FileSystems.getDefault().getPath("img/formes", "S.png");
    Path TPath = FileSystems.getDefault().getPath("img/formes", "T.png");
    Path ZPath = FileSystems.getDefault().getPath("img/formes", "Z.png");
    
    //Briks
    MagentaBrick = new ImageIcon(magentaPath.toString()).getImage();
    BlueBrick    = new ImageIcon(bluePath.toString()).getImage();
    CyanBrick    = new ImageIcon(cyanPath.toString()).getImage();
    YellowBrick  = new ImageIcon(yellowPath.toString()).getImage();
    OrangeBrick  = new ImageIcon(orangePath.toString()).getImage();
    RedBrick     = new ImageIcon(redPath.toString()).getImage();
    GreenBrick   = new ImageIcon(greenPath.toString()).getImage();
    
    //Next Brick Shape
    IShape = new ImageIcon(IPath.toString()).getImage();
    JShape = new ImageIcon(JPath.toString()).getImage();
    LShape = new ImageIcon(LPath.toString()).getImage();
    OShape = new ImageIcon(OPath.toString()).getImage();
    SShape = new ImageIcon(SPath.toString()).getImage();
    TShape = new ImageIcon(TPath.toString()).getImage();
    ZShape = new ImageIcon(ZPath.toString()).getImage();
  }
  
  public void NextShapePaint(Graphics g){
	  //
	  int x= 812;
	  int y= 75;
	  
	  //We use a BufferedImage to improve performances and avoid flickering
	  Graphics2D brickDrawer = (Graphics2D) brickBuffer.getGraphics();
	  
	  //Tricky trick to clear the buffer, thanks StackOverflow 
	  //http://stackoverflow.com/questions/5672697/java-filling-a-bufferedimage-with-transparent-pixels
	  brickDrawer.setComposite(AlphaComposite.getInstance(AlphaComposite.CLEAR));
	  brickDrawer.fillRect(0,0,brickBuffer.getWidth(),brickBuffer.getHeight());
	  brickDrawer.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER));
	    
	  switch (board.getBrickFactory().getNextBrickType()){
	    case  I:  brickDrawer.drawImage(IShape, x, y, null);
	    break;
	    case  J:  brickDrawer.drawImage(JShape, x, y, null);
	    break;
	    case  L:  brickDrawer.drawImage(LShape, x, y, null);
	    break;
	    case  O:  brickDrawer.drawImage(OShape, x, y, null);
	    break;
	    case  S:  brickDrawer.drawImage(SShape, x, y, null);
	    break;
	    case  T:  brickDrawer.drawImage(TShape, x, y, null);
	    break;
	    case  Z:  brickDrawer.drawImage(ZShape, x, y, null);
	    break;
	  }
	    g.drawImage(brickBuffer, 0, 0, null);
  }
  
  
  public void BricksPaint(Graphics g, int i, int j, Brick brick ){
    int x = (int)( 363+30*WIDTHTAB/BRICKSIZE*i/10);
    int y = (int)( -621+30*HEIGHTTAB/BRICKSIZE*j/20);
    BrickType type = brick.getType(); // on recupe le type de brick
    
    //We use a BufferedImage to improve performances and avoid flickering
    Graphics2D brickDrawer = (Graphics2D) brickBuffer.getGraphics();
    
    //Tricky trick to clear the buffer, thanks StackOverflow 
    //http://stackoverflow.com/questions/5672697/java-filling-a-bufferedimage-with-transparent-pixels
    brickDrawer.setComposite(AlphaComposite.getInstance(AlphaComposite.CLEAR));
    brickDrawer.fillRect(0,0,brickBuffer.getWidth(),brickBuffer.getHeight());
    brickDrawer.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER));
    
    switch (type){
    case  I:  brickDrawer.drawImage(CyanBrick, x, -y, null);
    break;
    case  J:  brickDrawer.drawImage(BlueBrick, x, -y, null);
    break;
    case  L:  brickDrawer.drawImage(OrangeBrick, x, -y, null);
    break;
    case  O:  brickDrawer.drawImage(YellowBrick, x, -y, null);
    break;
    case  S:  brickDrawer.drawImage(GreenBrick, x, -y, null);
    break;
    case  T:  brickDrawer.drawImage(MagentaBrick, x, -y, null);
    break;
    case  Z:  brickDrawer.drawImage(RedBrick, x, -y, null);
    break;
    }
    g.drawImage(brickBuffer, 0, 0, null);
  }

    public void GridPaint(Graphics g){
      for(int i = 0; i < 10; i++) {
        for(int j = 0; j < 20; j++) {
          if (board.getBoard()[i][j]!=null) BricksPaint(g, i , j, board.getBoard()[i][j] );
                
        }
      }
    }
    
    public void ScorePaint(Graphics g){
    	g.setFont(new Font("Arial", Font.BOLD, 30));
    	g.setColor(Color.WHITE);
        g.drawString(String.valueOf(board.getScore()), 810, 500);
    }
    
    
    @Override
    public void paintComponent(Graphics g) {
      super.paintComponent(g);
      
      //Draw background Image
      Graphics backgroundDrawer = backgroundBuffer.getGraphics();
      backgroundDrawer.drawImage(fond, 0, 0, null);
      g.drawImage(backgroundBuffer, 0, 0, null);
     
      //Then draw the bricks on the grid
      GridPaint(g);
      
      //Draw the score
      ScorePaint(g);
      
      //Draw the next Shape
      NextShapePaint(g);
    }
    
    
    @Override
    public void setSize(int width, int height) {
      super.setSize(width, height);
    //We use a BufferedImage to improve performances and avoid flickering
      backgroundBuffer = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
      brickBuffer = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
    }
    
    @Override
    public void setBounds(int x, int y, int width, int height) {
      super.setBounds(x, y, width, height);
    //We use a BufferedImage to improve performances and avoid flickering
      backgroundBuffer = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
      brickBuffer = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
    }

}
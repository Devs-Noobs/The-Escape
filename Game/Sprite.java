import java.awt.Image;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

/**
 * Write a description of class Sprite here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public interface Sprite
{
   public void draw(Graphics2D g2);
   public void setX(int x);
   public int getX();
   public void setY(int y);
   public int getY();
   public int getWidth();
   public int getHeight();
   public void update();
   public Sprite clone();
   public Image getImage();
   public void addPlayer(Player player);
}

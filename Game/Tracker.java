import java.awt.Image;
import java.util.ArrayList;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.Random;
import javax.swing.JPanel;
import java.awt.image.BufferedImage;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.Line2D;



/**
    The CatAnimation class creates a wild cat animation containing
    eight frames. 
*/
public class Tracker implements Sprite {
    
    Animation animation;
    private int x;        // x position of animation
    private int y;        // y position of animation
    private JPanel panel;
    private int width;
    private SoundManager soundManager;
    private int height;
    private Random random;
    private int dx;        // increment to move along x-axis
    private int dy; 
    private Player player;// increment to move along y-axis
    private Bullet bullet;
    private boolean soundPlayed;
    
    private Image trackerImage;

    public Tracker(JPanel p, int xPos, int yPos,Player player,Bullet bullet) {

        animation = new Animation(true);    // loop continuously
        panel = p;
    
        random = new Random();
        soundPlayed = false;
        soundManager = SoundManager.getInstance();

        x = xPos;
        y = yPos;
            dx = 1;
            dy = 1;
      
        this.bullet = bullet;  
        this.player = player;
                   // increment to move along y-axis

        // load images for wild cat animation

        Image animImage1 = ImageManager.loadImage("images/monster_sprite_REAL.png");

        int columns = 4;
        int rows = 3;
        
        int imageWidth = animImage1.getWidth(null) / columns;
        int imageHeight = animImage1.getHeight(null) / rows;

    for (int row = 0; row < rows; row++) {
        for (int col = 0; col < columns; col++) {
            BufferedImage frameImage = new BufferedImage(imageWidth, imageHeight, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g = (Graphics2D) frameImage.getGraphics();
    
            g.drawImage(animImage1, 
                    0, 0, imageWidth, imageHeight,
                    col * imageWidth, row * imageHeight, (col * imageWidth) + imageWidth, (row * imageHeight) + imageHeight, null);
    
            animation.addFrame(frameImage, 100);
        }
    }
    
    }

    


    public void start() {
      // x=5;
       //y=10;
       
       animation.start();
    }
    
    
    
    
    
    public void update() {

        /*if (!animation.isStillActive()) {
            return;
        }*/
        
            animation.update();

        //if (!panel.isVisible ()) return;

     

      if (x > player.getX())
      x = x - dx;
      else
      if (x < player.getX())
       x = x + dx;

      if (y > player.getY())
      y = y - dy;
      else
      if (y < player.getY())
       y = y + dy;
       
       if (Math.abs (x - player.getX()) < 50 && !soundPlayed) {
    soundManager.playSound ("alien_close", true);
        soundPlayed = true;
     }

     if (Math.abs (x - player.getX()) > 80 && soundPlayed) {
    soundManager.stopSound ("alien_close");
        soundPlayed = false;
     }

     if (Math.abs (y - player.getY()) < 50 && !soundPlayed) {
    soundManager.playSound ("alien_close", true);
        soundPlayed = true;
     }

     if (Math.abs (y - player.getY()) > 80 && soundPlayed) {
    soundManager.stopSound ("alien_close");
        soundPlayed = false;
     }
         
    }


    public void draw(Graphics2D g2) {

        if (!animation.isStillActive()) {
            return;
        }

        g2.drawImage(animation.getImage(), x, y, 150, 150, null);
    }
    
     public Rectangle2D.Double getBoundingRectangle() {
        return new Rectangle2D.Double (x, y, width, height);
    }
    public void stop(){
        x=x;
        y=y;
        dx=0;
        dy=0;
    }
     public static boolean collidesWithTrackerAndBullet(Tracker tracker, Bullet bullet) {
        if ((tracker != null) && (bullet != null)){
            Rectangle2D.Double trackerRect = tracker.getBoundingRectangle();
            Rectangle2D.Double bulletRect = bullet.getBoundingRectangle();
          
            return trackerRect.intersects(bulletRect);
        }
        return false;
    }
    
    public void setX(int x){}
    public int getX(){return 10;}
    public void setY(int y){}
    public int getY(){return 10;}
    public int getWidth(){return 10;}
    public int getHeight(){return 10;}
    
    
    public Sprite clone(){
        return new Tracker(panel, x, y, player, bullet);
    }
    
    public Image getImage() {
        return trackerImage;
    }
    
    public void addPlayer(Player player){
        this.player = player;
    }
}

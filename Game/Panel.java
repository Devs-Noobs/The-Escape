import java.util.Random;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import javax.swing.JPanel;
import java.awt.Image;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.awt.image.BufferedImage;

public class Panel implements Sprite {

    private static final int XSIZE = 50; // width of the image
    private static final int YSIZE = 50; // height of the image
    private static final int YPOS = 150; // vertical position of the image

    private JPanel panel; // JPanel on which image will be drawn
    private Dimension dimension;

    private int x;
    private int y;
    private int width, height;

    private Player player;

    private Image spriteImage;
    private Animation active, inactive;

    // Graphics2D g2;

    int time, timeChange; // to control when the image is grayed
    boolean originalImage, grayImage;

    private boolean activated;

    public Panel (JPanel panel, int xPos, int yPos, Player player) {
        this.panel = panel;

        dimension = panel.getSize();
        Random random = new Random();
        x = xPos;
        y = yPos;

        width = 32;
        height = 32;

        this.player = player;

        time = 0;
        timeChange = 1;
        originalImage = true;
        grayImage = false;

        Image img;

        active = new Animation(true);
        for (int i = 0; i < 20; i++) {
            img = ImageManager.loadImage("images/panel/active/" + i + ".png");

            BufferedImage frame = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g = (Graphics2D) frame.getGraphics();
   
            g.drawImage(img,
                  0, 0,
                  null);
   
            active.addFrame(frame, 100);
        }
        active.start();

        inactive = new Animation(false);
        for (int i = 0; i < 20; i++) {
            img = ImageManager.loadImage("images/panel/inactive/" + i + ".png");

            BufferedImage frame = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g = (Graphics2D) frame.getGraphics();
   
            g.drawImage(img,
                  0, 0,
                  null);
   
            inactive.addFrame(frame, 100);
        }
        inactive.start();

        activated = false;
        spriteImage = active.getImage();
    }

    private Panel (Panel panel) {
        this.active = panel.active;
        this.inactive = panel.inactive;
        this.dimension = panel.dimension;
        this.width = panel.width;
        this.height = panel.height;
        this.panel = panel.panel;
        this.player = panel.player;
        this.x = panel.x;
        this.y = panel.y;
        this.time = panel.time;
        this.timeChange = panel.timeChange; // set to 1
        this.originalImage = panel.originalImage;
        this.grayImage = panel.grayImage;
        this.spriteImage = panel.spriteImage;
        this.activated = panel.activated;
    }

    public void draw(Graphics2D g2) {

        g2.drawImage(spriteImage, x, y, XSIZE, YSIZE, null);

    }

    public boolean collidesWithPlayer() {
        Rectangle2D.Double myRect = getBoundingRectangle();
        Rectangle2D.Double playerRect = player.getBoundingRectangle();

        if (myRect.intersects(playerRect)) {
            return true;
        } else
            return false;
    }

    public Rectangle2D.Double getBoundingRectangle() {
        return new Rectangle2D.Double(x, y, XSIZE, YSIZE);
    }

    public void update() {}

    public void activate() {
        activated = true;
    }

    public boolean isActive() {
        return activated;
    }
    
    public int getWidth(){
        return width;
    }
    
    public int getHeight(){
        return height;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public Image getImage() {
        if (activated == false) {
            spriteImage = active.getImage();
            active.update();
        }
        else {
            spriteImage = inactive.getImage();
            inactive.update();
        }
        
        return spriteImage;
    }

    public void addPlayer(Player player){
        this.player = player;
    }

    public Sprite clone() {
        return new Panel(this);
    }
}
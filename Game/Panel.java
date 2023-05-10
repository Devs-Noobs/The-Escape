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

public class Panel {

    private static final int XSIZE = 50; // width of the image
    private static final int YSIZE = 50; // height of the image
    private static final int YPOS = 150; // vertical position of the image

    private JPanel panel; // JPanel on which image will be drawn
    private Dimension dimension;
    private int x;
    private int y;

    private Player player;

    private BufferedImage spriteImage; // image for sprite

    // Graphics2D g2;

    int time, timeChange; // to control when the image is grayed
    boolean originalImage, grayImage;

    private boolean activated;

    public Panel (JPanel panel, Player player) {
        this.panel = panel;
        // Graphics g = window.getGraphics ();
        // g2 = (Graphics2D) g;

        dimension = panel.getSize();
        Random random = new Random();
        x = 4128;
        y = 270;

        this.player = player;

        time = 0; // range is 0 to 10
        timeChange = 1; // set to 1
        originalImage = true;
        grayImage = false;

        spriteImage = ImageManager.loadBufferedImage("images/Heart.png");

        activated = false;
    }

    public void draw(Graphics2D g2) {

        g2.drawImage(spriteImage, x, y, XSIZE, YSIZE, null);

    }

    public boolean collidesWithPlayer() {
        Rectangle2D.Double myRect = getBoundingRectangle();
        Rectangle2D.Double playerRect = player.getBoundingRectangle();

        if (myRect.intersects(playerRect)) {
            System.out.println("Collision with player!");
            return true;
        } else
            return false;
    }

    public Rectangle2D.Double getBoundingRectangle() {
        return new Rectangle2D.Double(x, y, XSIZE, YSIZE);
    }

    public void update() {
    }

    public void activate() {
        activated = true;
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
        return spriteImage;
    }

    public boolean isActive() {
        return activated;
    }
}
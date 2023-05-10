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

public class Door implements Sprite {

    private static final int XSIZE = 50; // width of the image
    private static final int YSIZE = 50; // height of the image
    // private static final int DX = 2; // amount of pixels to move in one update
    private static final int YPOS = 150; // vertical position of the image

    private JPanel panel; // JPanel on which image will be drawn
    private Dimension dimension;
    private int x;
    private int y;
    private int width, height;

    private Player player;

    private BufferedImage spriteImage; // image for sprite

    // Graphics2D g2;

    int time, timeChange; // to control when the image is grayed
    boolean originalImage, grayImage;

    private boolean activated;

    public Door(JPanel panel, int xPos, int yPos, Player player) {
        this.panel = panel;

        dimension = panel.getSize();
        x = xPos;
        y = yPos;

        width = 32;
        height = 64;

        this.player = player;

        time = 0; // range is 0 to 10
        timeChange = 1; // set to 1
        originalImage = true;
        grayImage = false;

        spriteImage = ImageManager.loadBufferedImage("images/Heart.png");

        activated = false;
    }

    private Door (Door door){
        this.dimension = door.dimension;
        this.width = door.width;
        this.height = door.height;
        this.panel = door.panel;
        this.player = door.player;
        this.x = door.x;
        this.y = door.y;
        this.time = door.time;
        this.timeChange = door.timeChange; // set to 1
        this.originalImage = door.originalImage;
        this.grayImage = door.grayImage;
        this.spriteImage = door.spriteImage;
        this.activated = door.activated;
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

    public void update() {
    }

    public boolean isActive() {
        return activated;
    }

    public void activate() {
        activated = true;
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
        return spriteImage;
    }

    public void addPlayer(Player player){
        this.player = player;
    }

    public Sprite clone() {
        return new Door(this);
    }
}
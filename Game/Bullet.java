import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.Line2D;
import javax.swing.JPanel;
import java.util.Random;
import java.util.LinkedList;

public class Bullet extends Thread {
    private JPanel panel;
    private TileMap map;
    private int x;
    private int y;

    private int width;
    private int height;
    private boolean bulletActive;
    private int dx; // increment to move alond x-axis
    private int dy; // increment to move along y-axis

    Ellipse2D.Double bullet;

    private Color backgroundColour;
    private SoundManager soundManager;
    private Tank tank;
    private Player player;
    private BufferedImage bulletImage;

    public Bullet(TileMap t, int xPos, int yPos, LinkedList sprites, Player player) {
        map = t;
        // backgroundColour = panel.getBackground();

        this.tank = tank;
        this.player = player;
        width = 32;
        height = 24;
        x = player.getX() + 30;
        y = player.getY() - 100;
        bulletActive = false;
        soundManager = SoundManager.getInstance();
        bulletImage = ImageManager.loadBufferedImage("images/bullet.png");
        dx = 2;
        dy = 10;

        if (player.direction() == "L") {
            bulletImage = ImageManager.hFlipImage(bulletImage);
            dx = -dx;
        }
    }

    public boolean isActive() {

        return bulletActive;

    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Image getImage() {
        return bulletImage;
    }

    public void update() {
        x = x + dx;
    }

    public void draw(Graphics2D g2) {
        bullet = new Ellipse2D.Double(x, y, width, height);

        g2.setColor(Color.YELLOW);
        g2.fill(bullet);

        g2.setColor(Color.BLACK);
        g2.draw(bullet);

        g2.setColor(Color.BLACK);
    }

    public void erase() {

        Graphics g = panel.getGraphics();
        Graphics2D g2 = (Graphics2D) g;

        g2.setColor(backgroundColour);
        g2.fill(new Rectangle2D.Double(x - 10, y - 10, width + 20, height + 20));

        g.dispose();
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void boom() {
        /*
         * if (panel == null) return;
         * if (!panel.isVisible())
         * return;
         */

        bulletActive = true;
        // this.draw(g2);

        /*
         * int x1 = Math.round(player.getX()) + offsetX;
         * int y1 = Math.round(player.getY()) + offsetY;
         * g2.drawImage(this.getImage(), x1, y1, this.getWidth(), this.getHeight(),
         * null);
         */

        /*
         * for (int i = 0; i < 10000; i++){
         * try
         * {
         * this.draw(g2);
         * Thread.sleep(100);
         * x = x + dx;
         * }
         * catch (InterruptedException ie)
         * {
         * ie.printStackTrace();
         * }
         * }
         */

    }

    /*
     * public boolean collidesWithtank(tank tank) {
     * Rectangle2D.Double myRect = getBoundingRectangle();
     * Rectangle2D.Double tankRect = tank.getBoundingRectangle();
     * 
     * return myRect.intersects(tankRect);
     * }
     */
    public static boolean collidesWithTankAndBullet(Tank tank, Bullet bullet) {
        if ((tank != null) && (bullet != null)) {
            Rectangle2D.Double tankRect = tank.getBoundingRectangle();
            Rectangle2D.Double bulletRect = bullet.getBoundingRectangle();

            return bulletRect.intersects(tankRect);
        }
        return false;
    }

    public boolean isOnBullet(int x, int y) {
        if (bullet == null)
            return false;

        return bullet.contains(x, y);
    }

    public Rectangle2D.Double getBoundingRectangle() {
        return new Rectangle2D.Double(x, y, width, height);
    }
}

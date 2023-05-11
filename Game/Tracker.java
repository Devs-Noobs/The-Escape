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
 * The CatAnimation class creates a wild cat animation containing
 * eight frames.
 */
public class Tracker implements Sprite {
    private static final int SPEED = 1; // amount of pixels to move in one update
    private Animation animation;
    private int x; // x position of animation
    private int y; // y position of animation
    private JPanel panel;
    private int width;
    private SoundManager soundManager;
    private int height;
    private Random random;
    private int dx; // increment to move along x-axis
    private int dy;
    private Player player;// increment to move along y-axis
    // private Bullet bullet;
    private boolean soundPlayed;
    private boolean started;
    private boolean stopped;

   private boolean goingL, goingR;

    private Image trackerImage;
    private Animation moveL, moveR;
    private Animation deathL, deathR;

    private int boundWidth;
    private int boundHeight;

    public Tracker(JPanel p, int xPos, int yPos, Player player) {
        animation = new Animation(true); // loop continuously
        panel = p;

        random = new Random();

        soundPlayed = false;
        soundManager = SoundManager.getInstance();

        x = xPos;
        y = yPos;
        dx = SPEED;
        dy = SPEED;

        width = 48;
        height = 48;

        goingL = false;
        goingR = false;

        this.player = player;

        Image spriteImage;
        int columns;
        int rows;
        int imageWidth;
        int imageHeight;

        Image animImage1 = ImageManager.loadImage("images/monster_sprite_REAL.png");
        columns = 4;
        rows = 3;
        imageWidth = animImage1.getWidth(null) / columns;
        imageHeight = animImage1.getHeight(null) / rows;

        {
            BufferedImage frameImage = new BufferedImage(imageWidth, imageHeight, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g = (Graphics2D) frameImage.getGraphics();

            g.drawImage(animImage1,
                    0, 0, imageWidth, imageHeight,
                    0 * imageWidth, 0 * imageHeight, (0 * imageWidth) + imageWidth, (0 * imageHeight) + imageHeight,
                    null);

            trackerImage = frameImage;
        }

        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < columns; col++) {
                BufferedImage frameImage = new BufferedImage(imageWidth, imageHeight, BufferedImage.TYPE_INT_ARGB);
                Graphics2D g = (Graphics2D) frameImage.getGraphics();

                g.drawImage(animImage1,
                        0, 0, imageWidth, imageHeight,
                        col * imageWidth, row * imageHeight, (col * imageWidth) + imageWidth,
                        (row * imageHeight) + imageHeight, null);

                animation.addFrame(frameImage, 100);
            }
        }

        //Animations
        spriteImage = ImageManager.loadImage("images/enemies/air/left/Move.png");
        moveL = new Animation(true);
        moveR = new Animation(true);
        columns = 4;
        rows = 1;

        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < columns; col++) {
                BufferedImage frame = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
                Graphics2D g = (Graphics2D) frame.getGraphics();

                g.drawImage(spriteImage,
                    0, 0, width, height,
                    (col * width), (row * height), 
                    ((col * width) + width), ((row * height) + height), 
                    null);

                moveL.addFrame(frame, 100);
                moveR.addFrame(ImageManager.hFlipImage(frame), 100);
            }
        }
        moveL.start();
        moveR.start();

        spriteImage = ImageManager.loadImage("images/enemies/air/left/Death.png");
        deathL = new Animation(true);
        deathR = new Animation(true);
        columns = 4;
        rows = 1;

        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < columns; col++) {
                BufferedImage frame = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
                Graphics2D g = (Graphics2D) frame.getGraphics();

                g.drawImage(spriteImage,
                    0, 0, width, height,
                    (col * width), (row * height), 
                    ((col * width) + width), ((row * height) + height), 
                    null);

                deathL.addFrame(frame, 100);
                deathR.addFrame(ImageManager.hFlipImage(frame), 100);
            }
        }

        started = false;
        stopped = false;
    }

    private Tracker(Tracker tracker) {
        this.animation = tracker.animation;
        this.moveL = tracker.moveL;
        this.moveR = tracker.moveR;
        this.x = tracker.x;
        this.y = tracker.y;
        this.panel = tracker.panel;
        this.width = tracker.width;
        this.soundManager = tracker.soundManager;
        this.height = tracker.height;
        this.random = tracker.random;
        this.dx = tracker.dx;
        this.dy = tracker.dy;
        this.player = tracker.player;
        this.soundPlayed = tracker.soundPlayed;
        this.started = tracker.started;
        this.stopped = tracker.stopped;
        this.trackerImage = tracker.trackerImage;
    }

    public void update() {
        animation.update();

        if (x > player.getX()) {
            x = x - dx;

            goingL = true;
            goingR = false;
        }
        else if (x < player.getX()) {
            x = x + dx;

            goingL = false;
            goingR = true;
        }
        if (y > player.getY())
            y = y - dy;
        else if (y < player.getY())
            y = y + dy;

        if (Math.abs(x - player.getX()) < 50 && !soundPlayed) {
            soundManager.playSound("alien_close", false);
            soundPlayed = true;
        }

        if (Math.abs(x - player.getX()) > 80 && soundPlayed) {
            soundManager.stopSound("alien_close");
            soundPlayed = false;
        }

        if (Math.abs(y - player.getY()) < 50 && !soundPlayed) {
            soundManager.playSound("alien_close", false);
            soundPlayed = true;
        }

        if (Math.abs(y - player.getY()) > 80 && soundPlayed) {
            soundManager.stopSound("alien_close");
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

        return new Rectangle2D.Double(x, y, width, height);
    }

    public void stop() {

        if (!stopped) {
            x = x;
            y = y;
            dx = 0;
            dy = 0;
            stopped = true;
            animation.stop();
        }

    }

    public boolean isStopped() {
        return stopped;
    }

    public static boolean collidesWithTrackerAndBullet(Tracker tracker, Bullet bullet) {
        if ((tracker != null) && (bullet != null)) {
            if (tracker.isStopped())
                return false;

            Rectangle2D.Double trackerRect = tracker.getBoundingRectangle();
            Rectangle2D.Double bulletRect = bullet.getBoundingRectangle();

            return trackerRect.intersects(bulletRect);
        }
        return false;
    }

    public static boolean collidesWithTrackerAndPlayer(Tracker tracker, Player player) {
        if ((tracker.getAnimation() != null) && (!tracker.getAnimation().isStillActive()))
            return false;

        if ((tracker != null) && (player != null)) {
            Rectangle2D.Double trackerRect = tracker.getBoundingRectangle();
            Rectangle2D.Double playerRect = player.getBoundingRectangle();

            return trackerRect.intersects(playerRect);
        }
        return false;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public Sprite clone() {
        return new Tracker(this);
    }

    public Image getImage() {
        if (goingR == true) {
           trackerImage = moveR.getImage();
           moveR.update();
        } else if (goingL == true) {
           trackerImage = moveL.getImage();
           moveL.update();
        }

        return trackerImage;
    }

    public Animation getAnimation() {
        return animation;
    }

    public void addPlayer(Player player) {
        this.player = player;
    }
}

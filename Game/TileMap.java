import java.awt.Image;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.util.LinkedList;
import java.util.Iterator;
import javax.swing.JPanel;

/**
 * The TileMap class contains the data for a tile-based
 * map, including Sprites. Each tile is a reference to an
 * Image. Images are used multiple times in the tile map.
 * map.
 */

public class TileMap {

    private static final int TILE_SIZE = 64;
    private static final int TILE_SIZE_BITS = 6;
    private TankBullet tankBullet;
    private Image[][] tiles;
    private int screenWidth, screenHeight;
    private int mapWidth, mapHeight;
    private int offsetY;
    private Tank tank;
    private LinkedList<Sprite> sprites;
    private LinkedList<Bullet> bullets;
    private Player player;
    private boolean tankShoot;
    private boolean shoot;
    // SoundManager soundmanager;
    BackgroundManager bgManager;

    private GamePanel panel;
    private Dimension dimension;

    /**
     * Creates a new TileMap with the specified width and
     * height (in number of tiles) of the map.
     */
    public TileMap(GamePanel panel, int width, int height) {

        this.panel = panel;
        dimension = panel.getSize();

        screenWidth = dimension.width;
        screenHeight = dimension.height;
        // soundmanager = SoundManager.getInstance();
        System.out.println("Width: " + screenWidth);
        System.out.println("Height: " + screenHeight);

        mapWidth = width;
        mapHeight = height;

        // get the y offset to draw all sprites and tiles

        offsetY = screenHeight - tilesToPixels(mapHeight);
        System.out.println("offsetY: " + offsetY);

        bgManager = new BackgroundManager(panel, 12);

        tiles = new Image[mapWidth][mapHeight];
        player = new Player(panel, this, bgManager);
        // door = new Door(panel, player);

        sprites = new LinkedList<Sprite>();
        bullets = new LinkedList<Bullet>();

        Image playerImage = player.getImage();
        int playerHeight = playerImage.getHeight(null);

        int x, y;
        x = (dimension.width / 2) + TILE_SIZE; // position player in middle of screen

        // x = 1000; // position player in 'random' location
        y = dimension.height - (TILE_SIZE + playerHeight);

        player.setX(x);
        player.setY(y);

        System.out.println("Player coordinates: " + x + "," + y);
        shoot = false;
        tankShoot = false;

    }

    /**
     * Gets the width of this TileMap (number of pixels across).
     */
    public int getWidthPixels() {
        return tilesToPixels(mapWidth);
    }

    /**
     * Gets the width of this TileMap (number of tiles across).
     */
    public int getWidth() {
        return mapWidth;
    }

    /**
     * Gets the height of this TileMap (number of tiles down).
     */
    public int getHeight() {
        return mapHeight;
    }

    public int getOffsetY() {
        return offsetY;
    }

    /**
     * Gets the tile at the specified location. Returns null if
     * no tile is at the location or if the location is out of
     * bounds.
     */
    public Image getTile(int x, int y) {
        if (x < 0 || x >= mapWidth ||
                y < 0 || y >= mapHeight) {
            return null;
        } else {
            return tiles[x][y];
        }
    }

    /**
     * Sets the tile at the specified location.
     */
    public void setTile(int x, int y, Image tile) {
        tiles[x][y] = tile;
    }

    /**
     * Gets an Iterator of all the Sprites in this map,
     * excluding the player Sprite.
     */

    public Iterator getSprites() {
        return sprites.iterator();
    }

    public void addSprite(Sprite sprite) {
        sprite.addPlayer(player);
        sprites.add(sprite);
    }

    /**
     * Class method to convert a pixel position to a tile position.
     */

    public static int pixelsToTiles(float pixels) {
        return pixelsToTiles(Math.round(pixels));
    }

    /**
     * Class method to convert a pixel position to a tile position.
     */

    public static int pixelsToTiles(int pixels) {
        return (int) Math.floor((float) pixels / TILE_SIZE);
    }

    /**
     * Class method to convert a tile position to a pixel position.
     */

    public static int tilesToPixels(int numTiles) {
        return numTiles * TILE_SIZE;
    }

    /**
     * Draws the specified TileMap.
     */
    public void draw(Graphics2D g2) {
        int mapWidthPixels = tilesToPixels(mapWidth);

        // get the scrolling position of the map
        // based on player's position

        int offsetX = screenWidth / 2 -
                Math.round(player.getX()) - TILE_SIZE;
        offsetX = Math.min(offsetX, 0);
        offsetX = Math.max(offsetX, screenWidth - mapWidthPixels);

        bgManager.draw(g2);

        // draw the visible tiles

        int firstTileX = pixelsToTiles(-offsetX);
        int lastTileX = firstTileX + pixelsToTiles(screenWidth) + 1;
        for (int y = 0; y < mapHeight; y++) {
            for (int x = firstTileX; x <= lastTileX; x++) {
                Image image = getTile(x, y);
                if (image != null) {
                    g2.drawImage(image,
                            tilesToPixels(x) + offsetX,
                            tilesToPixels(y) + offsetY,
                            null);
                }
            }
        }

        // draw sprites
        Iterator i = getSprites();
        while (i.hasNext()) {
            Sprite sprite = (Sprite) i.next();

            if (sprite instanceof Tank) {
                Tank tank = (Tank) sprite;
                int x = Math.round(sprite.getX()) + offsetX;
                int y = Math.round(sprite.getY()) - 10;

                if (!tank.isStopped()) {
                    drawBoundedRectangle(g2, java.awt.Color.GREEN, x, x + sprite.getWidth(), y, y + sprite.getHeight());

                    g2.drawImage(sprite.getImage(), x, y, sprite.getWidth(), sprite.getHeight(), null);
                } else if (tank.isStopped() && (tank.getAnimation() != null) && (tank.getAnimation().isStillActive())) {
                    drawBoundedRectangle(g2, java.awt.Color.GREEN, x, x + sprite.getWidth(), y, y + sprite.getHeight());

                    g2.drawImage(tank.getAnimation().getImage(), x, y, sprite.getWidth(), sprite.getHeight(), null);
                }
            }

            if (sprite instanceof Tracker) {
                Tracker tracker = (Tracker) sprite;
                int x = Math.round(sprite.getX()) + offsetX;
                int y = Math.round(sprite.getY());
                // g2.drawImage(sprite.getImage(), x, y, null);

                if (!tracker.isStopped()) {
                    drawBoundedRectangle(g2, java.awt.Color.RED, x, x + sprite.getWidth(), y, y + sprite.getHeight());

                    g2.drawImage(tracker.getImage(), x, y, sprite.getWidth(), sprite.getHeight(), null);
                }
            }

            if (sprite instanceof Door) {
                Door door = (Door) sprite;
                int x = Math.round(sprite.getX()) + offsetX;
                int y = Math.round(sprite.getY());

                if (door.collidesWithPlayer()) {
                    if (door.isActive() == false) {
                        panel.lostLife();
                        return;
                    } else {
                        panel.endLevel();
                        return;
                    }
                }

                drawBoundedRectangle(g2, java.awt.Color.BLUE, x, x + sprite.getWidth(), y, y + sprite.getHeight());

                g2.drawImage(door.getImage(), x, y, sprite.getWidth(), sprite.getHeight(), null);
            }

            if (sprite instanceof Panel) {
                Panel doorSwitch = (Panel) sprite;
                int x = Math.round(sprite.getX()) + offsetX;
                int y = Math.round(sprite.getY());

                drawBoundedRectangle(g2, java.awt.Color.BLUE, x, x + sprite.getWidth(), y, y + sprite.getHeight());

                g2.drawImage(doorSwitch.getImage(), x, y, sprite.getWidth(), sprite.getHeight(), null);
            }
            /*
             * // wake up the creature when it's on screen
             * if (sprite instanceof Creature &&
             * x >= 0 && x < screenWidth)
             * {
             * ((Creature)sprite).wakeUp();
             * }
             */
        }

        if (shoot) {
            shoot = false;
            Bullet bullet = new Bullet(this, 0, 0, null, player);
            // soundmanager.playSound("boomSound",false);
            bullet.boom();

            // soundManager.playClip("boom", false);
            int y1 = Math.round(player.getY()) + 20;

            bullet.setY(y1);
            bullets.add(bullet);
        }

        Iterator i2 = bullets.listIterator(0);
        while (i2.hasNext()) {
            Bullet bullet = (Bullet) i2.next();
            if (bullet != null && bullet.isActive()) {
                int x1 = Math.round(bullet.getX()) + offsetX;
                int y1 = bullet.getY();

                drawBoundedRectangle(g2, java.awt.Color.RED, x1, x1 + bullet.getWidth(), y1, y1 + bullet.getHeight());

                g2.drawImage(bullet.getImage(), x1, y1, bullet.getWidth(), bullet.getHeight(), null);
            }
        }

        // draw player
        if (player.isAttacking() == true) {
            if (player.direction() == "L") {
                g2.drawImage(player.getImage(),
                        Math.round(player.getX()) + offsetX - 94,
                        Math.round(player.getY()), // + offsetY,
                        null);
            }
            else {
                g2.drawImage(player.getImage(),
                        Math.round(player.getX()) + offsetX,
                        Math.round(player.getY()), // + offsetY,
                        null);
            }
        } else {
            g2.drawImage(player.getImage(),
                    Math.round(player.getX()) + offsetX,
                    Math.round(player.getY()), // + offsetY,
                    null);
        }

    }

    public void drawBoundedRectangle(Graphics2D g2, java.awt.Color colour, int x1, int x2, int y1, int y2) {
        g2.setColor(colour);
        g2.drawLine(x1, y1, x2, y1);
        g2.drawLine(x1, y1, x1, y2);
        g2.drawLine(x1, y2, x2, y2);
        g2.drawLine(x2, y1, x2, y2);
    }

    public void tankShoot(boolean tankShoot) {
        this.tankShoot = tankShoot;
    }

    public void shoot(boolean shoot) {
        this.shoot = shoot;
        player.attack();
    }

    public boolean interact() {
        System.out.println("Interaction");
        boolean pSwitch = false;

        Iterator i = getSprites();

        while (i.hasNext()) {
            Sprite s = (Sprite) i.next();

            if (s instanceof Panel) {
                Panel doorSwitch = (Panel) s;

                if (
                    doorSwitch.collidesWithPlayer()
                    && !doorSwitch.isActive()
                ) {
                    System.out.println("Interacted with Switch");

                    doorSwitch.activate();
                    pSwitch = true;
                }
            }

            
        }

        Iterator i2 = getSprites();

        while (i2.hasNext()) {
            Sprite s = (Sprite) i2.next();

            if (s instanceof Door) {
                Door door = (Door) s;

                if (
                    pSwitch == true
                    && !door.isActive()
                ) {
                    System.out.println("Door activated!");

                    door.activate();
                }
            }
        }

        return true;
    }

    public void moveLeft() {
        int x, y;
        x = player.getX();
        y = player.getY();

        String mess = "Going left. x = " + x + " y = " + y;
        System.out.println(mess);

        player.move(1);

    }

    public void moveRight() {
        int x, y;
        x = player.getX();
        y = player.getY();

        String mess = "Going right. x = " + x + " y = " + y;
        System.out.println(mess);

        player.move(2);

    }

    public void jump() {
        int x, y;
        x = player.getX();
        y = player.getY();

        String mess = "Jumping. x = " + x + " y = " + y;
        System.out.println(mess);

        player.move(3);

    }

    public void attack() {
        player.attack();
    }

    public void update() {
        
        player.update();

        Iterator i2 = bullets.listIterator(0);
        while (i2.hasNext()) {
            Bullet bullet = (Bullet) i2.next();
            if (bullet != null && bullet.isActive()) {
                bullet.update();
            }
        }

        Iterator i = getSprites();
        while (i.hasNext()) {
            Sprite sprite = (Sprite) i.next();
            sprite.update();

            if (sprite instanceof Tracker) {
                Tracker tracker = (Tracker) sprite;
                if (Tracker.collidesWithTrackerAndPlayer(tracker, player) && !tracker.isStopped()) {
                    panel.lostLife();
                }

                i2 = bullets.listIterator(0);
                while (i2.hasNext()) {
                    Bullet bullet = (Bullet) i2.next();

                    if (Tracker.collidesWithTrackerAndBullet(tracker, bullet) && bullet.isActive()) {
                        if (!tracker.isStopped()) {
                            tracker.stop();
                        }
                    }
                }
            }

            if (sprite instanceof Tank) {
                Tank tank = (Tank) sprite;
                if (Tank.collidesWithTankAndPlayer(tank, player) && !tank.isStopped()) {
                    panel.lostLife();
                }

                i2 = bullets.listIterator(0);
                while (i2.hasNext()) {
                    Bullet bullet = (Bullet) i2.next();

                    if (Bullet.collidesWithTankAndBullet(tank, bullet) && bullet.isActive()) {
                        System.out.println("BULLET & TANK COLLIDE");
                        if (!tank.isStopped()) {
                            tank.stop();
                        }
                    }
                }

            }
        }

    }

}

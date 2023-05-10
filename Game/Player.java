import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;
import javax.swing.JPanel;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.rmi.Naming;
import java.awt.Point;

public class Player {
   private static final int DX = 8; // amount of X pixels to move in one keystroke
   private static final int DY = 32; // amount of Y pixels to move in one keystroke

   private static final int TILE_SIZE = 64;

   private JPanel panel; // reference to the JFrame on which player is drawn
   private TileMap tileMap;
   private BackgroundManager bgManager;

   private int x; // x-position of player's sprite
   private int y; // y-position of player's sprite

   Graphics2D g2;
   private Dimension dimension;

   private int width, height;
   private Image stripImage;

   private Image playerImage;
   private Animation idleL, idleR;
   private Animation moveL, moveR;
   private Animation attackL, attackR;
   private Animation deathL, deathR;

   private boolean jumping;
   private int timeElapsed;
   private int startY;
   private int startX;

   private boolean goingUp, goingDown;

   private boolean goingL, goingR;

   private boolean moving;
   private long moveTimer;

   private boolean attacking;
   private long attackTimer;

   private boolean inAir;
   private int initialVelocityX, initialVelocityY;
   private int startAir;

   public Player(JPanel panel, TileMap t, BackgroundManager b) {
      this.panel = panel;

      tileMap = t; // tile map on which the player's sprite is displayed
      bgManager = b; // instance of BackgroundManager

      goingUp = goingDown = false;
      inAir = false;
      attacking = false;

      width = 64;
      height = 64;

      // Left
      goingL = false;

      idleL = new Animation(true);
      stripImage = ImageManager.loadImage("images/player/left/Idle.png");
      for (int i = 0; i < 8; i++) {
         BufferedImage frame = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
         Graphics2D g = (Graphics2D) frame.getGraphics();

         g.drawImage(stripImage,
               0, 0,
               64, 64,
               (i * width), 0,
               ((i * width) + width), height,
               null);

         idleL.addFrame(frame, 100);
      }
      idleL.start();

      moveL = new Animation(true);
      stripImage = ImageManager.loadImage("images/player/left/Run.png");
      for (int i = 0; i < 8; i++) {
         BufferedImage frame = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
         Graphics2D g = (Graphics2D) frame.getGraphics();

         g.drawImage(stripImage,
               0, 0,
               64, 64,
               (i * width), 0,
               ((i * width) + width), height,
               null);

         moveL.addFrame(frame, 100);
      }
      moveL.start();

      attackL = new Animation(true);
      stripImage = ImageManager.loadImage("images/player/left/HeavyA.png");
      for (int i = 0; i < 12; i++) {
         BufferedImage frame = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
         Graphics2D g = (Graphics2D) frame.getGraphics();

         g.drawImage(stripImage,
               0, 0,
               160, 64,
               (i * 160), 0,
               ((i * 160) + 160), 64,
               null);

         attackL.addFrame(frame, 100);
      }

      deathL = new Animation(true);
      stripImage = ImageManager.loadImage("images/player/left/Death.gif");
      for (int i = 0; i < 6; i++) {
         BufferedImage frame = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
         Graphics2D g = (Graphics2D) frame.getGraphics();

         g.drawImage(stripImage,
               0, 0,
               64, 64,
               (i * width), 0,
               ((i * width) + width), height,
               null);

         deathL.addFrame(frame, 100);
      }

      // Right
      goingR = false;

      idleR = new Animation(true);
      stripImage = ImageManager.loadImage("images/player/right/Idle.png");
      for (int i = 0; i < 8; i++) {
         BufferedImage frame = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
         Graphics2D g = (Graphics2D) frame.getGraphics();

         g.drawImage(stripImage,
               0, 0,
               64, 64,
               (i * width), 0,
               ((i * width) + width), height,
               null);

         idleR.addFrame(frame, 100);
      }
      idleR.start();

      moveR = new Animation(true);
      stripImage = ImageManager.loadImage("images/player/right/Run.png");
      for (int i = 0; i < 8; i++) {
         BufferedImage frame = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
         Graphics2D g = (Graphics2D) frame.getGraphics();

         g.drawImage(stripImage,
               0, 0,
               64, 64,
               (i * width), 0,
               ((i * width) + width), height,
               null);

         moveR.addFrame(frame, 100);
      }
      moveR.start();

      attackR = new Animation(true);
      stripImage = ImageManager.loadImage("images/player/right/HeavyA.png");
      for (int i = 0; i < 12; i++) {
         BufferedImage frame = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
         Graphics2D g = (Graphics2D) frame.getGraphics();

         g.drawImage(stripImage,
               0, 0,
               160, 64,
               (i * 160), 0,
               ((i * 160) + 160), 64,
               null);

         attackR.addFrame(frame, 100);
      }

      deathR = new Animation(true);
      stripImage = ImageManager.loadImage("images/player/right/Death.gif");
      for (int i = 0; i < 6; i++) {
         BufferedImage frame = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
         Graphics2D g = (Graphics2D) frame.getGraphics();

         g.drawImage(stripImage,
               0, 0,
               64, 64,
               (i * width), 0,
               ((i * width) + width), height,
               null);

         deathR.addFrame(frame, 100);
      }

      playerImage = idleR.getImage();
      goingR = true;
   }

   public Point collidesWithTile(int newX, int newY) {
      int playerWidth = playerImage.getWidth(null);
      int offsetY = tileMap.getOffsetY();
      int xTile = tileMap.pixelsToTiles(newX);
      int yTile = tileMap.pixelsToTiles(newY - offsetY);

      if (tileMap.getTile(xTile, yTile) != null) {
         Point tilePos = new Point(xTile, yTile);

         return tilePos;
      } else {
         return null;
      }
   }

   public Point collidesWithTileDown(int newX, int newY) {

      int playerWidth = playerImage.getWidth(null);
      int playerHeight = playerImage.getHeight(null);

      int offsetY = tileMap.getOffsetY();
      int xTile = tileMap.pixelsToTiles(newX);
      int yTileFrom = tileMap.pixelsToTiles(y - offsetY);
      int yTileTo = tileMap.pixelsToTiles(newY - offsetY + playerHeight);

      for (int yTile = yTileFrom; yTile <= yTileTo; yTile++) {
         if (tileMap.getTile(xTile, yTile) != null) {
            Point tilePos = new Point(xTile, yTile);
            return tilePos;
         } else {
            if (tileMap.getTile(xTile + 1, yTile) != null) {
               int leftSide = (xTile + 1) * TILE_SIZE;
               if (newX + playerWidth > leftSide) {
                  Point tilePos = new Point(xTile + 1, yTile);
                  return tilePos;
               }
            }
         }
      }

      return null;
   }

   public Point collidesWithTileUp(int newX, int newY) {

      int playerWidth = playerImage.getWidth(null);

      int offsetY = tileMap.getOffsetY();
      int xTile = tileMap.pixelsToTiles(newX);

      int yTileFrom = tileMap.pixelsToTiles(y - offsetY);
      int yTileTo = tileMap.pixelsToTiles(newY - offsetY);

      for (int yTile = yTileFrom; yTile >= yTileTo; yTile--) {
         if (tileMap.getTile(xTile, yTile) != null) {
            Point tilePos = new Point(xTile, yTile);
            return tilePos;
         } else {
            if (tileMap.getTile(xTile + 1, yTile) != null) {
               int leftSide = (xTile + 1) * TILE_SIZE;
               if (newX + playerWidth > leftSide) {
                  Point tilePos = new Point(xTile + 1, yTile);
                  return tilePos;
               }
            }
         }

      }

      return null;
   }

   /*
    * 
    * public Point collidesWithTile(int newX, int newY) {
    * 
    * int playerWidth = playerImage.getWidth(null);
    * int playerHeight = playerImage.getHeight(null);
    * 
    * int fromX = Math.min (x, newX);
    * int fromY = Math.min (y, newY);
    * int toX = Math.max (x, newX);
    * int toY = Math.max (y, newY);
    * 
    * int fromTileX = tileMap.pixelsToTiles (fromX);
    * int fromTileY = tileMap.pixelsToTiles (fromY);
    * int toTileX = tileMap.pixelsToTiles (toX + playerWidth - 1);
    * int toTileY = tileMap.pixelsToTiles (toY + playerHeight - 1);
    * 
    * for (int x=fromTileX; x<=toTileX; x++) {
    * for (int y=fromTileY; y<=toTileY; y++) {
    * if (tileMap.getTile(x, y) != null) {
    * Point tilePos = new Point (x, y);
    * return tilePos;
    * }
    * }
    * }
    * 
    * return null;
    * }
    */

   public synchronized void move(int direction) {
      moving = true;
      moveTimer = System.currentTimeMillis();

      int newX = x;
      Point tilePos = null;

      if (!panel.isVisible())
         return;

      if (direction == 1) { // move left
         playerImage = moveL.getImage();
         moveL.update();

         goingL = true;
         goingR = false;

         newX = x - DX;

         if (newX < 0) {
            x = 0;
            return;
         }

         tilePos = collidesWithTile(newX, y);
      } else if (direction == 2) { // move right
         playerImage = moveR.getImage();
         moveR.update();

         goingL = false;
         goingR = true;

         int playerWidth = playerImage.getWidth(null);
         newX = x + DX;

         int tileMapWidth = tileMap.getWidthPixels();

         if (newX + playerImage.getWidth(null) >= tileMapWidth) {
            x = tileMapWidth - playerImage.getWidth(null);
            return;
         }

         tilePos = collidesWithTile(newX + playerWidth, y);
      } else // jump
      if (direction == 3 && !jumping) {
         jump();
         return;
      }

      if (tilePos != null) {
         if (direction == 1) {
            System.out.println(": Collision going left");

            x = ((int) tilePos.getX() + 1) * TILE_SIZE; // keep flush with right side of tile
         } else if (direction == 2) {
            System.out.println(": Collision going right");

            int playerWidth = playerImage.getWidth(null);
            x = ((int) tilePos.getX()) * TILE_SIZE - playerWidth; // keep flush with left side of tile
         }
      } else {
         if (direction == 1) {
            x = newX;
            bgManager.moveLeft();
         } else if (direction == 2) {
            x = newX;
            bgManager.moveRight();
         }

         if (isInAir()) {
            System.out.println("In the air. Starting to fall.");

            if (direction == 1) { // make adjustment for falling on left side of tile
               int playerWidth = playerImage.getWidth(null);
               x = x - playerWidth + DX;
            }

            fall();
         }
      }
   }

   public boolean isInAir() {

      int playerHeight;
      Point tilePos;

      if (!jumping && !inAir) {
         playerHeight = playerImage.getHeight(null);
         tilePos = collidesWithTile(x, y + playerHeight + 1); // check below player to see if there is a tile

         if (tilePos == null) // there is no tile below player, so player is in the air
            return true;
         else // there is a tile below player, so the player is on a tile
            return false;
      }

      return false;
   }

   private void fall() {

      jumping = false;
      inAir = true;
      timeElapsed = 0;

      goingUp = false;
      goingDown = true;

      startY = y;
      initialVelocityY = 0;
   }

   public void jump() {

      if (!panel.isVisible())
         return;

      jumping = true;
      timeElapsed = 0;

      goingUp = true;
      goingDown = false;
      startX = x;
      startY = y;
      initialVelocityX = 15;
      initialVelocityY = 70;
   }

   public void attack() {
      attacking = true;
      attackTimer = System.currentTimeMillis();

      attackR.start();
      attackL.start();

      // if (goingR == true) {
      //    playerImage = attackR.getImage();
      //    System.out.println("Attacking Right");
      // } else if (goingL == true) {
      //    playerImage = attackL.getImage();
      //    System.out.println("Attacking Left");
      // }
   }

   public void update() {
      int distanceY = 0;
      int distanceX = 0;
      int newY = 0;
      int newX = 0;

      timeElapsed++;

      if (jumping || inAir) {
         distanceY = (int) (initialVelocityY * timeElapsed -
               4.9 * timeElapsed * timeElapsed);
         newY = startY - distanceY;
         distanceX = (int) (initialVelocityX * timeElapsed);

         if (goingL == true)
            newX = startX - distanceX;
         else
            newX = startX + distanceX;

         if (newY > y && goingUp) {
            goingUp = false;
            goingDown = true;
         }

         if (goingUp) {
            Point tilePos = collidesWithTileUp(x, newY);
            if (tilePos != null) { // hits a tile going up
               System.out.println("Jumping: Collision Going Up!");

               int offsetY = tileMap.getOffsetY();
               int topTileY = ((int) tilePos.getY()) * TILE_SIZE + offsetY;
               int bottomTileY = topTileY + TILE_SIZE;

               y = bottomTileY;
               fall();
            } else {
               y = newY;
               x = newX;
               System.out.println("Jumping: No collision.");
            }
         } else if (goingDown) {
            Point tilePos = collidesWithTileDown(x, newY);
            if (tilePos != null) { // hits a tile going up
               System.out.println("Jumping: Collision Going Down!");
               int playerHeight = playerImage.getHeight(null);
               goingDown = false;

               int offsetY = tileMap.getOffsetY();
               int topTileY = ((int) tilePos.getY()) * TILE_SIZE + offsetY;

               y = topTileY - playerHeight;
               jumping = false;
               inAir = false;
            } else {
               y = newY;
               System.out.println("Jumping: No collision.");
            }
         }
      }

      // Idle Timer
      long timer = System.currentTimeMillis() - moveTimer;
      if (timer >= 100) {
         if (moving == true && jumping == false) {
            moving = false;
         }
      }

      // Updates Animations
      if (attacking == false && moving == false) {
         if (goingR == true) {
            playerImage = idleR.getImage();
            idleR.update();
         } else if (goingL == true) {
            playerImage = idleL.getImage();
            idleL.update();
         }
      } else if (moving == true) {
         if (goingR == true) {
            playerImage = moveR.getImage();
            moveR.update();
         } else if (goingL == true) {
            playerImage = moveL.getImage();
            moveL.update();
         }
      } else if (attacking == true) {
         timer = System.currentTimeMillis() - attackTimer;
         if (timer >= 1200) {
            attacking = false;
            attackR.stop();
            attackL.stop();
         } else if (goingR) {
            playerImage = attackR.getImage();
            attackR.update();
         } else if (goingL) {
            playerImage = attackL.getImage();
            attackL.update();
         }
      }
   }

   public int direction() {
      if (goingR)
         return 1;
      else
         return 0;
   }

   public boolean isAttacking() {
      return attacking;
   }

   public void moveUp() {

      if (!panel.isVisible())
         return;

      y = y - DY;
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
      return playerImage;
   }

   public Rectangle2D.Double getBoundingRectangle() {
      int playerWidth = playerImage.getWidth(null);
      int playerHeight = playerImage.getHeight(null);

      return new Rectangle2D.Double(x, y, playerWidth, playerHeight);
   }
}
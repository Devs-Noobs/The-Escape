import java.awt.Dimension;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.Line2D;
import javax.swing.JPanel;
import java.util.Random;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.Point;

public class Tank implements Sprite {
    private static final int DX = 2;    // amount of X pixels to move in one unit of time
    
    private JPanel panel;
    
    private int x;
    private int y;
    
    private TileMap tileMap;
    
    private int width;
    private int height;
    private boolean soundPlayed;
    private int dx;  //increment to move alond x-axis
    private int dy;  //increment to move along y-axis
    private boolean moveRight;
    private boolean originalImage;
    private boolean stopped;
    private int timeElapsed;
    private Color backgroundColour;
    private Dimension dimension;
    private SoundManager soundManager;
    private BufferedImage tankImage;
    private BufferedImage tankImageRight;
    private BufferedImage tankImageLeft;
    private Player player;

    public Tank (JPanel p,int xPos, int yPos, Player player){
        //tileMap = t;
        panel = p;
        dimension = panel.getSize();
        backgroundColour = panel.getBackground();
        
        this.player = player;
        
        this.stopped = false;
        soundPlayed = false;
        width = 60; //93;
        height = 40; //59;
        
        originalImage = true;
        x = xPos;
        y = yPos;
        
        tankImageLeft = ImageManager.loadBufferedImage("images/tank/TankMoveLeft.png");
        tankImageRight = ImageManager.hFlipImage(tankImageLeft);
        tankImage = tankImageLeft;
        soundManager = SoundManager.getInstance();
        dx = DX;
        dy = 0;
        moveRight = true;
        timeElapsed = 0;
    }
    
    private Tank (Tank tank){
        this.panel = tank.panel;
        this.x = tank.x;
        this.y = tank.y;
        this.width = tank.width;
        this.height = tank.height;
        this.dx = tank.dx;
        this.dy = tank.dy;
        this.backgroundColour = tank.backgroundColour;
        this.dimension = tank.dimension;
        this.soundManager = tank.soundManager;
        this.tankImage = tank.tankImage;
        this.tankImageRight = tank.tankImageRight;
        this.tankImageLeft = tank.tankImageLeft;
        this.player = tank.player;
        this.stopped = tank.stopped;
    }
    
    public Sprite clone(){
        return new Tank(this);
    }
    
    public boolean faceRightDirection(){
        return (tankImage == tankImageRight);
    }
    

    public void draw(Graphics2D g2){
        if (originalImage){
            g2.drawImage(tankImageLeft, x, y, getWidth(), getHeight(), null);
        }else
             g2.drawImage(tankImageRight, x, y, getWidth(), getHeight(), null);
    }

    
    public void erase(){
        
        Graphics g = panel.getGraphics();
        Graphics2D g2 =(Graphics2D) g;
        
        g2.setColor(backgroundColour);
        g2.fill(new Rectangle2D.Double(x-10,y-10,width+20,height+20));
        
        g.dispose();
    }
    
    private boolean collidesWithplayer() {
        Rectangle2D.Double myRect = getBoundingRectangle();
        Rectangle2D.Double playerRect = player.getBoundingRectangle();
      
        return myRect.intersects(playerRect); 
    }
    
    public static boolean collidesWithTankAndBullet(Tank tank, Bullet bullet) {
        if ((tank != null) && (bullet != null)){
            Rectangle2D.Double tankRect = tank.getBoundingRectangle();
            Rectangle2D.Double bulletRect = bullet.getBoundingRectangle();
            
            return bulletRect.intersects(tankRect);
        }
        return false;
    }
    
    
    public static boolean collidesWithTankAndPlayer(Tank tank, Player player) {
        if ((tank != null) && (player != null)){
            Rectangle2D.Double tankRect = tank.getBoundingRectangle();
            Rectangle2D.Double playerRect = player.getBoundingRectangle();
            
            return tankRect.intersects(playerRect);
        }
        return false;
    }

    /*public boolean isOnTank(int x, int y) {
        if (Tank == null)
            return false;

        return Tank.contains(x, y);
    }*/
    
    
    public int getX() {
        return x;
    }
    
    public int getY() {
        return y;
    }


    public Rectangle2D.Double getBoundingRectangle() {
        return new Rectangle2D.Double (x, y, width, height);
    }
    
    public void update() {
        if (stopped)
            return;
        
        if (player != null){
            x = x + dx;
            if (moveRight){
                if (getX() < player.getX()){ // move left now
                    moveRight = false;
                    originalImage = false;
                    tankImage =tankImageRight;
                    dx = Math.abs(dx);
                    x = x + dx;
                }
            }
            else
            if (getX() > player.getX()){ // move right now
                originalImage = true;
                tankImage = tankImageLeft;
                moveRight = true;
                dx = dx * -1;
                x = x + dx;
            }
            
            /*if (Math.abs (x - player.getX()) < 50 && !soundPlayed) {
            //soundManager.playSound ("alien_close", false);
            //soundManager.playSound("appear", false);
            soundPlayed = true;
            tileMap.tankShoot(true);
            }*/
        }
    }
    
    public void stop(){
        
        if (!stopped){
            x=x;
            y=y;
            dx=0;
            dy=0;
            stopped = true;
        }
        
    }
    
    public boolean isStopped(){
        return stopped;
    }
    
    public Point collidesWithTile(int newX, int newY) {
        int tankWidth = tankImage.getWidth(null);
        int offsetY = tileMap.getOffsetY();
        int xTile = tileMap.pixelsToTiles(newX);
        int yTile = tileMap.pixelsToTiles(newY - offsetY);
        
        if (tileMap.getTile(xTile, yTile) != null) {
            Point tilePos = new Point (xTile, yTile);
            return tilePos;
        }
        else {
            return null;
        }
    }
    
    public int getWidth(){
        return width;
    }
    
    public int getHeight(){
        return height;
    }
    
    
    public void setX(int x) {
        this.x = x;
    }
    
    
    public void setY(int y) {
        this.y = y;
    }
    
    public Image getImage() {
        return tankImage;
    }
    
    public void addPlayer(Player player){
        this.player = player;
    }
}

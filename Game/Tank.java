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

public class Tank implements Sprite {
    private static final int DX = 2;    // amount of X pixels to move in one unit of time
    
    private JPanel panel;
    
    private int x;
    private int y;
    
    private int width;
    private int height;
    
    private int dx;  //increment to move alond x-axis
    private int dy;  //increment to move along y-axis
    private int initialVelocityX;
    private boolean moveRight;

    private int timeElapsed;
    private int startX;
    private Color backgroundColour;
    private Dimension dimension;
    private SoundManager soundManager;
    private Random random;
    private Image tankImage;
    private Player player;

   public Tank (JPanel p, int xPos, int yPos, Player player){
        panel = p;
        dimension = panel.getSize();
        backgroundColour = panel.getBackground();
        
        this.player = player;
        
        //width = 15;
        //height = 20;
        width = 92; //920;
        height = 30; //306;
        
        random = new Random();
        
        x = xPos;
        y = yPos;
        
        //setLocation();
        
        tankImage = ImageManager.loadImage("images/tank.png");
        soundManager = SoundManager.getInstance();
        dx = DX;
        dy = 0;
        moveRight = true;
        initialVelocityX = 15;
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
        this.random = tank.random;
        this.tankImage = tank.tankImage;
        this.player = tank.player;
    }
    
    public Sprite clone(){
        return new Tank(this);
    }
    
    public void setLocation(){
       int panelWidth=panel.getWidth();
       x = random.nextInt(panelWidth-width);
       y=10;
    }
    
    public void draw(Graphics2D g2){
        g2.drawImage(tankImage, x, y, getWidth(), getHeight(), null);
    }

    
    public void erase(){
        
        Graphics g = panel.getGraphics();
        Graphics2D g2 =(Graphics2D) g;
        
        g2.setColor(backgroundColour);
        g2.fill(new Rectangle2D.Double(x-10,y-10,width+20,height+20));
        
        g.dispose();
    }
    
    public void move() {

        if (!panel.isVisible()) return;

        //x = x + dx;
        //y = y + dy;

        //int height = panel.getHeight();
        

        if (y > height) {
            //setLocation();
             soundManager.playSound("appear", false);
            //dy = dy + 1;    // increase increment each time alien hits bottom of GamePanel
        }
    }


    
    private boolean collidesWithplayer() {
        Rectangle2D.Double myRect = getBoundingRectangle();
        Rectangle2D.Double playerRect = player.getBoundingRectangle();
      
        return myRect.intersects(playerRect); 
    }
    
    public static boolean collidesWithTankAndBullet(Tank Tank, Bullet bullet) {
        if ((Tank != null) && (bullet != null)){
            Rectangle2D.Double TankRect = Tank.getBoundingRectangle();
            Rectangle2D.Double playerRect = bullet.getBoundingRectangle();
            
            return playerRect.intersects(TankRect);
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
        if (player != null){
            x = x + dx;
            
            if (moveRight){
                if (getX() < player.getX()){ // move left now
                    moveRight = false;
                    dx = dx * -1;
                }
            } else {
                if (getX() > player.getX()){ // move right now
                    moveRight = true;
                    dx = dx * -1;
                }
            }
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

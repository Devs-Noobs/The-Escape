import java.awt.Image;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.Line2D;
import javax.swing.JPanel;
import java.util.Random;
import java.util.LinkedList;

public class Bullet extends Thread{
    private JPanel panel;
    private TileMap map;
    private int x;
    private int y;
    
    private int width;
    private int height;
    private boolean bulletActive;
    private int dx;  //increment to move alond x-axis
    private int dy;  //increment to move along y-axis
    
    Ellipse2D.Double bullet;
        
   private Color backgroundColour;
   private SoundManager soundManager;
   private Tank tank;
   private Player player; 
   private Image bulletImage;
   
   public Bullet (TileMap t, int xPos, int yPos, LinkedList sprites, Player player){
        map = t;
        //backgroundColour = panel.getBackground();
        
        this.tank = tank;
        this.player = player;
        width = 10;
        height = 15;
        x= player.getX() + 30;
        y= player.getY() - 100;
        bulletActive = false;
        soundManager = SoundManager.getInstance();
        bulletImage = ImageManager.loadImage("images/bullet.png");
        dx=2;
        dy=10;
    }
    public boolean isActive(){
        
       return bulletActive;
        
    }
    public int getWidth(){
        return width;
    }
    
    public int getHeight(){
        return height;
    }
    public Image getImage() {
      return bulletImage;
   }

    public void setLocation(){
        //x= square.getX() + 30;
        //y= square.getY() + 30;
        x = x - dx;
    }
    
    public void draw(Graphics2D g2){
        bullet = new Ellipse2D.Double(x,y,width,height);
        
        g2.setColor(Color.YELLOW);
        g2.fill(bullet);
        
        g2.setColor(Color.BLACK);
        g2.draw(bullet);
        
        g2.setColor(Color.BLACK);
            
            
        
    }
    
    public void erase(){
        
        Graphics g = panel.getGraphics();
        Graphics2D g2 =(Graphics2D) g;
        
        g2.setColor(backgroundColour);
        g2.fill(new Rectangle2D.Double(x-10,y-10,width+20,height+20));
        
        g.dispose();
    }
    
     public void move(Graphics2D g2) {
        if (panel == null) return;
        
        if (!panel.isVisible()) return;
        
            setLocation();
             //y = y - dy;
        
             this.draw(g2);
               
        }
        
    public int getX() {
        return x;
    }
    
    public int getY() {
        return y;
    }
    
    public void boom(Graphics2D g2) {
        /*
        if (panel == null) return;
        if (!panel.isVisible()) 
            return;
        */
        
        bulletActive = true;
        this.draw(g2);
        /*
        for (int i = 0; i < 10000; i++){
            try
            {
                this.draw(g2);
                Thread.sleep(100);
                x = x + dx;
            }
            catch (InterruptedException ie)
            {
                ie.printStackTrace();
            }
        }
        */
            
    }
    
    
    /*
    public boolean collidesWithtank(tank tank) {
        Rectangle2D.Double myRect = getBoundingRectangle();
        Rectangle2D.Double tankRect = tank.getBoundingRectangle();
      
        return myRect.intersects(tankRect); 
    }
    */
   public static boolean collidesWithtankAndBullet(Tank tank, Bullet bullet) {
        if ((tank != null) && (bullet != null)){
            Rectangle2D.Double tankRect = tank.getBoundingRectangle();
            Rectangle2D.Double squareRect = bullet.getBoundingRectangle();
            
            return tankRect.intersects(squareRect);
        }
        return false;
    }


    public boolean isOnBullet(int x, int y) {
        if (bullet == null)
            return false;

        return bullet.contains(x, y);
    }


    public Rectangle2D.Double getBoundingRectangle() {
        return new Rectangle2D.Double (x, y, width, height);
    }
}

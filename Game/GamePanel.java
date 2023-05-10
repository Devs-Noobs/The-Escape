import javax.swing.JPanel;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Color;
import java.awt.geom.Rectangle2D;

/**
   A component that displays all the game entities
*/

public class GamePanel extends JPanel
               implements Runnable {

    // private SoundManager soundManager;

    private boolean isRunning;
    private boolean isPaused;

    private Thread gameThread;

    private BufferedImage image;
     private Image backgroundImage;

    private BirdAnimation animation;
    private volatile boolean isAnimShown;
    private volatile boolean isAnimPaused;

    private ImageEffect imageEffect;        // sprite demonstrating an image effect

    private TileMapManager tileManager;
    private TileMap    tileMap;

    private boolean levelChange;
    private int level;
    private boolean gameOver;

    public GamePanel () {

        isRunning = false;
        isPaused = false;
        isAnimShown = false;
        isAnimPaused = false;
        // soundManager = SoundManager.getInstance();

        image = new BufferedImage (600, 500, BufferedImage.TYPE_INT_RGB);

        level = 1;
        levelChange = false;
    }


    public void createGameEntities() {
        animation = new BirdAnimation();
        imageEffect = new ImageEffect (this);
    }


    public void run () {
        try {
            isRunning = true;
            while (isRunning) {
                if (!isPaused && !gameOver)
                    gameUpdate();
                gameRender();
                Thread.sleep (50);    
            }
        }
        catch(InterruptedException e) {}
    }
    

    public void gameUpdate() {

        tileMap.update();

        if (levelChange) {
            levelChange = false;
            tileManager = new TileMapManager (this);

            try {
                String filename = "maps/map" + level + ".txt";
                tileMap = tileManager.loadMap(filename) ;
                int w, h;
                w = tileMap.getWidth();
                h = tileMap.getHeight();
                System.out.println ("Changing level to Level " + level);
                System.out.println ("Width of tilemap " + w);
                System.out.println ("Height of tilemap " + h);
            }
            catch (Exception e) {        // no more maps: terminate game
                gameOver = true;
                System.out.println(e);
                System.out.println("Game Over"); 
                return;
/*
                System.exit(0);
*/
            }

            createGameEntities();
            return;
                
        }
        
        /*
        if(bullet!=null && bullet.isActive()){
            Graphics2D imageContext = (Graphics2D) image.getGraphics();
            imageContext.drawImage(backgroundImage, 0, 0,400,400, null);    // draw the background image
            
            //soundManager.playSound("boomSound",false);
            
            updateBullet();
            bullet.move();
            
            for(int i=0;i<NUM_tankS;i++){
                if (bullet.collidesWithtankAndBullet(tanks[i], bullet)){
                    //if (explosion == null){
                    explosion.setlocation();
                    explosionStarted = true;
                    explosion.start();
                    explosion = new Explode(this, tanks[i].getX(), tanks[i].getY(),tank, bullet);
                  //}
                    soundManager.playClip("appear", false);
                    //if (explosion != null) {
                        
                   // }
                }

            }
             // if (animation.collidesWithTrackerAndBullet( animation, bullet)){                    
                    // soundManager.playSound("appear", false);
                    // isHit= true;
                        // animation.stop();
                // }
        }
        */
    }
    
    // public void shoot (){
        // bullet = new Bullet(this,50,350,tank,player);
        // if(bullet != null && !isPaused && !bullet.isActive()){
            // soundManager.playClip("boom", false);
            // Graphics2D imageContext = (Graphics2D) image.getGraphics();
            // imageContext.drawImage(backgroundImage, 0, 0,400,400, null);    // draw the background image
            
            // bullet.boom(imageContext);
            // //Bullet bullet = new Bullet(this, 30, 150, tank, player);
            
            
        

        
        
       
        // }
        // if (!isPaused && isAnimShown)
            // animation.update();

        // imageEffect.update();
    // }


    public void gameRender() {

        // draw the game objects on the image

        Graphics2D imageContext = (Graphics2D) image.getGraphics();

        tileMap.draw (imageContext);

        if (isAnimShown)
            animation.draw(imageContext);        // draw the animation

        imageEffect.draw(imageContext);            // draw the image effect

        if (gameOver) {
            Color darken = new Color (0, 0, 0, 125);
            imageContext.setColor (darken);
            imageContext.fill (new Rectangle2D.Double (0, 0, 600, 500));
        }
        
        /*
        if(bullet!=null){
            if (bullet.isActive()){
                updateBullet();
                bullet.boom(imageContext);
            }
        }
        */


        Graphics2D g2 = (Graphics2D) getGraphics();    // get the graphics context for the panel
        if (g2 != null)
            g2.drawImage(image, 0, 0, 600, 500, null);    // draw the image on the graphics context

        imageContext.dispose();
    }


    public void startGame() {                // initialise and start the game thread 

        if (gameThread == null) {
            // soundManager.playSound ("background", true);

            gameOver = false;

            tileManager = new TileMapManager (this);

            try {
                tileMap = tileManager.loadMap("maps/map1.txt");
                int w, h;
                w = tileMap.getWidth();
                h = tileMap.getHeight();
                System.out.println ("Width of tilemap " + w);
                System.out.println ("Height of tilemap " + h);
            }
            
            catch (Exception e) {
                System.out.println(e);
                System.exit(0);
            }

            createGameEntities();

            gameThread = new Thread(this);
            gameThread.start();            

        }
    }


    public void startNewGame() {                // initialise and start a new game thread 
        if (gameThread != null || !isRunning) {
            //soundManager.playSound ("background", true);

            endGame();

            gameOver = false;
            level = 1;

            tileManager = new TileMapManager (this);

            try {
                tileMap = tileManager.loadMap("maps/map1.txt");
                int w, h;
                w = tileMap.getWidth();
                h = tileMap.getHeight();
                System.out.println ("Width of tilemap " + w);
                System.out.println ("Height of tilemap " + h);
            }
            catch (Exception e) {
                System.out.println(e);
                System.exit(0);
            }

            createGameEntities();

            gameThread = new Thread(this);
            gameThread.start();            

        }
    }


    public void pauseGame() {                // pause the game (don't update game entities)
        if (isRunning) {
            if (isPaused)
                isPaused = false;
            else
                isPaused = true;

            // if (isAnimShown) {
            //     if (isPaused)
            //         animation.stopSound();
            //     else
            //         animation.playSound();
            // }
        }
    }


    public void endGame() {                    // end the game thread
        isRunning = false;
        //soundManager.stopClip ("background");
    }

    
    public void moveLeft() {
        if (!gameOver)
            tileMap.moveLeft();
    }


    public void moveRight() {
        if (!gameOver)
            tileMap.moveRight();
    }

    public void shoot(){
        if(!gameOver)
            tileMap.shoot(true);
    }
    
    public void jump() {
        if (!gameOver)
            tileMap.jump();
    }

    
    public void showAnimation() {
        isAnimShown = true;
        animation.start();
        
    }


    public void endLevel() {
        level += 1;
        levelChange = true;
    }
    
    public void lostLife() {
        level = 1;
        levelChange = true;
    }

}
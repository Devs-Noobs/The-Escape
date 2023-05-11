import java.awt.*;
import java.awt.geom.AffineTransform;
import java.io.*;
import java.util.ArrayList;
import javax.swing.JPanel;
import javax.swing.ImageIcon;

/**
 * The TileMapeManager class loads and manages tile Images and
 * "host" Sprites used in the game. Game Sprites are cloned from
 * "host" Sprites.
 */
public class TileMapManager {

    private ArrayList<Image> tiles;
    private int currentMap = 0;

    private GamePanel panel;

    // host sprites used for cloning
    private Sprite tankSprite;
    private Sprite trackerSprite;
    private Sprite doorSprite;
    private Sprite panelSprite;
    /*
     * 
     * // host sprites used for cloning
     * private Sprite playerSprite;
     * private Sprite musicSprite;
     * private Sprite coinSprite;
     * private Sprite goalSprite;
     * private Sprite grubSprite;
     * private Sprite flySprite;
     */

    public TileMapManager(GamePanel panel) {
        this.panel = panel;

        loadTileImages();

        loadCreatureSprites();
        // loadPowerUpSprites();
    }

    public TileMap loadMap(String filename)
            throws IOException {
        ArrayList<String> lines = new ArrayList<String>();
        int mapWidth = 0;
        int mapHeight = 0;

        // read every line in the text file into the list

        BufferedReader reader = new BufferedReader(new FileReader(filename));

        while (true) {
            String line = reader.readLine();
            // no more lines to read
            if (line == null) {
                reader.close();
                break;
            }

            // add every line except for comments
            if (!line.startsWith("#")) {
                lines.add(line);
                mapWidth = Math.max(mapWidth, line.length());
            }
        }

        // parse the lines to create a TileMap
        mapHeight = lines.size();

        TileMap newMap = new TileMap(panel, mapWidth, mapHeight);

        for (int y = 0; y < mapHeight; y++) {
            String line = lines.get(y);
            for (int x = 0; x < line.length(); x++) {
                char ch = line.charAt(x);

                // check if the char represents tile A, B, C etc.
                int tile = ch - 'A';
                if (tile >= 0 && tile < tiles.size()) {
                    newMap.setTile(x, y, tiles.get(tile));
                }
                
                else if (ch == '1') {
                    addSprite(newMap, trackerSprite, x, y);
                } 
                else if (ch == '2') {
                    addSprite(newMap, tankSprite, x, y);
                }
                else if (ch == '9') {
                    addSprite(newMap, doorSprite, x, y);
                }
                else if (ch == '8') {
                    addSprite(newMap, panelSprite, x, y);
                }
            }
        }

        return newMap;
    }

    /// *
    private void addSprite(TileMap map,
            Sprite hostSprite, int tileX, int tileY) {
        if (hostSprite != null) {
            // clone the sprite from the "host"
            Sprite sprite = (Sprite) hostSprite.clone();

            // center the sprite
            sprite.setX(
                    TileMap.tilesToPixels(tileX) +
                            (TileMap.tilesToPixels(1) -
                                    sprite.getWidth()) / 2);

            // bottom-justify the sprite
            sprite.setY(
                    TileMap.tilesToPixels(tileY + 1) -
                            sprite.getHeight());

            // add it to the map
            map.addSprite(sprite);
        }
    }

    // */

    // -----------------------------------------------------------
    // code for loading sprites and images
    // -----------------------------------------------------------

    public void loadTileImages() {
        // keep looking for tile A,B,C, etc. this makes it
        // easy to drop new tiles in the images/ folder

        File file;

        System.out.println("loadTileImages called.");

        tiles = new ArrayList<Image>();
        char ch = 'A';

        while (true) {
            String filename = "images/tiles/tile_" + ch + ".png";
            file = new File(filename);
            if (!file.exists()) {
                System.out.println("Image file could not be opened: " + filename);

                break;
            } else
                System.out.println("Image file opened: " + filename);

            Image tileImage = new ImageIcon(filename).getImage();
            tiles.add(tileImage);

            ch++;
        }
    }

    public void loadCreatureSprites() {
        tankSprite = new Tank(panel, 100, 100, null);
        trackerSprite = new Tracker(panel, 100, 100, null);
        doorSprite = new Door(panel, 100, 100, null);
        panelSprite = new Panel(panel, 100, 100, null);
    }
}

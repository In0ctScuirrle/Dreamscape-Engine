package lu.embellished_duck.dreamscape_engine.world;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import lu.embellished_duck.dreamscape_engine.core.GamePanel;
import lu.embellished_duck.dreamscape_engine.util.ImageHelper;

import javax.imageio.ImageIO;
import java.awt.*;
import java.util.Arrays;
import java.util.Objects;

/**
 * Manages everything related to tiles, it contains functions that load them at the start of the game, store them in the tile library and also allows for placement around the world map
 *
 * @since 0.1.0
 *
 * @author Will Blanchard
 */
@Getter
@Slf4j
public class TileManager {

    //=======================
    // INSTANTIATE VARIABLES
    //=======================
    private GamePanel gamePanel;

    private WorldManager worldManager;

    public Tile[] tileLibrary;

    private boolean IO_Error = false;


    //=============
    // CONSTRUCTOR
    //=============
    public TileManager(GamePanel gamePanel) {

        this.gamePanel = gamePanel;
        this.worldManager = new WorldManager(gamePanel);

        tileLibrary = new Tile[5];

        getTileImages();
        worldManager.loadMap("test_world");

    }//End of Constructor


    /**
     * All tiles that are used by the game are stored in the tile library, this way they're only ever loaded once and unnecessary resource consumption is avoided
     *
     * @since 0.1.0
     *
     * @author Will Blanchard
     */
    public void getTileImages() {

        log.info("Loading Tile Textures...");

        initializeImage(0, "wall_panel", true);
        initializeImage(1, "carpet", false);

        log.info("{} Tile Textures Loaded Successfully!", Arrays.stream(tileLibrary).filter(Objects::nonNull).count());

    }//End of Method


    /**
     * Helper function which initializes the tiles in the tile library
     *
     * @param index The tile library index of the tile that is to be instantiated
     * @param filePath The filepath of the tile's texture
     * @param collision Whether this tile should contain collision or not
     *
     * @since 0.2.0
     *
     * @author Will Blanchard
     */
    private void initializeImage(int index, String filePath, boolean collision) {

        ImageHelper imageHelper = ImageHelper.INSTANCE;

        try {

            tileLibrary[index] = new Tile();
            tileLibrary[index].setImage(ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/textures/tile/" + filePath + ".png"))));
            tileLibrary[index].setImage(imageHelper.scaleImage(tileLibrary[index].getImage(), gamePanel.TILE_SIZE, gamePanel.TILE_SIZE));
            tileLibrary[index].setCollision(collision);

        } catch (Exception e) {

            log.error(formattedError(index));

        }//End of Try-Catch Statement

    }//End of Helper Method


    /**
     * Helper method which can identify an error at a specific index in the tile library
     *
     * @param index The index at which the error has occurred
     * @return Formatted error text
     *
     * @since 0.2.0
     *
     * @author Will Blanchard
     */
    private String formattedError(int index) {

        return """
                    File IO error at index %s: One of the errors couldn't be found due to either reason |
                    
                    1. The image file name isn't spelled correctly
                    
                    2. There is a type in the file path, most likely a missing / in front of the assets path
                    
                    3. The file extension is incorrect, it needs to be a .png format image
                    """.formatted(index);

    }//End of Helper Method


    /**
     * Draws the world on the screen, it also moves the world around the player which is a strange concept, but it works really well.
     *
     * @param graphics2D Graphics passed in from {@code GamePanel}
     *
     * @since 0.1.0
     *
     * @author Will Blanchard
     */
    public void draw(Graphics2D graphics2D) {

        int worldCol = 0;
        int worldRow = 0;

        //This while loop automates drawing, so you don't need to draw every tile individually
        while (worldCol < gamePanel.MAX_WORLD_COL && worldRow < gamePanel.MAX_WORLD_ROW) {

            int tileNum = worldManager.mapTileNum[worldCol][worldRow];

            int worldX = worldCol * gamePanel.TILE_SIZE;
            int worldY = worldRow * gamePanel.TILE_SIZE;

            int screenX = worldX - gamePanel.getPlayer().worldX + gamePanel.getPlayer().screenX;
            int screenY = worldY - gamePanel.getPlayer().worldY + gamePanel.getPlayer().screenY;

            //This If statement ensures that off-screen tiles are not rendered to save on performance (Cause no GPU access cause no LWJGL or JOGL
            if (worldX + gamePanel.TILE_SIZE > gamePanel.getPlayer().worldX - gamePanel.getPlayer().screenX
                    || worldX - gamePanel.TILE_SIZE > gamePanel.getPlayer().worldX + gamePanel.getPlayer().screenX
                    || worldY + gamePanel.TILE_SIZE > gamePanel.getPlayer().worldY - gamePanel.getPlayer().screenY
                    || worldY - gamePanel.TILE_SIZE > gamePanel.getPlayer().worldY + gamePanel.getPlayer().screenY) {

                graphics2D.drawImage(tileLibrary[tileNum].getImage(), screenX, screenY, null);

            }//End of If Statement

            worldCol++;

            if (worldCol == gamePanel.MAX_WORLD_COL) {

                worldCol = 0;//Reset the value
                worldRow++;

            }//End of If Statement

        }//End of While Loop

    }//End of Method

}//End of Class
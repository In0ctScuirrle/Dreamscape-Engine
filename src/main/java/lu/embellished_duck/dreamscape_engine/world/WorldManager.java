package lu.embellished_duck.dreamscape_engine.world;

import lombok.extern.slf4j.Slf4j;
import lu.embellished_duck.dreamscape_engine.core.GamePanel;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Assistant to {@code TileManager}, contains functions which manage loading and saving the world to a file
 *
 * @since 0.4.0
 *
 * @author Will Blanchard
 */
@Slf4j
public class WorldManager {

    //=======================
    // INSTANTIATE VARIABLES
    //=======================
    private final GamePanel gamePanel;

    public int[][] mapTileNum;


    //=============
    // CONSTRUCTOR
    //=============
    public WorldManager(GamePanel gamePanel) {

        this.gamePanel = gamePanel;

        mapTileNum = new int[gamePanel.MAX_WORLD_COL][gamePanel.MAX_WORLD_ROW];

    }//End of Constructor


    /**
     * This function will load a selected world map and parse it into a 2D array which can be read by the {@Code TileManager}. The numbers used in world files are reference numbers for the tile library, which is how
     * this program draws the tiles to the map.
     *
     * @since 0.4.0
     *
     * @author Will Blanchard
     *
     * @param map The location of the map file in the game resources folder
     */
    public void loadMap(String map) {

        try {

            InputStream inputStream = getClass().getResourceAsStream("/data/maps/" + map + ".txt");
            assert inputStream != null;
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));


            int col = 0;
            int row = 0;

            while (col < gamePanel.MAX_WORLD_COL && row < gamePanel.MAX_WORLD_ROW) {

                String line = reader.readLine();//Reading the file by line and storing it in a String

                while (col < gamePanel.MAX_WORLD_COL) {

                    String[] numbers = line.split(" ");
                    int num = Integer.parseInt(numbers[col]);
                    mapTileNum[col][row] = num;
                    col++;

                }//End of Inner-While Loop

                if (col == gamePanel.MAX_WORLD_COL) {

                    col = 0;
                    row++;

                }//End of If Statement

            }//End of While Loop

            reader.close();


        } catch (IOException e) {

            log.error("""
                    Map file couldn't be loaded or was read improperly. This could be due to a variety of reasons |
                    
                    1. The map file is missing spaces in between identifying numbers and was therefore unreadable
                    
                    2. The path to the file isn't correct, everything is case sensitive
                    
                    3. Every map file must provide values for every space, meaning a {} space must have a value for it (even if there is nothing there)""", gamePanel.WORLD_DIMENSIONS);

        }//End of Try-Catch Statement

    }//End of Class

}//End of Class
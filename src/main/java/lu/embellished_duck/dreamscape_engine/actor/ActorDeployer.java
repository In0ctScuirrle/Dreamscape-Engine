package lu.embellished_duck.dreamscape_engine.actor;

import lombok.extern.slf4j.Slf4j;
import lu.embellished_duck.dreamscape_engine.actor.objects.SuperObject;
import lu.embellished_duck.dreamscape_engine.core.GamePanel;
import lu.embellished_duck.dreamscape_engine.util.ImageHelper;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.Objects;

import static lu.embellished_duck.dreamscape_engine.core.GamePanel.SCALE;
import static lu.embellished_duck.dreamscape_engine.core.GamePanel.TILE_SIZE;
import static lu.embellished_duck.dreamscape_engine.util.ErrorFormats.textureLoadingError;

/**
 * Deploys actors into the game world on boot, at least the ones available in the current world.
 *
 * @since 0.1.0
 *
 * @author Will Blanchard
 */
@Slf4j
public class ActorDeployer {

    //=======================
    // INSTANTIATE VARIABLES
    //=======================
    private static ActorDeployer instance = null;

    private GamePanel gamePanel;


    //=============
    // CONSTRUCTOR
    //=============
    public ActorDeployer(GamePanel gamePanel) {

        this.gamePanel = gamePanel;

    }//End of Constructor


    /**
     * Deploys the objects into the game world
     */
    public void deployObjects() {

        initializeObject(0, "four_panel_door", 6 * TILE_SIZE, 3 * TILE_SIZE, (int) (256 * SCALE), (int) (256 * SCALE), TILE_SIZE * 2, TILE_SIZE * 2, true);

    }//End of Method


    /**
     * Modified version of the {@code initializeTile()} function in {@code TileManager} which handles the instantiation of tiles
     *
     * @param index The super object library index that the super object will instantiate in.
     * @param name The name of the super object
     * @param xPos The x position of this object within the game world
     * @param yPos The y position of this object within the game world
     * @param scaledWidth The width value for the scale object, can be a custom value but usually recommended to be dealt with as the resolution value times the scale or units of tile size.
     * @param scaledHeight The height value for the scale object, same rules apply as with the {@code scaledWidth}.
     * @param hitBoxWidth The width of the super object's hit box
     * @param hitBoxHeight The height of the super object's hit box
     * @param collision Whether this object should use collision or not, most likely should be set to true.
     *
     * @since 0.2.0
     *
     * @author Will Blanchard
     */
    private void initializeObject(int index, String name, int xPos, int yPos, int scaledWidth, int scaledHeight, int hitBoxWidth, int hitBoxHeight, boolean collision) {

        ImageHelper imageHelper = ImageHelper.INSTANCE;

        try {

            gamePanel.objects[index] = new SuperObject(name, hitBoxWidth, hitBoxHeight, collision);
            gamePanel.objects[index].setImage(ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/textures/object/" + name + ".png"))));
            gamePanel.objects[index].setImage(imageHelper.scaleImage(gamePanel.objects[index].getImage(), scaledWidth, scaledHeight));
            gamePanel.objects[index].setCollision(collision);
            gamePanel.objects[index].setWorldX(xPos);
            gamePanel.objects[index].setWorldY(yPos);

        } catch (IOException ex) {

            log.error(textureLoadingError(index));

        }//End of Try-Catch Statement

    }//End of Method


    //==================
    // SINGLETON GETTER
    //==================
    public static synchronized ActorDeployer getInstance(GamePanel gamePanel) {

        if (instance == null) {instance = new ActorDeployer(gamePanel);}

        return instance;

    }//End of Singleton Getter

}//End of Class
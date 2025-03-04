package lu.embellished_duck.dreamscape_engine.world;

import lombok.Getter;
import lombok.Setter;

import java.awt.image.BufferedImage;

/**
 * A holder class for tiles in the game world
 *
 * @since 0.1.0
 *
 * @author Will Blanchard
 */
@Getter
@Setter
public class Tile {

    private boolean collision = false;

    private BufferedImage image;

}//End of Class
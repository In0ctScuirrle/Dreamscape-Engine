package lu.embellished_duck.dreamscape_engine.util;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * A helper primarily for {@Code TileManager}, but can also be used with other classes. It deals with the scaling of textures and providing it in an abstract nature so it can be highly applicable
 *
 * @since 0.2.0
 *
 * @author Will Blanchard
 */
public enum ImageHelper {

    INSTANCE;

    /**
     * Takes in a texture which has been loaded and scales it so it can be applied to something in the game.
     *
     * @param originalImage The original texture
     * @param width The scaled width of the texture
     * @param height The scaled height of the texture
     * @return A scaled texture
     */
    public BufferedImage scaleImage(BufferedImage originalImage, int width, int height) {

        BufferedImage scaledImaged = new BufferedImage(width, height, originalImage.getType());
        Graphics2D graphics2D = scaledImaged.createGraphics();
        graphics2D.drawImage(originalImage, 0, 0, width, height, null);
        graphics2D.dispose();

        return scaledImaged;

    }//End of Helper Method

}//End of Enumerated Class
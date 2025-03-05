package lu.embellished_duck.dreamscape_engine.actor.objects;

import lombok.Getter;
import lombok.Setter;
import lu.embellished_duck.dreamscape_engine.core.GamePanel;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Provides and abstract parent for all types of objects in a game, objects with custom properties (such as a special interaction event) should inherit this class and then implement their custom functionality
 *
 * @since 0.1.0
 *
 * @author Will Blanchard
 */
@Getter
@Setter
public class SuperObject {

    //=======================
    // INSTANTIATE VARIABLES
    //=======================
    private final String name;

    private int worldX;
    private int worldY;
    private int hitBoxWidth;
    private int hitBoxHeight;

    private boolean collision = false;

    private BufferedImage image;

    private Rectangle hitBox = new Rectangle(0, 0, 96, 96);


    //=============
    // CONSTRUCTOR
    //=============
    public SuperObject(String name, int realWidth, int realHeight, boolean collision) {

        this.name = name;

        this.hitBoxWidth = realWidth;
        this.hitBoxHeight = realHeight;

        this.collision = collision;

        hitBox.setSize((int) (realWidth * GamePanel.SCALE), (int) (realHeight * GamePanel.SCALE));

    }//End of Constructor


    /**
     * Draws the super object on the screen
     *
     * @param graphics2D Graphics which are passed from the {@code GamePanel}
     *
     * @since 0.1.0
     *
     * @author Will Blanchard
     */
    public void draw(Graphics2D graphics2D, GamePanel gamePanel) {

        int screenX = worldX - gamePanel.getPlayer().worldX + gamePanel.getPlayer().screenX;
        int screenY = worldY - gamePanel.getPlayer().worldY + gamePanel.getPlayer().screenY;

        //This If statement ensures that off-screen tiles are not rendered to save on performance (Cause no GPU access cause no LWJGL or JOGL
        if (worldX + GamePanel.TILE_SIZE > gamePanel.getPlayer().worldX - gamePanel.getPlayer().screenX
                || worldX - GamePanel.TILE_SIZE > gamePanel.getPlayer().worldX + gamePanel.getPlayer().screenX
                || worldY + GamePanel.TILE_SIZE > gamePanel.getPlayer().worldY - gamePanel.getPlayer().screenY
                || worldY - GamePanel.TILE_SIZE > gamePanel.getPlayer().worldY + gamePanel.getPlayer().screenY) {

            graphics2D.drawImage(image, screenX, screenY, null);

        }//End of If Statement

    }//End of Method

}//End of Class
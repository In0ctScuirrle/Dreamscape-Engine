package lu.embellished_duck.dreamscape_engine.actor;

import lu.embellished_duck.dreamscape_engine.core.GamePanel;
import lu.embellished_duck.dreamscape_engine.input.KeyHandler;

import java.awt.*;

/**
 * Represents the entity which the player controls.
 *
 * @since 0.1.0
 *
 * @author Will Blanchard
 *
 */
public class Player extends Entity {

    //======================
    // Instantiate Variables
    //======================
    private static Player instance = null;

    KeyHandler keyHandler;

    //These are hardcoded values that tell the program where on the screen the player character should start when they enter the game
    public final int screenX;
    public final int screenY;

    private boolean showHitBox = false;


    //=============
    // CONSTRUCTOR
    //=============
    private Player(GamePanel gamePanel, KeyHandler keyHandler) {
        //affinityMap.put(Affinity.ACID, 50);

        super(gamePanel, Type.PLAYER);
        this.keyHandler = keyHandler;

        //This logic makes that player always start in the middle of the screen
        screenX = gamePanel.SCREEN_WIDTH / 2 - (gamePanel.TILE_SIZE / 2);
        screenY = gamePanel.SCREEN_HEIGHT / 2 - (gamePanel.REAL_TILE_SIZE / 2);

        setHitBox(new Rectangle());
        getHitBox().x = 8;
        getHitBox().y = 12;
        setSolidAreaDefaultX(getHitBox().x);
        setSolidAreaDefaultY(getHitBox().y);
        getHitBox().setSize(80, 170);

        initialize();

    }//End of Constructor


    /**
     * Initializes the player and spawns them in the position recorded by the save file
     *
     * @since 0.1.0
     *
     * @author Will Blanchard
     *
     */
    public void initialize() {

        setMovementSpeed(4);

        //Setting the player's default position
        worldX = gamePanel.TILE_SIZE * 4;
        worldY = gamePanel.TILE_SIZE * 4;

        getPlayerImages();

    }//End of Method


    /**
     * Gets and cycles the player's animation frames
     *
     * @since 0.2.0
     *
     * @author Will Blanchard
     */
    private void getPlayerImages() {

        //Stuff will go here eventually

    }//End of Method


    /**
     * Updates the player based on keyboard inputs and external events
     *
     * @since 0.1.0
     *
     * @author Will Blanchard
     */
    public void update() {

        int originalWorldX = this.worldX;
        int originalWorldY = this.worldY;

        if (keyHandler.upPressed || keyHandler.downPressed || keyHandler.leftPressed || keyHandler.rightPressed) {

            if (keyHandler.upPressed) {
                setDirection(Direction.UP);
                worldY -= getMovementSpeed();
            }
            if (keyHandler.downPressed) {
                setDirection(Direction.DOWN);
                worldY += getMovementSpeed();
            }
            if (keyHandler.leftPressed) {
                setDirection(Direction.LEFT);
                worldX -= getMovementSpeed();
            }
            if (keyHandler.rightPressed) {
                setDirection(Direction.RIGHT);
                worldX += getMovementSpeed();
            }

            //Handle collision
            setCollisionOn(false);
            gamePanel.getCollisionDetector().checkTileCollision(this);

            if (isCollisionOn()) {
                this.setWorldX(originalWorldX);
                this.setWorldY(originalWorldY);
            }//End of If Statement

        }//End of If Statement

    }//End of Method


    /**
     * Draws the player on the screen
     * @param graphics2D Graphics which are passed from the {@code GamePanel}
     *
     * @since 0.1.0
     *
     * @author Will Blanchard
     */
    public void draw(Graphics2D graphics2D) {

        graphics2D.setColor(Color.white);
        graphics2D.fillRect(screenX, screenY, gamePanel.TILE_SIZE, gamePanel.TILE_SIZE * 2);

        if (showHitBox) {

            graphics2D.setColor(Color.RED);
            graphics2D.drawRect(screenX + getHitBox().x, screenY + getHitBox().y, getHitBox().width, getHitBox().height);

        }//End of If Statement

    }//End of Method


    //==================
    // SINGLETON GETTER
    //==================
    public static synchronized Player getInstance(GamePanel gamePanel) {

        if (instance == null) {

            instance = new Player(gamePanel, KeyHandler.getInstance(gamePanel));

        }//End of If Statement

        return instance;

    }//End of Singleton Getter

}//End of Class
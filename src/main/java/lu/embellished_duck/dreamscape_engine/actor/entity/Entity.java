package lu.embellished_duck.dreamscape_engine.actor.entity;

import lombok.Getter;
import lombok.Setter;
import lu.embellished_duck.dreamscape_engine.core.GamePanel;

import java.awt.*;

/**
 * Represents an actor which can move and holds stats, can usually be interacted with by the player and environment
 *
 * @since 0.1.0
 *
 * @author Will Blanchard
 */
@Getter
@Setter
public class Entity {

    //======================
    // Instantiate Variables
    //======================
    protected GamePanel gamePanel;

    //Position in the world
    public int worldX, worldY;


    //Animation Variables
    private Direction direction;

    public enum Direction {

        UP,
        DOWN,
        LEFT,
        RIGHT

    };//End of Enumeration


    //Collision Variables
    private Rectangle hitBox;
    private int solidAreaDefaultX, solidAreaDefaultY;
    private boolean collisionOn = false;


    //Enumerated type to identify what the entity is
    private Type entityType;

    public enum Type{

        PLAYER,
        NPC,
        ENEMY,
        BOSS;

    }//End of Enumerated Type

    private int movementSpeed;


    //=============
    // CONSTRUCTOR
    //=============
    public Entity(GamePanel gamePanel, Type type) {

        this.gamePanel = gamePanel;
        this.entityType = type;

    }//End of Constructor

}//End of Class
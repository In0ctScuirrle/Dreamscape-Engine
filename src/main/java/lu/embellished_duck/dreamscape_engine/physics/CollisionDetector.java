package lu.embellished_duck.dreamscape_engine.physics;

import lu.embellished_duck.dreamscape_engine.actor.entity.Entity;
import lu.embellished_duck.dreamscape_engine.core.GamePanel;

import static lu.embellished_duck.dreamscape_engine.core.GamePanel.TILE_SIZE;

public class CollisionDetector {

    //=======================
    // INSTANTIATE VARIABLES
    //=======================
    private GamePanel gamePanel;


    //=============
    // CONSTRUCTOR
    //=============
    public CollisionDetector(GamePanel gamePanel) {

        this.gamePanel = gamePanel;

    }//End of Constructor


    /**
     * Checks for tile-entity collision. The math behind it is kind of complicated, but it does work quite well
     *
     * @since 0.1.0
     *
     * @author Will Blanchard
     *
     * @param entity The entity for which to check for collision
     */
    public void checkTileCollision(Entity entity) {

        //----------------------------------------------------
        // FIRST UP THE ROW AND COL OF THE HIT-BOX ARE NEEDED
        //----------------------------------------------------
        int entityLeftWorldX = entity.worldX + entity.getHitBox().x;
        int entityRightWorldX = entity.worldX + entity.getHitBox().x + entity.getHitBox().width;
        int entityTopWorldY = entity.worldY + entity.getHitBox().y;
        int entityBottomWorldY = entity.worldY + entity.getHitBox().y + entity.getHitBox().height;

        int entityLeftCol = entityLeftWorldX / TILE_SIZE;
        int entityRightCol = entityRightWorldX / TILE_SIZE;
        int entityTopRow = entityTopWorldY / TILE_SIZE;
        int entityBottomRow = entityBottomWorldY / TILE_SIZE;

        int tileNum1, tileNum2;

        switch (entity.getDirection()) {

            case UP :

                entityTopRow = (entityTopWorldY - entity.getMovementSpeed()) / TILE_SIZE;

                //This essentially predicts where the player will be and checks for collision with the if statement
                tileNum1 = gamePanel.getTileManager().getWorldManager().mapTileNum[entityLeftCol][entityTopRow];
                tileNum2 = gamePanel.getTileManager().getWorldManager().mapTileNum[entityRightCol][entityTopRow];

                if (gamePanel.getTileManager().tileLibrary[tileNum1].isCollision() || gamePanel.getTileManager().tileLibrary[tileNum2].isCollision()) {

                    entity.setCollisionOn(true);

                }//End of If Statement

                break;

            case DOWN :

                entityBottomRow = (entityBottomWorldY + entity.getMovementSpeed()) / TILE_SIZE;

                tileNum1 = gamePanel.getTileManager().getWorldManager().mapTileNum[entityLeftCol][entityBottomRow];
                tileNum2 = gamePanel.getTileManager().getWorldManager().mapTileNum[entityRightCol][entityBottomRow];

                if (gamePanel.getTileManager().tileLibrary[tileNum1].isCollision() || gamePanel.getTileManager().tileLibrary[tileNum2].isCollision()) {

                    entity.setCollisionOn(true);

                }//End of If Statement

                break;

            case LEFT :

                entityLeftCol = (entityLeftWorldX - entity.getMovementSpeed()) / TILE_SIZE;

                tileNum1 = gamePanel.getTileManager().getWorldManager().mapTileNum[entityLeftCol][entityTopRow];
                tileNum2 = gamePanel.getTileManager().getWorldManager().mapTileNum[entityLeftCol][entityBottomRow];

                if (gamePanel.getTileManager().tileLibrary[tileNum1].isCollision() || gamePanel.getTileManager().tileLibrary[tileNum2].isCollision()) {

                    entity.setCollisionOn(true);

                }//End of If Statement

                break;

            case RIGHT :

                entityRightCol = (entityRightWorldX + entity.getMovementSpeed()) / TILE_SIZE;

                tileNum1 = gamePanel.getTileManager().getWorldManager().mapTileNum[entityRightCol][entityTopRow];
                tileNum2 = gamePanel.getTileManager().getWorldManager().mapTileNum[entityRightCol][entityBottomRow];

                if (gamePanel.getTileManager().tileLibrary[tileNum1].isCollision() || gamePanel.getTileManager().tileLibrary[tileNum2].isCollision()) {

                    entity.setCollisionOn(true);

                }//End of If Statement

                break;

        }//End of Switch-Case Statement

    }//End of Method

}//End of Class
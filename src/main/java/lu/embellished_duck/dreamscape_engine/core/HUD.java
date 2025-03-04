package lu.embellished_duck.dreamscape_engine.core;

import java.awt.*;
import java.text.DecimalFormat;

public class HUD {

    //=======================
    // INSTANTIATE VARIABLES
    //=======================
    private static HUD instance = null;

    private String notification = "";

    private int notificationDuration = 0;

    private double playTime = 0;
    private DecimalFormat playtimeFormat = new DecimalFormat("#0.00");

    private boolean notificationOn = false;

    private GamePanel gamePanel;

    private Graphics2D graphics2D;

    private Font aerial_40;


    //=============
    // CONSTRUCTOR
    //=============
    private HUD(GamePanel gamePanel) {

        this.gamePanel = gamePanel;

        this.aerial_40 = new Font("Aerial", Font.PLAIN, 40);

    }//End of Constructor


    /**
     * Creates and displays a notification on the screen, can be called by various events.
     *
     * @since 0.4.0
     *
     * @author Will Blanchard
     *
     * @param text The content of the notification being sent
     */
    public void showNotification(String text) {

        this.notification = text;
        this.notificationOn = true;

    }//End of Method


    /**
     * Draws HUD elements on the screen
     *
     * @param graphics2D Graphics which are passed from the {@code GamePanel}
     */
    public void draw(Graphics2D graphics2D) {

        this.graphics2D = graphics2D;

        graphics2D.setFont(aerial_40);
        graphics2D.setColor(Color.yellow);

        if (gamePanel.gameState.equals(GamePanel.GameState.PLAY)) {

            //Some stuff here

        }//End of If Statement

        if (gamePanel.gameState.equals(GamePanel.GameState.PAUSE)) {

            drawPauseScreen();

        }//End of If Statement

    }//End of Method


    /**
     * Helper function which draws the pause screen, these types of things require a custom method to be written rather than
     * using abstract methods (Though I could probably achieve that if I tried hard enough.
     *
     * @since 0.4.0
     *
     * @author Will Blanchard
     */
    private void drawPauseScreen() {

        graphics2D.setFont(graphics2D.getFont().deriveFont(Font.BOLD, 80));
        String text = "PAUSE";
        int x = getXValueForCenteredText(text);
        int y = gamePanel.SCREEN_HEIGHT / 2;

        graphics2D.drawString(text, x, y);

    }//End of Helper Method


    /**
     * Calculates and returns the x position that HUD text should be located on the screen based on it's bounds
     *
     * @return The calculated x position
     *
     * @since 0.4.0
     *
     * @author Will Blanchard
     *
     */
    public int getXValueForCenteredText(String text) {

        return gamePanel.SCREEN_WIDTH / 2 - ((int) graphics2D.getFontMetrics().getStringBounds(text, graphics2D).getWidth()) / 2;

    }//End of Method


    //==================
    // SINGLETON GETTER
    //==================
    public static synchronized HUD getInstance(GamePanel gamePanel) {

        if (instance == null) {

            instance = new HUD(gamePanel);

        }

        return instance;

    }//End of Singleton Getter

}//End of Enumerated Class
package lu.embellished_duck.dreamscape_engine.core;

import lombok.extern.slf4j.Slf4j;

import javax.swing.*;

/**
 * @author Will Blanchard
 *
 * @since 0.1.0
 */
@Slf4j
public class Main {

    //=============
    // MAIN METHOD
    //=============
    public static void main(String args[]) {

        log.info("Launching Dreamscape Engine...");

        //Creating the game window
        JFrame window = new JFrame("Dreamscape Game");

        GamePanel gamePanel = new GamePanel();
        window.add(gamePanel);

        //Configuring the window
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.pack();
        window.setLocationRelativeTo(null);
        window.setVisible(true);

        gamePanel.initializeGame();

        log.info("Game Started for Dreamscape Engine");

    }//End of Main Method

}//End of Class
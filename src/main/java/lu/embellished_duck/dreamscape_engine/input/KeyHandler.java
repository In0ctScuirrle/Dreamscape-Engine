package lu.embellished_duck.dreamscape_engine.input;

import lu.embellished_duck.dreamscape_engine.core.GamePanel;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {

    //=======================
    // INSTANTIATE VARIABLES
    //=======================
    private GamePanel gamePanel;

    private static KeyListener instance = null;

    public boolean upPressed;
    public boolean downPressed;
    public boolean leftPressed;
    public boolean rightPressed;


    //=============
    // CONSTRUCTOR
    //=============
    private KeyHandler(GamePanel gamePanel) {

        this.gamePanel = gamePanel;

    }//End of Constructor


    @Override
    public void keyTyped(KeyEvent e) {}


    @Override
    public void keyPressed(KeyEvent e) {

        int code = e.getKeyCode();

        if(code == KeyEvent.VK_W) {
            upPressed = true;
        }
        if (code == KeyEvent.VK_S) {
            downPressed = true;
        }
        if (code == KeyEvent.VK_A) {
            leftPressed = true;
        }
        if (code == KeyEvent.VK_D) {
            rightPressed = true;
        }
        if (code == KeyEvent.VK_ESCAPE) {
            if (gamePanel.gameState.equals(GamePanel.GameState.PLAY)) {

                gamePanel.gameState = GamePanel.GameState.PAUSE;

            } else if (gamePanel.gameState.equals(GamePanel.GameState.PAUSE)) {

                gamePanel.gameState = GamePanel.GameState.PLAY;

            }//End of If-Else-If Statement
        }//End of If Statement

    }//End of Method


    @Override
    public void keyReleased(KeyEvent e) {

        int code = e.getKeyCode();

        if(code == KeyEvent.VK_W) {
            upPressed = false;
        }
        if (code == KeyEvent.VK_S) {
            downPressed = false;
        }
        if (code == KeyEvent.VK_A) {
            leftPressed = false;
        }
        if (code == KeyEvent.VK_D) {
            rightPressed = false;
        }

    }//End of Method


    //==================
    // SINGLETON GETTER
    //==================
    public static synchronized KeyHandler getInstance(GamePanel gamePanel) {

        if (instance == null) {

            instance = new KeyHandler(gamePanel);

        }//End of If Statement

        return (KeyHandler) instance;

    }//End of Singleton Getter


}//End of Class
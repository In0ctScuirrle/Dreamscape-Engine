package lu.embellished_duck.dreamscape_engine.core;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import lu.embellished_duck.dreamscape_engine.actor.ActorDeployer;
import lu.embellished_duck.dreamscape_engine.actor.entity.Player;
import lu.embellished_duck.dreamscape_engine.actor.objects.SuperObject;
import lu.embellished_duck.dreamscape_engine.input.KeyHandler;
import lu.embellished_duck.dreamscape_engine.output.Audio;
import lu.embellished_duck.dreamscape_engine.physics.CollisionDetector;
import lu.embellished_duck.dreamscape_engine.world.TileManager;

import javax.swing.*;
import java.awt.*;

/**
 * Acts as a central logic point for everything in the game. Problems usually stemmed from here.
 *
 * @author Will Blanchard
 *
 * @since 0.1.0
 */
@Slf4j
@Getter
public class GamePanel extends JPanel implements Runnable {

    //===================
    // GRAPHICS SETTINGS
    //===================
    public static final int REAL_TILE_SIZE = 128;//The default tile resolution, separate from objects. (Texture resolution)
    public static final float SCALE = 0.75f;//The scale by which to multiply the REAL_TILE_SIZE.
    public static final int TILE_SIZE = Math.round(REAL_TILE_SIZE * SCALE);


    //The maximum number of tile rows and columns that can be displayed on the screen.
    public final int MAX_TILE_COL = 15;
    public final int MAX_TILE_ROW = 10;


    //Screen Dimensions
    public final int SCREEN_WIDTH = TILE_SIZE * MAX_TILE_COL;
    public final int SCREEN_HEIGHT = TILE_SIZE * MAX_TILE_ROW;


    //Immutable because this game is only 2D. So it's not like it's performance needs to be limited
    public final int FPS = 120;


    //=================
    // WORLD SETTINGS
    //=================

    //Integers representing the maximum number of tiles across and down a map
    public final int MAX_WORLD_ROW = 15;
    public final int MAX_WORLD_COL = 17;

    public final int WORLD_WIDTH = TILE_SIZE * MAX_WORLD_COL;
    public final int WORLD_HEIGHT = TILE_SIZE * MAX_WORLD_ROW;

    public final String WORLD_DIMENSIONS = MAX_WORLD_COL + " x " + MAX_WORLD_ROW;


    //==================
    // SYSTEM VARIABLES
    //==================
    private final ActorDeployer actorDeployer = ActorDeployer.getInstance(this);
    private final Audio music = new Audio();
    private final Audio soundEffects = new Audio();
    private final CollisionDetector collisionDetector = new CollisionDetector(this);
    private final HUD headsUpDisplay = HUD.getInstance(this);
    private final TileManager tileManager = new TileManager(this);
    private final KeyHandler keyHandler = KeyHandler.getInstance(this);

    private Thread gameThread;

    public final SuperObject[] objects = new SuperObject[10];


    //=============================
    // ENTITY AND OBJECT VARIABLES
    //=============================
    private final Player player = Player.getInstance(this);


    //======================
    // GAME STATE VARIABLES
    //======================
    public GameState gameState;
    public enum GameState {

        PLAY,
        PAUSE

    }//End of Enumeration


    //=============
    // CONSTRUCTOR
    //=============
    public GamePanel() {

        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        this.setBackground(Color.BLACK);

        //This allows all drawing in this component to be done by an off-screen painting buffer, improving render performance
        this.setDoubleBuffered(true);

        this.addKeyListener(keyHandler);
        this.setFocusable(true);

    }//End of Constructor


    /**
     * Method which initializes the game and engine
     *
     * @author Will Blanchard
     *
     * @since 0.1.0
     */
    public void initializeGame() {

        //playMusic(11);
        gameState = GameState.PLAY;

        actorDeployer.deployObjects();

        start();

    }//End of Method


    /**
     * Initializes the game's thread and starts it, sends feedback to the logger.
     *
     * @since 0.1.0
     *
     * @author Will Blanchard
     */
    private void start() {

        gameThread = new Thread(this);
        gameThread.start();
        log.info("Game has booted successfully!");

    }//End of Method


    /**
     * Manages the delta time variable for drawing the frames.
     *
     * @since 0.1.0
     *
     * @author Will Blanchard
     */
    @Override
    public void run() {

        //-----------------
        // Time Variables
        //-----------------

        double drawInterval = (double) 1000000000 / FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;

        while (gameThread != null) {

            currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / drawInterval;
            lastTime = currentTime;

            if (delta >= 1) {

                update();
                repaint();
                delta--;

            }//End of If Statement

        }//End of While Loop

    }//End of Method


    /**
     * This function is in charge of updating the entities and changes which occur in the world. All .update() methods should be called in here.
     *
     * @since 0.1.0
     *
     * @author Will Blanchard
     */
    public void update() {

        if (gameState.equals(GameState.PLAY)) {
            player.update();
        }

        if (gameState.equals(GameState.PAUSE)) {
            //Something
        }

    }//End of Method


    /**
     * This function on the other hand deals with painting everything on the screen, it does so using CPU resources so not the best for large scale projects. But it works nonetheless
     *
     * @since 0.1.0
     *
     * @author Will Blanchard
     *
     * @param graphics the <code>Graphics</code> object required to paint components on the screen
     */
    public void paintComponent(Graphics graphics) {

        //Calling the same method from the parent class of JPanel
        super.paintComponent(graphics);

        //Type casting the graphics as an instance of 2-Dimensional graphics
        Graphics2D graphics2D = (Graphics2D) graphics;

        //It is paramount to know that the background tiles MUST be painted BEFORE everything else.
        //Otherwise, there will be issues with layers

        tileManager.draw(graphics2D);

        for (SuperObject superObject : objects) {

            if (superObject != null) {superObject.draw(graphics2D, this);}

        }//End of For-Each Loop

        player.draw(graphics2D);

        headsUpDisplay.draw(graphics2D);

        graphics2D.dispose();

    }//End of Method


    /**
     * Calls and plays a musical track.
     *
     * @param i The soundtrack's music library index number
     *
     * @since 0.3.0
     *
     * @author Will Blanchard
     */
    public void playMusic(int i) {

        music.setFile(i);
        music.play();
        music.loop();

    }//End of Method


    /**
     * Stops the musical track currently being played
     *
     * @since 0.3.0
     *
     * @author Will Blanchard
     */
    public void stopMusic() {

        music.stop();

    }//End of Method


    /**
     * Calls and plays a sound effect.
     * @param i The effect's sound library index number
     *
     * @since 0.3.0
     *
     * @author Will Blanchard
     */
    public void playSoundEffect(int i) {

        soundEffects.setFile(i);
        soundEffects.play();

    }//End of Method

}//End of Class
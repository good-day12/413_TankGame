package TankGame.game;


import TankGame.*;
import TankGame.ResourceHandler.GameConstants;
import TankGame.ResourceHandler.Resources;
import TankGame.ResourceHandler.Sound;
import TankGame.game.GameObjects.GameObject;
import TankGame.game.UI.Camera;
import TankGame.game.UI.UserInterface;
import TankGame.game.GameObjects.mobile.Bullet;
import TankGame.game.GameObjects.mobile.TankControl;
import TankGame.game.GameObjects.mobile.Tank;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;


/**
 * @author anthony-pc
 */
public class GameWorld extends JPanel implements Runnable {

    private BufferedImage world;
    private Tank t1;
    private Tank t2;
    private Camera cam;
    private Launcher lf;
    private long tick = 0;
    private Sound bgMusic;
    private List<GameObject> gameObjects = new ArrayList<>(500);
    private boolean resetGame = false;
    UserInterface ui = new UserInterface();


    /**
     * 
     * @param lf
     */
    public GameWorld(Launcher lf) {
        this.lf = lf;
    }

    /**
     * Run the game, check if we need to reset, update the game, check for collisions, remove if objects have collided,
     * redraw the game,
     */
    @Override
    public void run() {
        try {
            //run background music while game is running
            bgMusic = Resources.getSound("bg");
            bgMusic.setVolume(0.5f);
            bgMusic.setLooping();
            bgMusic.playSound();

            while (true) {
                //check if we need to reset game
                if (this.resetGame) this.resetGame();

                this.updateGame();

                GameObject.collisionChecks(gameObjects);    //collision checks

                gameObjects.removeIf(go -> go.isHasCollided()); //remove all objects that have collided

                this.repaint();   // redraw game

                /*
                 * Sleep for 1000/144 ms (~6.9ms). This is done to have our 
                 * loop run at a fixed rate per/sec. 
                */
                Thread.sleep(1000 / 144);
            }
        } catch (InterruptedException ignored) {
            System.out.println(ignored);
        }
    }

    /**
     * Reset game to its initial state.
     */
    public void resetGame() {
        //reset ticks
        this.tick = 0;

        this.InitializeGame();
        resetGame = false;
    }

    /**
     * Load all resources for Tank Wars Game, set all Game Objects to their
     * initial state, create two tanks and add them to the game objects list,
     * create camera object and assign them to tanks and vice versa
     */
    public void InitializeGame() {
        this.world = new BufferedImage(GameConstants.WORLD_WIDTH,
                GameConstants.WORLD_HEIGHT,
                BufferedImage.TYPE_INT_RGB);

        //load gameObjects with objects from our maps
        gameObjects = ui.createGameObjectsList();

        //first tank: 300, 300      starting for second tank would be x = 1200 (30) y = 1350 (45)

        //ADD FIRST TANK
        t1 = new Tank(300, 300, 0, 0, (short) 0, 1, Resources.getSprite("tank1"));
        TankControl tc1 = new TankControl(t1, KeyEvent.VK_W, KeyEvent.VK_S, KeyEvent.VK_A, KeyEvent.VK_D, KeyEvent.VK_SPACE);
        this.lf.getJf().addKeyListener(tc1);
        gameObjects.add(t1);

        //ADD SECOND TANK
        t2 = new Tank(1200, 1350, 0, 0, (short) 0, 2, Resources.getSprite("tank2"));
        TankControl tc2 = new TankControl(t2, KeyEvent.VK_UP, KeyEvent.VK_DOWN, KeyEvent.VK_LEFT, KeyEvent.VK_RIGHT, KeyEvent.VK_SHIFT);
        this.lf.getJf().addKeyListener(tc2);
        gameObjects.add(t2);

        //add camera object
        cam = new Camera(t1, t2);

        //attach camera object to tanks
        t1.setCam(cam);
        t2.setCam(cam);
    }

    /**
     * Paint/draw the game to the world BufferedImage
     * @param g
     */
    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        Graphics2D buffer = world.createGraphics();
        ui.drawFloor(buffer);

        for (GameObject gameObject : gameObjects) {
            gameObject.drawImage(buffer);
        }

        this.t1.drawImage(buffer);
        this.t2.drawImage(buffer);

        this.cam.drawSplitScreen(g2, world);
        this.ui.drawMiniMap(g2, world);
    }

    /**
     * So functions outside of GameWorld can add  objects to GameObject as necessary
     * @param g - item to be added to gameObjects list
     */
    public void addGameObject(GameObject g) { this.gameObjects.add(g); }

    /**
     * To update the game by updating the tanks, checking for reset game, and updating the bullets in gameObjects
     * as well
     */
    private void updateGame(){
        //update tick
        this.tick++;
        //update tanks, if returns false, tank is dead, end game
        if ( !this.t1.update(this) ||
                !this.t2.update(this)){
            this.resetGame = true;
            this.lf.setFrame("end");
            Thread.currentThread().stop();
        }

        //update bullets
        for(int i = 0; i < gameObjects.size(); i++){
            if (gameObjects.get(i) instanceof Bullet){
                ((Bullet) gameObjects.get(i)).update();
                if (((Bullet) gameObjects.get(i)).checkBorder()) gameObjects.remove(i);
            }
        }
    }
}

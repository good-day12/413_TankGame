/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TankGame.game;


import TankGame.*;
import TankGame.ResourceHandler.GameConstants;
import TankGame.ResourceHandler.Resources;
import TankGame.ResourceHandler.Sound;
import TankGame.game.GameObjects.GameObject;
import TankGame.game.GameObjects.mobile.Bullet;
import TankGame.game.GameObjects.mobile.TankControl;
import TankGame.game.GameObjects.mobile.Tank;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
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

    /**
     * 
     * @param lf
     */
    public GameWorld(Launcher lf) {
        this.lf = lf;
    }

    @Override
    public void run() {
        try {
            //run background music while game is running
//            bgMusic = Resources.getSound("bg");
//            bgMusic.setVolume(0.5f);
//            bgMusic.setLooping();
//            bgMusic.playSound();

            while (true) {
                //check if we need to reset game
                if (this.resetGame) this.resetGame();
                //update tick
                this.tick++;
                //update tanks, if returns false, tank is dead, end game
                if ( !this.t1.update(this) ||
                !this.t2.update(this)){
                    this.resetGame = true;
                    this.lf.setFrame("end");
                    Thread.currentThread().stop();
                }

                //update bullets, what if i made this a function in the bullet class?
                for(int i = 0; i < gameObjects.size(); i++){
                    if (gameObjects.get(i) instanceof Bullet){
                        ((Bullet) gameObjects.get(i)).update();
                        if (((Bullet) gameObjects.get(i)).checkBorder()) gameObjects.remove(i);
                    }
                }

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
        //reset game by resetting ticks, tanks, and remove all bullets from gameObjects list
        this.tick = 0;
        this.t1.resetTank();
        this.t2.resetTank();
        this.gameObjects.removeIf(a -> a instanceof Bullet);
        resetGame = false;
    }

    /**
     * Load all resources for Tank Wars Game. Set all Game Objects to their
     * initial state as well. will be ugly and big
     */
    public void InitializeGame() {
        this.world = new BufferedImage(GameConstants.WORLD_WIDTH,
                GameConstants.WORLD_HEIGHT,
                BufferedImage.TYPE_INT_RGB);

        try(BufferedReader mapReader = new BufferedReader(new InputStreamReader(GameWorld.class.getClassLoader().getResourceAsStream("Maps/map1.csv")))){
            for(int i = 0; mapReader.ready(); i++){
                String[] gameObjectNums = mapReader.readLine().split(",");
                for(int j = 0; j < gameObjectNums.length; j++){
                    if(gameObjectNums[j].equals("0")) continue;
                    String objectType = gameObjectNums[j];
                    this.gameObjects.add(GameObject.gameObjectFactory(objectType, i *30, j*30));
                }
            }
        } catch(IOException e){
            e.printStackTrace();
        }



        //ADD FIRST TANK
        t1 = new Tank(400, 400, 0, 0, (short) 0, 1, Resources.getSprite("tank1"));
        TankControl tc1 = new TankControl(t1, KeyEvent.VK_W, KeyEvent.VK_S, KeyEvent.VK_A, KeyEvent.VK_D, KeyEvent.VK_SPACE);
        this.lf.getJf().addKeyListener(tc1);
        gameObjects.add(t1);

        //ADD SECOND TANK
        t2 = new Tank(400, 400, 0, 0, (short) 0, 2, Resources.getSprite("tank2"));
        TankControl tc2 = new TankControl(t2, KeyEvent.VK_UP, KeyEvent.VK_DOWN, KeyEvent.VK_LEFT, KeyEvent.VK_RIGHT, KeyEvent.VK_SHIFT);
        this.lf.getJf().addKeyListener(tc2);
        gameObjects.add(t2);

        //add camera object
        cam = new Camera(t1, t2);

        //attach camera object to tanks
        t1.setCam(cam);
        t2.setCam(cam);

    }

    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        Graphics2D buffer = world.createGraphics();
        drawFloor(buffer);

        for (int i = 0; i < gameObjects.size(); i ++){
            gameObjects.get(i).drawImage(buffer);
        }

        this.t1.drawImage(buffer);
        this.t2.drawImage(buffer);
        //g2.drawImage(world, 0, 0, null);

        this.cam.drawSplitScreen(g2, world);
        drawMiniMap(g2, world);
    }

    void drawFloor(Graphics buffer){
        //320 is the width of our image
        for (int i = 0; i < GameConstants.WORLD_WIDTH; i += 320){
            for (int j = 0; j < GameConstants.WORLD_HEIGHT; j+= 240){
                buffer.drawImage(Resources.getSprite("floor"), i, j, null);
            }
        }
    }

    //can make it's own object
    void drawMiniMap(Graphics2D g, BufferedImage world){
        BufferedImage mm = world.getSubimage(0,0, GameConstants.WORLD_WIDTH, GameConstants.WORLD_HEIGHT);
        AffineTransform at = new AffineTransform();
        at.translate(GameConstants.GAME_SCREEN_WIDTH/2f - (GameConstants.WORLD_WIDTH * .2f)/2f,
                GameConstants.GAME_SCREEN_HEIGHT - (GameConstants.WORLD_HEIGHT * .2f));
        at.scale(.2, .2);
        g.drawImage(mm, at, null);
    }

    public void addGameObject(GameObject g) { this.gameObjects.add(g); }
}

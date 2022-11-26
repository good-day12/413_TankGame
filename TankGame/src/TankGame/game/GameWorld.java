/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TankGame.game;


import TankGame.GameConstants;
import TankGame.Launcher;
import TankGame.Resources;
import TankGame.game.GameObjects.GameObject;
import TankGame.game.GameObjects.mobile.TankControl;
import TankGame.game.GameObjects.stationary.Wall;
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

    private List<GameObject> gameObjects = new ArrayList<>(500);

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
            //this.resetGame();
            while (true) {
                this.tick++;
                this.t1.update(); // update tank
                this.t2.update(); // update tank



                GameObject.collisionChecks(gameObjects);
//                //COLLISIONSSSSSSSSSSSSSSS
//                //outer loop, things that initiate collisions,
//                //inner loop, things that get hit
//                for (int i =0; i < this.gameObjects.size(); i++){
//                    GameObject ob1 = this.gameObjects.get(i);
//                    if (ob1 instanceof Wall) continue; //powerups, etc
//                    for (int j = 0; j < this.gameObjects.size(); j++){
//                        if (i == j) continue;
//                        GameObject ob2 = this.gameObjects.get(j);
//                        if(ob1.getHitbox().intersects(ob2.getHitbox())){
//                            //do collision stuff
//                        }
//                    }
//                }




                this.repaint();   // redraw game
                
                /*
                 * Sleep for 1000/144 ms (~6.9ms). This is done to have our 
                 * loop run at a fixed rate per/sec. 
                */
                Thread.sleep(1000 / 144);

                /*
                 * simulate an end game event
                 * we will do this with by ending the game when ~8 seconds has passed.
                 * This will need to be changed since the will always close after 8 seconds
                 */
                if (this.tick >= 144 * 8) {
                    this.lf.setFrame("end");
                    return;
                }

            }
        } catch (InterruptedException ignored) {
            System.out.println(ignored);
        }
    }

    /**
     * Reset game to its initial state.
     */
    public void resetGame() {
        this.tick = 0;
        this.t1.setX(300);
        this.t1.setY(300);
    }

    /**
     * Load all resources for Tank Wars Game. Set all Game Objects to their
     * initial state as well. will be ugly and big
     */
    public void InitializeGame() {
        this.world = new BufferedImage(GameConstants.WORLD_WIDTH,
                GameConstants.WORLD_HEIGHT,
                BufferedImage.TYPE_INT_RGB);

        try(BufferedReader mapReader = new BufferedReader(new InputStreamReader(GameWorld.class.getClassLoader().getResourceAsStream("Maps/Book1.csv")))){
            for(int i = 0; mapReader.ready(); i++){
                String[] gameObjectNums = mapReader.readLine().split(",");
                for(int j = 0; j < gameObjectNums.length; j++){
                    String objectType = gameObjectNums[j];
                    this.gameObjects.add(GameObject.gameObjectFactory(objectType, i *30, j*30));
                }
            }
        } catch(IOException e){
            e.printStackTrace();
        }



        //ADD FIRST TANK
        t1 = new Tank(300, 300, 0, 0, (short) 0, Resources.getSprite("tank1"));
        TankControl tc1 = new TankControl(t1, KeyEvent.VK_W, KeyEvent.VK_S, KeyEvent.VK_A, KeyEvent.VK_D, KeyEvent.VK_SPACE);
        this.lf.getJf().addKeyListener(tc1);

        //ADD SECOND TANK
        t2 = new Tank(300, 300, 0, 0, (short) 0, Resources.getSprite("tank2"));
        TankControl tc2 = new TankControl(t2, KeyEvent.VK_UP, KeyEvent.VK_DOWN, KeyEvent.VK_LEFT, KeyEvent.VK_RIGHT, KeyEvent.VK_SHIFT);
        this.lf.getJf().addKeyListener(tc2);

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
            if (gameObjects.get(i) != null){
                gameObjects.get(i).drawImage(buffer);
            }
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

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TankGame.game;


import TankGame.GameConstants;
import TankGame.Launcher;
import TankGame.Resources;
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

    //DO NOT DO THIS JUST FOR TESTING
    private List<Wall> walls = new ArrayList<Wall>();

    //List<gameObjects> go

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

                //include centerscreen for camera
                //check for collisions
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
                String[] gameObjects = mapReader.readLine().split(",");
                for(int j = 0; j < gameObjects.length; j++){
                    String objectType = gameObjects[j];
                    //this.go.add(GameObject.gameObjectFactory(objectType, i *30, j*30));
                    //if("0".equals(objectType)) continue;
                    //or if(Objects.equals("0", objectType)) continue;
                    switch(objectType){
                        case "0" -> {}

                        case "2" -> {
                            //breakable wall
                            walls.add(new Wall(i*30, j*30, Resources.getSprite("break1")));

                        }

                        case "3", "9" -> {
                            //load unbreakable wall
                            walls.add(new Wall(i*30, j*30, Resources.getSprite("unbreak")));
                        }
                        //rest of the numbers will be powerups, and etc
                    }
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
//        buffer.setColor(Color.black);
//        buffer.fillRect(0,0, GameConstants.GAME_SCREEN_WIDTH, GameConstants.GAME_SCREEN_HEIGHT);

        //cant alter walls so forEach won't work for breakable
        this.walls.forEach(wall -> wall.drawImage(buffer));

        this.t1.drawImage(buffer);
        this.t2.drawImage(buffer);
        //g2.drawImage(world, 0, 0, null);

//        drawSplitScreen(g2, world);
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
//        BufferedImage mm = world.getSubimage(0,0, GameConstants.GAME_SCREEN_WIDTH, GameConstants.GAME_SCREEN_HEIGHT);

//        g.scale(.2,.2);
        AffineTransform at = new AffineTransform();
        at.translate(GameConstants.GAME_SCREEN_WIDTH/2f - (GameConstants.WORLD_WIDTH * .2f)/2f,
                GameConstants.GAME_SCREEN_HEIGHT - (GameConstants.WORLD_HEIGHT * .2f));
        at.scale(.2, .2);
        g.drawImage(mm, at, null);
    }

//can be made into a camera object, keeps tank in middle, keeps track of where tank is
    void drawSplitScreen(Graphics2D g, BufferedImage world){
        BufferedImage lh = world.getSubimage((int) t1.getScreenX(), //lh = left half
                (int) t1.getScreenY(),
                GameConstants.GAME_SCREEN_WIDTH/2,
                GameConstants.GAME_SCREEN_HEIGHT);

        BufferedImage rh = world.getSubimage((int) t2.getScreenX(), //rh = right half
                (int) t2.getScreenY(),
                GameConstants.GAME_SCREEN_WIDTH/2,
                GameConstants.GAME_SCREEN_HEIGHT);

        g.drawImage(lh, 0,0, null);
        g.drawImage(rh, GameConstants.GAME_SCREEN_WIDTH/2, 0, null);
    }
}

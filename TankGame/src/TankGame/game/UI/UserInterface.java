package TankGame.game.UI;

import TankGame.ResourceHandler.GameConstants;
import TankGame.ResourceHandler.Resources;
import TankGame.game.GameObjects.GameObject;
import TankGame.game.GameWorld;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class UserInterface {

    /**
     * This will create a list of GameObjects based on the map read by the function
     * @return List<GameObject> this is our list of gameObjects to be added
     */
    public List<GameObject> createGameObjectsList(){
        List<GameObject> gameObjects = new ArrayList<>(500);
        try(BufferedReader mapReader = new BufferedReader(new InputStreamReader(
                GameWorld.class.getClassLoader().getResourceAsStream("Maps/map2.csv")
        ))){
            for(int i = 0; mapReader.ready(); i++){
                String[] gameObjectNums = mapReader.readLine().split(",");
                for(int j = 0; j < gameObjectNums.length; j++){
                    if(gameObjectNums[j].equals("0")) continue;
                    String objectType = gameObjectNums[j];
                    gameObjects.add(GameObject.gameObjectFactory(objectType, i *30, j*30));
                }
            }
        } catch(IOException e){
            e.printStackTrace();
        }

        return gameObjects;
    }

    /**
     * This will draw the floor of our game to the panel
     * @param buffer Graphics - the panel we are drawing too
     */
    public void drawFloor(Graphics buffer){
        //320 is the width of our image
        for (int i = 0; i < GameConstants.WORLD_WIDTH; i += 320){
            for (int j = 0; j < GameConstants.WORLD_HEIGHT; j+= 240){
                buffer.drawImage(Resources.getSprite("floor"), i, j, null);
            }
        }
    }

    /**
     * This method will draw the mini-map at the bottom of the game for players to see the whole map
     * @param g Graphics2D that we are drawing to
     * @param world BufferedImage that we are taking and scaling down for our mini-map
     */
    public void drawMiniMap(Graphics2D g, BufferedImage world){
        BufferedImage mm = world.getSubimage(0,0, GameConstants.WORLD_WIDTH, GameConstants.WORLD_HEIGHT);
        AffineTransform at = new AffineTransform();
        at.translate(GameConstants.GAME_SCREEN_WIDTH/2f - (GameConstants.WORLD_WIDTH * .2f)/2f,
                GameConstants.GAME_SCREEN_HEIGHT - (GameConstants.WORLD_HEIGHT * .2f));
        at.scale(.2, .2);
        g.drawImage(mm, at, null);
    }
}

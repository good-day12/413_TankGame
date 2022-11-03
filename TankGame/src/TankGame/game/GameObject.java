package TankGame.game;

import TankGame.Resources;
import TankGame.game.stationary.Wall;

import java.awt.*;

public abstract class GameObject {

    //this is called the static factory method
    public static GameObject gameObjectFactory(String type, float x, float y){
        switch(type){
    //        case "0" -> {}

            case "2" -> {
                //breakable wall
                return new Wall(x, y, Resources.getSprite("break"));

            }

            case "3", "9" -> {
                //load unbreakable wall
                return new Wall(x, y, Resources.getSprite("unbreak"));
            }

            default -> {
                return null;
                //rest of the numbers will be powerups, and etc
            }
        }
    }

    public abstract void drawImage(Graphics g);
}
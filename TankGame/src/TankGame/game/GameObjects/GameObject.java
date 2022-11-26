package TankGame.game.GameObjects;

import TankGame.Resources;
import TankGame.game.GameObjects.stationary.Wall;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.List;

public abstract class GameObject {

    protected float x;
    protected float y;
    protected BufferedImage img;

    protected Rectangle hitbox;
    //game object needs to create hitbox
    //should have x, y variables as well

    public Rectangle getHitbox(){
        return this.hitbox.getBounds();
    }

    public void setX(float x){ this.x = x; }

    public void setY(float y) { this.y = y;}

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public GameObject(float x, float y, BufferedImage img) {
        this.x = x;
        this.y = y;
        this.img = img;
        this.hitbox = new Rectangle((int) x, (int) y, this.img.getWidth(), this.img.getHeight());
    }

    //this is called the static factory method
    public static GameObject gameObjectFactory(String type, float x, float y){
        switch(type){
            case "0" -> {}

            case "2" -> {
                //breakable wall
                return new Wall(x, y, Resources.getSprite("break1"));

            }

            case "3", "9" -> {
                //load unbreakable wall
                return new Wall(x, y, Resources.getSprite("unbreak"));
            }

            default -> {
                throw new IllegalStateException("Unexpected value " + type);
                //rest of the numbers will be powerups, and etc
            }
        }
        return null;
    }

    public static void collisionChecks(List<GameObject> gameObjects){
        //COLLISIONSSSSSSSSSSSSSSS
        //outer loop, things that initiate collisions,
        //inner loop, things that get hit
        for (int i =0; i < gameObjects.size(); i++){
            GameObject ob1 = gameObjects.get(i);
            if (ob1 instanceof Wall) continue; //powerups, etc
            for (int j = 0; j < gameObjects.size(); j++){
                if (i == j) continue;
                GameObject ob2 = gameObjects.get(j);
                if(ob1.getHitbox().intersects(ob2.getHitbox())){
                    //do collision stuff
                }
            }
        }
    }

    public abstract void drawImage(Graphics g);
}

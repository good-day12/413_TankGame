package TankGame.game.GameObjects;

import TankGame.Resources;
import TankGame.game.GameObjects.mobile.Bullet;
import TankGame.game.GameObjects.mobile.Tank;
import TankGame.game.GameObjects.stationary.*;

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
    public boolean hasCollided;

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
        this.hasCollided = false;
    }

    //this is called the static factory method
    public static GameObject gameObjectFactory(String type, float x, float y){
        return switch(type){
            case "2" -> new BreakableWall(x, y, Resources.getSprite("break1"));
            case "3", "9" -> new Wall(x, y, Resources.getSprite("unbreak"));
            case "4" ->     new Speed(x,y,Resources.getSprite("speed"));
            case "5" ->     new Health(x, y, Resources.getSprite("health"));
            case "6" ->     new Shield(x,y, Resources.getSprite("shield"));
            default -> throw new IllegalStateException("Unexpected value: " + type);
        };
    }

    public static void collisionChecks(List<GameObject> gameObjects){
        //COLLISIONSSSSSSSSSSSSSSS
        //outer loop, things that initiate collisions,
        //inner loop, things that get hit
        for (int i =0; i < gameObjects.size(); i++){
            GameObject ob1 = gameObjects.get(i);
            if (ob1 instanceof Wall) continue; //powerups, etc
            if (ob1 instanceof BreakableWall) continue;
            if (ob1 instanceof Shield) continue;
            if (ob1 instanceof Speed) continue;
            if (ob1 instanceof Health) continue;

            //ob1 should only be tanks and bullets
            for (int j = 0; j < gameObjects.size(); j++){
                if (i == j) continue; //cant hit self
                GameObject ob2 = gameObjects.get(j);
                if(ob1.getHitbox().intersects(ob2.getHitbox())){
                    //do collision stuff, should be abstracted to lower classes
                    //if bullet colliding with tank, take life away
                    //if tank colliding with wall, reset position
                    //if tank colliding with powerUp, give power
                    //if tank collding with breakable wall, start breaking wall

                    //powerup example, should be in powerup class
//                    if(ob2 instanceof PowerUp && !ob2.hasCollided){
//                        System.out.println("hit a powerup");
//                        Resources.getSound("powerup").playSound();
//                        ob2.hasCollided = true;
//                    }

                    //shooting example,
//
                    if (ob2 instanceof Bullet && !ob2.hasCollided){
                        //second condition is to ensure tank can't hit self && ( (Bullet) ob2).getTankId() != ((Tank) ob1).getTankId()
                        if (ob1 instanceof Tank && ( (Bullet) ob2).getTankId() != ((Tank) ob1).getTankId()) {
                            ((Bullet) ob2).collision((Tank) ob1); //what if a bullet hits a bullet?
                            //Resources.getSound("bullet").playSound();
                            System.out.println("BULLET HIT!");
                            gameObjects.remove(ob2); //remove bullet after collision
                        }
                        //what to do when a bullet is hit
                    }
                    //System.out.println("COLLIDED");
                }
            }
        }
    }

    public void drawImage(Graphics g){
        Graphics2D g2d = (Graphics2D) g;
        g.drawImage(this.img, (int) x, (int) y, null);
    };
}

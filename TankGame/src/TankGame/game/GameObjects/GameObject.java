package TankGame.game.GameObjects;

import TankGame.ResourceHandler.Resources;
import TankGame.game.GameObjects.mobile.Bullet;
import TankGame.game.GameObjects.mobile.Tank;
import TankGame.game.GameObjects.stationary.*;
import TankGame.game.GameObjects.stationary.PowerUps.Health;
import TankGame.game.GameObjects.stationary.PowerUp;
import TankGame.game.GameObjects.stationary.PowerUps.Shield;
import TankGame.game.GameObjects.stationary.PowerUps.ShootSpeed;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.List;

public abstract class GameObject {

    protected float x;
    protected float y;
    protected BufferedImage img;
    protected Rectangle hitbox;
    protected boolean hasCollided;

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

    public boolean isHasCollided() {
        return hasCollided;
    }

    public void setHasCollided(boolean hasCollided) {
        this.hasCollided = hasCollided;
    }

    /**
     * Constructor for gameObject on map
     * every gameobject also has a hitbox and boolean of hasCollided
     * @param x - x location on img
     * @param y - y location on img
     * @param img - img of object
     */
    public GameObject(float x, float y, BufferedImage img) {
        this.x = x;
        this.y = y;
        this.img = img;
        this.hitbox = new Rectangle((int) x, (int) y, this.img.getWidth(), this.img.getHeight());
        this.hasCollided = false;
    }

    /**
     * Use static factory method to create GameObjects from a number representing the
     * corresponding GameObject
     * @param type - string of number to represent a GameObject
     * @param x - x location of GameObject
     * @param y - y location of GameObject
     * @return - GameObject of appropriate value according to the type
     */
    public static GameObject gameObjectFactory(String type, float x, float y){
        return switch(type){
            case "2" ->         new BreakableWall(x, y, Resources.getSprite("break1"));
            case "3", "9" ->    new Wall(x, y, Resources.getSprite("unbreak"));
            case "4" ->         new ShootSpeed(x,y,Resources.getSprite("speed"));
            case "5" ->         new Health(x, y, Resources.getSprite("health"));
            case "6" ->         new Shield(x,y, Resources.getSprite("shield"));
            default -> throw    new IllegalStateException("Unexpected value: " + type);
        };
    }

    /**
     * This will use the gameObjects and check their locations and compare two objects to see if they collide
     * and will give the appropriate reaction of they do collide
     * @param gameObjects - a list of objects to check if they collide with each other
     */
    public static void collisionChecks(List<GameObject> gameObjects){
        //outer loop, things that initiate collisions,
        //inner loop, things that get hit
        for (int i =0; i < gameObjects.size(); i++){
            GameObject ob1 = gameObjects.get(i);
            if (ob1 instanceof Wall) continue; //powerups, etc
            if (ob1 instanceof BreakableWall) continue;
            if (ob1 instanceof Shield) continue;
            if (ob1 instanceof ShootSpeed) continue;
            if (ob1 instanceof Health) continue;

            //ob1 should only be tanks and bullets
            for (int j = 0; j < gameObjects.size(); j++){
                if (i == j) continue; //can't hit self
                GameObject ob2 = gameObjects.get(j);
                if(ob1.getHitbox().intersects(ob2.getHitbox())){

                    //Bullets hitting tanks
                    if (ob2 instanceof Bullet && !ob2.hasCollided){
                        //second condition is to ensure tank can't hit self
                        if (ob1 instanceof Tank && ( (Bullet) ob2).getBulletId() != ((Tank) ob1).getTankId()) {
                            ((Bullet) ob2).collision((Tank) ob1); //what if a bullet hits a bullet?
                        }
                    }

                    //Walls
                    if (ob2 instanceof Wall){
                        //tank hitting walls
                        if (ob1 instanceof Tank) {
                            ((Wall) ob2).collision((Tank) ob1);
                        }
                        //bullet hitting walls
                        if (ob1 instanceof Bullet){
                            ((Wall) ob2).collision((Bullet) ob1);
                        }
                    }

                    //Breakable walls
                    if(ob2 instanceof BreakableWall){
                        //bullet hits breakable wall
                        if (ob1 instanceof Bullet){
                            ((BreakableWall) ob2).collide((Bullet) ob1);
                        }
                    }
                    //powerups
                    if (ob2 instanceof PowerUp){
                        //ensure bullets don't hit power ups
                        if (ob1 instanceof Tank){
                            ((PowerUp) ob2).collide((Tank) ob1);
                        }
                    }
                }
            }
        }
    }

    /**
     * Draw the image of the GameObject
     * @param g - graphics to draw image on
     */
    public void drawImage(Graphics g){
        Graphics2D g2d = (Graphics2D) g;
        g.drawImage(this.img, (int) x, (int) y, null);
    };
}

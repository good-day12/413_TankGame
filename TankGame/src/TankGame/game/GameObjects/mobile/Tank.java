package TankGame.game.GameObjects.mobile;

import TankGame.Animations;
import TankGame.GameConstants;
import TankGame.Resources;
import TankGame.game.Camera;
import TankGame.game.GameObjects.GameObject;
import TankGame.game.GameWorld;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author anthony-pc
 */
public class Tank extends GameObject {

    /*
    * Should I include a camera object here? I need to be able to call center screen with
    * this class...*/
    private float vx;
    private float vy;
    private float angle;
    private float R = 5; //r-value is the hypotenuse
    private float ROTATIONSPEED = 3.0f;
    private int lives = 3;
    private int health = 100;
    private int tankId;
    private boolean UpPressed;
    private boolean DownPressed;
    private boolean RightPressed;
    private boolean LeftPressed;
    private boolean shootPressed;

    private long coolDown = 2000; //2000 milliseconds, 2 seconds, cooldown for shooting POWERUP possibility
    private long timeLastShot = 0;

    private Camera cam;
    private List<Animations> anims = new ArrayList<>(20);


    private List<Bullet> ammo = new ArrayList<>(500); //start with large array so we don't need to resize

    public Tank(float x, float y, float vx, float vy, float angle, int tankId, BufferedImage img) {
        super(x, y, img);
        this.vx = vx;
        this.vy = vy;
        this.angle = angle;
        this.tankId = tankId;
    }

    public int getTankId() {
        return tankId;
    }

    public void setCam(Camera cam) {
        this.cam = cam;
    }
    public void setLives(int l){ this.lives = l; }
    public int getLives(){ return this.lives; }
    public int getHealth() { return health; }
    public void setHealth(int health) { this.health = health; }
    void toggleUpPressed() {
        this.UpPressed = true;
    }
    void toggleDownPressed() {
        this.DownPressed = true;
    }
    void toggleRightPressed() {
        this.RightPressed = true;
    }
    void toggleLeftPressed() {
        this.LeftPressed = true;
    }
    void toggleShootPressed(){ this.shootPressed = true; }
    void unToggleUpPressed() {
        this.UpPressed = false;
    }
    void unToggleDownPressed() {
        this.DownPressed = false;
    }
    void unToggleRightPressed() {
        this.RightPressed = false;
    }
    void unToggleLeftPressed() {
        this.LeftPressed = false;
    }
    void unToggleShootPressed() { this.shootPressed = false; }

    public void update(GameWorld gw) {
        if (this.UpPressed) {
            this.moveForwards();
        }

        if (this.DownPressed) {
            this.moveBackwards();
        }

        if (this.LeftPressed) {
            this.rotateLeft();
        }

        if (this.RightPressed) {
            this.rotateRight();
        }
        if (this.shootPressed && (this.timeLastShot + coolDown) < System.currentTimeMillis()){
            this.timeLastShot = System.currentTimeMillis();
            Bullet b = new Bullet (setBulletStartX(), setBulletStartY(), angle, tankId);
            gw.addGameObject(b);
            //this.ammo.add(b);
//            Resources.getSound("Shoot").playSound();
//            this.anims.add(new Animations(setBulletStartX(), setBulletStartY(), Resources.getAnimation("shoot")));
        }

        //this.ammo.forEach(b -> b.update());
//        this.anims.forEach(a -> a.update());
        //this.ammo.removeIf(b -> b.checkBorder());
        //remove it from gameObjects list somehow

    }

    private void rotateLeft() {
        this.angle -= this.ROTATIONSPEED;
    }

    private void rotateRight() {
        this.angle += this.ROTATIONSPEED;
    }

    private void moveBackwards() {
        vx =  Math.round(R * Math.cos(Math.toRadians(angle)));
        vy =  Math.round(R * Math.sin(Math.toRadians(angle)));
        x -= vx;
        y -= vy;
       checkBorder();
       cam.centerScreen();
       this.hitbox.setLocation((int) x, (int) y);
    }

    private void moveForwards() {
        vx = Math.round(R * Math.cos(Math.toRadians(angle)));
        vy = Math.round(R * Math.sin(Math.toRadians(angle)));
        x += vx;
        y += vy;
        checkBorder();
        cam.centerScreen();
        this.hitbox.setLocation((int) x, (int) y);
    }

    private void checkBorder() {
        if (x < 30) {
            x = 30;
        }
        if (x >= GameConstants.WORLD_WIDTH - 88) {
            x = GameConstants.WORLD_WIDTH - 88;
        }
        if (y < 40) {
            y = 40;
        }
        if (y >= GameConstants.WORLD_HEIGHT - 80) {
            y = GameConstants.WORLD_HEIGHT - 80;
        }
    }

    @Override
    public String toString() {
        return "x=" + x + ", y=" + y + ", angle=" + angle;
    }

    public void drawImage(Graphics g) {
        AffineTransform rotation = AffineTransform.getTranslateInstance(x, y);
        rotation.rotate(Math.toRadians(angle), this.img.getWidth() / 2.0, this.img.getHeight() / 2.0);
        Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage(this.img, rotation, null);
        g2d.setColor(Color.RED);
        g2d.drawRect((int)x,(int)y,this.img.getWidth(), this.img.getHeight());

        //this.ammo.forEach(b -> b.drawImage(g)); //don't use forEach, traditional for loop

        //health bar
        g2d.drawRect((int) x - 20,(int) y - 20, 100, 15);
        g2d.fillRect((int) x - 20,(int) y - 20, this.health, 15);
        //can use g2d.setColor(Color.red) //if we want to change the colors
        //do a loop using lives, i * 15 for x
        g2d.drawOval((int) x, (int) y + 65, 10, 10);
    }

    private int setBulletStartX(){
        float cx = 29f * (float) Math.cos(Math.toRadians(angle));
        return (int) x + this.img.getWidth() / 2 + (int) cx - 4;
    }
    private int setBulletStartY(){
        float cy = 29f * (float) Math.sin(Math.toRadians(angle));
        return (int) y + this.img.getHeight() / 2 + (int) cy - 4;
    }



}

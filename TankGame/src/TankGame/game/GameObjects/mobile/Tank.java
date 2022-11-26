package TankGame.game.GameObjects.mobile;

import TankGame.GameConstants;
import TankGame.game.Camera;
import TankGame.game.GameObjects.GameObject;

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
    private float x, screenX;
    private float y, screenY;
    private float vx;
    private float vy;
    private float angle;
    private float R = 5; //r-value is the hypotenuse
    private float ROTATIONSPEED = 3.0f;

    private BufferedImage img;
    private boolean UpPressed;
    private boolean DownPressed;
    private boolean RightPressed;
    private boolean LeftPressed;

    private boolean shootPressed;

    private long coolDown = 2000; //2000 milliseconds, 2 seconds, cooldown for shooting POWERUP possibility
    private long timeLastShot = 0;

    private Camera cam;

    private List<Bullet> ammo = new ArrayList<>(500); //start with large array so we don't need to resize

    public Tank(float x, float y, float vx, float vy, float angle, BufferedImage img) {
        this.x = x;
        this.y = y;
        this.vx = vx;
        this.vy = vy;
        this.img = img;
        this.angle = angle;
    }

    public void setX(float x){ this.x = x; }

    public void setY(float y) { this. y = y;}

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public void setCam(Camera cam) {
        this.cam = cam;
    }

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

    public void update() {
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
            Bullet b = new Bullet (setBulletStartX(), setBulletStartY(), angle);
            this.ammo.add(b);
        }

        this.ammo.forEach(b -> b.update());
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
//       centerScreen();
    }

    private void moveForwards() {
        vx = Math.round(R * Math.cos(Math.toRadians(angle)));
        vy = Math.round(R * Math.sin(Math.toRadians(angle)));
        x += vx;
        y += vy;
        checkBorder();
        cam.centerScreen();
//        centerScreen();
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
        this.ammo.forEach(b -> b.drawImage(g)); //don't use forEach, traditional for loop
    }

    //THIS NEEDS TO BE IN CAMERA CLASS
    private void centerScreen(){
        this.screenX = this.x - GameConstants.GAME_SCREEN_WIDTH / 4f;
        this.screenY = this.y - GameConstants.GAME_SCREEN_HEIGHT / 2f;

        if (this.screenX < 0) screenX = 0;
        if (this.screenY < 0) screenY = 0;

        if (this.screenX > GameConstants.WORLD_WIDTH - GameConstants.GAME_SCREEN_WIDTH / 2f){
            this.screenX = GameConstants.WORLD_WIDTH - GameConstants.GAME_SCREEN_WIDTH / 2f;
        }

        if (this.screenY > GameConstants.WORLD_HEIGHT - GameConstants.GAME_SCREEN_HEIGHT){
            this.screenY = GameConstants.WORLD_HEIGHT - GameConstants.GAME_SCREEN_HEIGHT;
        }

    }

    public int getScreenX() {
        return (int) screenX;
    }

    public int getScreenY(){
        return (int) screenY;
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

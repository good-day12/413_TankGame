package TankGame.game.GameObjects.mobile;

import TankGame.ResourceHandler.Animations;
import TankGame.ResourceHandler.GameConstants;
import TankGame.ResourceHandler.Resources;
import TankGame.game.GameObjects.GameObject;

import java.awt.*;
import java.awt.geom.AffineTransform;

public class Bullet extends GameObject {
    float angle;
    float vx = 0;
    float vy = 0;
    float R = 6;
    int bulletId;

    /**
     * Constructor for the Bullet object
     * @param x - x position of the bullet (same as muzzle of tank)
     * @param y - x position of the bullet (same as muzzle of tank)
     * @param angle - angle of the bullet (same as tank)
     * @param tankId - what tank shot this bullet
     */
    public Bullet(float x, float y, float angle, int tankId) {
        super(x, y, Resources.getSprite("bullet"));
        this.bulletId = tankId;
        this.angle = angle;
    }

    public int getBulletId() {
        return bulletId;
    }

    /**
     * To update the position and hitbox of the bullet
     */
    public void update(){
        vx = Math.round(R * Math.cos(Math.toRadians(angle)));
        vy = Math.round(R * Math.sin(Math.toRadians(angle)));
        x += vx;
        y += vy;
        this.hitbox.setLocation((int) x,(int) y);
    }

    /**
     * Draw the image of the bullet
     * @param g - where we are drawing the image onto
     */
    @Override
    public void drawImage(Graphics g) {
        AffineTransform rotation = AffineTransform.getTranslateInstance(x, y);
        rotation.rotate(Math.toRadians(angle), this.img.getWidth() / 2.0, this.img.getHeight() / 2.0);
        Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage(this.img, rotation, null);
    }

    /**
     * check to remove bullet if out of bounds of the map
     * @return true if it collides with border, false if it does not
     */
    public boolean checkBorder() {
        if (x < 30) {
            return true;
        }
        if (x >= GameConstants.WORLD_WIDTH - 88) {
            return true;
        }
        if (y < 40) {
            return true;
        }
        if (y >= GameConstants.WORLD_HEIGHT - 80) {
            return true;
        }
        return false;
    }

    /**
     * What to do when bullet hits tank, decrement health of tank, or if health is 0 decrement life,
     * or if we hit a shield decrement shield hit
     * @param t
     */
    public void collision(Tank t){
        Resources.getSound("bulletHit").playSound();
        if (t.isShield()){
            //set shield lives to decrement if hit by bullet
            t.getShield().setShieldLives(t.getShield().getShieldLives() - 1);
        } else { //if no shield check health
            t.setHealth(t.getHealth() - 25);
            if (t.getHealth() <= 0) {
                t.setLives(t.getLives() - 1); //decrement life of tank
                t.setHealth(100);
            }
        }
        t.addAnims(new Animations(this.getX(), this.getY(), Resources.getAnimation("collide")));
        this.hasCollided = true;
    }
}

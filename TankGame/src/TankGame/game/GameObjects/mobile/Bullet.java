package TankGame.game.GameObjects.mobile;

import TankGame.Animations;
import TankGame.GameConstants;
import TankGame.Resources;
import TankGame.game.GameObjects.GameObject;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

public class Bullet extends GameObject {
    float angle;
    float vx = 0;
    float vy = 0;
    float R = 6;
    int bulletId;

    public Bullet(float x, float y, float angle, int tankId) {
        super(x, y, Resources.getSprite("bullet"));
        this.bulletId = tankId;
        this.angle = angle;
    }

    public int getBulletId() {
        return bulletId;
    }

    public void update(){
        vx = Math.round(R * Math.cos(Math.toRadians(angle)));
        vy = Math.round(R * Math.sin(Math.toRadians(angle)));
        x += vx;
        y += vy;
        this.hitbox.setLocation((int) x,(int) y);
    }

    @Override
    public void drawImage(Graphics g) {
        AffineTransform rotation = AffineTransform.getTranslateInstance(x, y);
        rotation.rotate(Math.toRadians(angle), this.img.getWidth() / 2.0, this.img.getHeight() / 2.0);
        Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage(this.img, rotation, null);
        g2d.setColor(Color.GREEN);
        g2d.drawRect((int)x,(int)y,this.img.getWidth(), this.img.getHeight());
    }

    //check to remove bullet if out of bounds of the map
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
//what if we pass a gameObject to this function, then throw all the gross if statements in here?
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

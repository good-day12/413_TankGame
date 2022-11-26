package TankGame.game.GameObjects.mobile;

import TankGame.GameConstants;
import TankGame.Resources;
import TankGame.game.GameObjects.GameObject;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

public class Bullet extends GameObject {
    float x, y;
    float angle;
    float vx = 0;
    float vy = 0;
    float R = 6;
    BufferedImage img = Resources.getSprite("bullet");

    public Bullet(float x, float y, float angle) {
        super();
        this.x = x;
        this.y = y;
        this.angle = angle;
        this.hitbox = new Rectangle((int) x, (int) y, this.img.getWidth(), this.img.getHeight());
    }

    void update(){
        vx = Math.round(R * Math.cos(Math.toRadians(angle)));
        vy = Math.round(R * Math.sin(Math.toRadians(angle)));
        x += vx;
        y += vy;
    }

    @Override
    public void drawImage(Graphics g) {
        AffineTransform rotation = AffineTransform.getTranslateInstance(x, y);
        rotation.rotate(Math.toRadians(angle), this.img.getWidth() / 2.0, this.img.getHeight() / 2.0);
        //rotation.scale(3.9, 3.9)
        Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage(this.img, rotation, null);
        g2d.setColor(Color.GREEN);
        //g2d.rotate(Math.toRadians(angle), bounds.x + bounds.width/2, bounds.y + bounds.height/2);
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
}

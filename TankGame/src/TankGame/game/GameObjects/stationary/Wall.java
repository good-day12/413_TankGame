package TankGame.game.GameObjects.stationary;

import TankGame.game.GameObjects.GameObject;
import TankGame.game.GameObjects.mobile.Bullet;
import TankGame.game.GameObjects.mobile.Tank;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Wall extends GameObject {

    public Wall(float x, float y, BufferedImage img) {
        super(x, y, img);
    }

    //if tank collides into wall, prevent tank movement into tank's hitbox
    public void collision(Tank t){
        //create new rectangle representing the intersection of two hitboxes
        Rectangle i = new Rectangle(t.getHitbox().intersection(this.getHitbox()));
        double yMidpoint = this.y - (this.getHitbox().getHeight() / 2);
        double xMidpoint = this.x + (this.getHitbox().getWidth() / 2);
        if(i.getHeight() > i.getWidth()){
            if(i.getX() > xMidpoint ){ //tank is to right of wall
                t.setX( (float) (i.getX() + i.getWidth()) );
            } else{ //tank is to left of wall
                t.setX( (float) (i.getX() - this.getHitbox().getWidth() - i.getWidth() - 20 ) );
            }
        }
        else{
            if(t.getY() < yMidpoint){ //tank is above wall
                t.setY((float) (this.getHitbox().getY() - t.getHitbox().getHeight()));
            } else{ //tank is below wall
                t.setY((float) (this.getHitbox().getY() + t.getHitbox().getHeight()) - 10);
            }
        }
    }

    //if bullet hit walls
    public void collision(Bullet b) {
        b.setHasCollided(true);
    }
}

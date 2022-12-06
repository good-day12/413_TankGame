package TankGame.game.GameObjects.stationary;

import TankGame.ResourceHandler.Resources;
import TankGame.game.GameObjects.mobile.Bullet;

import java.awt.image.BufferedImage;

public class BreakableWall extends Wall{

    private int lives = 2;

    /**
     * Constructor for powerup on map
     * @param x - x location on img
     * @param y - y location on img
     * @param img - img of wall
     */
    public BreakableWall(float x, float y, BufferedImage img) {
        super(x, y, img);
    }

    /**
     * If bullet hits wall, change img and decrement lives, if lives = 0 remove wall
     * @param b - bullet to hit wall
     */
    public void collide(Bullet b){
        lives--;
        b.setHasCollided(true);
        if (lives == 1){
            this.img = Resources.getSprite("break2");
        }
        else if (lives == 0){
            this.setHasCollided(true);
        }
    }

}

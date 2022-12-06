package TankGame.game.GameObjects.stationary;

import TankGame.game.GameObjects.GameObject;
import TankGame.game.GameObjects.mobile.Tank;

import java.awt.image.BufferedImage;

public abstract class PowerUp extends GameObject {

    /**
     * Constructor for powerup on map
     * @param x - x location on img
     * @param y - y location on img
     * @param img - img of powerup
     */
    public PowerUp(float x, float y, BufferedImage img) {
        super(x, y, img);
    }

    /**
     * What to do when these powerups collide with a tank
     * @param t
     */
    public abstract void collide(Tank t);
}

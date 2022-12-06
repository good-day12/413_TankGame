package TankGame.game.GameObjects.stationary.PowerUps;

import TankGame.ResourceHandler.Resources;
import TankGame.game.GameObjects.mobile.Tank;
import TankGame.game.GameObjects.stationary.PowerUp;

import java.awt.image.BufferedImage;

public class ShootSpeed extends PowerUp {

    /**
     * Constructor for powerup on map
     * @param x - x location on img
     * @param y - y location on img
     * @param img - img of powerup
     */
    public ShootSpeed(float x, float y, BufferedImage img) {
        super(x, y, img);
    }

    /**
     * To give the tank the speed boost condition
     * @param t
     */
    @Override
    public void collide(Tank t) {
        Resources.getSound("powerup").playSound();
        t.shotSpeedBoost();
        this.hasCollided = true;
    }

}

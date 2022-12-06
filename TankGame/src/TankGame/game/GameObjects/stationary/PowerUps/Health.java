package TankGame.game.GameObjects.stationary.PowerUps;

import TankGame.ResourceHandler.Resources;
import TankGame.game.GameObjects.mobile.Tank;
import TankGame.game.GameObjects.stationary.PowerUp;

import java.awt.image.BufferedImage;

/**
 * Class is for the health powerup, will give tank 100% health or extra life if health is 100% already
 */
public class Health extends PowerUp {

    /**
     * Constructor for powerup on map
     * @param x - x location on img
     * @param y - y location on img
     * @param img - img of powerup
     */
    public Health(float x, float y, BufferedImage img) {
        super(x, y, img);
    }

    /**
     * If we collide with tank, set health to 100% or give extra life if health is already 100%
     * @param t
     */
    @Override
    public void collide(Tank t) {
        Resources.getSound("powerup").playSound();
        //set health to 100 if under 100
        if (t.getHealth() < 100) t.setHealth(100);
        //give an extra life if health is already 100
        if (t.getHealth() == 100) t.setLives(t.getLives() + 1);
        this.hasCollided = true;
    }
}

package TankGame.game.GameObjects.stationary.PowerUps;

import TankGame.ResourceHandler.Resources;
import TankGame.game.GameObjects.mobile.Tank;
import TankGame.game.GameObjects.stationary.PowerUp;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Shield extends PowerUp {

    Tank t;
    private final long shieldUseTime = 20000; //20,000 milliseconds, 20 seconds, how long shield will last
    private long timeGotShield = 0;
    private int shieldLives = 2;

    /**
     * Constructor for powerup on map
     * @param x - x location on img
     * @param y - y location on img
     * @param img - img of powerup
     */
    public Shield(float x, float y, BufferedImage img) { super(x, y, img); }

    public int getShieldLives() {
        return shieldLives;
    }

    public void setShieldLives(int shieldLives) {
        this.shieldLives = shieldLives;
    }

    /**
     * What to do when we collide with a tank,
     * Set tank Shield to this shield
     * Change image from powerup to the shield image
     * @param t
     */
    @Override
    public void collide(Tank t) {
        //ensure tank doesn't already have a shield
        if (!t.isShield()) {
            Resources.getSound("powerup").playSound();
            this.img = Resources.getSprite("shield" + t.getTankId());
            this.t = t;
            t.addShield(this);
            timeGotShield = System.currentTimeMillis();
        }
    }

    /**
     * To draw image of shield over tank when the tank is protected
     * @param g - what we are drawing onto
     */
    @Override
    public void drawImage(Graphics g) {
        if (t != null) {
            this.setX(t.getX());
            this.setY(t.getY());
        }
        super.drawImage(g);
    }

    /**
     * Update the shield condition, if timer is not out and shield still have lives keep shield, else set to false
     */
    public void shieldUpdate(){
        //if our shield timer runs out of lives or is hit too much, remove shield effect
        if (timeGotShield + shieldUseTime < System.currentTimeMillis() || shieldLives == 0){
            this.setHasCollided(true);
            t.setShield(false);
        }
    }
}

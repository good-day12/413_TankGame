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

    //constructor for powerUp on map
    public Shield(float x, float y, BufferedImage img) { super(x, y, img); }

    public int getShieldLives() {
        return shieldLives;
    }

    public void setShieldLives(int shieldLives) {
        this.shieldLives = shieldLives;
    }

    //use timing method from teacher's cooldown shooting idea
    @Override
    public void collide(Tank t) {
        //ensure tank doesn't already have a shield
        if (!t.isShield()) {
            this.img = Resources.getSprite("shield" + t.getTankId());
            this.t = t;
            t.addShield(this);
            timeGotShield = System.currentTimeMillis();
        }
    }

    @Override
    public void drawImage(Graphics g) {
        if (t != null) {
            this.setX(t.getX());
            this.setY(t.getY());
        }
        super.drawImage(g);
    }

    public void shieldUpdate(){
        //if our shield timer runs out of lives or is hit too much, remove shield effect
        if (timeGotShield + shieldUseTime < System.currentTimeMillis() || shieldLives == 0){
            this.setHasCollided(true);
            t.setShield(false);
        }
    }
}

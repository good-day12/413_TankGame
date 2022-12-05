package TankGame.game.GameObjects.stationary.PowerUps;

import TankGame.game.GameObjects.mobile.Tank;
import TankGame.game.GameObjects.stationary.PowerUp;

import java.awt.image.BufferedImage;

public class ShootSpeed extends PowerUp {

    public ShootSpeed(float x, float y, BufferedImage img) {
        super(x, y, img);
    }

    //use timing method from teacher's cooldown shooting idea
    @Override
    public void collide(Tank t) {
        t.shotSpeedBoost();
        this.hasCollided = true;
    }

}

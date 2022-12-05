package TankGame.game.GameObjects.stationary;

import TankGame.game.GameObjects.mobile.Tank;

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

package TankGame.game.GameObjects.stationary.PowerUps;

import TankGame.game.GameObjects.mobile.Tank;
import TankGame.game.GameObjects.stationary.PowerUp;

import java.awt.image.BufferedImage;

public class Health extends PowerUp {
    public Health(float x, float y, BufferedImage img) {
        super(x, y, img);
    }

    @Override
    public void collide(Tank t) {
        //set health to 100 if under 100
        if (t.getHealth() < 100) t.setHealth(100);
        //give an extra life if health is already 100
        if (t.getHealth() == 100) t.setLives(t.getLives() + 1);
        this.hasCollided = true;
    }
}
package TankGame.game.GameObjects.stationary;

import TankGame.game.GameObjects.GameObject;
import TankGame.game.GameObjects.mobile.Tank;

import java.awt.image.BufferedImage;

public class Health extends PowerUp {
    public Health(float x, float y, BufferedImage img) {
        super(x, y, img);
    }

    @Override
    void collide(Tank t) {

    }
}

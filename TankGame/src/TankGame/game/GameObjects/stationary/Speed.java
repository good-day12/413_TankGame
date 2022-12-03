package TankGame.game.GameObjects.stationary;

import TankGame.game.GameObjects.GameObject;
import TankGame.game.GameObjects.mobile.Tank;

import java.awt.image.BufferedImage;

public class Speed extends PowerUp {
    public Speed(float x, float y, BufferedImage img) {
        super(x, y, img);
    }

    @Override
    void collide(Tank t) {

    }
}

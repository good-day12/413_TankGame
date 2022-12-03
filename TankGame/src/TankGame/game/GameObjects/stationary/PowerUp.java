package TankGame.game.GameObjects.stationary;

import TankGame.game.GameObjects.GameObject;
import TankGame.game.GameObjects.mobile.Tank;

import java.awt.image.BufferedImage;

public abstract class PowerUp extends GameObject {

    public PowerUp(float x, float y, BufferedImage img) {
        super(x, y, img);
    }

    abstract void collide(Tank t);
}

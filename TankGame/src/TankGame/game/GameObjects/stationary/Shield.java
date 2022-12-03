package TankGame.game.GameObjects.stationary;

import TankGame.game.GameObjects.GameObject;
import TankGame.game.GameObjects.mobile.Tank;

import java.awt.image.BufferedImage;

public class Shield extends PowerUp {
    public Shield(float x, float y, BufferedImage img) {
        super(x, y, img);
    }

    //use timing method from teacher's cooldown shooting idea
    @Override
    public void collide(Tank t) {

    }
}

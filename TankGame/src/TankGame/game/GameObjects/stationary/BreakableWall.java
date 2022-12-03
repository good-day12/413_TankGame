package TankGame.game.GameObjects.stationary;

import TankGame.Resources;
import TankGame.game.GameObjects.mobile.Bullet;

import java.awt.image.BufferedImage;

public class BreakableWall extends Wall{

    private int lives = 2;

    public BreakableWall(float x, float y, BufferedImage img) {
        super(x, y, img);
    }

    public void collide(Bullet b){
        lives--;
        b.setHasCollided(true);
        if (lives == 1){
            this.img = Resources.getSprite("break2");
        }
        else if (lives == 0){
            this.setHasCollided(true);
        }
    }

}

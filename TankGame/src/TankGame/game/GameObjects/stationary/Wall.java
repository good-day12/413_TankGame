package TankGame.game.GameObjects.stationary;

import TankGame.game.GameObjects.GameObject;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Wall extends GameObject {

    public Wall(float x, float y, BufferedImage img) {
        super(x, y, img);
    }

    public void drawImage(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g.drawImage(this.img, (int) x, (int) y, null);
        g2d.setColor(Color.YELLOW);
        g2d.drawRect((int)x,(int)y,this.img.getWidth(), this.img.getHeight());
    }

}

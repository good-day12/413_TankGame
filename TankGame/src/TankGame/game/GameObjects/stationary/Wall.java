package TankGame.game.GameObjects.stationary;

import TankGame.game.GameObjects.GameObject;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Wall extends GameObject {

    float x, y;
    BufferedImage img;

    public Wall(float x, float y, BufferedImage img) {
        this.x = x;
        this.y = y;
        this.img = img;
    }

    public void drawImage(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g.drawImage(this.img, (int) x, (int) y, null);
        g2d.setColor(Color.YELLOW);
        g2d.drawRect((int)x,(int)y,this.img.getWidth(), this.img.getHeight());
    }

}

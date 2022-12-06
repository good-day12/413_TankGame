package TankGame.game.UI;

import TankGame.ResourceHandler.GameConstants;
import TankGame.game.GameObjects.mobile.Tank;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Camera {
    private Tank t1;
    private Tank t2;
    private float t1screenX;
    private float t1screenY;
    private float t2screenX;
    private float t2screenY;



    public Camera(Tank t1, Tank t2) {
        this.t1 = t1;
        this.t2 = t2;
        //run this function to give us our values for screenX & Y for tanks 1 & 2
        centerScreen();
    }

    public void drawSplitScreen(Graphics2D g, BufferedImage world){
        BufferedImage lh = world.getSubimage((int) this.t1screenX, //lh = left half
                (int) this.t1screenY,
                GameConstants.GAME_SCREEN_WIDTH/2,
                GameConstants.GAME_SCREEN_HEIGHT);

        BufferedImage rh = world.getSubimage((int) this.t2screenX, //rh = right half
                (int) this.t2screenY,
                GameConstants.GAME_SCREEN_WIDTH/2,
                GameConstants.GAME_SCREEN_HEIGHT);

        g.drawImage(lh, 0,0, null);
        g.drawImage(rh, GameConstants.GAME_SCREEN_WIDTH/2, 0, null);
    }

    public void centerScreen(){
        //center the first tank
        this.t1screenX = t1.getX() - GameConstants.GAME_SCREEN_WIDTH / 4f;
        this.t1screenY = t1.getY() - GameConstants.GAME_SCREEN_HEIGHT / 2f;

        if (this.t1screenX < 0) t1screenX = 0;
        if (this.t1screenY < 0) t1screenY = 0;

        if (this.t1screenX > GameConstants.WORLD_WIDTH - GameConstants.GAME_SCREEN_WIDTH / 2f){
            this.t1screenX = GameConstants.WORLD_WIDTH - GameConstants.GAME_SCREEN_WIDTH / 2f;
        }

        if (this.t1screenY > GameConstants.WORLD_HEIGHT - GameConstants.GAME_SCREEN_HEIGHT){
            this.t1screenY = GameConstants.WORLD_HEIGHT - GameConstants.GAME_SCREEN_HEIGHT;
        }

        //center the second tank
        this.t2screenX = t2.getX() - GameConstants.GAME_SCREEN_WIDTH / 4f;
        this.t2screenY = t2.getY() - GameConstants.GAME_SCREEN_HEIGHT / 2f;

        if (this.t2screenX < 0) t2screenX = 0;
        if (this.t2screenY < 0) t2screenY = 0;

        if (this.t2screenX > GameConstants.WORLD_WIDTH - GameConstants.GAME_SCREEN_WIDTH / 2f){
            this.t2screenX = GameConstants.WORLD_WIDTH - GameConstants.GAME_SCREEN_WIDTH / 2f;
        }

        if (this.t2screenY > GameConstants.WORLD_HEIGHT - GameConstants.GAME_SCREEN_HEIGHT){
            this.t2screenY = GameConstants.WORLD_HEIGHT - GameConstants.GAME_SCREEN_HEIGHT;
        }

    }

}

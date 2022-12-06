package TankGame.ResourceHandler;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.List;

/**
 * This class is to handle animatinos and ensure they run appropriately
 */
public class Animations {
    float x,y;
    List<BufferedImage> frames;
    long delay = 20;
    long timeSinceLastFrameUpdate = 0;
    boolean isRunning = false;
    int currentFrame = 0;

    /**
     * Constructor to create animation with location
     * @param x - x location on map
     * @param y - y location on map
     * @param frames - the list of pictures that make up our animation
     */
    public Animations(float x, float y, List<BufferedImage> frames){
        this.frames = frames;
        this.x = x - this.frames.get(0).getWidth()/2f;
        this.y = y - this.frames.get(0).getHeight()/2f;
        this.timeSinceLastFrameUpdate = 0;
        this.isRunning = true;
    }

    public boolean isRunning() {
        return isRunning;
    }

    public void setRunning(boolean running) {
        isRunning = running;
    }

    /**
     * Update will cycle the animation through the list of pictures, picture by picture, for each frame
     */
    public void update(){
        if ( (this.timeSinceLastFrameUpdate + this.delay) < System.currentTimeMillis()){
            currentFrame++; //currentFrame = (currentFrame + 1) % 32
            this.timeSinceLastFrameUpdate = System.currentTimeMillis();
            if (currentFrame > this.frames.size() - 1){
                this.isRunning = false;
            }
        }
    }

    /**
     * To draw the current image our animation is on
     * @param g
     */
    public void drawImage(Graphics2D g){
        if (isRunning) {
            g.drawImage(this.frames.get(currentFrame), (int) x, (int) y, null);
        }
    }
}

package TankGame.ResourceHandler;

import TankGame.game.GameWorld;

import javax.sound.sampled.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.*;

import static javax.imageio.ImageIO.read;

public class Resources {

    private static final Map<String, BufferedImage> sprites = new HashMap<>();
    private static final Map<String, Sound> sounds = new HashMap<>();
    private static final Map<String, List<BufferedImage>> animations = new HashMap<>();

    /**
     * Short function to shorten our load path in the initializations
     * @param path - path to image
     * @return - BufferedImage from resources
     * @throws IOException - if we don't load a source
     */
    private static BufferedImage loadSprite (String path) throws IOException{
        return read(Objects.
                requireNonNull(GameWorld
                        .class
                        .getClassLoader()
                        .getResource(path)));
    }

    /**
     * Initialize all the images used for GameObjects
     */
    private static void initSprites(){
        try {
            Resources.sprites.put("tank1", loadSprite("Tank/tank1.png"));
            Resources.sprites.put("tank2", loadSprite("Tank/tank2.png"));
            Resources.sprites.put("bullet", loadSprite("Bullet/bullet.jpg"));
            Resources.sprites.put("menu", loadSprite("Menu/title.png"));
            Resources.sprites.put("unbreak", loadSprite("Wall/unbreak.jpg"));
            Resources.sprites.put("break1", loadSprite("Wall/Wall1.gif"));
            Resources.sprites.put("break2", loadSprite("Wall/Wall2.gif"));
            Resources.sprites.put("floor", loadSprite("Background.bmp"));
            Resources.sprites.put("health", loadSprite("PowerUp/health.png"));
            Resources.sprites.put("shield", loadSprite("PowerUp/shield.png"));
            Resources.sprites.put("shield1", loadSprite("PowerUp/Shield1.gif"));
            Resources.sprites.put("shield2", loadSprite("PowerUp/Shield2.gif"));

            Resources.sprites.put("speed", loadSprite("PowerUp/speed.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Initialize all the sounds used for game
     */
    private static void initSounds(){
        AudioInputStream audioStream;
        Clip c;
        Sound s;

        try {
            //background music
            audioStream = AudioSystem.getAudioInputStream(
                    Resources.class.getClassLoader().getResource("Music/Music.mid"));
            c = AudioSystem.getClip();
            c.open((audioStream));
            s = new Sound(c);
            Resources.sounds.put("bg", s);

            //shot explosion sound
            audioStream = AudioSystem.getAudioInputStream(
                    Resources.class.getClassLoader().getResource("Sounds/shotexplosion.wav"));
            c = AudioSystem.getClip();
            c.open((audioStream));
            s = new Sound(c);
            Resources.sounds.put("bulletHit", s);

            //bullet shot sound
            audioStream = AudioSystem.getAudioInputStream(
                    Resources.class.getClassLoader().getResource("Sounds/bullet.wav"));
            c = AudioSystem.getClip();
            c.open((audioStream));
            s = new Sound(c);
            Resources.sounds.put("bulletShot", s);

            //pick up powerup sound
            audioStream = AudioSystem.getAudioInputStream(
                    Resources.class.getClassLoader().getResource("Sounds/pickup.wav"));
            c = AudioSystem.getClip();
            c.open((audioStream));
            s = new Sound(c);
            Resources.sounds.put("powerup", s);

        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Initialize all the animations used for game
     */
    private static void initAnimations(){
        try{
            //% -> marker for string 0-> means to fill with zero if not enough #'s 4-> how many nums d-> int
            String base = "Animations/bullet/expl_08_%04d.png"; //need full path name
            List<BufferedImage> temp = new ArrayList<>();
            for (int i = 0; i < 32; i++){
                String fName = String.format(base, i);
                temp.add(loadSprite(fName));
            }
            Resources.animations.put("shoot", temp);

            base = "Animations/nuke/expl_01_%04d.png"; //need second path name
            temp.clear();
            for (int i = 0; i < 24; i++){
                String fName = String.format(base, i);
                temp.add(loadSprite(fName));
            }
            Resources.animations.put("collide", temp);

        }catch(IOException e){
            e.printStackTrace();
            System.exit(-3);
        }

    }

    public static void loadResources(){
        initSprites();
        initSounds();
        initAnimations();
    }

    public static BufferedImage getSprite (String key){
        //can check for null
        if(!Resources.sprites.containsKey(key)) {
            System.out.println(key + " resource not found");
            System.exit(-2);
        }
        return Resources.sprites.get(key);
    }

    public static Sound getSound (String key){
        //can check for null
        if(!Resources.sounds.containsKey(key)) {
            System.out.println(key + " resource not found");
            System.exit(-2);
        }
        return Resources.sounds.get(key);
    }

    public static List<BufferedImage> getAnimation(String key) {
        if (!Resources.animations.containsKey(key)){
            System.out.println(key + " animation resource not found");
            System.exit(-2);
        }
        return animations.get(key);
    }
}

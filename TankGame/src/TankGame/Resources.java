package TankGame;

import TankGame.game.GameWorld;

import javax.imageio.ImageIO;
import javax.sound.sampled.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.*;

import static javax.imageio.ImageIO.read;

public class Resources {

    private static final Map<String, BufferedImage> sprites = new HashMap<>();
    private static final Map<String, Sound> sounds = new HashMap<>();
    private static final Map<String, List<BufferedImage>> animations = new HashMap<>();

    private static BufferedImage loadSprite (String path) throws IOException{
        return read(Objects.
                requireNonNull(GameWorld
                        .class
                        .getClassLoader()
                        .getResource(path)));
    }

    private static void initSprites(){
        try {
            Resources.sprites.put("tank1", loadSprite("Tank/tank1.png"));
            Resources.sprites.put("tank2", loadSprite("Tank/tank2.png"));
            Resources.sprites.put("bullet", loadSprite("Bullet/Shell.gif"));
            Resources.sprites.put("menu", loadSprite("Menu/title.png"));
            Resources.sprites.put("unbreak", loadSprite("Wall/Wall1.gif"));
            Resources.sprites.put("break1", loadSprite("Wall/Wall2.gif"));
            Resources.sprites.put("floor", loadSprite("Background.bmp"));
            Resources.sprites.put("health", loadSprite("PowerUp/health.png"));
            Resources.sprites.put("shield", loadSprite("PowerUp/shield.png"));
            Resources.sprites.put("speed", loadSprite("PowerUp/speed.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void initSounds(){
        AudioInputStream audioStream;
        Clip c;
        Sound s;

        try {
            audioStream = AudioSystem.getAudioInputStream(
                    Objects.requireNonNull(Resources.class.getClassLoader().getResource("Music/music.mid")));
            c = AudioSystem.getClip();
            c.open((audioStream));
            s = new Sound(c);
            Resources.sounds.put("bg", s);

            //a new sound example
//            audioStream = AudioSystem.getAudioInputStream(
//                    Resources.class.getClassLoader().getResource("Music/NEWSOUND"));
//            c = AudioSystem.getClip();
//            c.open((audioStream));
//            s = new Sound(c);
//            Resources.sounds.put("NEWSOUND NAME", s);

        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            throw new RuntimeException(e);
        }
    }

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

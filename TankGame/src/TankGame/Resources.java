package TankGame;

import TankGame.game.GameWorld;

import javax.imageio.ImageIO;
import javax.sound.sampled.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.*;

public class Resources {

    private static final Map<String, BufferedImage> sprites = new HashMap<>();
    private static final Map<String, Sound> sounds = new HashMap<>();
    private static final Map<String, List<BufferedImage>> animations = new HashMap<>();

//    private static BufferedImage loadSprite (String path) throws IOException{
//        return read(Objects.
//                requireNonNull(GameWorld
//                        .class
//                        .getClassLoader()
//                        .getResource(path)));
//    }

    private static void initSprites(){
        try {
            Resources.sprites.put("tank1", ImageIO.read(
                    Objects.requireNonNull(GameWorld.class.getClassLoader().getResource("Tank/tank1.png"),
                            "Could not find tank1.png")));

            Resources.sprites.put("tank2", ImageIO.read(
                    Objects.requireNonNull(GameWorld.class.getClassLoader().getResource("Tank/tank2.png"),
                            "Could not find tank2.png")));

            Resources.sprites.put("bullet", ImageIO.read(
                    Objects.requireNonNull(GameWorld.class.getClassLoader().getResource("Bullet/Shell.gif"),
                            "Could not find Shell.gif")));

            Resources.sprites.put("menu", ImageIO.read(Objects.requireNonNull(Resources.class.getClassLoader().getResource("Menu/title.png"),
                    "Could not find menu")));

            Resources.sprites.put("unbreak", ImageIO.read(Objects.requireNonNull(Resources.class.getClassLoader().getResource("Wall/Wall1.gif"),
                    "Could not find unbreakable wall")));

            Resources.sprites.put("break1", ImageIO.read(Objects.requireNonNull(Resources.class.getClassLoader().getResource("Wall/Wall2.gif"),
                    "Could not find breakable wall")));

            Resources.sprites.put("floor", ImageIO.read(Objects.requireNonNull(Resources.class.getClassLoader().getResource("Background.bmp"),
                    "Could not find floor")));
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
                    Resources.class.getClassLoader().getResource("Music/music.mid"));
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

        } catch (UnsupportedAudioFileException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (LineUnavailableException e) {
            throw new RuntimeException(e);
        }
    }

    private static void initAnimations(){
//        try{
//            //% -> marker for string 0-> means to fill with zero if not enough #'s 4-> how many nums d-> int
//            String base = "expl_08_%04d.png"; //need full path name
//            List<BufferedImage> temp = new ArrayList<>();
//            for (int i = 0; i < 32; i++){
//                String fName = String.format(base, i);
//                System.out.println(fName);
////                temp.add(loadSprite(fname));
//            }
//            Resources.animations.put("shoot", temp);
//
//            base = "expl_08_%04d.png"; //need second path name
//            temp.clear();
//            for (int i = 0; i < 32; i++){
//                String fName = String.format(base, i);
//                System.out.println(fName);
////                temp.add(loadSprite(fname));
//            }
//            Resources.animations.put("collide", temp);
//
//        }catch(IOException e){
//            e.printStackTrace();
//            System.exit(-3);
//        }

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

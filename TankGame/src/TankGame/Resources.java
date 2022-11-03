package TankGame;

import TankGame.game.GameWorld;

import javax.imageio.ImageIO;
import javax.sound.sampled.Clip;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class Resources {

    private static final Map<String, BufferedImage> sprites = new HashMap<>();
    private static final Map<String, Clip> sounds = new HashMap<>();
    private static final Map<String, List<BufferedImage>> animations = new HashMap<>();

//    private static BufferedImage loadSprite (String path) throws IOException{
//        return read(Objects.requireNonNull(GameWorld.class.getClassLoader().getResource(path)));
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
                    "Could not find menu")));

            Resources.sprites.put("break1", ImageIO.read(Objects.requireNonNull(Resources.class.getClassLoader().getResource("Wall/Wall2.gif"),
                    "Could not find menu")));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void initSounds(){}

    private static void initAnimations(){}

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

}

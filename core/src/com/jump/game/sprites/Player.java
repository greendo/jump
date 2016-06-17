package com.jump.game.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.jump.game.Jumper;

import java.util.HashMap;

/**
 * Created by jc on 25.05.16.
 */
public class Player extends Objects {

    private HashMap<String, TextureRegion> texture;
    private TextureRegion currentTexture;
    public TextureRegion getCurrentTexture() {return currentTexture;}

    private int gravity = -15;

    /** GRAVITY CONSTANT */
    public static final int DEF_GRAV = -15;
    /** JUMP SPEED Oy */
    public static final int JUMP_SPD = 3;
    /** Константы для ограничения скорости */
    public static final float JUMP_X_MAX = (float) (Jumper.WIDTH / 1.3);
    public static final float JUMP_Y_MAX = (float) (Jumper.HEIGHT / 0.75);

    public Player(int world, int player, int x, int y) {
        position = new Vector2(x, y);
        speed = new Vector2(0, 0);

        texture = new HashMap<String, TextureRegion>();
        Texture valera = new Texture("world" + world + "/player" + player + ".png");
        texture.put("up", new TextureRegion(valera, 0, 0, valera.getWidth() / 3, valera.getHeight()));
        texture.put("down", new TextureRegion(valera, valera.getWidth() / 3, 0, valera.getWidth() / 3, valera.getHeight()));
        texture.put("stand", new TextureRegion(valera, 2 * valera.getWidth() / 3, 0, valera.getWidth() / 3, valera.getHeight()));
        //texture = new Texture("player.png");
        frame = new Rectangle(x, y, valera.getWidth() / 6, valera.getHeight() / 2);
        currentTexture = texture.get("stand");
    }

    @Override
    public void update(float delta) {
        if(position.y > 0)
            speed.add(0, gravity);

        speed.scl(delta);

        position.add(speed.x, speed.y);

        speed.scl(1 / delta);
        frame.setPosition(position);
    }

    public void jump(Vector2 valera) {
        if(gravity == 0) {
            speed.y = valera.y * JUMP_SPD;
            speed.x = valera.x * JUMP_SPD;
            /** Ограничитель скорости */
            if(speed.y > JUMP_Y_MAX)
                speed.y = JUMP_Y_MAX;
            if(speed.x > JUMP_X_MAX)
                speed.x = JUMP_X_MAX;
        }
    }

    public void plat(int onPl, int h, int slider) {
        /** Для определения, врезался или приземлился на платформу */
        int wut = h - 30;
        /** Держаться на платформе */
        if(onPl == 2 && position.y > wut && speed.y <= 0 && slider == 0) {
            gravity = 0;
            speed.y = 0;
            speed.x = 0;
            position.y = h - 17;
            currentTexture = texture.get("stand");
        }
        /** Для скользящих платформ */
        else if(onPl == 2 && position.y > wut && speed.y <= 0 && slider == 3) {
            gravity = 0;
            speed.y = 0;
            if(speed.x > 0)
                speed.x -= 20;
            else
                speed.x = 0;
            position.y = h - 17;
            currentTexture = texture.get("stand");
        }
        /** Врезаться в платформу, если низко летел */
        else if(onPl == 1 && position.y < h - 20 && speed.y < 0 && speed.x > 0) {
            position.x -= currentTexture.getTexture().getWidth() / 16;
            speed.x = -5;
            currentTexture = texture.get("down");
        }
        /** Установить гравитацию при прыжке */
        else if(position.y > h) {
            gravity = DEF_GRAV;
            currentTexture = texture.get("up");
        }
        /** Спрыгнуть (осталось от раннера) */
        else {
            gravity = DEF_GRAV;
            if(speed.y == 0)
                speed.y = -450;
            currentTexture = texture.get("down");
        }
    }

    public int collides(PlatformContainer platform) {
        if(platform.getPlatform().getFrameLow().overlaps(frame))
            return 1;
        if(platform.getPlatform().getFrame().overlaps(frame))
            return 2;
        return 0;
    }
}

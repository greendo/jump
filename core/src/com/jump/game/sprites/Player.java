package com.jump.game.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.jump.game.sprites.platforms.Platform;

/**
 * Created by jc on 25.05.16.
 */
public class Player extends Objects {

    private int gravity = -15;

    /** GRAVITY CONSTANT */
    public static final int DEF_GRAV = -15;
    /** JUMP SPEED Oy */
    public static final int JUMP_SPD = 3;

    public Player(int x, int y) {
        position = new Vector2(x, y);
        speed = new Vector2(0, 0);
        texture = new Texture("player.png");
        frame = new Rectangle(x, y, texture.getWidth(), texture.getHeight());
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
        }
        /** Для скользящих платформ */
        else if(onPl == 2 && position.y > wut && speed.y <= 0 && slider == 3) {
            gravity = 0;
            speed.y = 0;
            if(speed.x > 0)
                speed.x -= 10;
            else
                speed.x = 0;
            position.y = h - 17;
        }
        /** Врезаться в платформу, если низко летел */
        else if(onPl == 1 && position.y < h - 20 && speed.y < 0 && speed.x > 0) {
            position.x -= texture.getWidth() / 2;
            speed.x = -5;
        }
        /** Установить гравитацию при прыжке */
        else if(position.y > h)
            gravity = DEF_GRAV;
        /** Спрыгнуть (осталось от раннера) */
        else {
            gravity = DEF_GRAV;
            if(speed.y == 0)
                speed.y = -450;
        }
    }

    public int collides(PlatformContainer platform) {
        if(platform.getFrameLow().overlaps(frame))
            return 1;
        if(platform.getFrame().overlaps(frame))
            return 2;
        return 0;
    }
}

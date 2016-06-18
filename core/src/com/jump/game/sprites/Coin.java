package com.jump.game.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.jump.game.sprites.platforms.Platform;

/**
 * Created by jc on 18.06.16.
 */
public class Coin extends Objects {

    private int gravity = -15;
    public static int size = 40;
    public int getSize() {return size;}

    public Coin(int x, int y) {
        texture = new Texture("coin.png");
        frame = new Rectangle(x, y, size, size);
        position = new Vector2(x, y);
        speed = new Vector2(0, 200);
    }

    @Override
    public void update(float delta) {}
    public void update(float delta, Platform platform) {
        speed.add(0, gravity);
        speed.scl(delta);
        position.add(speed.x, speed.y);
        speed.scl(1 / delta);

        if(collides(platform)) {
            speed.y = 200;
        }
        frame.setPosition(position);
    }

    public boolean collides(Platform platform) {
        if(platform.getFrame().overlaps(frame))
            return true;
        return false;
    }
}

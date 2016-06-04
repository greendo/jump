package com.jump.game.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by jc on 26.05.16.
 */
public abstract class Objects {

    protected Vector2 position;
    protected Vector2 speed;
    protected Texture texture;
    protected Rectangle frame;

    public Vector2 getPosition() {return position;}
    public Texture getTexture() {return texture;}
    public Rectangle getFrame() {return frame;}
    public void reposition(float x) {position.set(x, 0);}
    public float getSpeed() {return speed.x;}

    public abstract void update(float delta);
}

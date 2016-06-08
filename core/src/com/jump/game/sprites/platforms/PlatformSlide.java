package com.jump.game.sprites.platforms;

/**
 * Created by jc on 07.06.16.
 */
public class PlatformSlide extends Platform {

    public PlatformSlide(float x, String worldName) {
        super(x, worldName);
    }

    @Override
    public void update(float delta) {
        position.add(speed.x * delta, 0);
        frame.setPosition(position.x + GAP, height - 15);
        frameLow.setPosition(position.x + GAP, 0);
    }
}

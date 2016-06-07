package com.jump.game.sprites.platforms;

/**
 * Created by jc on 07.06.16.
 */
public class PlatformGrow extends Platform {

    public PlatformGrow(float x, String worldName) {
        super(x, worldName);
        speed.y = 1;
    }

    @Override
    public void update(float delta) {
        position.add(speed.x * delta, 0);
        if(touchEvent)
            height += speed.y;
        frame.setPosition(position.x + GAP, height - 15);
    }
}

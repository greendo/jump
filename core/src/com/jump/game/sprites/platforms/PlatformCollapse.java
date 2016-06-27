package com.jump.game.sprites.platforms;

import com.jump.game.Jumper;
import com.jump.game.sprites.World;

/**
 * Created by jc on 07.06.16.
 */
public class PlatformCollapse extends Platform {

    public PlatformCollapse(float x, String worldName) {
        super(x, worldName);
        texture = World.pCollapse;
        speed.y = -1;
    }

    @Override
    public void update(float delta) {
        position.add(speed.x * delta, 0);
        if(touchEvent)
            height += speed.y;
        frame.setPosition(position.x + GAP, height - 15);
        frameLow.setPosition(position.x + GAP, height - Jumper.HEIGHT / 4 - lowGap);
    }
}

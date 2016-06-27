package com.jump.game.sprites.platforms;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.utils.TiledDrawable;
import com.jump.game.sprites.World;

/**
 * Created by jc on 16.06.16.
 */
public class PlatformSimple extends Platform {

    public PlatformSimple(float x, String worldName, int playerIndex) {
        super(x, worldName);

        int fixWidth = width / 100;
        if(fixWidth == 1)
            fixWidth = 2;
        int fixHeight = height / 100;

        width = fixWidth * 100;
        height = fixHeight * 100;

        frame = new Rectangle(x + GAP, height - 15,
                width - 2 * GAP, 1);
        frameLow = new Rectangle(x + GAP, 0, width - 2 * GAP, height - 30);

        tile = World.tSimple;
        texture = World.pSimple;
    }

    public TiledDrawable getTile() {return tile;}
}

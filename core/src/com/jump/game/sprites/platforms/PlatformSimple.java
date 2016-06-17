package com.jump.game.sprites.platforms;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.utils.TiledDrawable;

/**
 * Created by jc on 16.06.16.
 */
public class PlatformSimple extends Platform {

    public PlatformSimple(float x, String worldName) {
        super(x, worldName);

        Texture valera = new Texture(worldName + "/platUnique1.png");

        int fixWidth = width / valera.getWidth();
        int fixHeight = height / valera.getHeight();

        width = fixWidth * valera.getWidth();
        height = fixHeight * valera.getHeight();

        frame = new Rectangle(x + GAP, height - 15,
                width - 2 * GAP, 1);
        frameLow = new Rectangle(x + GAP, 0, width - 2 * GAP, height - 30);

        TextureRegion mosaic = new TextureRegion(valera);
        tile = new TiledDrawable(mosaic);

        texture = new Texture(worldName + "/hatUnique1.png");
    }

    public TiledDrawable getTile() {return tile;}
}

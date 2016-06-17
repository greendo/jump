package com.jump.game.sprites;

import com.jump.game.sprites.platforms.Platform;
import com.jump.game.sprites.platforms.PlatformCollapse;
import com.jump.game.sprites.platforms.PlatformGrow;
import com.jump.game.sprites.platforms.PlatformSimple;
import com.jump.game.sprites.platforms.PlatformSlide;

import java.util.Random;

/**
 * Created by jc on 07.06.16.
 */
public class PlatformContainer {

    private Platform platform;
    private String worldName;

    public PlatformContainer(float x, String worldName) {
        this.worldName = worldName;

        platform = new PlatformSimple(x, worldName);
    }

    public void reInit(float x) {
        Random random = new Random();

        switch (random.nextInt(4)) {
            case 0:
                platform = new PlatformSimple(x, worldName);
                break;
            case 1:
                platform = new PlatformCollapse(x, worldName);
                break;
            case 2:
                platform = new PlatformGrow(x, worldName);
                break;
            case 3:
                platform = new PlatformSlide(x, worldName);
                break;
        }
    }

    public Platform getPlatform() {return platform;}
}

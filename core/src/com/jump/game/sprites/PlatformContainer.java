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
    private int playerIndex;
    private Coin coin;

    public PlatformContainer(float x, String worldName, int playerIndex) {
        this.worldName = worldName;
        this.playerIndex = playerIndex;
        coin = null;
        platform = new PlatformSimple(x, worldName, playerIndex);
    }

    public void reInit(float x) {
        Random random = new Random();

        switch (random.nextInt(4)) {
            case 0:
                platform = new PlatformSimple(x, worldName, playerIndex);
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

        if(random.nextInt(10) == 5)
            coin = new Coin((int) (platform.getPosition().x + platform.getWidth() / 2),
                    platform.getHeight() + Coin.size * 4);
        else
            coin = null;
    }

    public Platform getPlatform() {return platform;}
    public Coin getCoin() {return coin;}
    public void setCoinToNull() {coin = null;}
}

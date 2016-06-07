package com.jump.game.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.jump.game.sprites.platforms.Platform;
import com.jump.game.sprites.platforms.PlatformCollapse;
import com.jump.game.sprites.platforms.PlatformGrow;
import com.jump.game.sprites.platforms.PlatformSlide;

import java.util.Random;

/**
 * Created by jc on 07.06.16.
 */
public class PlatformContainer {

    private Platform platform;
    private String worldName;
    private String type;

    public PlatformContainer(float x, String worldName) {
        this.worldName = worldName;

        platform = new Platform(x, worldName);
        type = "platform";
    }

    public void reInit(float x) {
        Random random = new Random();

        switch (random.nextInt(4)) {
            case 0:
                platform = new Platform(x, worldName);
                type = "platform";
                break;
            case 1:
                platform = new PlatformCollapse(x, worldName);
                type = "platformCollapse";
                break;
            case 2:
                platform = new PlatformGrow(x, worldName);
                type = "platformGrow";
                break;
            case 3:
                platform = new PlatformSlide(x, worldName);
                type = "platformSlide";
                break;
        }
    }

    public Vector2 getPosition() {return platform.getPosition();}
    public int getWidth() {return platform.getWidth();}
    public int getHole() {return platform.getHole();}
    public Rectangle getFrame() {return platform.getFrame();}
    public Texture getTexture() {return platform.getTexture();}
    public int getHeight() {return platform.getHeight();}
    public String getType() {return type;}
    public void update(float delta) {platform.update(delta);}
    public void setTouchEvent() {platform.setTouchEvent();}
}

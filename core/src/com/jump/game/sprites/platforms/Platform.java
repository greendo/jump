package com.jump.game.sprites.platforms;

/**
 * Created by jc on 07.06.16.
 */

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.jump.game.Jumper;
import com.jump.game.sprites.Objects;

import java.util.Random;

/** Вложеный приватный класс для платформ */
public class Platform extends Objects {

    /** Контроль интерактивной части текстур*/
    protected int GAP = 15;

    /** Минимальная и максимальная ширина для платформ и ям */
    private int MAX_PLAT_WIDTH = Jumper.WIDTH / 4;
    private int MIN_PLAT_WIDTH = Jumper.WIDTH / 5;
    private int MAX_HOLE_WIDTH = Jumper.WIDTH / 6;
    private int MIN_HOLE_WIDTH = Jumper.WIDTH / 7;

    /** Ширина для платформ и ям */
    private int width;
    private int hole;
    /** Высота для платформ */
    protected int height;

    /** Фрейм для низкого врезания */
    protected Rectangle frameLow;

    /** Ворлднэйм */
    private String worldName;
    private Random rand;

    /** Для платформ, которые имеют ивент после приземления игрока */
    protected boolean touchEvent;

    public Platform(float x, String worldName) {
        touchEvent = false;
        this.worldName = worldName;
        Random rand = new Random();
        texture = new Texture(worldName + "/plat1.png");
        position = new Vector2(x, 0);
        speed = new Vector2(0, 0);

        width = rand.nextInt(MAX_PLAT_WIDTH) + MIN_PLAT_WIDTH;
        hole = rand.nextInt(MAX_HOLE_WIDTH) + MIN_HOLE_WIDTH;
        height = Jumper.HEIGHT / 4;

        frame = new Rectangle(x + GAP, Jumper.HEIGHT / 4 - 15,
                width - 2 * GAP, 1);
        frameLow = new Rectangle(x + GAP, 0, width - 2 * GAP, Jumper.HEIGHT / 4 - 30);
    }

    @Override
    public void update(float delta) {
        position.add(speed.x * delta, speed.y * delta);
        frame.setPosition(position.x + GAP, height - 15);
        frameLow.setPosition(position.x + GAP, 0);
    }

    public int getHole() {return hole;}
    public int getWidth() {return width;}
    public int getHeight() {return height;}
    public void setTouchEvent() {touchEvent = true;}
    public Rectangle getFrameLow() {return frameLow;}
}
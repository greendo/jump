package com.jump.game.sprites;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.jump.game.Jumper;

import java.util.Random;

/**
 * Created by jc on 02.06.16.
 */
public class World {

    /** Кол-во платформ и фонов в памяти */
    public static final int PLAT_COUNT = 3;

    /** Имя мира */
    private String worldName;
    private Texture texture;
    private Random rand;
    protected static boolean debug;
    /** Платформы */
    private Array<WorldPlatform> platforms;

    public World(String worldName, boolean debug) {
        this.worldName = worldName;
        texture = new Texture(worldName + "/back1.png");
        rand = new Random();
        platforms = new Array<WorldPlatform>();
        this.debug = debug;

        for(int i = 0; i < PLAT_COUNT; i++) {
            if(i == 0)
                platforms.add(new WorldPlatform(0));
            else
                platforms.add(new WorldPlatform(platforms.get(i - 1).getPosition().x +
                + platforms.get(i - 1).getWidth() +
                + platforms.get(i - 1).getHole()));
        }
    }






    /** Вложеный приватный класс для платформ */
    private class WorldPlatform extends Objects {

        /** Контроль интерактивной части текстур*/
        private int GAP = 15;

        /** Минимальная и максимальная ширина для платформ и ям */
        private int MAX_WIDTH = Jumper.WIDTH / 3;
        private int MIN_WIDTH = Jumper.WIDTH / 5;

        /** Ширина для платформ и ям */
        private int width;
        private int hole;

        private WorldPlatform(float x) {
            texture = new Texture(worldName + "/plat1.png");
            position = new Vector2(x, 0);
            speed = new Vector2(0, 0);

            width = rand.nextInt(MAX_WIDTH) + MIN_WIDTH;
            hole = rand.nextInt(MAX_WIDTH) + MIN_WIDTH;

            frame = new Rectangle(x + GAP, Jumper.HEIGHT / 4 - 15,
                    width - 2 * GAP, 1);
        }

        @Override
        public void update(float delta) {
            position.add(speed.x * delta, speed.y);
            frame.setPosition(position.x + GAP, Jumper.HEIGHT / 4 - 15);
        }

        public void reInit(float x) {
            texture = new Texture(worldName + "/plat1.png");
            position = new Vector2(x, 0);
            speed = new Vector2(0, 0);

            width = rand.nextInt(MAX_WIDTH) + MIN_WIDTH;
            hole = rand.nextInt(MAX_WIDTH) + MIN_WIDTH;

            frame = new Rectangle(x + GAP, Jumper.HEIGHT / 4 - 15,
                    width - 2 * GAP, 1);
        }

        public int getHole() {return hole;}
        public int getWidth() {return width;}
    }







    /** Репозиция и реинициализация обьектов, если они ушли за экран влево */
    public boolean update(float delta, Player player, OrthographicCamera camera) {
        player.update(delta);

        /** Траблы с камерой */
        if(player.position.x - camera.position.x < Jumper.WIDTH / 7)
            camera.position.x += 1;
        else
            camera.position.x += 5;

        boolean onPl = false;

        /** Траблы с рандомом */
        for(int i = 0; i < PLAT_COUNT; i++)
            if(platforms.get(i).getWidth() + platforms.get(i).getPosition().x < camera.position.x - Jumper.WIDTH / 2) {
                if(i == 0)
                    platforms.get(i).reInit(platforms.get(PLAT_COUNT - 1).getPosition().x +
                            platforms.get(PLAT_COUNT - 1).getWidth() +
                            platforms.get(PLAT_COUNT - 1).getHole());
                else
                    platforms.get(i).reInit(platforms.get(i - 1).getPosition().x +
                            platforms.get(i - 1).getWidth() +
                            platforms.get(i - 1).getHole());
            }

        for(World.WorldPlatform wm : platforms)
            if(player.collides(wm.getFrame()))
                onPl = true;

        player.plat(onPl, Jumper.HEIGHT);

        /** check if dead */
        if(player.getPosition().y <= 0 || camera.position.x > player.position.x + Jumper.WIDTH)
            return true;

        return false;
    }

    /** Отрисовка после репозиции */
    public void render(SpriteBatch sb, BitmapFont font, OrthographicCamera camera, Player player) {

        /** Задний фон */
        sb.draw(texture, camera.position.x - camera.viewportWidth / 2, 0, Jumper.WIDTH, Jumper.HEIGHT);

        for(World.WorldPlatform wm : platforms) {
            sb.draw(wm.getTexture(), wm.getPosition().x, wm.getPosition().y, wm.getWidth(), Jumper.HEIGHT / 4);
        }

        if(debug)
            debug(sb, font, camera, player);
    }

    private void debug(SpriteBatch sb, BitmapFont font, OrthographicCamera camera, Player player) {
        font.draw(sb, " cameraX: " + camera.position.x,
                camera.position.x - camera.viewportWidth / 2, 60);

        for(int i = 0; i < PLAT_COUNT; i++)
            font.draw(sb, "textX: " + platforms.get(i).getFrame().getX(), platforms.get(i).getPosition().x, 210);
    }

    /** Метод для чистки памяти, юзаем сами при паузе или вызывается при звонке етц. */
    public void dispose() {
        texture.dispose();
        for(WorldPlatform wp : platforms)
            wp.getTexture().dispose();
    }
}

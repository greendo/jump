package com.jump.game.sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.jump.game.Jumper;
import com.jump.game.sprites.platforms.PlatformSimple;
import com.jump.game.sprites.platforms.PlatformSlide;

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
    private Array<PlatformContainer> platforms;

    public World(String worldName, boolean debug) {
        this.worldName = worldName;
        texture = new Texture(worldName + "/back1.png");
        rand = new Random();
        platforms = new Array<PlatformContainer>();
        this.debug = debug;

        for(int i = 0; i < PLAT_COUNT; i++) {
            if(i == 0)
                platforms.add(new PlatformContainer(0, worldName));
            else
                platforms.add(new PlatformContainer(platforms.get(i - 1).getPlatform().getPosition().x +
                + platforms.get(i - 1).getPlatform().getWidth() +
                + platforms.get(i - 1).getPlatform().getHole(),
                        worldName));
        }
    }







    /** Репозиция и реинициализация обьектов, если они ушли за экран влево */
    public boolean update(float delta, Player player, OrthographicCamera camera) {
        player.update(delta);

        /** На платформе ли */
        int onPl = 0;
        int height = Jumper.HEIGHT / 4;
        /** На скользящей платформе ли */
        int slider = 0;

        for(int i = 0; i < PLAT_COUNT; i++) {
            /** Тут мы ивентим рост или снижение платформы */
            if(player.position.x - camera.position.x < Jumper.WIDTH / 7)
                platforms.get(i).getPlatform().update(delta);
            /** Тут переинитим платформу, слева от экрана */
            if (platforms.get(i).getPlatform().getWidth() + platforms.get(i).getPlatform().getPosition().x < camera.position.x - Jumper.WIDTH / 2) {
                if (i == 0)
                    platforms.get(i).reInit(platforms.get(PLAT_COUNT - 1).getPlatform().getPosition().x +
                            platforms.get(PLAT_COUNT - 1).getPlatform().getWidth() +
                            platforms.get(PLAT_COUNT - 1).getPlatform().getHole());
                else
                    platforms.get(i).reInit(platforms.get(i - 1).getPlatform().getPosition().x +
                            platforms.get(i - 1).getPlatform().getWidth() +
                            platforms.get(i - 1).getPlatform().getHole());
            }
        }

        for(PlatformContainer pc : platforms)
            if(player.collides(pc) != 0) {
                onPl = player.collides(pc);
                height = pc.getPlatform().getHeight();
                pc.getPlatform().setTouchEvent();
                if(pc.getPlatform() instanceof PlatformSlide)
                    slider = 3;
            }

        player.plat(onPl, height, slider);

        /** Траблы с камерой
         if(player.position.x - camera.position.x < Jumper.WIDTH / 7)
         camera.position.x += 2;
         else
         camera.position.x += 5;*/
        if(onPl != 0 && camera.position.x - player.position.x < Jumper.WIDTH / 7)
            camera.position.x += 5;

        /** check if dead */
        if(player.getPosition().y <= 0 || camera.position.x > player.position.x + Jumper.WIDTH)
            return true;

        return false;
    }

    /** Отрисовка после репозиции */
    public void render(SpriteBatch sb, BitmapFont font, OrthographicCamera camera, Player player) {

        /** Задний фон */
        sb.draw(texture, camera.position.x - camera.viewportWidth / 2, 0, Jumper.WIDTH, Jumper.HEIGHT);

        for(PlatformContainer wm : platforms) {
            if(wm.getPlatform() instanceof PlatformSimple) {
                try {
                    wm.getPlatform().getTile().draw(sb, wm.getPlatform().getPosition().x, wm.getPlatform().getPosition().y,
                            wm.getPlatform().getWidth(), wm.getPlatform().getHeight());

                    sb.draw(wm.getPlatform().getTexture(), wm.getPlatform().getPosition().x - 10, wm.getPlatform().getHeight() + 10 - wm.getPlatform().getTexture().getHeight(),
                            wm.getPlatform().getWidth() + 20, wm.getPlatform().getTexture().getHeight());
                }
                catch (NullPointerException e) {
                    Gdx.app.error("Platform", "indi platform method was called from platform: " +
                            wm.getPlatform().getClass().getName(), e);
                }
            }
            else
                sb.draw(wm.getPlatform().getTexture(), wm.getPlatform().getPosition().x, wm.getPlatform().getPosition().y,
                        wm.getPlatform().getWidth(), wm.getPlatform().getHeight());
        }

        if(debug)
            debug(sb, font, camera, player);
    }

    /** Для дебага же */
    private void debug(SpriteBatch sb, BitmapFont font, OrthographicCamera camera, Player player) {
        font.draw(sb, " cameraX: " + camera.position.x,
                camera.position.x - camera.viewportWidth / 2, 60);
        font.draw(sb, " playerX: " + player.position.x,
                camera.position.x - camera.viewportWidth / 2, 90);

        for(int i = 0; i < PLAT_COUNT; i++) {
            font.draw(sb, "type: " + platforms.get(i).getPlatform().getClass().getName(),
                    platforms.get(i).getPlatform().getPosition().x, 240);
        }
    }

    /** Метод для чистки памяти, юзаем сами при паузе или вызывается при звонке етц. */
    public void dispose() {
        texture.dispose();
        for(PlatformContainer wp : platforms)
            wp.getPlatform().getTexture().dispose();
    }
}

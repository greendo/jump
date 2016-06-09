package com.jump.game.sprites;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
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
                platforms.add(new PlatformContainer(platforms.get(i - 1).getPosition().x +
                + platforms.get(i - 1).getWidth() +
                + platforms.get(i - 1).getHole(),
                        worldName));
        }
    }







    /** Репозиция и реинициализация обьектов, если они ушли за экран влево */
    public boolean update(float delta, Player player, OrthographicCamera camera) {
        player.update(delta);

        /** Траблы с камерой */
        if(player.position.x - camera.position.x < Jumper.WIDTH / 7)
            camera.position.x += 2;
        else
            camera.position.x += 5;

        /** На платформе ли */
        int onPl = 0;
        int height = Jumper.HEIGHT / 4;
        /** На скользящей платформе ли */
        int slider = 0;

        for(int i = 0; i < PLAT_COUNT; i++) {
            /** Тут мы ивентим рост или снижение платформы */
            if(player.position.x - camera.position.x < Jumper.WIDTH / 7)
                platforms.get(i).update(delta);
            /** Тут переинитим платформу, слева от экрана */
            if (platforms.get(i).getWidth() + platforms.get(i).getPosition().x < camera.position.x - Jumper.WIDTH / 2) {
                if (i == 0)
                    platforms.get(i).reInit(platforms.get(PLAT_COUNT - 1).getPosition().x +
                            platforms.get(PLAT_COUNT - 1).getWidth() +
                            platforms.get(PLAT_COUNT - 1).getHole());
                else
                    platforms.get(i).reInit(platforms.get(i - 1).getPosition().x +
                            platforms.get(i - 1).getWidth() +
                            platforms.get(i - 1).getHole());
            }
        }

        for(PlatformContainer pc : platforms)
            if(player.collides(pc) != 0) {
                onPl = player.collides(pc);
                height = pc.getHeight();
                pc.setTouchEvent();
                if(pc.getType() == "platformSlide")
                    slider = 3;
            }

        player.plat(onPl, height, slider);

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
            sb.draw(wm.getTexture(), wm.getPosition().x, wm.getPosition().y, wm.getWidth(), wm.getHeight());
        }

        if(debug)
            debug(sb, font, camera, player);
    }

    private void debug(SpriteBatch sb, BitmapFont font, OrthographicCamera camera, Player player) {
        font.draw(sb, " cameraX: " + camera.position.x,
                camera.position.x - camera.viewportWidth / 2, 60);

        for(int i = 0; i < PLAT_COUNT; i++) {
            font.draw(sb, "textX: " + platforms.get(i).getFrame().getX(), platforms.get(i).getPosition().x, 210);
            font.draw(sb, "type: " + platforms.get(i).getType(), platforms.get(i).getPosition().x, 240);

            font.draw(sb, "frameX: " + platforms.get(i).getFrame().x, platforms.get(i).getPosition().x, 270);
            font.draw(sb, "frameY: " + platforms.get(i).getFrame().y, platforms.get(i).getPosition().x, 300);
        }

        font.draw(sb, "playerSpdX: " + player.speed.x, camera.position.x + 50, 300);
        font.draw(sb, "playerSpdY: " + player.speed.y, camera.position.x + 50, 330);

        font.draw(sb, "width: " + Jumper.WIDTH, camera.position.x + 50, 360);
        font.draw(sb, "height: " + Jumper.HEIGHT, camera.position.x + 50, 390);
    }

    /** Метод для чистки памяти, юзаем сами при паузе или вызывается при звонке етц. */
    public void dispose() {
        texture.dispose();
        for(PlatformContainer wp : platforms)
            wp.getTexture().dispose();
    }
}

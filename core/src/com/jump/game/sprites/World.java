package com.jump.game.sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.utils.TiledDrawable;
import com.badlogic.gdx.utils.Array;
import com.jump.game.controllers.ActionController;
import com.jump.game.Jumper;
import com.jump.game.sprites.platforms.PlatformSimple;
import com.jump.game.sprites.platforms.PlatformSlide;


/**
 * Created by jc on 02.06.16.
 */
public class World {

    /** Кол-во платформ в памяти */
    public static final int PLAT_COUNT = 3;

    /** Счет, рекорд и монеты */
    private int score = 0;
    public int getScore() {return score;}
    private int record = Jumper.gameVars.getRecord();
    private int coins = Jumper.gameVars.getCoins();
    public int getCoins() {return coins;}

    /** Мб перестанет вылетать */
    public static Texture pSimple, pSlide, pCollapse, pGrow, coin;
    public static TiledDrawable tSimple;

    /** Имя мира */
    private Texture texture;
    protected static boolean debug;
    /** Платформы */
    private Array<PlatformContainer> platforms;

    public World(String worldName, boolean debug, int playerIndex) {
        texture = new Texture(worldName + "/back.png");
        platforms = new Array<PlatformContainer>();
        this.debug = debug;


        tSimple = new TiledDrawable(
                new TextureRegion(new Texture(worldName + "/platUnique" + playerIndex + ".png"))
        );
        pSimple = new Texture(worldName + "/hatUnique" + playerIndex + ".png");
        pGrow = new Texture(worldName + "/grow.png");
        pCollapse = new Texture(worldName + "/collapse.png");
        pSlide = new Texture(worldName + "/slide.png");
        coin = new Texture("coin.png");



        for(int i = 0; i < PLAT_COUNT; i++) {
            if(i == 0)
                platforms.add(new PlatformContainer(0, worldName, playerIndex));
            else
                platforms.add(new PlatformContainer(platforms.get(i - 1).getPlatform().getPosition().x +
                + platforms.get(i - 1).getPlatform().getWidth() +
                + platforms.get(i - 1).getPlatform().getHole(),
                        worldName, playerIndex));
        }
    }







    /** Репозиция и реинициализация обьектов, если они ушли за экран влево */
    public boolean update(float delta, Player player, OrthographicCamera camera) {
        player.update(delta);
        for(PlatformContainer pc : platforms) {
            if(pc.getCoin() != null)
                pc.getCoin().update(delta, pc.getPlatform());
        }

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
            if(player.collidesPlat(pc) != 0) {
                onPl = player.collidesPlat(pc);
                height = pc.getPlatform().getHeight();
                pc.getPlatform().setTouchEvent();
                /** Для скользящих */
                if(pc.getPlatform() instanceof PlatformSlide)
                    slider = 3;
                /** Для НЕдублирования очков */
                if(!pc.getPlatform().mount && player.collidesPlat(pc) == 2) {
                    pc.getPlatform().mount = true;
                    score++;
                }
                /** Для скольжения */
                if(slider == 3 && player.speed.x != 0)
                    player.setCurrentTexture("slide");
                /** Для установки анимации на платформе, если палец не на экране */
                else if(!ActionController.touched)
                    player.setCurrentTexture("stand");
                /** Для монет */
                if(pc.getCoin() != null)
                    if(player.collidesCoin(pc)) {
                        pc.setCoinToNull();
                        coins++;
                    }
            }

        player.plat(onPl, height, slider);

        /** Траблы с камерой
         if(player.position.x - camera.position.x < Jumper.WIDTH / 7)
         camera.position.x += 2;
         else
         camera.position.x += 5;*/
        if(camera.position.x - player.position.x < Jumper.WIDTH / 3)
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

        for(PlatformContainer pc : platforms) {
            if(pc.getPlatform() instanceof PlatformSimple) {
                try {
                    pc.getPlatform().getTile().draw(sb, pc.getPlatform().getPosition().x, pc.getPlatform().getPosition().y,
                            pc.getPlatform().getWidth(), pc.getPlatform().getHeight());

                    sb.draw(pc.getPlatform().getTexture(), pc.getPlatform().getPosition().x - 10, pc.getPlatform().getHeight() + 10 - pc.getPlatform().getTexture().getHeight(),
                            pc.getPlatform().getWidth() + 20, pc.getPlatform().getTexture().getHeight());
                }
                catch (NullPointerException e) {
                    Gdx.app.error("Platform", "indi platform method was called from platform: " +
                            pc.getPlatform().getClass().getName(), e);
                }
            }
            else
                try {
                    sb.draw(pc.getPlatform().getTexture(), pc.getPlatform().getPosition().x, pc.getPlatform().getPosition().y,
                            pc.getPlatform().getWidth(), pc.getPlatform().getHeight());
                }
                catch (NullPointerException e) {
                    Gdx.app.error("Platform", "couldn't render " + pc.getPlatform().getClass().getName(), e);
                }

            if(pc.getCoin() != null)
                sb.draw(pc.getCoin().getTexture(), pc.getCoin().getPosition().x, pc.getCoin().getPosition().y,
                        pc.getCoin().getSize(), pc.getCoin().getSize());
        }

        /** Счет, монеты и рекорд */
        font.draw(sb, "score: " + score, camera.position.x - Jumper.WIDTH / 2 + 10, Jumper.HEIGHT - 10);
        if(record < score)
            record = score;
        font.draw(sb, "record: " + record, camera.position.x - Jumper.WIDTH / 2 + 10, Jumper.HEIGHT - 40);
        font.draw(sb, "coins: " + coins, camera.position.x + Jumper.WIDTH / 2 - 60, Jumper.HEIGHT - 10);

        if(debug)
            debug(sb, font, camera, player);
    }

    /** Для дебага же */
    private void debug(SpriteBatch sb, BitmapFont font, OrthographicCamera camera, Player player) {
        font.draw(sb, " cameraX: " + camera.position.x,
                camera.position.x - camera.viewportWidth / 2, 60);
        font.draw(sb, " playerX: " + player.position.x,
                camera.position.x - camera.viewportWidth / 2, 90);
        font.draw(sb, " playerY: " + player.position.y,
                camera.position.x - camera.viewportWidth / 2, 120);
        font.draw(sb, " playerSpdX: " + player.speed.x,
                camera.position.x - camera.viewportWidth / 2, 150);
        font.draw(sb, " playerSpdY: " + player.speed.y,
                camera.position.x - camera.viewportWidth / 2, 180);

        for(int i = 0; i < PLAT_COUNT; i++) {
            font.draw(sb, "type: " + platforms.get(i).getPlatform().getClass().getName(),
                    platforms.get(i).getPlatform().getPosition().x, 240);

            font.draw(sb, "posX: " + platforms.get(i).getPlatform().position.x,
                    platforms.get(i).getPlatform().getPosition().x, 270);

            if(platforms.get(i).getCoin() != null) {
                font.draw(sb, "coinX: " + platforms.get(i).getCoin().getPosition().x,
                        camera.position.x - camera.viewportWidth / 2 + i * 100, 300);
                font.draw(sb, "coinY: " + platforms.get(i).getCoin().getPosition().y,
                        camera.position.x - camera.viewportWidth / 2 + i * 100, 330);
            }
        }
    }

    /** Метод для чистки памяти, юзаем сами при паузе или вызывается при звонке етц. */
    public void dispose() {
        texture.dispose();
        pSimple.dispose();
        pSlide.dispose();
        pCollapse.dispose();
        pGrow.dispose();
        coin.dispose();
        tSimple.getRegion().getTexture().dispose();
    }
}

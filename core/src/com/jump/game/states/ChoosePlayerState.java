package com.jump.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.jump.game.ChoosePlayerController;
import com.jump.game.Jumper;
import com.jump.game.sprites.Player;

import java.io.File;
import java.util.HashMap;

/**
 * Created by jc on 18.06.16.
 */
public class ChoosePlayerState extends State {

    private HashMap<Integer, WorldSelectPlayer> worlds;
    private int currentWorld = 1;

    public ChoosePlayerState(StateManager sm) {
        super(sm);
        /** count all worlds without static 1 file*/
        int dirsCount = 1;

        worlds = new HashMap<Integer, WorldSelectPlayer>();
        for(int i = 1; i <= dirsCount; i++)
            worlds.put(i, new WorldSelectPlayer(i));


        camera.setToOrtho(false, Jumper.WIDTH, Jumper.HEIGHT);

        Gdx.input.setInputProcessor(new ChoosePlayerController(worlds.get(currentWorld).frames, this));
    }

    public HashMap<Integer, Rectangle> getFrames() {return worlds.get(currentWorld).frames;}
    public void nextWorld() {currentWorld++;}
    public void prevWorld() {currentWorld--;}


    /** Ширина картинок = 100, высота = 100 */
    private int width = 100;
    private int height = 100;


    /** Метод для создания мира */
    public void createWorld(int playerIndex) {
        dispose();
        sManager.init(new GameState(sManager, currentWorld, playerIndex));
    }


    private class WorldSelectPlayer {

        private HashMap<Integer, TextureRegion> players;
        private HashMap<Integer, Rectangle> frames;
        private Texture backChoise;

        public WorldSelectPlayer(int worldIndex) {
            String worldName = "world" + worldIndex + '/';
            players = new HashMap<Integer, TextureRegion>();
            frames = new HashMap<Integer, Rectangle>();
            backChoise = new Texture(worldName + "backChoise.png");

            /** count player characters */
            int playerCount = 3;

            int valeraa = (Jumper.WIDTH - 5 * width) / 6;

            for(int i = 1; i <= playerCount; i++) {
                Texture valera = new Texture(worldName + "player" + i + ".png");
                players.put(i, new TextureRegion(valera, valera.getWidth() / 4, valera.getHeight()));

                if(i <= 5)
                    frames.put(i, new Rectangle(i * valeraa + (i - 1) * width, 150, width, height));
                else
                    frames.put(i, new Rectangle((i - 5) * valeraa + (i - 1) * width, 300, width, height));
            }
        }

        public HashMap<Integer, Rectangle> getFrames() {return frames;}
    }









    @Override
    protected void handleInput() {

    }

    @Override
    public void update(float delta) {

    }

    @Override
    public void render(SpriteBatch sb, BitmapFont font) {
        //sb.setProjectionMatrix(camera.combined);
        sb.begin();

        /** Фон для выбора */
        sb.draw(worlds.get(currentWorld).backChoise, camera.position.x - camera.viewportWidth / 2, 0,
                Jumper.WIDTH, Jumper.HEIGHT);

        for(int i = 1; i <= worlds.get(currentWorld).players.size(); i++) {
            sb.draw(worlds.get(currentWorld).players.get(i),
                    worlds.get(currentWorld).frames.get(i).getX(),
                    worlds.get(currentWorld).frames.get(i).getY() + height / 2,
                        width, height);
        }

        /** Картинки для смены мира будут тут */

        sb.end();
    }

    @Override
    public void dispose() {
        for(int i = 1; i <= worlds.size(); i++) {
            worlds.get(i).backChoise.dispose();
            for(int y = 1; y <= worlds.get(i).players.size(); y++) {
                worlds.get(i).players.get(y).getTexture().dispose();
            }
        }
    }

    @Override
    public void continueGame() {}
}
